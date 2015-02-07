package org.jgll.parser.datadependent;

import static org.jgll.datadependent.ast.AST.greaterEq;
import static org.jgll.datadependent.ast.AST.integer;
import static org.jgll.datadependent.ast.AST.var;
import static org.jgll.grammar.condition.DataDependentCondition.predicate;

import org.jgll.grammar.Grammar;
import org.jgll.grammar.GrammarGraph;
import org.jgll.grammar.symbol.Character;
import org.jgll.grammar.symbol.Nonterminal;
import org.jgll.grammar.symbol.Rule;
import org.jgll.parser.GLLParser;
import org.jgll.parser.ParseResult;
import org.jgll.parser.ParserFactory;
import org.jgll.util.Configuration;
import org.jgll.util.Input;
import org.jgll.util.Visualization;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Anastasia Izmaylova
 * 
 * Operator precedence
 * 
 * l - by left recursive E
 * r - by right recursive E
 * 
 * S ::= E(0,0)
 * 
 * E(l,r) ::= [3 >= l, 3 >= r] E(4,r) '^' E(0,3) // propagate only r to the left as '^' is defined as right associative
 *          | [2 >= r] E(0,0) '-'
 *          | 'a'
 *
 * 1st alternative: 
 * 		E(4,r): right (3 - left), r - can be changed by a parent
 * 		E(0,3): right (4 - left), i.e.,
 * 			  E(0,0)
 *           /   |   \
 *       E(4,0)  ^  E(0,3)
 *                 /   |   \
 *             E(4,3)  ^  E(0,3)
 *
 */

public class Test4 {
	
	private Grammar grammar;

	@Before
	public void init() {
		
		Nonterminal S = Nonterminal.withName("S");
		
		Nonterminal E = Nonterminal.builder("E").addParameters("l", "r").build();
		
		Rule r0 = Rule.withHead(S).addSymbol(Nonterminal.builder(E).apply(integer(0), integer(0)).build()).build();
		
		Rule r1_1 = Rule.withHead(E)
					.addSymbol(Nonterminal.builder(E).apply(integer(4), var("r"))
							.addPreCondition(predicate(greaterEq(integer(3), var("l"))))
							.addPreCondition(predicate(greaterEq(integer(3), var("r")))).build())
					.addSymbol(Character.from('^'))
					.addSymbol(Nonterminal.builder(E).apply(integer(0), integer(3)).build()).build();
		
		Rule r1_2 = Rule.withHead(E)
				.addSymbol(Nonterminal.builder(E).apply(integer(0), integer(0))
						.addPreCondition(predicate(greaterEq(integer(2), var("r")))).build())
				.addSymbol(Character.from('-')).build();
		
		Rule r1_3 = Rule.withHead(E).addSymbol(Character.from('a')).build();
		
		grammar = Grammar.builder().addRules(r0, r1_1, r1_2, r1_3).build();
		
	}
	
	@Test
	public void test() {
		System.out.println(grammar);
		
		 Input input = Input.fromString("a^a-^a");
		// Input input = Input.fromString("a^a^a");
		// Input input = Input.fromString("a^a-");
		// Input input = Input.fromString("a-^a");
		
		GrammarGraph graph = grammar.toGrammarGraph(input, Configuration.DEFAULT);
		
		GLLParser parser = ParserFactory.getParser(Configuration.DEFAULT, input, grammar);
		ParseResult result = parser.parse(input, graph, Nonterminal.withName("S"));
		
		Visualization.generateGrammarGraph("/Users/anastasiaizmaylova/git/diguana/test/org/jgll/parser/datadependent/", graph);
		
		if (result.isParseSuccess()) {
			Visualization.generateSPPFGraph("/Users/anastasiaizmaylova/git/diguana/test/org/jgll/parser/datadependent/", 
					result.asParseSuccess().getRoot(), input);
		}
		
	}

}
