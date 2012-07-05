package compiler;

import ast.*;

import it.m2j.IdType;
import it.m2j.SymbolDesc;

import java.io.*;
import java.util.ArrayList;


public class SymbolTableConstructor extends Visitor
{
    //pointer to the current symbol table
    private SymbolTable sTable = new SymbolTable();    

    //the class that we are currently checking
    //private ClassSymbol curClass = null;

    /**
     * Create a new Symbol table constructor
     * @param out PrintWriter to be used for output
     */
    public SymbolTableConstructor(PrintWriter out)
    {
        super(out);               

//        //add the built in types to the type table
//        sTable.put(new TypeSymbol("unknown"));
//        sTable.put(new TypeSymbol("void"));
//        sTable.put(new TypeSymbol("int"));
//        sTable.put(new TypeSymbol("boolean"));
//        sTable.put(new TypeSymbol("null"));
//
//        //Object
//        SymbolTable objectST = new SymbolTable(sTable, true);
//
//        MethodSymbol objectInit = new MethodSymbol("Object", "void", new Symbol[0], Modifier.PUBLIC, null, null, true);
//        objectInit.setMethodNumber(NumberGenerator.getInstance().getMethodNumber(objectInit));
//        objectST.put(objectInit);
//
//        MethodSymbol objectToString = new MethodSymbol("toString", "String", new Symbol[0], Modifier.PUBLIC, null, null, false);
//        objectToString.setMethodNumber(NumberGenerator.getInstance().getMethodNumber(objectToString));
//        objectST.put(objectToString);
//
//        ClassSymbol object = new ClassSymbol("Object", null, objectST);
//        objectInit.setOwner(object);
//        objectToString.setOwner(object);
//        sTable.put(object);
//
//        //String
//        SymbolTable stringST = new SymbolTable(sTable, true);
//
//
//        MethodSymbol stringInit = new MethodSymbol("String", "void", new Symbol[0], Modifier.PUBLIC, null, null, true);
//        stringInit.setMethodNumber(NumberGenerator.getInstance().getMethodNumber(stringInit));
//        stringST.put(stringInit);
//
//        MethodSymbol stringInitString = new MethodSymbol("String", "void", new Symbol[] {new ParamSymbol("str", "String", null)}, Modifier.PUBLIC, null, null, true);
//        stringInitString.setMethodNumber(NumberGenerator.getInstance().getMethodNumber(stringInitString));
//        stringST.put(stringInitString);
//
//        MethodSymbol stringToString = new MethodSymbol("toString", "String", new Symbol[0], Modifier.PUBLIC, null, null, false);
//        stringToString.setMethodNumber(NumberGenerator.getInstance().getMethodNumber(stringToString));
//        stringST.put(stringToString);
//
//        MethodSymbol stringLength = new MethodSymbol("length", "int", new Symbol[0], Modifier.PUBLIC, null, null, false);
//        stringLength.setMethodNumber(NumberGenerator.getInstance().getMethodNumber(stringLength));
//        stringST.put(stringLength);
//
//        MethodSymbol stringCharAt = new MethodSymbol("charAt", "String", new Symbol[] {new ParamSymbol("index", "int", null)}, Modifier.PUBLIC, null, null, false);
//        stringCharAt.setMethodNumber(NumberGenerator.getInstance().getMethodNumber(stringCharAt));
//        stringST.put(stringCharAt);
//
//        stringST.put(new StringFieldSymbol());
//
//        ClassSymbol string = new ClassSymbol("String", object, stringST);
//        stringInit.setOwner(string);
//        stringInitString.setOwner(string);
//        stringToString.setOwner(string);
//        stringLength.setOwner(string);
//        stringCharAt.setOwner(string);
//
//        sTable.put(string);
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

    //visit a compound statement node and it to the symbol table
    public Object visit(BlockNode node)
    {
//        //create a symbol table for the compound statement
//        sTable = new SymbolTable(sTable);
//
//        //create an entry in the symbol table for the cs
//        ScopeSymbol scope = ScopeSymbol.getInstance(sTable, node);

    	//visit the statements in the compound statement
        node.visitChildren(this);
//
//        //restore the symbol table
//        sTable = sTable.getParent();
//
//        //add the compound statement
//        sTable.put(scope);
//
//        node.setSymbol(scope);

        return null;
    }

    /**
     * visit a variable declaration node in the ast, add the variable to the symbol table
     * @param node
     * @return
     */
    public Object visit(DeclNode node)
    {    	    	    	
    	SymbolDesc varDesc = (SymbolDesc) node.visitVar(this);
    	
    	if (sTable.putVariable(node.getName(), varDesc.getType(), varDesc.getBlock()) == false)
    		error("Variable already declared: ", node);
        
    	return null;	
    }

    /**
     * visit a method definition node and add the method, its parameters and any variables in its body
     * to the symbol table
     * @param node a MethodNode to visit
     * @return null
     */
    public Object visit(FunctionNode node)
    {    	
        visitFunction(node);

        return null;
    }

    private ArrayList<IdType> functionParams = new ArrayList<IdType>();
    
    //visit a function definition
    private void visitFunction(FunctionNode node)
    {
    	//TODO: fix the param list
    	if (sTable.get(node.getName()) != null)
    		error("Function '" + node.getName() + "' already declared.", node);    
    	else
    	{
    		node.visitParams(this);
    		
    		sTable.putFunction(node.getName(), node.getType(), functionParams);
    		
        	node.visitBody(this);        	        	
    	}
    }

    /**
     * visits a node representing a single parameter to a function, adding it to the symbol table
     * @param node an ArgNode to visit
     * @return null
     */
    public Object visit(ArgNode node)
    {
    	IdType type = node.getType();
    	
    	functionParams.add(type);
    	
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
    public Object visit(PrintNode node)
    {
        return null;
    }
    public Object visit(VarNode node)
    {
        SymbolDesc varDesc = new SymbolDesc();
        varDesc.setVariableSymbol(node.getType(), node.getBlockNumber());
    	
        return varDesc;
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
    public Object visit(NegNode node)
    {
        return null;
    }
    public Object visit(NotNode node)
    {
        return null;
    }
    public Object visit(NewNode node)
    {
        return null;
    }
	
	public Object visit(FloatNode node) {
		
		return null;
	}

	public Object visit(CastNode node) {
		
		return null;
	}

	public Object visit(ModNode node) {

		return null;
	}

	@Override
	public Object visit(LTNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LETNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(GTNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(GETNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(SignNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(NullNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ArrayNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}
}