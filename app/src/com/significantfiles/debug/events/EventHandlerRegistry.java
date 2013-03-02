package com.significantfiles.debug.events;

import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.MethodEntryEvent;

public class EventHandlerRegistry {

	
	public void handle(final Event event) {
		if( event instanceof MethodEntryEvent) {
			final MethodEntryEvent methodEntry = (MethodEntryEvent) event;
			handleMethodEntry(methodEntry);
		}
	}

	private void handleMethodEntry(final MethodEntryEvent methodEntry) {
		final Location location = methodEntry.location();
		
		final Method method = methodEntry.method();
		
		System.out.println( ">>> handle event in eventmanager" );
	}
	
	
	
}
