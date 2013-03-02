package com.significantfiles.debug.method.call.ParserTest;

import method.call.MethodCallPathRule;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;


public class ParserTestCase {

	public static void main(String[] args) {
		final MethodCallPathRule parser = Parboiled.createParser(MethodCallPathRule.class);
		final TracingParseRunner<MethodCallPathRule> runner = new TracingParseRunner<MethodCallPathRule>(parser.Expression());
		
		final ParsingResult<MethodCallPathRule> result = runner.run( "public * *.toString()");
		
		System.out.println( "has errors: " + result.hasErrors() );
	}
}
