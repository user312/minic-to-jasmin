package ast;

import compiler.*;


public class FieldNode extends SlotNode implements NodeWithValue
{
    private ExprNode value;

    private FieldSymbol symbol = null;

    public FieldNode(String name, Modifier access, String type, ExprNode value, int lineNumber, int colNumber)
    {
        super(name, access, type, lineNumber, colNumber);
        this.value = value;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String getType()
    {
        return type;
    }

    public Object visitValue(Visitor v)
    {
        return value.accept(v);
    }

    public String toString()
    {
        return access + " " + type + " " + name + " = " + value;
    }
    public void setSymbol(FieldSymbol symbol)
    {
        this.symbol = symbol;
    }
    public FieldSymbol getSymbol()
    {
        return symbol;
    }
}