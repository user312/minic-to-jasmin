package ast;

import compiler.*;


public class ArgNode extends Node
{
    private String type;
    private String name;

    private ParamSymbol symbol = null;

    public ArgNode(String name, String type, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.name = name;
        this.type = type;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return type + " " + name;
    }
    public void setSymbol(ParamSymbol symbol)
    {
        this.symbol = symbol;
    }
    public ParamSymbol getSymbol()
    {
        return symbol;
    }
}