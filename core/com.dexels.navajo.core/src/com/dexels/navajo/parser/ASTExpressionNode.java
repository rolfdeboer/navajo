/* Generated By:JJTree: Do not edit this line. ASTExpressionNode.java */

package com.dexels.navajo.parser;



public final class ASTExpressionNode extends SimpleNode {
    public ASTExpressionNode(int id) {
        super(id);
    }

    public final Object interpret() throws TMLExpressionException {

        Object a = jjtGetChild(0).interpret();

        return a;
    }

}
