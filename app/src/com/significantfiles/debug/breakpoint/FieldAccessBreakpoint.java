package com.significantfiles.debug.breakpoint;


public class FieldAccessBreakpoint {

	public String path;
	
	private int accessRule; // read write
	
	public FieldAccessBreakpoint( String path ) {
		this.path = path;
		System.out.println(">>>>>>> path : " + path);
	}
		
}
