

/**
 * shortcut-version of 
 * 		System.out.println();
 * 
 * @param msg
 */
function println( msg ) {
	java.lang.System.out.println( msg );
}

/**
 * shortcut-version of a internal tracer.
 * 		
 * @param msg
 */
function trace( msg ) {
	com.significantfiles.debug.Tracer.trace( msg );
}

function fieldAccess( path ) {
	
//	var breakpoint = new debugPackage.breakpoint.FieldAccessBreakpoint( path );	
	var breakpoints = tracer.getBreakpoints();
	var breakpoint = new com.significantfiles.debug.breakpoint.FieldAccessBreakpoint( path );	
	
	breakpoints.add( breakpoint );
//	println( breakpoint.astPathString );
}

/** 
 * 
98 #+
 * 
 * @param path
 */
function enterMetßüpewwwwwwwwwwwwwwwwwwwwwwwwhod( path ) {
	
//	var breakpoint = new debugPackage.breakpoint.FieldAccessBreakpoint( path );	
	var breakpoints = tracer.getBreakpoints();
	var breakpoint = new com.significantfiles.debug.breakpoint.MethodCallBreakpoint( path );	
	
	breakpoints.add( breakpoint );
//	println( breakpoint.astPathString );
	
}