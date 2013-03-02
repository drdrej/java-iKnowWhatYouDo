package com.significantfiles.debug.breakpoint;

/**
 * akzeptiert pfade der Syntax:
 * 
 * 
 * 
 * @author asiebert
 *
 */
public class MethodCallBreakpoint {

	public MethodCallBreakpoint( final String path ) {
		final String methodPart = extractMethodPart( path );
		
		final String methodNameSignature = "[a-zA-Z0-9$_]+\\(\\)";
		final boolean matches = methodPart.matches(  methodNameSignature );
	
		
		if( !matches ) {
			// Log and ignore this breakpoint ...
			System.out.println( ">>> method : " + path );
		}
	}

	private String extractMethodPart(final String path) {
		final int idx = path.lastIndexOf( '.' );
		
		if( idx < 0 ) {
			return path.substring(idx+1);
		} else {
			return path;
		}
	}
	
	
}
