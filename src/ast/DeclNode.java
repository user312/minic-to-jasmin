package ast;

import it.m2j.IdType;
import compiler.*;


public class DeclNode extends ExprNode
{

    private VarNode vNode;

    //private VariableSymbol symbol = null;

    public DeclNode(VarNode e, IdType type, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        vNode = e;
        vNode.setType(type);
    }   
    
    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public VarNode getVar()
    {
    	return vNode;
    }
    
    public String toString()
    {
        return vNode.toString();
    }
}