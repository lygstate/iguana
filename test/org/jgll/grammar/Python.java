package org.jgll.grammar;

import java.util.Arrays;

import org.jgll.grammar.condition.ConditionType;
import org.jgll.grammar.condition.RegularExpressionCondition;
import org.jgll.grammar.symbol.CharacterClass;
import org.jgll.grammar.symbol.Character;
import org.jgll.grammar.symbol.CharacterRange;
import org.jgll.grammar.symbol.Keyword;
import org.jgll.grammar.symbol.Nonterminal;
import org.jgll.grammar.symbol.Rule;
import org.jgll.regex.Alt;
import org.jgll.regex.Group;
import org.jgll.regex.Plus;
import org.jgll.regex.Star;

import com.google.common.collect.Sets;

public class Python {

	public static Grammar grammar = 
		Grammar.builder()
		//ShortBytesChar ::= [\u0001-\u10FFFF] 
		.addRule(Rule.withHead(Nonterminal.builder("ShortBytesChar").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(1, 1114111).build())).addPostConditions(Sets.newHashSet(new RegularExpressionCondition(ConditionType.NOT_MATCH, Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(34, 34).build(), CharacterRange.builder(39, 39).build(), CharacterRange.builder(92, 92).build(), CharacterRange.builder(10, 10).build(), CharacterRange.builder(13, 13).build())).build())).build()))).build()).build())
		//IntPart ::= Digit+ 
		.addRule(Rule.withHead(Nonterminal.builder("IntPart").build()).addSymbol(Plus.builder(Nonterminal.builder("Digit").build()).build()).build())
		//LongBytesChar ::= [\u0001-\u10FFFF] 
		.addRule(Rule.withHead(Nonterminal.builder("LongBytesChar").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(1, 1114111).build())).addPostConditions(Sets.newHashSet(new RegularExpressionCondition(ConditionType.NOT_MATCH, Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build())).build()))).build()).build())
		//BinInteger ::= [0] ([b] | [B]) BinDigit+ 
		.addRule(Rule.withHead(Nonterminal.builder("BinInteger").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(48, 48).build())).build()).addSymbol(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(98, 98).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(66, 66).build())).build())).build()).addSymbol(Plus.builder(Nonterminal.builder("BinDigit").build()).build()).build())
		//Term ::= Factor (([/] | [*] | [%] | "//") Factor)* 
		.addRule(Rule.withHead(Nonterminal.builder("Term").build()).addSymbol(Nonterminal.builder("Factor").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(47, 47).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(37, 37).build())).build(), Keyword.builder(Arrays.asList(Character.builder(47).build(), Character.builder(47).build())).build())).build(), Nonterminal.builder("Factor").build())).build()).build()).build())
		//ImportStmt ::= ImportName 
		.addRule(Rule.withHead(Nonterminal.builder("ImportStmt").build()).addSymbol(Nonterminal.builder("ImportName").build()).build())
		//ImportStmt ::= ImportFrom 
		.addRule(Rule.withHead(Nonterminal.builder("ImportStmt").build()).addSymbol(Nonterminal.builder("ImportFrom").build()).build())
		//Parameters ::= [(] [T y p e d a r g s l i s t] [)] 
		.addRule(Rule.withHead(Nonterminal.builder("Parameters").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(40, 40).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(84, 84).build(), CharacterRange.builder(121, 121).build(), CharacterRange.builder(112, 112).build(), CharacterRange.builder(101, 101).build(), CharacterRange.builder(100, 100).build(), CharacterRange.builder(97, 97).build(), CharacterRange.builder(114, 114).build(), CharacterRange.builder(103, 103).build(), CharacterRange.builder(115, 115).build(), CharacterRange.builder(108, 108).build(), CharacterRange.builder(105, 105).build(), CharacterRange.builder(115, 115).build(), CharacterRange.builder(116, 116).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(41, 41).build())).build()).build())
		//ShortString ::= ["] ShortStringItem* ["] 
		.addRule(Rule.withHead(Nonterminal.builder("ShortString").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(34, 34).build())).build()).addSymbol(Star.builder(Nonterminal.builder("ShortStringItem").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(34, 34).build())).build()).build())
		//ShortString ::= ['] ShortStringItem* ['] 
		.addRule(Rule.withHead(Nonterminal.builder("ShortString").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(39, 39).build())).build()).addSymbol(Star.builder(Nonterminal.builder("ShortStringItem").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(39, 39).build())).build()).build())
		//DottedAsNames ::= DottedAsName ([,] DottedAsName)* 
		.addRule(Rule.withHead(Nonterminal.builder("DottedAsNames").build()).addSymbol(Nonterminal.builder("DottedAsName").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("DottedAsName").build())).build()).build()).build())
		//ShortStringItem ::= ShortStringChar 
		.addRule(Rule.withHead(Nonterminal.builder("ShortStringItem").build()).addSymbol(Nonterminal.builder("ShortStringChar").build()).build())
		//ShortStringItem ::= StringEscapeSeq 
		.addRule(Rule.withHead(Nonterminal.builder("ShortStringItem").build()).addSymbol(Nonterminal.builder("StringEscapeSeq").build()).build())
		//EncodingDecl ::= Name 
		.addRule(Rule.withHead(Nonterminal.builder("EncodingDecl").build()).addSymbol(Nonterminal.builder("Name").build()).build())
		//LongStringItem ::= LongStringChar 
		.addRule(Rule.withHead(Nonterminal.builder("LongStringItem").build()).addSymbol(Nonterminal.builder("LongStringChar").build()).build())
		//LongStringItem ::= StringEscapeSeq 
		.addRule(Rule.withHead(Nonterminal.builder("LongStringItem").build()).addSymbol(Nonterminal.builder("StringEscapeSeq").build()).build())
		//FloatNumber ::= ExponentFloat 
		.addRule(Rule.withHead(Nonterminal.builder("FloatNumber").build()).addSymbol(Nonterminal.builder("ExponentFloat").build()).build())
		//FloatNumber ::= PointFloat 
		.addRule(Rule.withHead(Nonterminal.builder("FloatNumber").build()).addSymbol(Nonterminal.builder("PointFloat").build()).build())
		//empty() ::= 
		.addRule(Rule.withHead(Nonterminal.builder("empty()").build()).build())
		//StringLiteral ::= StringPrefix? (LongString | ShortString) 
		.addRule(Rule.withHead(Nonterminal.builder("StringLiteral").build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("StringPrefix").build()).build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("LongString").build(), Nonterminal.builder("ShortString").build())).build()).build())
		//WhileStmt ::= "while" Test [:] Suite ("else" [:] Suite)? 
		.addRule(Rule.withHead(Nonterminal.builder("WhileStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(119).build(), Character.builder(104).build(), Character.builder(105).build(), Character.builder(108).build(), Character.builder(101).build())).build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Suite").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build()).build()).build())
		//SmallStmt ::= PassStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("PassStmt").build()).build())
		//SmallStmt ::= ExprStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("ExprStmt").build()).build())
		//SmallStmt ::= DelStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("DelStmt").build()).build())
		//SmallStmt ::= NonlocalStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("NonlocalStmt").build()).build())
		//SmallStmt ::= ImportStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("ImportStmt").build()).build())
		//SmallStmt ::= AssertStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("AssertStmt").build()).build())
		//SmallStmt ::= FlowStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("FlowStmt").build()).build())
		//SmallStmt ::= GlobalStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SmallStmt").build()).addSymbol(Nonterminal.builder("GlobalStmt").build()).build())
		//HexDigit ::= [a-f] 
		.addRule(Rule.withHead(Nonterminal.builder("HexDigit").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(97, 102).build())).build()).build())
		//HexDigit ::= [A-F] 
		.addRule(Rule.withHead(Nonterminal.builder("HexDigit").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(65, 70).build())).build()).build())
		//HexDigit ::= Digit 
		.addRule(Rule.withHead(Nonterminal.builder("HexDigit").build()).addSymbol(Nonterminal.builder("Digit").build()).build())
		//ImagNumber ::= (IntPart | FloatNumber) ([j] | [J]) 
		.addRule(Rule.withHead(Nonterminal.builder("ImagNumber").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("IntPart").build(), Nonterminal.builder("FloatNumber").build())).build()).addSymbol(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(106, 106).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(74, 74).build())).build())).build()).build())
		//Classdef ::= "class" Name ([(] Arglist? [)])? [:] Suite 
		.addRule(Rule.withHead(Nonterminal.builder("Classdef").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(99).build(), Character.builder(108).build(), Character.builder(97).build(), Character.builder(115).build(), Character.builder(115).build())).build()).addSymbol(Nonterminal.builder("Name").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(40, 40).build())).build(), org.jgll.regex.Opt.builder(Nonterminal.builder("Arglist").build()).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(41, 41).build())).build())).build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Suite").build()).build())
		//Stmt ::= CompoundStmt 
		.addRule(Rule.withHead(Nonterminal.builder("Stmt").build()).addSymbol(Nonterminal.builder("CompoundStmt").build()).build())
		//Stmt ::= SimpleStmt 
		.addRule(Rule.withHead(Nonterminal.builder("Stmt").build()).addSymbol(Nonterminal.builder("SimpleStmt").build()).build())
		//Keyword ::= "yield" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(121).build(), Character.builder(105).build(), Character.builder(101).build(), Character.builder(108).build(), Character.builder(100).build())).build()).build())
		//Keyword ::= "lambda" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(108).build(), Character.builder(97).build(), Character.builder(109).build(), Character.builder(98).build(), Character.builder(100).build(), Character.builder(97).build())).build()).build())
		//Keyword ::= "raise" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(114).build(), Character.builder(97).build(), Character.builder(105).build(), Character.builder(115).build(), Character.builder(101).build())).build()).build())
		//Keyword ::= "while" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(119).build(), Character.builder(104).build(), Character.builder(105).build(), Character.builder(108).build(), Character.builder(101).build())).build()).build())
		//Keyword ::= "not" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(110).build(), Character.builder(111).build(), Character.builder(116).build())).build()).build())
		//Keyword ::= "finally" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(105).build(), Character.builder(110).build(), Character.builder(97).build(), Character.builder(108).build(), Character.builder(108).build(), Character.builder(121).build())).build()).build())
		//Keyword ::= "and" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(110).build(), Character.builder(100).build())).build()).build())
		//Keyword ::= "or" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(111).build(), Character.builder(114).build())).build()).build())
		//Keyword ::= "class" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(99).build(), Character.builder(108).build(), Character.builder(97).build(), Character.builder(115).build(), Character.builder(115).build())).build()).build())
		//Keyword ::= "break" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(98).build(), Character.builder(114).build(), Character.builder(101).build(), Character.builder(97).build(), Character.builder(107).build())).build()).build())
		//Keyword ::= "as" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(115).build())).build()).build())
		//Keyword ::= "import" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(109).build(), Character.builder(112).build(), Character.builder(111).build(), Character.builder(114).build(), Character.builder(116).build())).build()).build())
		//Keyword ::= "from" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(114).build(), Character.builder(111).build(), Character.builder(109).build())).build()).build())
		//Keyword ::= "global" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(103).build(), Character.builder(108).build(), Character.builder(111).build(), Character.builder(98).build(), Character.builder(97).build(), Character.builder(108).build())).build()).build())
		//Keyword ::= "assert" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(115).build(), Character.builder(115).build(), Character.builder(101).build(), Character.builder(114).build(), Character.builder(116).build())).build()).build())
		//Keyword ::= "None" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(78).build(), Character.builder(111).build(), Character.builder(110).build(), Character.builder(101).build())).build()).build())
		//Keyword ::= "True" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(84).build(), Character.builder(114).build(), Character.builder(117).build(), Character.builder(101).build())).build()).build())
		//Keyword ::= "try" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(116).build(), Character.builder(114).build(), Character.builder(121).build())).build()).build())
		//Keyword ::= "False" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(70).build(), Character.builder(97).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build()).build())
		//Keyword ::= "except" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(120).build(), Character.builder(99).build(), Character.builder(101).build(), Character.builder(112).build(), Character.builder(116).build())).build()).build())
		//Keyword ::= "if" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(102).build())).build()).build())
		//Keyword ::= "return" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(114).build(), Character.builder(101).build(), Character.builder(116).build(), Character.builder(117).build(), Character.builder(114).build(), Character.builder(110).build())).build()).build())
		//Keyword ::= "else" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build()).build())
		//Keyword ::= "in" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(110).build())).build()).build())
		//Keyword ::= "is" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(115).build())).build()).build())
		//Keyword ::= "nonlocal" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(110).build(), Character.builder(111).build(), Character.builder(110).build(), Character.builder(108).build(), Character.builder(111).build(), Character.builder(99).build(), Character.builder(97).build(), Character.builder(108).build())).build()).build())
		//Keyword ::= "continue" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(99).build(), Character.builder(111).build(), Character.builder(110).build(), Character.builder(116).build(), Character.builder(105).build(), Character.builder(110).build(), Character.builder(117).build(), Character.builder(101).build())).build()).build())
		//Keyword ::= "with" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(119).build(), Character.builder(105).build(), Character.builder(116).build(), Character.builder(104).build())).build()).build())
		//Keyword ::= "for" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(111).build(), Character.builder(114).build())).build()).build())
		//Keyword ::= "pass" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(112).build(), Character.builder(97).build(), Character.builder(115).build(), Character.builder(115).build())).build()).build())
		//Keyword ::= "elif" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(105).build(), Character.builder(102).build())).build()).build())
		//Keyword ::= "def" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(100).build(), Character.builder(101).build(), Character.builder(102).build())).build()).build())
		//Keyword ::= "del" 
		.addRule(Rule.withHead(Nonterminal.builder("Keyword").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(100).build(), Character.builder(101).build(), Character.builder(108).build())).build()).build())
		//StringPrefix ::= [u] 
		.addRule(Rule.withHead(Nonterminal.builder("StringPrefix").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(117, 117).build())).build()).build())
		//StringPrefix ::= [U] 
		.addRule(Rule.withHead(Nonterminal.builder("StringPrefix").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(85, 85).build())).build()).build())
		//StringPrefix ::= [R] 
		.addRule(Rule.withHead(Nonterminal.builder("StringPrefix").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(82, 82).build())).build()).build())
		//ShortBytesitem ::= ShortBytesChar 
		.addRule(Rule.withHead(Nonterminal.builder("ShortBytesitem").build()).addSymbol(Nonterminal.builder("ShortBytesChar").build()).build())
		//ShortBytesitem ::= BytesEscapeSeq 
		.addRule(Rule.withHead(Nonterminal.builder("ShortBytesitem").build()).addSymbol(Nonterminal.builder("BytesEscapeSeq").build()).build())
		//CompIter ::= CompFor 
		.addRule(Rule.withHead(Nonterminal.builder("CompIter").build()).addSymbol(Nonterminal.builder("CompFor").build()).build())
		//CompIter ::= CompIf 
		.addRule(Rule.withHead(Nonterminal.builder("CompIter").build()).addSymbol(Nonterminal.builder("CompIf").build()).build())
		//StarExpr ::= [*] Expr 
		.addRule(Rule.withHead(Nonterminal.builder("StarExpr").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build()).addSymbol(Nonterminal.builder("Expr").build()).build())
		//NonzeroDigit ::= [1-9] 
		.addRule(Rule.withHead(Nonterminal.builder("NonzeroDigit").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(49, 57).build())).build()).build())
		//YieldExpr ::= "yield" YieldArg? 
		.addRule(Rule.withHead(Nonterminal.builder("YieldExpr").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(121).build(), Character.builder(105).build(), Character.builder(101).build(), Character.builder(108).build(), Character.builder(100).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("YieldArg").build()).build()).build())
		//ImportFrom ::= "from" (("..." | [.])+ | (("..." | [.])* DottedName)) "import" ([*] | ImportAsNames | ([(] ImportAsNames [)])) 
		.addRule(Rule.withHead(Nonterminal.builder("ImportFrom").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(114).build(), Character.builder(111).build(), Character.builder(109).build())).build()).addSymbol(Alt.builder(Arrays.asList(Plus.builder(Alt.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(46).build(), Character.builder(46).build(), Character.builder(46).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(46, 46).build())).build())).build()).build(), Group.builder(Arrays.asList(Star.builder(Alt.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(46).build(), Character.builder(46).build(), Character.builder(46).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(46, 46).build())).build())).build()).build(), Nonterminal.builder("DottedName").build())).build())).build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(109).build(), Character.builder(112).build(), Character.builder(111).build(), Character.builder(114).build(), Character.builder(116).build())).build()).addSymbol(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build(), Nonterminal.builder("ImportAsNames").build(), Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(40, 40).build())).build(), Nonterminal.builder("ImportAsNames").build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(41, 41).build())).build())).build())).build()).build())
		//DottedAsName ::= DottedName ("as" Name)? 
		.addRule(Rule.withHead(Nonterminal.builder("DottedAsName").build()).addSymbol(Nonterminal.builder("DottedName").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(115).build())).build(), Nonterminal.builder("Name").build())).build()).build()).build())
		//BytesPrefix ::= "bR" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(98).build(), Character.builder(82).build())).build()).build())
		//BytesPrefix ::= "rB" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(114).build(), Character.builder(66).build())).build()).build())
		//BytesPrefix ::= "Rb" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(82).build(), Character.builder(98).build())).build()).build())
		//BytesPrefix ::= [B] 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(66, 66).build())).build()).build())
		//BytesPrefix ::= "BR" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(66).build(), Character.builder(82).build())).build()).build())
		//BytesPrefix ::= "br" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(98).build(), Character.builder(114).build())).build()).build())
		//BytesPrefix ::= [b] 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(98, 98).build())).build()).build())
		//BytesPrefix ::= "Br" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(66).build(), Character.builder(114).build())).build()).build())
		//BytesPrefix ::= "RB" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(82).build(), Character.builder(66).build())).build()).build())
		//BytesPrefix ::= "rb" 
		.addRule(Rule.withHead(Nonterminal.builder("BytesPrefix").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(114).build(), Character.builder(98).build())).build()).build())
		//FlowStmt ::= YieldStmt 
		.addRule(Rule.withHead(Nonterminal.builder("FlowStmt").build()).addSymbol(Nonterminal.builder("YieldStmt").build()).build())
		//FlowStmt ::= RaiseStmt 
		.addRule(Rule.withHead(Nonterminal.builder("FlowStmt").build()).addSymbol(Nonterminal.builder("RaiseStmt").build()).build())
		//FlowStmt ::= BreakStmt 
		.addRule(Rule.withHead(Nonterminal.builder("FlowStmt").build()).addSymbol(Nonterminal.builder("BreakStmt").build()).build())
		//FlowStmt ::= ContinueStmt 
		.addRule(Rule.withHead(Nonterminal.builder("FlowStmt").build()).addSymbol(Nonterminal.builder("ContinueStmt").build()).build())
		//FlowStmt ::= ReturnStmt 
		.addRule(Rule.withHead(Nonterminal.builder("FlowStmt").build()).addSymbol(Nonterminal.builder("ReturnStmt").build()).build())
		//EscapeSeq ::= [\] [x] HexInteger HexInteger 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(120, 120).build())).build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).build())
		//EscapeSeq ::= [\] OctInteger OctInteger OctInteger 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(Nonterminal.builder("OctInteger").build()).addSymbol(Nonterminal.builder("OctInteger").build()).addSymbol(Nonterminal.builder("OctInteger").build()).build())
		//EscapeSeq ::= [\] [\] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).build())
		//EscapeSeq ::= [\] [t] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(116, 116).build())).build()).build())
		//EscapeSeq ::= [\] [a] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(97, 97).build())).build()).build())
		//EscapeSeq ::= [\] [r] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(114, 114).build())).build()).build())
		//EscapeSeq ::= [\] [n] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(110, 110).build())).build()).build())
		//EscapeSeq ::= [\] [v] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(118, 118).build())).build()).build())
		//EscapeSeq ::= [\] [b] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(98, 98).build())).build()).build())
		//EscapeSeq ::= [\] [f] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(102, 102).build())).build()).build())
		//EscapeSeq ::= [\] ['] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(39, 39).build())).build()).build())
		//EscapeSeq ::= [\] ["] 
		.addRule(Rule.withHead(Nonterminal.builder("EscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(34, 34).build())).build()).build())
		//CompoundStmt ::= WithStmt 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("WithStmt").build()).build())
		//CompoundStmt ::= WhileStmt 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("WhileStmt").build()).build())
		//CompoundStmt ::= Decorated 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("Decorated").build()).build())
		//CompoundStmt ::= Classdef 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("Classdef").build()).build())
		//CompoundStmt ::= ForStmt 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("ForStmt").build()).build())
		//CompoundStmt ::= TryStmt 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("TryStmt").build()).build())
		//CompoundStmt ::= IfStmt 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("IfStmt").build()).build())
		//CompoundStmt ::= Funcdef 
		.addRule(Rule.withHead(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("Funcdef").build()).build())
		//LambdefNocond ::= "lambda" Varargslist? [:] TestNocond 
		.addRule(Rule.withHead(Nonterminal.builder("LambdefNocond").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(108).build(), Character.builder(97).build(), Character.builder(109).build(), Character.builder(98).build(), Character.builder(100).build(), Character.builder(97).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Varargslist").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("TestNocond").build()).build())
		//Augassign ::= "**=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "//=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(47).build(), Character.builder(47).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "^=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(94).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "|=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(124).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "&=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(38).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= ">>=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(62).build(), Character.builder(62).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "/=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(47).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "-=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(45).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "+=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(43).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "*=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "<<=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(60).build(), Character.builder(60).build(), Character.builder(61).build())).build()).build())
		//Augassign ::= "%=" 
		.addRule(Rule.withHead(Nonterminal.builder("Augassign").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(37).build(), Character.builder(61).build())).build()).build())
		//Atom ::= [{] [D i c t o r s e t m a k e r] [}] 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(123, 123).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(68, 68).build(), CharacterRange.builder(105, 105).build(), CharacterRange.builder(99, 99).build(), CharacterRange.builder(116, 116).build(), CharacterRange.builder(111, 111).build(), CharacterRange.builder(114, 114).build(), CharacterRange.builder(115, 115).build(), CharacterRange.builder(101, 101).build(), CharacterRange.builder(116, 116).build(), CharacterRange.builder(109, 109).build(), CharacterRange.builder(97, 97).build(), CharacterRange.builder(107, 107).build(), CharacterRange.builder(101, 101).build(), CharacterRange.builder(114, 114).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(125, 125).build())).build()).build())
		//Atom ::= "True" 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(84).build(), Character.builder(114).build(), Character.builder(117).build(), Character.builder(101).build())).build()).build())
		//Atom ::= [[] [T e s t l i s t C o m p] []] 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(91, 91).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(84, 84).build(), CharacterRange.builder(101, 101).build(), CharacterRange.builder(115, 115).build(), CharacterRange.builder(116, 116).build(), CharacterRange.builder(108, 108).build(), CharacterRange.builder(105, 105).build(), CharacterRange.builder(115, 115).build(), CharacterRange.builder(116, 116).build(), CharacterRange.builder(67, 67).build(), CharacterRange.builder(111, 111).build(), CharacterRange.builder(109, 109).build(), CharacterRange.builder(112, 112).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(93, 93).build())).build()).build())
		//Atom ::= Number 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(Nonterminal.builder("Number").build()).build())
		//Atom ::= "..." 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(46).build(), Character.builder(46).build(), Character.builder(46).build())).build()).build())
		//Atom ::= "None" 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(78).build(), Character.builder(111).build(), Character.builder(110).build(), Character.builder(101).build())).build()).build())
		//Atom ::= Name 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(Nonterminal.builder("Name").build()).build())
		//Atom ::= [(] [Y i e l d E x p r | T e s t l i s t C o m p] [)] 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(40, 40).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(89, 89).build(), CharacterRange.builder(105, 105).build(), CharacterRange.builder(101, 101).build(), CharacterRange.builder(108, 108).build(), CharacterRange.builder(100, 100).build(), CharacterRange.builder(69, 69).build(), CharacterRange.builder(120, 120).build(), CharacterRange.builder(112, 112).build(), CharacterRange.builder(114, 114).build(), CharacterRange.builder(124, 124).build(), CharacterRange.builder(84, 84).build(), CharacterRange.builder(101, 101).build(), CharacterRange.builder(115, 115).build(), CharacterRange.builder(116, 116).build(), CharacterRange.builder(108, 108).build(), CharacterRange.builder(105, 105).build(), CharacterRange.builder(115, 115).build(), CharacterRange.builder(116, 116).build(), CharacterRange.builder(67, 67).build(), CharacterRange.builder(111, 111).build(), CharacterRange.builder(109, 109).build(), CharacterRange.builder(112, 112).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(41, 41).build())).build()).build())
		//Atom ::= String+ 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(Plus.builder(Nonterminal.builder("String").build()).build()).build())
		//Atom ::= "False" 
		.addRule(Rule.withHead(Nonterminal.builder("Atom").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(70).build(), Character.builder(97).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build()).build())
		//Varargslist ::= [*] Vfpdef? ([,] Vfpdef ([=] Test)?)* ([,] "**" Vfpdef)? 
		.addRule(Rule.withHead(Nonterminal.builder("Varargslist").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Vfpdef").build()).build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Vfpdef").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Vfpdef").build())).build()).build()).build())
		//Varargslist ::= Vfpdef ([=] Test)? ([,] Vfpdef ([=] Test)?)* ([,] (("**" Vfpdef) | ([*] Vfpdef? ([,] Vfpdef ([=] Test)?)* ([,] "**" Vfpdef)?))?)? 
		.addRule(Rule.withHead(Nonterminal.builder("Varargslist").build()).addSymbol(Nonterminal.builder("Vfpdef").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Vfpdef").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), org.jgll.regex.Opt.builder(Alt.builder(Arrays.asList(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Vfpdef").build())).build(), Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build(), org.jgll.regex.Opt.builder(Nonterminal.builder("Vfpdef").build()).build(), Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Vfpdef").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build()).build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Vfpdef").build())).build()).build())).build())).build()).build())).build()).build()).build())
		//Varargslist ::= "**" Vfpdef 
		.addRule(Rule.withHead(Nonterminal.builder("Varargslist").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build()).addSymbol(Nonterminal.builder("Vfpdef").build()).build())
		//Fraction ::= [.] Digit+ 
		.addRule(Rule.withHead(Nonterminal.builder("Fraction").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(46, 46).build())).build()).addSymbol(Plus.builder(Nonterminal.builder("Digit").build()).build()).build())
		//ForStmt ::= "for" Exprlist "in" TestList [:] Suite ("else" [:] Suite)? 
		.addRule(Rule.withHead(Nonterminal.builder("ForStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(111).build(), Character.builder(114).build())).build()).addSymbol(Nonterminal.builder("Exprlist").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(110).build())).build()).addSymbol(Nonterminal.builder("TestList").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Suite").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build()).build()).build())
		//Subscript ::= Test? [:] Test? Sliceop? 
		.addRule(Rule.withHead(Nonterminal.builder("Subscript").build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Test").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Test").build()).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Sliceop").build()).build()).build())
		//Subscript ::= Test 
		.addRule(Rule.withHead(Nonterminal.builder("Subscript").build()).addSymbol(Nonterminal.builder("Test").build()).build())
		//TestlistStarExpr ::= (Test | StarExpr) ([,] (Test | StarExpr))* [,]? 
		.addRule(Rule.withHead(Nonterminal.builder("TestlistStarExpr").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("Test").build(), Nonterminal.builder("StarExpr").build())).build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Alt.builder(Arrays.asList(Nonterminal.builder("Test").build(), Nonterminal.builder("StarExpr").build())).build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build()).build())
		//OrTest ::= AndTest ("or" AndTest)* 
		.addRule(Rule.withHead(Nonterminal.builder("OrTest").build()).addSymbol(Nonterminal.builder("AndTest").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(111).build(), Character.builder(114).build())).build(), Nonterminal.builder("AndTest").build())).build()).build()).build())
		//Expr ::= XorExpr ([|] XorExpr)* 
		.addRule(Rule.withHead(Nonterminal.builder("Expr").build()).addSymbol(Nonterminal.builder("XorExpr").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(124, 124).build())).build(), Nonterminal.builder("XorExpr").build())).build()).build()).build())
		//BytesEscapeSeq ::= EscapeSeq 
		.addRule(Rule.withHead(Nonterminal.builder("BytesEscapeSeq").build()).addSymbol(Nonterminal.builder("EscapeSeq").build()).build())
		//Power ::= Atom Trailer* ("**" Factor)? 
		.addRule(Rule.withHead(Nonterminal.builder("Power").build()).addSymbol(Nonterminal.builder("Atom").build()).addSymbol(Star.builder(Nonterminal.builder("Trailer").build()).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Factor").build())).build()).build()).build())
		//HexInteger ::= [0] ([x] | [X]) HexDigit+ 
		.addRule(Rule.withHead(Nonterminal.builder("HexInteger").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(48, 48).build())).build()).addSymbol(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(120, 120).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(88, 88).build())).build())).build()).addSymbol(Plus.builder(Nonterminal.builder("HexDigit").build()).build()).build())
		//NewLine ::= [\u000A \u000D 
		.addRule(Rule.withHead(Nonterminal.builder("NewLine").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(10, 10).build(), CharacterRange.builder(13, 13).build())).build()).build())
		//YieldStmt ::= YieldExpr 
		.addRule(Rule.withHead(Nonterminal.builder("YieldStmt").build()).addSymbol(Nonterminal.builder("YieldExpr").build()).build())
		//Digit ::= [0-9] 
		.addRule(Rule.withHead(Nonterminal.builder("Digit").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(48, 57).build())).build()).build())
		//Factor ::= ([-] | [+] | [~]) Factor 
		.addRule(Rule.withHead(Nonterminal.builder("Factor").build()).addSymbol(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(45, 45).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(43, 43).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(126, 126).build())).build())).build()).addSymbol(Nonterminal.builder("Factor").build()).build())
		//Factor ::= Power 
		.addRule(Rule.withHead(Nonterminal.builder("Factor").build()).addSymbol(Nonterminal.builder("Power").build()).build())
		//DelStmt ::= "del" Exprlist 
		.addRule(Rule.withHead(Nonterminal.builder("DelStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(100).build(), Character.builder(101).build(), Character.builder(108).build())).build()).addSymbol(Nonterminal.builder("Exprlist").build()).build())
		//LongString ::= """"" LongStringItem* """"" 
		.addRule(Rule.withHead(Nonterminal.builder("LongString").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(34).build(), Character.builder(34).build(), Character.builder(34).build())).build()).addSymbol(Star.builder(Nonterminal.builder("LongStringItem").build()).build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(34).build(), Character.builder(34).build(), Character.builder(34).build())).build()).build())
		//LongString ::= "'''" LongStringItem* "'''" 
		.addRule(Rule.withHead(Nonterminal.builder("LongString").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(39).build(), Character.builder(39).build(), Character.builder(39).build())).build()).addSymbol(Star.builder(Nonterminal.builder("LongStringItem").build()).build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(39).build(), Character.builder(39).build(), Character.builder(39).build())).build()).build())
		//Decorator ::= [@] DottedName ([(] Arglist? [)])? NewLine 
		.addRule(Rule.withHead(Nonterminal.builder("Decorator").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(64, 64).build())).build()).addSymbol(Nonterminal.builder("DottedName").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(40, 40).build())).build(), org.jgll.regex.Opt.builder(Nonterminal.builder("Arglist").build()).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(41, 41).build())).build())).build()).build()).addSymbol(Nonterminal.builder("NewLine").build()).build())
		//TestList ::= Test ([,] Test)* [,]? 
		.addRule(Rule.withHead(Nonterminal.builder("TestList").build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Test").build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build()).build())
		//Dictorsetmaker ::= Test (CompFor | (([,] Test)* [,]?)) 
		.addRule(Rule.withHead(Nonterminal.builder("Dictorsetmaker").build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("CompFor").build(), Group.builder(Arrays.asList(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Test").build())).build()).build(), org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build())).build())).build()).build())
		//Dictorsetmaker ::= Test [:] Test (CompFor | (([,] Test [:] Test)* [,]?)) 
		.addRule(Rule.withHead(Nonterminal.builder("Dictorsetmaker").build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("CompFor").build(), Group.builder(Arrays.asList(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Test").build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Test").build())).build()).build(), org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build())).build())).build()).build())
		//Decorators ::= Decorator+ 
		.addRule(Rule.withHead(Nonterminal.builder("Decorators").build()).addSymbol(Plus.builder(Nonterminal.builder("Decorator").build()).build()).build())
		//Funcdef ::= "def" Name Parameters ("->" Test)? [:] Suite 
		.addRule(Rule.withHead(Nonterminal.builder("Funcdef").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(100).build(), Character.builder(101).build(), Character.builder(102).build())).build()).addSymbol(Nonterminal.builder("Name").build()).addSymbol(Nonterminal.builder("Parameters").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(45).build(), Character.builder(62).build())).build(), Nonterminal.builder("Test").build())).build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Suite").build()).build())
		//Number ::= FloatNumber 
		.addRule(Rule.withHead(Nonterminal.builder("Number").build()).addSymbol(Nonterminal.builder("FloatNumber").build()).build())
		//Number ::= ImagNumber 
		.addRule(Rule.withHead(Nonterminal.builder("Number").build()).addSymbol(Nonterminal.builder("ImagNumber").build()).build())
		//Number ::= Integer 
		.addRule(Rule.withHead(Nonterminal.builder("Number").build()).addSymbol(Nonterminal.builder("Integer").build()).build())
		//BytesLiteral ::= BytesPrefix (ShortBytes | LongBytes) 
		.addRule(Rule.withHead(Nonterminal.builder("BytesLiteral").build()).addSymbol(Nonterminal.builder("BytesPrefix").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("ShortBytes").build(), Nonterminal.builder("LongBytes").build())).build()).build())
		//Tfpdef ::= Name ([:] Test)? 
		.addRule(Rule.withHead(Nonterminal.builder("Tfpdef").build()).addSymbol(Nonterminal.builder("Name").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Test").build())).build()).build()).build())
		//SimpleStmt ::= SmallStmt ([;] SmallStmt)* [;]? NewLine 
		.addRule(Rule.withHead(Nonterminal.builder("SimpleStmt").build()).addSymbol(Nonterminal.builder("SmallStmt").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(59, 59).build())).build(), Nonterminal.builder("SmallStmt").build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(59, 59).build())).build()).build()).addSymbol(Nonterminal.builder("NewLine").build()).build())
		//ArithExpr ::= Term (([-] | [+]) Term)* 
		.addRule(Rule.withHead(Nonterminal.builder("ArithExpr").build()).addSymbol(Nonterminal.builder("Term").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(45, 45).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(43, 43).build())).build())).build(), Nonterminal.builder("Term").build())).build()).build()).build())
		//CompIf ::= "if" TestNocond CompIter? 
		.addRule(Rule.withHead(Nonterminal.builder("CompIf").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(102).build())).build()).addSymbol(Nonterminal.builder("TestNocond").build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("CompIter").build()).build()).build())
		//Subscriptlist ::= Subscript ([,] Subscript)* [,]? 
		.addRule(Rule.withHead(Nonterminal.builder("Subscriptlist").build()).addSymbol(Nonterminal.builder("Subscript").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Subscript").build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build()).build())
		//WithItem ::= Test ("as" Expr)? 
		.addRule(Rule.withHead(Nonterminal.builder("WithItem").build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(115).build())).build(), Nonterminal.builder("Expr").build())).build()).build()).build())
		//PassStmt ::= "pass" 
		.addRule(Rule.withHead(Nonterminal.builder("PassStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(112).build(), Character.builder(97).build(), Character.builder(115).build(), Character.builder(115).build())).build()).build())
		//ShortBytes ::= ['] ShortBytesitem* ['] 
		.addRule(Rule.withHead(Nonterminal.builder("ShortBytes").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(39, 39).build())).build()).addSymbol(Star.builder(Nonterminal.builder("ShortBytesitem").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(39, 39).build())).build()).build())
		//ShortBytes ::= ["] ShortBytesitem* ["] 
		.addRule(Rule.withHead(Nonterminal.builder("ShortBytes").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(34, 34).build())).build()).addSymbol(Star.builder(Nonterminal.builder("ShortBytesitem").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(34, 34).build())).build()).build())
		//Exponent ::= ([e] | [E]) ([-] | [+])? Digit+ 
		.addRule(Rule.withHead(Nonterminal.builder("Exponent").build()).addSymbol(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(101, 101).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(69, 69).build())).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(45, 45).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(43, 43).build())).build())).build()).build()).addSymbol(Plus.builder(Nonterminal.builder("Digit").build()).build()).build())
		//TryStmt ::= "try" [:] Suite (((ExceptClause [:] Suite)+ ("else" [:] Suite)? ("finally" [:] Suite)?) | ("finally" [:] Suite)) 
		.addRule(Rule.withHead(Nonterminal.builder("TryStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(116).build(), Character.builder(114).build(), Character.builder(121).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Suite").build()).addSymbol(Alt.builder(Arrays.asList(Group.builder(Arrays.asList(Plus.builder(Group.builder(Arrays.asList(Nonterminal.builder("ExceptClause").build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build()).build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build()).build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(105).build(), Character.builder(110).build(), Character.builder(97).build(), Character.builder(108).build(), Character.builder(108).build(), Character.builder(121).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build()).build())).build(), Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(105).build(), Character.builder(110).build(), Character.builder(97).build(), Character.builder(108).build(), Character.builder(108).build(), Character.builder(121).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build())).build()).build())
		//LongStringChar ::= [\u0001-\u10FFFF] 
		.addRule(Rule.withHead(Nonterminal.builder("LongStringChar").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(1, 1114111).build())).addPostConditions(Sets.newHashSet(new RegularExpressionCondition(ConditionType.NOT_MATCH, Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build())).build()))).build()).build())
		//AndTest ::= NotTest ("and" NotTest)* 
		.addRule(Rule.withHead(Nonterminal.builder("AndTest").build()).addSymbol(Nonterminal.builder("NotTest").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(110).build(), Character.builder(100).build())).build(), Nonterminal.builder("NotTest").build())).build()).build()).build())
		//NonlocalStmt ::= "nonlocal" Name ([,] Name)* 
		.addRule(Rule.withHead(Nonterminal.builder("NonlocalStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(110).build(), Character.builder(111).build(), Character.builder(110).build(), Character.builder(108).build(), Character.builder(111).build(), Character.builder(99).build(), Character.builder(97).build(), Character.builder(108).build())).build()).addSymbol(Nonterminal.builder("Name").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Name").build())).build()).build()).build())
		//ContinueStmt ::= "continue" 
		.addRule(Rule.withHead(Nonterminal.builder("ContinueStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(99).build(), Character.builder(111).build(), Character.builder(110).build(), Character.builder(116).build(), Character.builder(105).build(), Character.builder(110).build(), Character.builder(117).build(), Character.builder(101).build())).build()).build())
		//Argument ::= Test [=] Test 
		.addRule(Rule.withHead(Nonterminal.builder("Argument").build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build()).addSymbol(Nonterminal.builder("Test").build()).build())
		//Argument ::= Test CompFor? 
		.addRule(Rule.withHead(Nonterminal.builder("Argument").build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("CompFor").build()).build()).build())
		//PointFloat ::= IntPart [.] 
		.addRule(Rule.withHead(Nonterminal.builder("PointFloat").build()).addSymbol(Nonterminal.builder("IntPart").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(46, 46).build())).build()).build())
		//PointFloat ::= IntPart? Fraction 
		.addRule(Rule.withHead(Nonterminal.builder("PointFloat").build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("IntPart").build()).build()).addSymbol(Nonterminal.builder("Fraction").build()).build())
		//ShiftExpr ::= ArithExpr (("<<" | ">>") ArithExpr)* 
		.addRule(Rule.withHead(Nonterminal.builder("ShiftExpr").build()).addSymbol(Nonterminal.builder("ArithExpr").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Alt.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(60).build(), Character.builder(60).build())).build(), Keyword.builder(Arrays.asList(Character.builder(62).build(), Character.builder(62).build())).build())).build(), Nonterminal.builder("ArithExpr").build())).build()).build()).build())
		//Test ::= Lambdef 
		.addRule(Rule.withHead(Nonterminal.builder("Test").build()).addSymbol(Nonterminal.builder("Lambdef").build()).build())
		//Test ::= OrTest ("if" OrTest "else" Test)? 
		.addRule(Rule.withHead(Nonterminal.builder("Test").build()).addSymbol(Nonterminal.builder("OrTest").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(102).build())).build(), Nonterminal.builder("OrTest").build(), Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build(), Nonterminal.builder("Test").build())).build()).build()).build())
		//Exprlist ::= (Expr | StarExpr) ([,] (Expr | StarExpr))* [,]? 
		.addRule(Rule.withHead(Nonterminal.builder("Exprlist").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("Expr").build(), Nonterminal.builder("StarExpr").build())).build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Alt.builder(Arrays.asList(Nonterminal.builder("Expr").build(), Nonterminal.builder("StarExpr").build())).build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build()).build())
		//ImportAsName ::= Name ("as" Name)? 
		.addRule(Rule.withHead(Nonterminal.builder("ImportAsName").build()).addSymbol(Nonterminal.builder("Name").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(115).build())).build(), Nonterminal.builder("Name").build())).build()).build()).build())
		//DecimalInteger ::= [0]+ 
		.addRule(Rule.withHead(Nonterminal.builder("DecimalInteger").build()).addSymbol(Plus.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(48, 48).build())).build()).build()).build())
		//DecimalInteger ::= NonzeroDigit Digit* 
		.addRule(Rule.withHead(Nonterminal.builder("DecimalInteger").build()).addSymbol(Nonterminal.builder("NonzeroDigit").build()).addSymbol(Star.builder(Nonterminal.builder("Digit").build()).build()).build())
		//GlobalStmt ::= "global" Name ([,] Name)* 
		.addRule(Rule.withHead(Nonterminal.builder("GlobalStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(103).build(), Character.builder(108).build(), Character.builder(111).build(), Character.builder(98).build(), Character.builder(97).build(), Character.builder(108).build())).build()).addSymbol(Nonterminal.builder("Name").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Name").build())).build()).build()).build())
		//ImportAsNames ::= ImportAsName ([,] ImportAsName)* [,]? 
		.addRule(Rule.withHead(Nonterminal.builder("ImportAsNames").build()).addSymbol(Nonterminal.builder("ImportAsName").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("ImportAsName").build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build()).build())
		//Typedargslist ::= [*] Tfpdef? ([,] Tfpdef ([=] Test)?)* ([,] "**" Tfpdef)? 
		.addRule(Rule.withHead(Nonterminal.builder("Typedargslist").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Tfpdef").build()).build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Tfpdef").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Tfpdef").build())).build()).build()).build())
		//Typedargslist ::= "**" Tfpdef 
		.addRule(Rule.withHead(Nonterminal.builder("Typedargslist").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build()).addSymbol(Nonterminal.builder("Tfpdef").build()).build())
		//Typedargslist ::= Tfpdef ([=] Test)? ([,] Tfpdef ([=] Test)?)* ([,] (("**" Tfpdef) | ([*] Tfpdef? ([,] Tfpdef ([=] Test)?)* ([,] "**" Tfpdef)?))?)? 
		.addRule(Rule.withHead(Nonterminal.builder("Typedargslist").build()).addSymbol(Nonterminal.builder("Tfpdef").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Tfpdef").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), org.jgll.regex.Opt.builder(Alt.builder(Arrays.asList(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Tfpdef").build())).build(), Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build(), org.jgll.regex.Opt.builder(Nonterminal.builder("Tfpdef").build()).build(), Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Tfpdef").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build()).build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Tfpdef").build())).build()).build())).build())).build()).build())).build()).build()).build())
		//OctInteger ::= [0] ([o] | [O]) OctDigit+ 
		.addRule(Rule.withHead(Nonterminal.builder("OctInteger").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(48, 48).build())).build()).addSymbol(Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(111, 111).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(79, 79).build())).build())).build()).addSymbol(Plus.builder(Nonterminal.builder("OctDigit").build()).build()).build())
		//Decorated ::= Decorators (Classdef | Funcdef) 
		.addRule(Rule.withHead(Nonterminal.builder("Decorated").build()).addSymbol(Nonterminal.builder("Decorators").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("Classdef").build(), Nonterminal.builder("Funcdef").build())).build()).build())
		//Integer ::= BinInteger 
		.addRule(Rule.withHead(Nonterminal.builder("Integer").build()).addSymbol(Nonterminal.builder("BinInteger").build()).build())
		//Integer ::= OctInteger 
		.addRule(Rule.withHead(Nonterminal.builder("Integer").build()).addSymbol(Nonterminal.builder("OctInteger").build()).build())
		//Integer ::= DecimalInteger 
		.addRule(Rule.withHead(Nonterminal.builder("Integer").build()).addSymbol(Nonterminal.builder("DecimalInteger").build()).build())
		//Integer ::= HexInteger 
		.addRule(Rule.withHead(Nonterminal.builder("Integer").build()).addSymbol(Nonterminal.builder("HexInteger").build()).build())
		//AssertStmt ::= "assert" Test ([,] Test)? 
		.addRule(Rule.withHead(Nonterminal.builder("AssertStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(115).build(), Character.builder(115).build(), Character.builder(101).build(), Character.builder(114).build(), Character.builder(116).build())).build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Test").build())).build()).build()).build())
		//TestlistComp ::= (Test | StarExpr) (CompFor | (([,] (Test | StarExpr))* [,]?)) 
		.addRule(Rule.withHead(Nonterminal.builder("TestlistComp").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("Test").build(), Nonterminal.builder("StarExpr").build())).build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("CompFor").build(), Group.builder(Arrays.asList(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Alt.builder(Arrays.asList(Nonterminal.builder("Test").build(), Nonterminal.builder("StarExpr").build())).build())).build()).build(), org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build())).build())).build()).build())
		//Sliceop ::= [:] Test? 
		.addRule(Rule.withHead(Nonterminal.builder("Sliceop").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Test").build()).build()).build())
		//YieldArg ::= TestList 
		.addRule(Rule.withHead(Nonterminal.builder("YieldArg").build()).addSymbol(Nonterminal.builder("TestList").build()).build())
		//YieldArg ::= "from" Test 
		.addRule(Rule.withHead(Nonterminal.builder("YieldArg").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(114).build(), Character.builder(111).build(), Character.builder(109).build())).build()).addSymbol(Nonterminal.builder("Test").build()).build())
		//BreakStmt ::= "break" 
		.addRule(Rule.withHead(Nonterminal.builder("BreakStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(98).build(), Character.builder(114).build(), Character.builder(101).build(), Character.builder(97).build(), Character.builder(107).build())).build()).build())
		//IfStmt ::= "if" Test [:] Suite ("elif" Test [:] Suite)* ("else" [:] Suite)? 
		.addRule(Rule.withHead(Nonterminal.builder("IfStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(102).build())).build()).addSymbol(Nonterminal.builder("Test").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Suite").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(105).build(), Character.builder(102).build())).build(), Nonterminal.builder("Test").build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build()).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(108).build(), Character.builder(115).build(), Character.builder(101).build())).build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build(), Nonterminal.builder("Suite").build())).build()).build()).build())
		//LongBytesItem ::= BytesEscapeSeq 
		.addRule(Rule.withHead(Nonterminal.builder("LongBytesItem").build()).addSymbol(Nonterminal.builder("BytesEscapeSeq").build()).build())
		//LongBytesItem ::= LongBytesChar 
		.addRule(Rule.withHead(Nonterminal.builder("LongBytesItem").build()).addSymbol(Nonterminal.builder("LongBytesChar").build()).build())
		//Vfpdef ::= Name 
		.addRule(Rule.withHead(Nonterminal.builder("Vfpdef").build()).addSymbol(Nonterminal.builder("Name").build()).build())
		//ReturnStmt ::= "return" TestList? 
		.addRule(Rule.withHead(Nonterminal.builder("ReturnStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(114).build(), Character.builder(101).build(), Character.builder(116).build(), Character.builder(117).build(), Character.builder(114).build(), Character.builder(110).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("TestList").build()).build()).build())
		//NotTest ::= "not" NotTest 
		.addRule(Rule.withHead(Nonterminal.builder("NotTest").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(110).build(), Character.builder(111).build(), Character.builder(116).build())).build()).addSymbol(Nonterminal.builder("NotTest").build()).build())
		//NotTest ::= Comparison 
		.addRule(Rule.withHead(Nonterminal.builder("NotTest").build()).addSymbol(Nonterminal.builder("Comparison").build()).build())
		//EvalInput ::= TestList NewLine* 
		.addRule(Rule.withHead(Nonterminal.builder("EvalInput").build()).addSymbol(Nonterminal.builder("TestList").build()).addSymbol(Star.builder(Nonterminal.builder("NewLine").build()).build()).build())
		//TestNocond ::= OrTest 
		.addRule(Rule.withHead(Nonterminal.builder("TestNocond").build()).addSymbol(Nonterminal.builder("OrTest").build()).build())
		//TestNocond ::= LambdefNocond 
		.addRule(Rule.withHead(Nonterminal.builder("TestNocond").build()).addSymbol(Nonterminal.builder("LambdefNocond").build()).build())
		//Suite ::= NewLine Stmt+ 
		.addRule(Rule.withHead(Nonterminal.builder("Suite").build()).addSymbol(Nonterminal.builder("NewLine").build()).addSymbol(Plus.builder(Nonterminal.builder("Stmt").build()).build()).build())
		//Suite ::= SimpleStmt 
		.addRule(Rule.withHead(Nonterminal.builder("Suite").build()).addSymbol(Nonterminal.builder("SimpleStmt").build()).build())
		//CompOp ::= "!=" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(33).build(), Character.builder(61).build())).build()).build())
		//CompOp ::= [<] 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(60, 60).build())).build()).build())
		//CompOp ::= [>] 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(62, 62).build())).build()).build())
		//CompOp ::= "in" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(110).build())).build()).build())
		//CompOp ::= "is" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(115).build())).build()).build())
		//CompOp ::= ">=" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(62).build(), Character.builder(61).build())).build()).build())
		//CompOp ::= "==" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(61).build(), Character.builder(61).build())).build()).build())
		//CompOp ::= "<=" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(60).build(), Character.builder(61).build())).build()).build())
		//CompOp ::= "<>" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(60).build(), Character.builder(62).build())).build()).build())
		//CompOp ::= "is" "not" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(115).build())).build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(110).build(), Character.builder(111).build(), Character.builder(116).build())).build()).build())
		//CompOp ::= "not" "in" 
		.addRule(Rule.withHead(Nonterminal.builder("CompOp").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(110).build(), Character.builder(111).build(), Character.builder(116).build())).build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(110).build())).build()).build())
		//RaiseStmt ::= "raise" (Test ("from" Test)?)? 
		.addRule(Rule.withHead(Nonterminal.builder("RaiseStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(114).build(), Character.builder(97).build(), Character.builder(105).build(), Character.builder(115).build(), Character.builder(101).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Nonterminal.builder("Test").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(114).build(), Character.builder(111).build(), Character.builder(109).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build()).build()).build())
		//StringEscapeSeq ::= [\] [u] HexInteger HexInteger HexInteger HexInteger HexInteger HexInteger HexInteger HexInteger 
		.addRule(Rule.withHead(Nonterminal.builder("StringEscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(117, 117).build())).build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).build())
		//StringEscapeSeq ::= [\] [u] HexInteger HexInteger HexInteger HexInteger 
		.addRule(Rule.withHead(Nonterminal.builder("StringEscapeSeq").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(92, 92).build())).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(117, 117).build())).build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).addSymbol(Nonterminal.builder("HexInteger").build()).build())
		//StringEscapeSeq ::= EscapeSeq 
		.addRule(Rule.withHead(Nonterminal.builder("StringEscapeSeq").build()).addSymbol(Nonterminal.builder("EscapeSeq").build()).build())
		//AndExpr ::= ShiftExpr ([&] ShiftExpr)* 
		.addRule(Rule.withHead(Nonterminal.builder("AndExpr").build()).addSymbol(Nonterminal.builder("ShiftExpr").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(38, 38).build())).build(), Nonterminal.builder("ShiftExpr").build())).build()).build()).build())
		//XorExpr ::= AndExpr ([^] AndExpr)* 
		.addRule(Rule.withHead(Nonterminal.builder("XorExpr").build()).addSymbol(Nonterminal.builder("AndExpr").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(94, 94).build())).build(), Nonterminal.builder("AndExpr").build())).build()).build()).build())
		//FileInput ::= (NewLine | Stmt)* 
		.addRule(Rule.withHead(Nonterminal.builder("FileInput").build()).addSymbol(Star.builder(Alt.builder(Arrays.asList(Nonterminal.builder("NewLine").build(), Nonterminal.builder("Stmt").build())).build()).build()).build())
		//String ::= StringLiteral 
		.addRule(Rule.withHead(Nonterminal.builder("String").build()).addSymbol(Nonterminal.builder("StringLiteral").build()).build())
		//String ::= BytesLiteral 
		.addRule(Rule.withHead(Nonterminal.builder("String").build()).addSymbol(Nonterminal.builder("BytesLiteral").build()).build())
		//Comparison ::= Expr (CompOp Expr)* 
		.addRule(Rule.withHead(Nonterminal.builder("Comparison").build()).addSymbol(Nonterminal.builder("Expr").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Nonterminal.builder("CompOp").build(), Nonterminal.builder("Expr").build())).build()).build()).build())
		//ExceptClause ::= "except" (Test ("as" Name)?)? 
		.addRule(Rule.withHead(Nonterminal.builder("ExceptClause").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(101).build(), Character.builder(120).build(), Character.builder(99).build(), Character.builder(101).build(), Character.builder(112).build(), Character.builder(116).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Nonterminal.builder("Test").build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(97).build(), Character.builder(115).build())).build(), Nonterminal.builder("Name").build())).build()).build())).build()).build()).build())
		//LongBytes ::= """"" LongBytesItem* """"" 
		.addRule(Rule.withHead(Nonterminal.builder("LongBytes").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(34).build(), Character.builder(34).build(), Character.builder(34).build())).build()).addSymbol(Star.builder(Nonterminal.builder("LongBytesItem").build()).build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(34).build(), Character.builder(34).build(), Character.builder(34).build())).build()).build())
		//LongBytes ::= "'''" LongBytesItem* "'''" 
		.addRule(Rule.withHead(Nonterminal.builder("LongBytes").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(39).build(), Character.builder(39).build(), Character.builder(39).build())).build()).addSymbol(Star.builder(Nonterminal.builder("LongBytesItem").build()).build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(39).build(), Character.builder(39).build(), Character.builder(39).build())).build()).build())
		//ImportName ::= "import" DottedAsNames 
		.addRule(Rule.withHead(Nonterminal.builder("ImportName").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(109).build(), Character.builder(112).build(), Character.builder(111).build(), Character.builder(114).build(), Character.builder(116).build())).build()).addSymbol(Nonterminal.builder("DottedAsNames").build()).build())
		//OctDigit ::= [0-7] 
		.addRule(Rule.withHead(Nonterminal.builder("OctDigit").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(48, 55).build())).build()).build())
		//BinDigit ::= [0] 
		.addRule(Rule.withHead(Nonterminal.builder("BinDigit").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(48, 48).build())).build()).build())
		//BinDigit ::= [1] 
		.addRule(Rule.withHead(Nonterminal.builder("BinDigit").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(49, 49).build())).build()).build())
		//Arglist ::= (Argument [,])* (([*] Test ([,] Argument)* ([,] "**" Test)?) | (Argument [,]?) | ("**" Test)) 
		.addRule(Rule.withHead(Nonterminal.builder("Arglist").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(Nonterminal.builder("Argument").build(), CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build())).build()).build()).addSymbol(Alt.builder(Arrays.asList(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(42, 42).build())).build(), Nonterminal.builder("Test").build(), Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("Argument").build())).build()).build(), org.jgll.regex.Opt.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Test").build())).build()).build())).build(), Group.builder(Arrays.asList(Nonterminal.builder("Argument").build(), org.jgll.regex.Opt.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build()).build())).build(), Group.builder(Arrays.asList(Keyword.builder(Arrays.asList(Character.builder(42).build(), Character.builder(42).build())).build(), Nonterminal.builder("Test").build())).build())).build()).build())
		//SingleInput ::= NewLine 
		.addRule(Rule.withHead(Nonterminal.builder("SingleInput").build()).addSymbol(Nonterminal.builder("NewLine").build()).build())
		//SingleInput ::= SimpleStmt 
		.addRule(Rule.withHead(Nonterminal.builder("SingleInput").build()).addSymbol(Nonterminal.builder("SimpleStmt").build()).build())
		//SingleInput ::= CompoundStmt NewLine 
		.addRule(Rule.withHead(Nonterminal.builder("SingleInput").build()).addSymbol(Nonterminal.builder("CompoundStmt").build()).addSymbol(Nonterminal.builder("NewLine").build()).build())
		//DottedName ::= Name ([.] Name)* 
		.addRule(Rule.withHead(Nonterminal.builder("DottedName").build()).addSymbol(Nonterminal.builder("Name").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(46, 46).build())).build(), Nonterminal.builder("Name").build())).build()).build()).build())
		//ShortStringChar ::= [\u0001-\u10FFFF] 
		.addRule(Rule.withHead(Nonterminal.builder("ShortStringChar").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(1, 1114111).build())).addPostConditions(Sets.newHashSet(new RegularExpressionCondition(ConditionType.NOT_MATCH, Alt.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(34, 34).build(), CharacterRange.builder(39, 39).build(), CharacterRange.builder(92, 92).build(), CharacterRange.builder(10, 10).build(), CharacterRange.builder(13, 13).build())).build())).build()))).build()).build())
		//WithStmt ::= "with" WithItem ([,] WithItem)* [:] Suite 
		.addRule(Rule.withHead(Nonterminal.builder("WithStmt").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(119).build(), Character.builder(105).build(), Character.builder(116).build(), Character.builder(104).build())).build()).addSymbol(Nonterminal.builder("WithItem").build()).addSymbol(Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(44, 44).build())).build(), Nonterminal.builder("WithItem").build())).build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Suite").build()).build())
		//ExponentFloat ::= (IntPart | PointFloat) Exponent 
		.addRule(Rule.withHead(Nonterminal.builder("ExponentFloat").build()).addSymbol(Alt.builder(Arrays.asList(Nonterminal.builder("IntPart").build(), Nonterminal.builder("PointFloat").build())).build()).addSymbol(Nonterminal.builder("Exponent").build()).build())
		//Trailer ::= [(] Arglist? [)] 
		.addRule(Rule.withHead(Nonterminal.builder("Trailer").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(40, 40).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Arglist").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(41, 41).build())).build()).build())
		//Trailer ::= [[] Subscriptlist []] 
		.addRule(Rule.withHead(Nonterminal.builder("Trailer").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(91, 91).build())).build()).addSymbol(Nonterminal.builder("Subscriptlist").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(93, 93).build())).build()).build())
		//Trailer ::= [.] Name 
		.addRule(Rule.withHead(Nonterminal.builder("Trailer").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(46, 46).build())).build()).addSymbol(Nonterminal.builder("Name").build()).build())
		//CompFor ::= "for" Exprlist "in" OrTest CompIter? 
		.addRule(Rule.withHead(Nonterminal.builder("CompFor").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(102).build(), Character.builder(111).build(), Character.builder(114).build())).build()).addSymbol(Nonterminal.builder("Exprlist").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(105).build(), Character.builder(110).build())).build()).addSymbol(Nonterminal.builder("OrTest").build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("CompIter").build()).build()).build())
		//Lambdef ::= "lambda" Varargslist? [:] Test 
		.addRule(Rule.withHead(Nonterminal.builder("Lambdef").build()).addSymbol(Keyword.builder(Arrays.asList(Character.builder(108).build(), Character.builder(97).build(), Character.builder(109).build(), Character.builder(98).build(), Character.builder(100).build(), Character.builder(97).build())).build()).addSymbol(org.jgll.regex.Opt.builder(Nonterminal.builder("Varargslist").build()).build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(58, 58).build())).build()).addSymbol(Nonterminal.builder("Test").build()).build())
		//ExprStmt ::= TestlistStarExpr ((Augassign (TestList | YieldExpr)) | ([=] (TestlistStarExpr | YieldExpr))*) 
		.addRule(Rule.withHead(Nonterminal.builder("ExprStmt").build()).addSymbol(Nonterminal.builder("TestlistStarExpr").build()).addSymbol(Alt.builder(Arrays.asList(Group.builder(Arrays.asList(Nonterminal.builder("Augassign").build(), Alt.builder(Arrays.asList(Nonterminal.builder("TestList").build(), Nonterminal.builder("YieldExpr").build())).build())).build(), Star.builder(Group.builder(Arrays.asList(CharacterClass.builder(Arrays.asList(CharacterRange.builder(61, 61).build())).build(), Alt.builder(Arrays.asList(Nonterminal.builder("TestlistStarExpr").build(), Nonterminal.builder("YieldExpr").build())).build())).build()).build())).build()).build())
		//Name ::= [a-z A-Z _] [a-z A-Z 0-9 _]* 
		.addRule(Rule.withHead(Nonterminal.builder("Name").build()).addSymbol(CharacterClass.builder(Arrays.asList(CharacterRange.builder(97, 122).build(), CharacterRange.builder(65, 90).build(), CharacterRange.builder(95, 95).build())).build()).addSymbol(Star.builder(CharacterClass.builder(Arrays.asList(CharacterRange.builder(97, 122).build(), CharacterRange.builder(65, 90).build(), CharacterRange.builder(48, 57).build(), CharacterRange.builder(95, 95).build())).build()).build()).build())
		.build();
}
