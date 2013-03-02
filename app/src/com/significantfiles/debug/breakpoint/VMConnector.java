package com.significantfiles.debug.breakpoint;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;

public class VMConnector {

	private final EventRequestManager eventRequestManager;

	public VMConnector(final EventRequestManager eventRequestManager) {
		this.eventRequestManager = eventRequestManager;
	}
	
	public void xxx(){
		System.out.println(">>>> call XXX()");
	}
	
	public void add( final FieldAccessBreakpoint breakpoint ) {
		System.out.println(">>> ADD BREAKPOINT TO MANAGER: " + breakpoint.path);
	}
	
	public void config() {
		final MethodEntryRequest methodEntryReq = this.eventRequestManager.createMethodEntryRequest();
		
//		Taskspace:
		methodEntryReq.addClassExclusionFilter( "java.*" ); // .TaskManagerFormComponent
		methodEntryReq.addClassExclusionFilter("com.documentum.fc.tracing.*");
		methodEntryReq.addClassExclusionFilter("org.apache.jasper.*");
		methodEntryReq.addClassExclusionFilter("org.apache.catalina.*");
		methodEntryReq.addClassExclusionFilter("org.apache.juli.logging.*");
		methodEntryReq.addClassExclusionFilter( "com.documentum.fc.client.impl.session.SessionPool" );
		methodEntryReq.addClassExclusionFilter( "sun.*" );
		methodEntryReq.addClassExclusionFilter( "org.apache.tomcat.*" );
		methodEntryReq.addClassExclusionFilter( "org.apache.coyote.*" );
		methodEntryReq.addClassExclusionFilter( "org.apache.*" );
		methodEntryReq.addClassExclusionFilter( "javax.*" );
		
		
		
		methodEntryReq.addClassExclusionFilter( "com.documentum.thirdparty.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.fc.common.DfFileWatcher" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.xerces_2_8_0.*");
		methodEntryReq.addClassExclusionFilter( "com.documentum.fc.client.*" );
		
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.common.ThreadLocalCache" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.common.ThreadLocalVariable" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.common.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.formext.common.ClientSessionState" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.env.EnvironmentService" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.env.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.test.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.formext.session.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.formext.control.docbase.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.contentxfer.ucf.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.servlet.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.layout.*" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.formext.common.ControlUtil" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.form.FormRequest" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.web.form.FormTag" );
		methodEntryReq.addClassExclusionFilter( "com.documentum.nls.NlsResourceBundle" );
		
		
//		
//		methodEntryReq.addClassFilter("*.TaskManagerFormComponent");
//		methodEntryReq.addClassFilter("org.chiba.xml.xforms.Container");
//		methodEntryReq.addClassFilter("com.documentum.xforms.engine.chiba.*");
		
//		methodEntryReq.addClassFilter( "com.documentum.*" );
//		methodEntryReq.addClassFilter( "com.contentteam.*" );
		
//		
////		// Distributer:
//		
////		methodEntryReq.addClassFilter( "*.PropertiesBasedObjectFactory" );
////		methodEntryReq.addClassFilter( "*.DirectoryDistributerPollManager" );
//		methodEntryReq.addClassExclusionFilter( "com.documentum.fc.tracing.*" );
//		methodEntryReq.addClassExclusionFilter( "java.*" );
//		methodEntryReq.addClassExclusionFilter( "javax.*" );
//		methodEntryReq.addClassExclusionFilter( "org.sadun.*" ); 
//		methodEntryReq.addClassExclusionFilter( "com.deltax.*" ); 
		
		
		methodEntryReq.setSuspendPolicy( EventRequest.SUSPEND_EVENT_THREAD );
		methodEntryReq.setEnabled( true );
		
		
//		this.eventRequestManager.createExceptionRequest(arg0, arg1, arg2)
	}
	
	public static VMConnector newInstance(final VirtualMachine vm) {
		final EventRequestManager eventReqManager = vm.eventRequestManager();
		return new VMConnector( eventReqManager );
	}
	
}
