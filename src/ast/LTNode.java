package ast;

import compiler.Visitor;

public class LTNode extends BinaryNode {

	public LTNode(Node left, Node right, int lineNumber, int colNumber) {
		super(left, right, lineNumber, colNumber);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object accept(Visitor v) {
		// TODO Auto-generated method stub
		return null;
	}

}
