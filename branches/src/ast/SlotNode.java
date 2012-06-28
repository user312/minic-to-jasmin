package ast;

import compiler.*;


public abstract class SlotNode extends Node
{
    protected Modifier access;
    protected String type;
    protected String name;

    public SlotNode(String name, Modifier access, String type, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.access = access;
        this.type = type;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Modifier getAccess()
    {
            return access;
    }
}