package com.significantfiles.debug.util;

import org.mozilla.javascript.Scriptable;

public class JavaScriptUtil {

	
	public static void listAttributes(final Scriptable scope) {
		final Object[] ids = scope.getIds();
		System.out.println(" [RHINO] print ttributes of scope-type: [=" + scope.getClassName() + "]");
		
		for (int i = 0; i < ids.length; i++) {
			System.out.println(" [RHINO] attribute-id: [=" + ids[i] + "]");
		}
	}
}
