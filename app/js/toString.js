/*
and( 
	exclude( "com.xxx.Trr" ),
    exclude( "*.toString()" ),
);
*/

//importPackage( com.significantfiles.debug.breakpoint );
//defineClass( com.significantfiles.debug.breakpoint.BreakPointAspect );


//var aspect = com.significantfiles.debug.breakpoint.BreakPointAspect;

var tempCounter = 0;


///*
//var x = new aspect( 
//   // entryMethod( "java.*.toString()" )
//   // loadClass( "java.*" )
//   		
//   /*$fieldAccess( */ "java.*.toString()/temp", /*read & write ) ), */
//		
//   function( data ) {
//	   tempCounter++;
//	   println( "[DEBUG] called: " + data );
//   }
//
//);
//*/

// fieldAccess( "Das ist ein Test." );

enterMethod( "* *.toString(*)", function( breakpoint ) {
	println( "call method: " + "breakpoint.getScopeMethod().getName()" )
} );