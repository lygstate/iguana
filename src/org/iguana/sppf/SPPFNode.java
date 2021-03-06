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

package org.iguana.sppf;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iguana.grammar.slot.GrammarSlot;
import org.iguana.traversal.SPPFVisitor;

/**
 * 
 * @author Ali Afroozeh
 * 
 */
public interface SPPFNode {
	
	public SPPFNode getChildAt(int index);
	
	public List<? extends SPPFNode> getChildren();
		
	public boolean isAmbiguous();

	public int childrenCount();

	public GrammarSlot getGrammarSlot();

	public void accept(SPPFVisitor visitAction);
	
	/**
	 * Compares this SPPFNode with the given node and their 
	 * children. 
	 * 
	 * @return true if this node is equal to the given node, and
	 * 				its children are equal to the corresponding
	 * 				(same order) children of the given node. 
	 * 
	 * Note: The standard equals method on SPPF nodes only compares
	 * 		 nodes based on their labels, ignoring their children. 
	 * 		 While parsing, the standard equals method is sufficient, 
	 * 		 but for comparing parse trees, the client should use deepEquals.
	 * 		 The deepEquals method is mainly meant for testing of the parser output
	 *       and should not be used while parsing.
	 */
	default boolean deepEquals(SPPFNode node) {
		return deepEquals(this, node, new HashSet<>());
	}
	
	static boolean deepEquals(SPPFNode node1, SPPFNode node2, Set<SPPFNode> visited) {

		if (visited.contains(node1)) 
			return true;
		
		visited.add(node1);
				
		if (!node1.equals(node2))
			return false;
		
		if (node1.childrenCount() != node2.childrenCount())
			return false;
		
		if (node1.isAmbiguous() ^ node2.isAmbiguous())
			return false;
		
		if (node1.isAmbiguous() && node2.isAmbiguous())
			return compareAmbiguousNodes(node1, node2, visited);
		
		Iterator<? extends SPPFNode> node1It = node1.getChildren().iterator();		
		Iterator<? extends SPPFNode> node2It = node2.getChildren().iterator();
		
		while (node1It.hasNext() && node2It.hasNext()) {
			SPPFNode node1Child = node1It.next();
			SPPFNode node2Child = node2It.next();
			if (!deepEquals(node1Child, node2Child, visited)) {
				return false;
			}
		}
		
		return true;
	}	
	
	static boolean compareAmbiguousNodes(SPPFNode node1, SPPFNode node2, Set<SPPFNode> visited) {
		
		Iterator<? extends SPPFNode> thisIt = node1.getChildren().iterator();

		outer:
		while(thisIt.hasNext()) {
			SPPFNode thisChild = thisIt.next();
			Iterator<? extends SPPFNode> otherIt = node2.getChildren().iterator();
			while(otherIt.hasNext()) {
				SPPFNode otherChild = otherIt.next();
				if(deepEquals(thisChild, otherChild, visited)) {
					continue outer;
				}
			} 
			return false;
		}
		
		return true;
	}
	
	default boolean isDummy() {
		return false;
	}

}
