package compiler;

import java.util.ArrayList;

import ast.AddNode;
import ast.AndNode;
import ast.ArgNode;
import ast.ArrayCallNode;
import ast.ArrayNewNode;
import ast.ArrayNode;
import ast.ArraySizeNode;
import ast.AssignNode;
import ast.BlockNode;
import ast.BoolNode;
import ast.CastNode;
import ast.DeclNode;
import ast.DivNode;
import ast.EqNode;
import ast.FloatNode;
import ast.FuncCallNode;
import ast.FunctionExtNode;
import ast.FunctionNode;
import ast.GETNode;
import ast.GTNode;
import ast.IfElseNode;
import ast.IfNode;
import ast.IntNode;
import ast.LETNode;
import ast.LTNode;
import ast.ListNode;
import ast.ModNode;
import ast.MulNode;
import ast.NotEqNode;
import ast.NotNode;
import ast.NullNode;
import ast.OrNode;
import ast.ReturnNode;
import ast.SignNode;
import ast.SimpleVarNode;
import ast.StringNode;
import ast.SubNode;
import ast.WhileNode;
import aux.*;

public class SymbolTableConstructor extends Visitor
{
    //pointer to the current symbol table
    private SymbolTable sTable;
    private int varCounter;
    private String className;

    /**
     * Create a new Symbol table constructor
     * @param out PrintWriter to be used for output
     */
    public SymbolTableConstructor(String className, int registerCounter)
    {
        sTable = new SymbolTable();
        varCounter = registerCounter;
        this.className = className;
    }

    /**
     * returns the current symbol table. Should be called once the visit is complete
     * to return the constructed symbol table
     * @return
     */
    public SymbolTable getSymbolTable()
    {
        return sTable;
    }

    //visit a list node, visit its children
    public Object visit(ListNode node)
    {
        node.visitChildren(this);
        return null;
    }

    //visit a block node and it to the symbol table
    public Object visit(BlockNode node)
    {
    	//visit the statements in the block node
        node.visitChildren(this);

        return null;
    }

    /**
     * visit a variable declaration node in the ast, add the variable to the symbol table
     * @param node
     * @return
     */
    public Object visit(DeclNode node)
    {    	    	    	
    	//Setting variable descriptor
    	SymbolDesc varDesc = (SymbolDesc) node.visitVar(this);
    	varDesc.setType(node.getType());
    	
    	if (sTable.putVariable(node.getName(), varDesc) == false)
    		error("Variable already declared: ", node);
        
    	return null;	
    }

    /**
     * visit a method definition node and add the method, its parameters and any variables in its body
     * to the symbol table
     * @param node a FunctionNode to visit
     * @return null
     */
    public Object visit(FunctionNode node)
    {    	    	
    	if (sTable.get(node.getName()) != null)
    		error("Function '" + node.getName() + "' already declared.", node);    
    	else
    	{
    		String name = node.getName();
    		
    		//function parameters must start with 0
    		int oldCounter = varCounter;
    		
    		varCounter = 0;
    		
    		node.visitParams(this);
    		
    		//restore the counter
    		varCounter = oldCounter;
    		
    		String fullPath = name + "/" + this.className;
    		sTable.putFunction(name, fullPath, node.getType(), functionParams, node.getDimension());
    		
        	node.visitBody(this);        	        	
    	}

        return null;
    }

    public Object visit(FunctionExtNode node)
    {
    	if (sTable.get(node.getName()) != null)
    		error("Function '" + node.getName() + "' already declared.", node);    
    	else
    	{
    		node.visitParams(this);
    		
    		sTable.putFunction(node.getName(), node.getClassName(), node.getType(), functionParams, node.getDimension());
    	}
    	
    	return null;
    }
    
    private ArrayList<NodeInfo> functionParams = new ArrayList<NodeInfo>();

    /**
     * visits a node representing a single parameter to a function, adding it to the symbol table
     * @param node an ArgNode to visit
     * @return null
     */
    public Object visit(ArgNode node)
    {   	
    	IdType type = node.getType();    	
    	int dim = node.getDimension();

    	NodeInfo info = new NodeInfo(type, dim);

    	functionParams.add(info); 

    	SymbolDesc varDesc = new SymbolDesc();
    	varDesc.setVariableSymbol(type, node.getDimension(), 1, varCounter);
    	    	
    	if (sTable.putVariable(node.getName(), varDesc) == false)
    		error("Variable already declared: ", node);
    	else
    		varCounter++;

    	return type;
    }

    public Object visit(IfNode node)
    {
        node.visitThen(this);
        return null;
    }
    
	public Object visit(IfElseNode node) 
	{
		node.visitThen(this);
		node.visitElse(this);
		
		return null;
	}    
		
	public Object visit(WhileNode node) {
		
		node.visitWhile(this);
		
		return null;
	}

    public Object visit(SimpleVarNode node)
    {
        SymbolDesc varDesc = new SymbolDesc();
        varDesc.setVariableSymbol(node.getType(), 0, node.getBlockNumber(), varCounter);    	        
        
        varCounter++;
        
        return varDesc;    	
    }
    
    public Object visit(ArrayNode node)
    {
        SymbolDesc varDesc = new SymbolDesc();
        varDesc.setVariableSymbol(node.getType(), node.getDimension(), node.getBlockNumber(), varCounter);
    	
        varCounter++;
        
        return varDesc;
    }
    
    //all the below methods do nothing, since none of these nodes define new symbols
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public Object visit(StringNode node)
    {
        return null;
    }
    public Object visit(OrNode node)
    {
        return null;
    }
    
    public Object visit(AssignNode node)
    {
        return null;
    }
    public Object visit(DivNode node)
    {
        return null;
    }
    public Object visit(AddNode node)
    {
        return null;
    }
    public Object visit(SubNode node)
    {
        return null;
    }
    public Object visit(FuncCallNode node)
    {
        return null;
    }
    public Object visit(AndNode node)
    {
        return null;
    }
    public Object visit(IntNode node)
    {
        return null;
    }
    public Object visit(EqNode node)
    {
        return null;
    }
    public Object visit(ReturnNode node)
    {
        return null;
    }
    public Object visit(MulNode node)
    {
        return null;
    }
    public Object visit(BoolNode node)
    {
        return null;
    }
    public Object visit(NotEqNode node)
    {
        return null;
    }
    public Object visit(NotNode node)
    {
        return null;
    }
	
	public Object visit(FloatNode node) 
	{	
		return null;
	}

	public Object visit(CastNode node) 
	{	
		return null;
	}

	public Object visit(ModNode node) 
	{
		return null;
	}

	public Object visit(LTNode node) 
	{
		return null;
	}

	public Object visit(LETNode node) 
	{
		return null;
	}
	
	public Object visit(GTNode node) 
	{
		return null;
	}

	public Object visit(GETNode node) 
	{
		return null;
	}

	public Object visit(SignNode node) 
	{
		return null;
	}

	public Object visit(NullNode letNode) 
	{
		return null;
	}
	
	public Object visit(ArrayNewNode node) 
	{	
		return null;
	}

	public Object visit(ArrayCallNode node) 
	{
		return null;
	}

	public Object visit(ArraySizeNode node) 
	{
		return null;
	}
}