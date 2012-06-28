package ast;

import compiler.Visitor;


public class NumberNode extends ExprNode
{
    private int value;

    public NumberNode(Integer value, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.value = value.intValue();
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    public String getType()
    {
        return "int";
    }
}