package com.significantfiles.debug;

import com.significantfiles.debug.breakpoint.FieldAccessBreakpoint;

public class BreakpointsManager {

	
	public void add( final FieldAccessBreakpoint fieldAccess ) {
		System.out.println(">>> add( fieldAccess( " + fieldAccess.path + ")");
	}
}
