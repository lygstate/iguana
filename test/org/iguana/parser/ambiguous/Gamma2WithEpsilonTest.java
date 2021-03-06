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

package org.iguana.parser.ambiguous;

import static org.junit.Assert.*;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Character;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Rule;
import org.iguana.parser.GLLParser;
import org.iguana.parser.ParseResult;
import org.iguana.parser.ParserFactory;
import org.iguana.util.Configuration;
import org.iguana.util.Input;
import org.iguana.util.ParseStatistics;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 *  S ::= S S S 
 *      | S S 
 *      | b
 *      | epsilon
 * 
 * 
 * @author Ali Afroozeh
 *
 */
public class Gamma2WithEpsilonTest {

    private Grammar grammar;
    
    private Nonterminal S = Nonterminal.withName("S");
    private Character b = Character.from('b');
    
    @Before
    public void init() {
        
        Rule r1 = Rule.withHead(S).addSymbols(S, S, S).build();
        Rule r2 = Rule.withHead(S).addSymbols(S, S).build();
        Rule r3 = Rule.withHead(S).addSymbols(b).build();
        Rule r4 = Rule.withHead(S).build();
        
        grammar = Grammar.builder().addRules(r1, r2, r3, r4).build();
    }
    
    @Test
    public void testParsers1() {
        Input input = Input.fromString(getBs(5));
        GLLParser parser = ParserFactory.getParser(Configuration.DEFAULT, input, grammar);
        ParseResult result = parser.parse(input, grammar, Nonterminal.withName("S"));
        assertTrue(result.isParseSuccess());
        ParseStatistics parseStatistics = result.asParseSuccess().getStatistics();
		assertEquals(129, parseStatistics.getDescriptorsCount());
		assertEquals(21, parseStatistics.getNonterminalNodesCount());
		assertEquals(21, parseStatistics.getIntermediateNodesCount());
		assertEquals(11, parseStatistics.getTerminalNodesCount());
		assertEquals(179, parseStatistics.getPackedNodesCount());
    }
    
    @Test
    public void testParsers2() {
        Input input = Input.fromString(getBs(10));
        GLLParser parser = ParserFactory.getParser(Configuration.DEFAULT, input, grammar);
        ParseResult result = parser.parse(input, grammar, Nonterminal.withName("S"));
        assertTrue(result.isParseSuccess());
        ParseStatistics parseStatistics = result.asParseSuccess().getStatistics();
		assertEquals(374, parseStatistics.getDescriptorsCount());
		assertEquals(66, parseStatistics.getNonterminalNodesCount());
		assertEquals(66, parseStatistics.getIntermediateNodesCount());
		assertEquals(21, parseStatistics.getTerminalNodesCount());
		assertEquals(879, parseStatistics.getPackedNodesCount());
    }
    
    @Test
    public void testParsers3() {
        Input input = Input.fromString(getBs(100));
        GLLParser parser = ParserFactory.getParser(Configuration.DEFAULT, input, grammar);
        ParseResult result = parser.parse(input, grammar, Nonterminal.withName("S"));
        assertTrue(result.isParseSuccess());
        ParseStatistics parseStatistics = result.asParseSuccess().getStatistics();
		assertEquals(26159, parseStatistics.getDescriptorsCount());
		assertEquals(5151, parseStatistics.getNonterminalNodesCount());
		assertEquals(5151, parseStatistics.getIntermediateNodesCount());
		assertEquals(201, parseStatistics.getTerminalNodesCount());
		assertEquals(530754, parseStatistics.getPackedNodesCount());
    }
    
    private String getBs(int size) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++) {
            sb.append("b");
        }
        return sb.toString();
    }
}
