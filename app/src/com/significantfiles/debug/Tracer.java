package com.significantfiles.debug;

import java.io.PrintWriter;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.mozilla.javascript.Scriptable;

import com.significantfiles.debug.breakpoint.BreakpointAspect;
import com.significantfiles.debug.breakpoint.VMConnector;
import com.significantfiles.debug.connector.JVMConnectorFactory;
import com.significantfiles.debug.events.EventHandlerRegistry;
import com.significantfiles.debug.util.JavaScriptUtil;
import com.sun.jdi.Method;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.tools.jdi.SocketAttachingConnector;

public class Tracer {

	private EventHandlerRegistry eventHandlerRegistry;

	private VMConnector connector;

	private BreakpointsManager breakpoints = new BreakpointsManager();
	
	public BreakpointsManager getBreakpoints() {
		return breakpoints;
	}

	public static void main(String[] args) throws Throwable {
		final Tracer tracer = new Tracer();
		tracer.trace(args);
	}

	public void trace(final String[] args) {
		startupMessage();

		final CmdParameter parameter = parseParameter(args);

		parameterMessage(parameter);

		if (parameter.startupInfo) {
			JVMConnectorFactory.listConnectors();
		}

		final Scriptable script = loadTraceScript(parameter);

		loadAspects(script);

		final VirtualMachine vm = attachVM(parameter);

		this.connector = VMConnector.newInstance(vm);

		connector.config();

		final EventQueue eventQueue = vm.eventQueue();

		final boolean vmIsConnected = true;

		final PrintWriter writer = parameter.createTraceWriter();

		while (vmIsConnected) {
			try {
				final EventSet eventSet = eventQueue.remove();
				handleEventSet(eventSet, writer);
				eventSet.resume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final VMDisconnectedException e) {
				handleDisconnectedException(e);
			} finally {
				writer.flush();
			}
		}
	}

	private void loadAspects(final Scriptable script) {
		JavaScriptUtil.listAttributes(script);
		final Object[] ids = script.getIds();

		for (final Object id : ids) {
			final String idStr = String.valueOf(id);
			final Object jsObj = script.get(idStr, script);

			if (jsObj instanceof BreakpointAspect) {
				final BreakpointAspect aspect = (BreakpointAspect) jsObj;
				registerBreakpoint(aspect);

				// final long start = System.currentTimeMillis();
				// for (int i = 0; i < 10000; i++) {
				aspect.handleAspect();
				// }
				//
				// for (int i = 0; i < 1000000; i++) {
				// java.lang.System.out.println( new Date() );
				// }
				//
				//
				// final long diff = System.currentTimeMillis() - start;
				// System.out.println("[js.sysout = " + diff + "]");
			}
		}
	}

	private void registerBreakpoint(final BreakpointAspect aspect) {
		System.out.println(">>>>> pointcut: " + aspect.pointcut);
		aspect.initScope(aspect.getParentScope());
	}

	private Scriptable loadTraceScript(final CmdParameter parameter) {
		try {
			return parameter.createJSScope(this);
		} catch (final Throwable e) {
			// TODO: logging
			e.printStackTrace();

			final String msg = "Couldn't evaluate javascript-file [= "
					+ parameter.getScript().getAbsolutePath() + "].";
			System.out.println(msg);
			System.exit(-1);

			return null;
		}
	}

	private void parameterMessage(final CmdParameter parameter) {
		final String msg = "> Try to attach to a runnig VM on "
				+ parameter.getHost() + ":" + parameter.getPort() + ".";
		System.out.println(msg);
	}

	private void startupMessage() {
		System.out
				.println("========================================================");
		System.out.println(":: IKnowWhatYouDo - V."
				+ BuildConstants.VERSION_STRING
				+ "                             ::");
		System.out
				.println(":: a scriptable debugger for java-virtual-machine     ::");
		System.out
				.println("::                                                    ::");
		System.out
				.println(":: (c) 2010 by Andreas Siebert(significantfiles.com)  ::");
		System.out
				.println("========================================================");
	}

	private CmdParameter parseParameter(String[] args) {
		final CmdParameter bean = new CmdParameter();
		final CmdLineParser parser = new CmdLineParser(bean);

		try {
			parser.parseArgument(args);
			bean.validate();

			return bean;
		} catch (final CmdLineException e) {
			System.err.println(e.getMessage());
			parser.printUsage(System.err);

			System.exit(-1);

			return null;
		}
	}

	private void handleDisconnectedException(VMDisconnectedException e) {
		;
	}

	private static void handleEventSet(EventSet eventSet,
			final PrintWriter writer) {
		for (final Event event : eventSet) {
			handleEvent(event, writer);
		}
	}

	private static void handleEvent(final Event event, final PrintWriter out) {
		if( event instanceof BreakpointEvent ) {
			BreakpointEvent bpe = (BreakpointEvent) event;
		}
		if (event instanceof MethodEntryEvent) {
			final MethodEntryEvent methodEntry = (MethodEntryEvent) event;

			// final Location location = methodEntry.location();
			final Method method = methodEntry.method();
			final String name = method.name();
			final ReferenceType declaringType = method.declaringType();

			out.println(">>> trace: " + declaringType.name() + "." + name
					+ "()");
		} else {
			out.println("[!] skip event: " + event);
		}
	}

	private static VirtualMachine attachVM(final CmdParameter parameter) {

		final String jvmConnectorType = "com.sun.tools.jdi.SocketAttachingConnector";
		final Class<? extends Connector> connectorType = JVMConnectorFactory
				.identifyType(jvmConnectorType);

		final SocketAttachingConnector connector = (SocketAttachingConnector) JVMConnectorFactory
				.findConnector(connectorType);

		final Map<String, Argument> args = parameter
				.createConnectorConfig(connector);
		// Map<String, Argument> args = setConnectorArgs(connector);

		try {
			return connector.attach(args);
		} catch (Throwable e) {
			System.out.println("> Couldn't connect to JVM on "
					+ parameter.getHost() + ":" + parameter.getPort());
			System.out.println("> Check " + getAppLogFile()
					+ " to understand and handle this problem.");

			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new IllegalStateException();
		}
	}

	private static String getAppLogFile() {
		return "<not. impl>";
	}

}
