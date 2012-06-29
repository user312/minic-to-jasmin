package ast;

import compiler.*;
//import utils.ParamUtils;


public class FuncCallNode extends ExprNode implements InvocNode
{    
    private String methodName;
    private ListNode params;

    //the static method to call
    //private MethodSymbol symbol = null;

    public FuncCallNode(String methodName, ListNode params, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);        
        this.methodName = methodName;
        this.params = params; //No Params
    }

    public FuncCallNode(String methodName, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);        
        this.methodName = methodName;
        this.params = null;
    }

    
    public Object accept(Visitor v)
    {
        return v.visit(this);
    }
    public String toString()
    {
    	return ""; //SISTEMARE
        //return target + "." + methodName + "(" + ParamUtils.makeSourceList(params) + ")";
    }

    public String getName()
    {
        return methodName;
    }

//    public void setSymbol(MethodSymbol symbol)
//    {
//        this.symbol = symbol;
//    }

    public Object[] visitParams(Visitor v)
    {
        if (params == null)
            return new Object[0];

        Node[] paramList = params.toArray();
        Object[] result = new Object[paramList.length];
        for (int i = 0; i < paramList.length; ++i)
            result[i] = paramList[i].accept(v);

        return result;
    }

    public Node[] getParams()
    {
        if (params == null)
            return new Node[0];

        return params.toArray();
    }

    public int getNumberOfParams()
    {
        if (params == null)
            return 0;

        return params.toArray().length;
    }

//    public MethodSymbol getSymbol()
//    {
//        return symbol;
//    }
}