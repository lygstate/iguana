package org.jgll.grammar.slot.firstfollow;

import static org.jgll.util.CollectionsUtil.*;
import static org.junit.Assert.*;

import org.jgll.grammar.symbol.Range;
import org.jgll.regex.RegularExpression;
import org.junit.Test;

public class Test1 {

	@Test
	public void test1() {
		
		RegularExpression r1 = Range.in(1, 3);
		RegularExpression r2 = Range.in(5, 7);
		
		TreeMapFollowTest test = new TreeMapFollowTest(set(r1, r2));
		
		assertTrue(test.test(1));
		assertTrue(test.test(2));
		assertTrue(test.test(3));
		assertFalse(test.test(4));
		assertTrue(test.test(5));
		assertTrue(test.test(6));
		assertTrue(test.test(7));
		assertFalse(test.test(8));
	}
	
	@Test
	public void test2() {
		
		RegularExpression r1 = Range.in(1, 7);
		RegularExpression r2 = Range.in(3, 6);
		RegularExpression r3 = Range.in(4, 11);
		
		TreeMapFollowTest test = new TreeMapFollowTest(set(r1, r2, r3));
		
		assertTrue(test.test(1));
		assertTrue(test.test(2));
		assertTrue(test.test(3));
		assertTrue(test.test(4));
		assertTrue(test.test(5));
		assertTrue(test.test(6));
		assertTrue(test.test(7));
		assertTrue(test.test(8));
		assertTrue(test.test(9));
		assertTrue(test.test(10));
		assertTrue(test.test(11));
	}
	
	
	@Test
	public void test3() {
		
		RegularExpression r1 = Range.in(1, 2);
		RegularExpression r2 = Range.in(4, 7);
		RegularExpression r3 = Range.in(5, 11);
		RegularExpression r4 = Range.in(13, 18);
		
		TreeMapFollowTest test = new TreeMapFollowTest(set(r1, r2, r3, r4));
	
		assertTrue(test.test(1));
		assertTrue(test.test(2));
		assertFalse(test.test(3));
		assertTrue(test.test(4));
		assertTrue(test.test(5));
		assertTrue(test.test(6));
		assertTrue(test.test(7));
		assertTrue(test.test(8));
		assertTrue(test.test(9));
		assertTrue(test.test(10));
		assertTrue(test.test(11));
		assertFalse(test.test(12));
		assertTrue(test.test(13));
		assertTrue(test.test(14));
		assertTrue(test.test(15));
		assertTrue(test.test(16));
		assertTrue(test.test(17));
		assertTrue(test.test(18));
		assertFalse(test.test(19));
		assertFalse(test.test(20));
	}
}