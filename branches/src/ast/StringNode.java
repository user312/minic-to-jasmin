package ast;

import compiler.Visitor;
import utils.ParamUtils;


public class StringNode extends ExprNode
{
    private String value;

    public StringNode(String value, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.value = value;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return "\"" + ParamUtils.escape(value) + "\"";
    }

    public String getType()
    {
        return "String";
    }
}