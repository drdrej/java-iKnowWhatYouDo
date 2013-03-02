package com.significantfiles.debug.connector;

import java.util.List;
import java.util.Map;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.tools.jdi.SocketAttachingConnector;

public class JVMConnectorFactory {

	
	public static void listConnectors() {
		System.out.println("> Your tools.jar supports following connections: ");
		System.out.println("  -----------------------------------------------");
		
		final VirtualMachineManager vmManager = Bootstrap
				.virtualMachineManager();

		final List<Connector> allConnectors = vmManager.allConnectors();
		for (final Connector connector : allConnectors) {
			final String description = connector.description();
		
			System.out.println("> connector[= " + connector.getClass()
					+ "].description = " + description);

			final Map<String, Argument> defaultArguments = connector
					.defaultArguments();
			
			for (final String argKey : defaultArguments.keySet()) {
				final Argument argValue = defaultArguments.get(argKey);
				final String argDescription = argValue.description();

				System.out.println("\t >>> argument[= " + argKey
						+ "].description = " + argDescription);
			}
		}
	}
	
	public static <T extends Connector> T findConnector(final Class<T> connectorType) {
		final VirtualMachineManager vmManager = Bootstrap
				.virtualMachineManager();
		
		final List<Connector> allConnectors = vmManager.allConnectors();

		for (final Connector connector : allConnectors) {
			final Class<? extends Connector> foundType = connector.getClass();

			if ( connectorType.equals(foundType) ) {
				return (T) connector;
			}
		}

		final String logMsg = "Couldn't find connector of type [= "
				+ connectorType.getName() + "]";
		
		throw new IllegalArgumentException(logMsg);
	}

	public static Class<? extends Connector> identifyType(final String jvmConnectorType) {
		final ClassLoader classLoader = JVMConnectorFactory.class.getClassLoader();
		
		try {
			final Class<?> type = classLoader.loadClass( jvmConnectorType );
			
			if( Connector.class.isAssignableFrom( type )) {
				return (Class<Connector>) type;
			}
			
			final String logMsg = "Passed parameter [= " + jvmConnectorType + " ] is not of type " + Connector.class.getName() + ". Couldn't identify connector.";
			throw new IllegalArgumentException(logMsg);
		} catch (final ClassNotFoundException e) {
			final String logMsg = "Couldn't load Connector [= " + jvmConnectorType + "]";
			System.out.println( logMsg );
			
			throw new IllegalArgumentException( logMsg );
		}
	}

}
