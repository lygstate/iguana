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

package org.iguana.datadependent.ast;

import org.iguana.datadependent.env.IEvaluatorContext;
import org.iguana.datadependent.traversal.IAbstractASTVisitor;

public class VariableDeclaration extends AbstractAST {
	
	private static final long serialVersionUID = 1L;

	static public Object defaultValue = new Object() {};
	
	private final String name;
	private final Expression expression;
	
	VariableDeclaration(String name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}
	
	VariableDeclaration(String name) {
		this(name, null);
	}
	
	public String getName() {
		return name;
	}
	
	public Expression getExpression() {
		return expression;
	}

	@Override
	public Object interpret(IEvaluatorContext ctx) {
		Object value = defaultValue;
		if (expression != null) {
			value = expression.interpret(ctx);
		}
		ctx.declareVariable(name, value);
		return null;
	}
	
	@Override
	public String getConstructorCode() {
		return "AST.varDecl(" + "\"" + name + "\"" + (expression != null? "," + expression.getConstructorCode() : "") + ")";
	}
	
	@Override
	public String toString() {
		return expression != null? String.format( "var %s = %s", name, expression) 
				: String.format("var %s", name);
	}

	@Override
	public <T> T accept(IAbstractASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
