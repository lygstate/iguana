package org.jgll.util.visualization;

import org.jgll.grammar.GrammarSlotRegistry;
import org.jgll.sppf.NonPackedNode;
import org.jgll.sppf.PackedNode;
import org.jgll.sppf.SPPFNode;

public abstract class ToDot {
	
	private GrammarSlotRegistry registry;
	
	public ToDot(GrammarSlotRegistry registry) {
		this.registry = registry;
	}
	
	protected String getId(SPPFNode node) {
		
		if (node instanceof NonPackedNode) {
			return registry.getId(node.getGrammarSlot()) + "_" + node.getLeftExtent() + "_" + node.getRightExtent();
		} else {			
			PackedNode packedNode = (PackedNode)node;
			return  getId(packedNode.getParent())  + "_" + registry.getId(packedNode.getGrammarSlot()) + "_" + packedNode.getPivot();			
		}
	}
}
