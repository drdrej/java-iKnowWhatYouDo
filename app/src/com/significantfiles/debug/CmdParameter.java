package com.significantfiles.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.Option;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.significantfiles.debug.breakpoint.BreakpointAspect;
import com.significantfiles.debug.breakpoint.VMConnector;
import com.significantfiles.debug.util.JavaScriptUtil;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.Argument;

public class CmdParameter {

	private static final int DEFAULT_LINE_NO = 1;

	@Option(name = "-port", usage = "Port number to which to attach for VM connections", required=true)
	private int port = 0;

	@Option(name = "-host", usage = "Machine name (or IP) to which to attach for VM connections", required=false)
	private String host = "localhost";

	@Option(name = "-startup-info", usage = "Print more detailed startup-info (for debugging)", required=false)
	public boolean startupInfo;

	@Option(name = "-trace-file", usage = "All trace outputs will print out to this file", required=false)
	private File traceFile;
	
	@Option(name = "-script", usage = "This file contains javascript-based aspects fto handle tracing and data-analysis", required=true)
	private File script;

	private File utilsScript;

//	@Option(name = "-log-to-console", usage = "Use to activate logging in the debuger", required=true)
//	private boolean logToConsole = false;
	
	public CmdParameter() {
		;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public File getTraceFile() {
		return traceFile;
	}
	
	public File getScript() {
		return script;
	}
	
	public Scriptable createJSScope(final Tracer tracer) throws Throwable {
		final Context cx = Context.enter();
		cx.setOptimizationLevel(9);
		
		final Scriptable scope = cx.initStandardObjects();
		ScriptableObject.defineClass(scope, BreakpointAspect.class );
		
		scope.put("tracer", scope, tracer);
		
		evaluateUtilsScript(cx, scope);
        evaluateScript(cx, scope, this.script);
        
		return scope;
	}

	private void evaluateUtilsScript(final Context cx, final Scriptable scope) throws Throwable {
		final InputStream inScript = this.getClass().getResourceAsStream( "utils.js" );
		if( inScript == null ) {
			throw new RuntimeException( "utils.js Script doesn't found...");
		}
		
		final Reader source = new InputStreamReader(inScript);
		final String jsString = source.toString();

		cx.evaluateReader(scope, source, "<utils.js>", DEFAULT_LINE_NO, null);
	}

	private void evaluateScript(final Context cx, final Scriptable scope, final File file) throws Throwable {
	    final FileReader source = new FileReader(file);
		cx.evaluateReader(scope, source, file.getAbsolutePath(), DEFAULT_LINE_NO, null);
	}

	public PrintWriter createTraceWriter() {
		if( this.traceFile == null ) {
			return new PrintWriter( System.out );
		}
		
		try {
			final FileWriter fileOut = new FileWriter(this.traceFile);
			final BufferedWriter buffer = new BufferedWriter(fileOut, 4096);
			return new PrintWriter(buffer);
		} catch (final Throwable x) {
			x.printStackTrace();

			return null;
		}
	}

	public void validate() {
		;
	}

	public final Map<String, Argument> createConnectorConfig(
			final Connector connector) {
		final Map<String, Argument> arguments = connector.defaultArguments();

		final Map<String, Argument> rval = new HashMap<String, Connector.Argument>();

		final Argument timeout = arguments.get("timeout");
		timeout.setValue("60000");
		rval.put("timeout", timeout);

		final Argument port = arguments.get("port");
		final String portName = String.valueOf(this.port);
		port.setValue(portName);
		rval.put("port", port);

		final Argument host = arguments.get("hostname");
		host.setValue(this.host);
		rval.put("hostname", host);

		return rval;
	}
}
