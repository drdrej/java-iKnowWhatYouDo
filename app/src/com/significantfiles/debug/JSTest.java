package com.significantfiles.debug;

import java.io.File;
import java.io.FileReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;

public class JSTest {

	public static void main(String[] args) {
		System.out.println(">>> start JS-Evaluator");
		final long now = System.currentTimeMillis();
		
		try {
			final Context cx = Context.enter();
			final Scriptable scope = cx.initStandardObjects();

//			final String arguments = "java.lang.System.out.println(3)";
			
			final File file = new File( "C:\\Users\\asiebert\\code\\IKnowWhatYoudo\\app\\js\\toString.js" );
			final FileReader source = new FileReader(file);
			
			final Object result = cx.evaluateReader(scope, source, "<cmd>",
					1, null);
			
			final Object[] ids = scope.getIds();
			
			for (int i = 0; i < ids.length; i++) {
				System.out.println(">>> id: [=" + ids[i] + "]");
			}
			
			final long diff = System.currentTimeMillis() - now;
			System.out.println(">>> result: " + result + " : time: " + diff);
			
			final NativeObject tracePoint = (NativeObject) scope.get( "ignoreJavaObj", scope );
			
			System.out.println(">>> trace point found: " + tracePoint.getProperty(tracePoint, "pointcut") + "  of type: " + tracePoint.getClass() );
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		
		Context.exit();
	}
}
