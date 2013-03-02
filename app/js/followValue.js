/**
 * this example shows a usage of function follow().
 * this function gives a possibility o trace all influences by
 * value of a listened variable.
 * 
 * Example: 
 * -----------------------------------
 * 1. following to passed level:
 *    follow( '*.HttpServletRequest.getParameter ( "key" ', 5 );
 *    this function will add an apsect to registry to trace all value-flows started 
 *    at a passed pointcut as first parameter of this function and 
 *    ended at level nr. 5.
 *    
 *    First call of getParameter() will be counted as level 0.
 */


follow( '*.HttpServletRequest.getParameter( "key" )', 5 );

// follow all changes in this scope.
follow( '*.calc()/temp.x' );

follow( '*.HttpServletRequest.getParameter( "key" )', "*.*Action.getView()" ):
