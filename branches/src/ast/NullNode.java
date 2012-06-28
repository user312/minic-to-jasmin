package ast;

import compiler.Visitor;


public class NullNode extends ExprNode
{
    public NullNode(int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return "null";
    }

    public String getType()
    {
        return "null";
    }
}