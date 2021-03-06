/*
 * Copyright (c) 2015, Ali Afroozeh and Anastasia Izmaylova, Centrum Wiskunde & Informatica (CWI)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 *
 */

package org.iguana.parser;

import static org.junit.Assert.*;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.GrammarGraph;
import org.iguana.grammar.condition.ContextFreeCondition;
import org.iguana.grammar.symbol.Character;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Rule;
import org.iguana.parser.GLLParser;
import org.iguana.parser.ParseResult;
import org.iguana.parser.ParserFactory;
import org.iguana.regex.Sequence;
import org.iguana.sppf.IntermediateNode;
import org.iguana.sppf.NonterminalNode;
import org.iguana.sppf.PackedNode;
import org.iguana.sppf.SPPFNode;
import org.iguana.sppf.SPPFNodeFactory;
import org.iguana.sppf.TerminalNode;
import org.iguana.util.Configuration;
import org.iguana.util.Input;
import org.junit.Before;

/**
 * 
 * S ::= a S b S \ a S
 *     | a S
 *     | s
 * 
 * @author Ali Afroozeh
 *
 */

// TODO: Context-free conditions don't work.
public class DanglingElseGrammar1 {

	private GLLParser parser;
	
	private Nonterminal S = Nonterminal.withName("S");
	private Character s = Character.from('s');
	private Character a = Character.from('a');
	private Character b = Character.from('b');
	private Sequence<?> group = Sequence.builder(a, S, b, S).addPreCondition(ContextFreeCondition.notMatch(a, S)).build();

	private Grammar grammar;
	

	@Before
	public void createGrammar() {
		
		Grammar.Builder builder = new Grammar.Builder();
		
		Rule rule1 = Rule.withHead(S).addSymbols(a, S).build();
		builder.addRule(rule1);
		
		Rule rule2 = Rule.withHead(S).addSymbols(group).build();
		builder.addRule(rule2);
		
		Rule rule3 = Rule.withHead(S).addSymbols(s).build();
		builder.addRule(rule3);
		
		grammar = builder.build();
	}
	
	public void test1() {
		Input input = Input.fromString("aasbs");
		parser = ParserFactory.getParser(Configuration.DEFAULT, input, grammar);
		ParseResult result = parser.parse(input, grammar, Nonterminal.withName("S"));
		assertTrue(result.isParseSuccess());
		assertTrue(result.asParseSuccess().getRoot().deepEquals(getExpectedSPPF1(parser.getGrammarGraph())));
	}
	
	public void test2() {
		Input input = Input.fromString("aaaaasbsbsbs");
		parser = ParserFactory.getParser(Configuration.DEFAULT, input, grammar);
		ParseResult result = parser.parse(input, grammar, Nonterminal.withName("S"));
		assertTrue(result.isParseSuccess());
		assertTrue(result.asParseSuccess().getRoot().deepEquals(getExpectedSPPF2(parser.getGrammarGraph())));
	}
	
	private SPPFNode getExpectedSPPF1(GrammarGraph registry) {
		SPPFNodeFactory factory = new SPPFNodeFactory(registry);
		NonterminalNode node1 = factory.createNonterminalNode("S", 0, 0, 5);
		PackedNode node2 = factory.createPackedNode("S ::= a S .", 1, node1);
		TerminalNode node3 = factory.createTerminalNode("a", 0, 1);
		NonterminalNode node4 = factory.createNonterminalNode("S", 0, 1, 5);
		PackedNode node5 = factory.createPackedNode("S ::= (a S b S) .", 1, node4);
		NonterminalNode node6 = factory.createNonterminalNode("(a S b S)", 0, 1, 5);
		PackedNode node7 = factory.createPackedNode("(a S b S) ::= a S b S .", 4, node6);
		IntermediateNode node8 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 1, 4);
		PackedNode node9 = factory.createPackedNode("(a S b S) ::= a S b . S", 3, node8);
		IntermediateNode node10 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 1, 3);
		PackedNode node11 = factory.createPackedNode("(a S b S) ::= a S . b S", 2, node10);
		TerminalNode node12 = factory.createTerminalNode("a", 1, 1);
		NonterminalNode node13 = factory.createNonterminalNode("S", 0, 2, 3);
		PackedNode node14 = factory.createPackedNode("S ::= s .", 2, node13);
		TerminalNode node15 = factory.createTerminalNode("s", 2, 1);
		node14.addChild(node15);
		node13.addChild(node14);
		node11.addChild(node12);
		node11.addChild(node13);
		node10.addChild(node11);
		TerminalNode node16 = factory.createTerminalNode("b", 3, 1);
		node9.addChild(node10);
		node9.addChild(node16);
		node8.addChild(node9);
		NonterminalNode node17 = factory.createNonterminalNode("S", 0, 4, 5);
		PackedNode node18 = factory.createPackedNode("S ::= s .", 4, node17);
		TerminalNode node19 = factory.createTerminalNode("s", 4, 1);
		node18.addChild(node19);
		node17.addChild(node18);
		node7.addChild(node8);
		node7.addChild(node17);
		node6.addChild(node7);
		node5.addChild(node6);
		node4.addChild(node5);
		node2.addChild(node3);
		node2.addChild(node4);
		PackedNode node20 = factory.createPackedNode("S ::= (a S b S) .", 0, node1);
		NonterminalNode node21 = factory.createNonterminalNode("(a S b S)", 0, 0, 5);
		PackedNode node22 = factory.createPackedNode("(a S b S) ::= a S b S .", 4, node21);
		IntermediateNode node23 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 0, 4);
		PackedNode node24 = factory.createPackedNode("(a S b S) ::= a S b . S", 3, node23);
		IntermediateNode node25 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 0, 3);
		PackedNode node26 = factory.createPackedNode("(a S b S) ::= a S . b S", 1, node25);
		NonterminalNode node27 = factory.createNonterminalNode("S", 0, 1, 3);
		PackedNode node28 = factory.createPackedNode("S ::= a S .", 2, node27);
		node28.addChild(node12);
		node28.addChild(node13);
		node27.addChild(node28);
		node26.addChild(node3);
		node26.addChild(node27);
		node25.addChild(node26);
		node24.addChild(node25);
		node24.addChild(node16);
		node23.addChild(node24);
		node22.addChild(node23);
		node22.addChild(node17);
		node21.addChild(node22);
		node20.addChild(node21);
		node1.addChild(node2);
		node1.addChild(node20);
		return node1;
	}
	
	private SPPFNode getExpectedSPPF2(GrammarGraph registry) {
		SPPFNodeFactory factory = new SPPFNodeFactory(registry);
		NonterminalNode node1 = factory.createNonterminalNode("S", 0, 0, 12);
		PackedNode node2 = factory.createPackedNode("S ::= a S .", 1, node1);
		TerminalNode node3 = factory.createTerminalNode("a", 0, 1);
		NonterminalNode node4 = factory.createNonterminalNode("S", 0, 1, 12);
		PackedNode node5 = factory.createPackedNode("S ::= a S .", 2, node4);
		TerminalNode node6 = factory.createTerminalNode("a", 1, 1);
		NonterminalNode node7 = factory.createNonterminalNode("S", 0, 2, 12);
		PackedNode node8 = factory.createPackedNode("S ::= (a S b S) .", 2, node7);
		NonterminalNode node9 = factory.createNonterminalNode("(a S b S)", 0, 2, 12);
		PackedNode node10 = factory.createPackedNode("(a S b S) ::= a S b S .", 11, node9);
		IntermediateNode node11 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 2, 11);
		PackedNode node12 = factory.createPackedNode("(a S b S) ::= a S b . S", 10, node11);
		IntermediateNode node13 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 2, 10);
		PackedNode node14 = factory.createPackedNode("(a S b S) ::= a S . b S", 3, node13);
		TerminalNode node15 = factory.createTerminalNode("a", 2, 1);
		NonterminalNode node16 = factory.createNonterminalNode("S", 0, 3, 10);
		PackedNode node17 = factory.createPackedNode("S ::= (a S b S) .", 3, node16);
		NonterminalNode node18 = factory.createNonterminalNode("(a S b S)", 0, 3, 10);
		PackedNode node19 = factory.createPackedNode("(a S b S) ::= a S b S .", 9, node18);
		IntermediateNode node20 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 3, 9);
		PackedNode node21 = factory.createPackedNode("(a S b S) ::= a S b . S", 8, node20);
		IntermediateNode node22 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 3, 8);
		PackedNode node23 = factory.createPackedNode("(a S b S) ::= a S . b S", 4, node22);
		TerminalNode node24 = factory.createTerminalNode("a", 3, 1);
		NonterminalNode node25 = factory.createNonterminalNode("S", 0, 4, 8);
		PackedNode node26 = factory.createPackedNode("S ::= (a S b S) .", 4, node25);
		NonterminalNode node27 = factory.createNonterminalNode("(a S b S)", 0, 4, 8);
		PackedNode node28 = factory.createPackedNode("(a S b S) ::= a S b S .", 7, node27);
		IntermediateNode node29 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 4, 7);
		PackedNode node30 = factory.createPackedNode("(a S b S) ::= a S b . S", 6, node29);
		IntermediateNode node31 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 4, 6);
		PackedNode node32 = factory.createPackedNode("(a S b S) ::= a S . b S", 5, node31);
		TerminalNode node33 = factory.createTerminalNode("a", 4, 1);
		NonterminalNode node34 = factory.createNonterminalNode("S", 0, 5, 6);
		PackedNode node35 = factory.createPackedNode("S ::= s .", 5, node34);
		TerminalNode node36 = factory.createTerminalNode("s", 5, 1);
		node35.addChild(node36);
		node34.addChild(node35);
		node32.addChild(node33);
		node32.addChild(node34);
		node31.addChild(node32);
		TerminalNode node37 = factory.createTerminalNode("b", 6, 1);
		node30.addChild(node31);
		node30.addChild(node37);
		node29.addChild(node30);
		NonterminalNode node38 = factory.createNonterminalNode("S", 0, 7, 8);
		PackedNode node39 = factory.createPackedNode("S ::= s .", 7, node38);
		TerminalNode node40 = factory.createTerminalNode("s", 7, 1);
		node39.addChild(node40);
		node38.addChild(node39);
		node28.addChild(node29);
		node28.addChild(node38);
		node27.addChild(node28);
		node26.addChild(node27);
		node25.addChild(node26);
		node23.addChild(node24);
		node23.addChild(node25);
		node22.addChild(node23);
		TerminalNode node41 = factory.createTerminalNode("b", 8, 1);
		node21.addChild(node22);
		node21.addChild(node41);
		node20.addChild(node21);
		NonterminalNode node42 = factory.createNonterminalNode("S", 0, 9, 10);
		PackedNode node43 = factory.createPackedNode("S ::= s .", 9, node42);
		TerminalNode node44 = factory.createTerminalNode("s", 9, 1);
		node43.addChild(node44);
		node42.addChild(node43);
		node19.addChild(node20);
		node19.addChild(node42);
		node18.addChild(node19);
		node17.addChild(node18);
		node16.addChild(node17);
		node14.addChild(node15);
		node14.addChild(node16);
		node13.addChild(node14);
		TerminalNode node45 = factory.createTerminalNode("b", 10, 1);
		node12.addChild(node13);
		node12.addChild(node45);
		node11.addChild(node12);
		NonterminalNode node46 = factory.createNonterminalNode("S", 0, 11, 12);
		PackedNode node47 = factory.createPackedNode("S ::= s .", 11, node46);
		TerminalNode node48 = factory.createTerminalNode("s", 11, 1);
		node47.addChild(node48);
		node46.addChild(node47);
		node10.addChild(node11);
		node10.addChild(node46);
		node9.addChild(node10);
		node8.addChild(node9);
		node7.addChild(node8);
		node5.addChild(node6);
		node5.addChild(node7);
		PackedNode node49 = factory.createPackedNode("S ::= (a S b S) .", 1, node4);
		NonterminalNode node50 = factory.createNonterminalNode("(a S b S)", 0, 1, 12);
		PackedNode node51 = factory.createPackedNode("(a S b S) ::= a S b S .", 11, node50);
		IntermediateNode node52 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 1, 11);
		PackedNode node53 = factory.createPackedNode("(a S b S) ::= a S b . S", 10, node52);
		IntermediateNode node54 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 1, 10);
		PackedNode node55 = factory.createPackedNode("(a S b S) ::= a S . b S", 2, node54);
		NonterminalNode node56 = factory.createNonterminalNode("S", 0, 2, 10);
		PackedNode node57 = factory.createPackedNode("S ::= a S .", 3, node56);
		node57.addChild(node15);
		node57.addChild(node16);
		PackedNode node58 = factory.createPackedNode("S ::= (a S b S) .", 2, node56);
		NonterminalNode node59 = factory.createNonterminalNode("(a S b S)", 0, 2, 10);
		PackedNode node60 = factory.createPackedNode("(a S b S) ::= a S b S .", 9, node59);
		IntermediateNode node61 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 2, 9);
		PackedNode node62 = factory.createPackedNode("(a S b S) ::= a S b . S", 8, node61);
		IntermediateNode node63 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 2, 8);
		PackedNode node64 = factory.createPackedNode("(a S b S) ::= a S . b S", 3, node63);
		NonterminalNode node65 = factory.createNonterminalNode("S", 0, 3, 8);
		PackedNode node66 = factory.createPackedNode("S ::= a S .", 4, node65);
		node66.addChild(node24);
		node66.addChild(node25);
		PackedNode node67 = factory.createPackedNode("S ::= (a S b S) .", 3, node65);
		NonterminalNode node68 = factory.createNonterminalNode("(a S b S)", 0, 3, 8);
		PackedNode node69 = factory.createPackedNode("(a S b S) ::= a S b S .", 7, node68);
		IntermediateNode node70 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 3, 7);
		PackedNode node71 = factory.createPackedNode("(a S b S) ::= a S b . S", 6, node70);
		IntermediateNode node72 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 3, 6);
		PackedNode node73 = factory.createPackedNode("(a S b S) ::= a S . b S", 4, node72);
		NonterminalNode node74 = factory.createNonterminalNode("S", 0, 4, 6);
		PackedNode node75 = factory.createPackedNode("S ::= a S .", 5, node74);
		node75.addChild(node33);
		node75.addChild(node34);
		node74.addChild(node75);
		node73.addChild(node24);
		node73.addChild(node74);
		node72.addChild(node73);
		node71.addChild(node72);
		node71.addChild(node37);
		node70.addChild(node71);
		node69.addChild(node70);
		node69.addChild(node38);
		node68.addChild(node69);
		node67.addChild(node68);
		node65.addChild(node66);
		node65.addChild(node67);
		node64.addChild(node15);
		node64.addChild(node65);
		node63.addChild(node64);
		node62.addChild(node63);
		node62.addChild(node41);
		node61.addChild(node62);
		node60.addChild(node61);
		node60.addChild(node42);
		node59.addChild(node60);
		node58.addChild(node59);
		node56.addChild(node57);
		node56.addChild(node58);
		node55.addChild(node6);
		node55.addChild(node56);
		node54.addChild(node55);
		node53.addChild(node54);
		node53.addChild(node45);
		node52.addChild(node53);
		node51.addChild(node52);
		node51.addChild(node46);
		node50.addChild(node51);
		node49.addChild(node50);
		node4.addChild(node5);
		node4.addChild(node49);
		node2.addChild(node3);
		node2.addChild(node4);
		PackedNode node76 = factory.createPackedNode("S ::= (a S b S) .", 0, node1);
		NonterminalNode node77 = factory.createNonterminalNode("(a S b S)", 0, 0, 12);
		PackedNode node78 = factory.createPackedNode("(a S b S) ::= a S b S .", 11, node77);
		IntermediateNode node79 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 0, 11);
		PackedNode node80 = factory.createPackedNode("(a S b S) ::= a S b . S", 10, node79);
		IntermediateNode node81 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 0, 10);
		PackedNode node82 = factory.createPackedNode("(a S b S) ::= a S . b S", 1, node81);
		NonterminalNode node83 = factory.createNonterminalNode("S", 0, 1, 10);
		PackedNode node84 = factory.createPackedNode("S ::= a S .", 2, node83);
		node84.addChild(node6);
		node84.addChild(node56);
		PackedNode node85 = factory.createPackedNode("S ::= (a S b S) .", 1, node83);
		NonterminalNode node86 = factory.createNonterminalNode("(a S b S)", 0, 1, 10);
		PackedNode node87 = factory.createPackedNode("(a S b S) ::= a S b S .", 9, node86);
		IntermediateNode node88 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 1, 9);
		PackedNode node89 = factory.createPackedNode("(a S b S) ::= a S b . S", 8, node88);
		IntermediateNode node90 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 1, 8);
		PackedNode node91 = factory.createPackedNode("(a S b S) ::= a S . b S", 2, node90);
		NonterminalNode node92 = factory.createNonterminalNode("S", 0, 2, 8);
		PackedNode node93 = factory.createPackedNode("S ::= a S .", 3, node92);
		node93.addChild(node15);
		node93.addChild(node65);
		PackedNode node94 = factory.createPackedNode("S ::= (a S b S) .", 2, node92);
		NonterminalNode node95 = factory.createNonterminalNode("(a S b S)", 0, 2, 8);
		PackedNode node96 = factory.createPackedNode("(a S b S) ::= a S b S .", 7, node95);
		IntermediateNode node97 = factory.createIntermediateNode("(a S b S) ::= a S b . S", 2, 7);
		PackedNode node98 = factory.createPackedNode("(a S b S) ::= a S b . S", 6, node97);
		IntermediateNode node99 = factory.createIntermediateNode("(a S b S) ::= a S . b S", 2, 6);
		PackedNode node100 = factory.createPackedNode("(a S b S) ::= a S . b S", 3, node99);
		NonterminalNode node101 = factory.createNonterminalNode("S", 0, 3, 6);
		PackedNode node102 = factory.createPackedNode("S ::= a S .", 4, node101);
		node102.addChild(node24);
		node102.addChild(node74);
		node101.addChild(node102);
		node100.addChild(node15);
		node100.addChild(node101);
		node99.addChild(node100);
		node98.addChild(node99);
		node98.addChild(node37);
		node97.addChild(node98);
		node96.addChild(node97);
		node96.addChild(node38);
		node95.addChild(node96);
		node94.addChild(node95);
		node92.addChild(node93);
		node92.addChild(node94);
		node91.addChild(node6);
		node91.addChild(node92);
		node90.addChild(node91);
		node89.addChild(node90);
		node89.addChild(node41);
		node88.addChild(node89);
		node87.addChild(node88);
		node87.addChild(node42);
		node86.addChild(node87);
		node85.addChild(node86);
		node83.addChild(node84);
		node83.addChild(node85);
		node82.addChild(node3);
		node82.addChild(node83);
		node81.addChild(node82);
		node80.addChild(node81);
		node80.addChild(node45);
		node79.addChild(node80);
		node78.addChild(node79);
		node78.addChild(node46);
		node77.addChild(node78);
		node76.addChild(node77);
		node1.addChild(node2);
		node1.addChild(node76);
		return node1;
	}

}
