package com.significantfiles.debug.breakpoint;

import java.util.Date;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/*
 * //var count_access_to_variables_called_temp_in_toString_method = 

function breakpoint(pointcut, aspect) {

   this.pointcut = pc;

   this.aspect = function( data ) {
//	   return 3;
	   tempCounter++;
   };
};
 */
public class BreakpointAspect extends ScriptableObject {
	
	private static final long serialVersionUID = 1L;

	public String pointcut;
	private Function advise;
	private Scriptable scope;
	
	public BreakpointAspect() {
		;
	}

	@Override
	public String getClassName() {
		return "aspect";
	}
	
	public void initScope( final Scriptable scriptable) {
		this.scope = scriptable;
	}
	
	public void jsConstructor(final String pointcut, final Function advise) {
		this.pointcut = pointcut;
		this.advise = advise;
	}
	
	public void handleAspect( ) {
		this.advise.call(Context.getCurrentContext(), this.scope, null, new Object[]{ new Date() } );
	}
	
	public boolean match() {
		return false;
	}
	
	
//	private final static BreakpointAspect EMPTY = new BreakpointAspect();
//	
//	public static BreakpointAspect create(final Scriptable scope, final String id) {
//		final Object object = scope.get(id, scope);
//		
//		if( object == Scriptable.NOT_FOUND) {
//			return EMPTY;
//		}
//		
//		if( !(object instanceof NativeObject) ) {
//			return EMPTY;
//		}
//		
//		final String pointcut = readPointcut();
//		
//		if( pointcut == null) 
//			return EMPTY;
//		
//		final Function advise = readAdvise();
//		
//		if( advise == null ) 
//			return EMPTY;
//		
//		return EMPTY;
//	}
//	
//	private static Function readAdvise() {
////		jsObj instanceof Function		
//		
//		return null;
//	}
//
//	private static String readPointcut() {
//		// TODO Auto-generated method stub
//		return "";
//	}
//
//	
//	@JSProperty
//	public String getPrototype() {
//		return "";
//	}

}
