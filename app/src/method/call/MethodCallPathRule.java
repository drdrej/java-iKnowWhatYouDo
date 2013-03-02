package method.call;

import org.parboiled.Action;
import org.parboiled.BaseParser;
import org.parboiled.Context;
import org.parboiled.Rule;

public class MethodCallPathRule extends BaseParser<Object> {

	private Action MethodNameAction = new Action() {

		@Override
		public boolean run(Context ctx) {
			final char currentChar = ctx.getCurrentChar();
			return Character.isJavaIdentifierPart(currentChar);
		}
		
	};
	
	public Rule Expression() {
		return Sequence(SpaceChars(), Visibility(), SpaceChars(), StaticPart(),
				SpaceChars(), ClassPath(), MethodHead());
		/* Sequence('a', ZeroOrMore(Expression()), 'b'); */
	}

	private Rule SpaceChars() {
		return OneOrMore(String(" "));
	}

	private Rule Visibility() {
		return Optional(String("public"), String("private"),
				String("protected"), String("*"));
	}

	private Rule StaticPart() {
		return String( "static" );
	}

	private Rule ClassPath() {
		return String( "*" );
	}

	private Object MethodHead() {
		return Sequence(String("*"), MethodName(), SpaceChars(), String( "()" ));
	}

	private Action MethodName() {
		return MethodNameAction;
	}

}
