package compiler;

import it.m2j.IdType;

import java.io.PrintWriter;

import ast.*;

public class SemanticChecker extends Visitor
{
    //pointer to the global symbol table
    private SymbolTable sTable;    

    //the class that we are currently checking
    //private ClassSymbol curClass = null;

    public SemanticChecker(SymbolTable sTable, PrintWriter out)
    {
        super(out);
        this.sTable = sTable;
    }

    public Object visit(DeclNode node)
    {    	
    	return null;
    }

    public Object visit(FieldNode node)
    {
    	return null;
    }

    //true if we are checking the lhs of an assignment
    private boolean inAssignment = false;
    
    public Object visit(AssignNode node)
    {
    	IdType type1 = sTable.getVariableType(node.getVar(),node.getBlock());    	
    	IdType type2 = node.getValue().getType();    	

    	if (type1 == IdType.ERR || type2 == IdType.ERR)
        	error("Error in assignment",node);

        // If operands are of two different types and they aren't numerics print error
        else if ( !type1.toString().equals(type2.toString()) ) 
        {                 
        	if (!(type1.IsNumeric() == true && type2.IsNumeric() == true)) //If both are numerics don't alert: PROM
        		error("Can't assign " + type2.toString() + " to " + type1.toString(), node );
            else if(type1 == IdType.INT && type2 == IdType.FLOAT)
            	error("Can't assign " + type2.toString() + " to " + type1.toString() + ". Required cast.", node );                	                
        }

    	return null;
    }

    public Object visit(VarNode node)
    {
//        //check for 'this'
//        if (node.isThis())
//        {
//            if (inAssignment)
//                error("Attempt to assign to 'this': ", node);
//
//            node.setType(curClass);
//            return curClass;
//        }
//
//        //check the variable is declared (in the symbol table)
//        Symbol symbol = curTable.get(node.getName());
//        if (symbol == null)
//        {
//            //check if its an inherited field
//            symbol = curClass.getField(node.getName());
//            if (symbol == null)
//            {
//                error("Variable has not been declared: ", node);
//                return sTable.get("unknown");
//            }
//        }
//
//        if (symbol instanceof FieldSymbol)
//        {
//            //if the variable is a field
//
//            //check the target has a field with the given parameters
//            FieldSymbol field = curClass.getField(node.getName());
//            if (field == null)
//            {
//                error("Variable has not been declared: ", node);
//                return sTable.get("unknown");
//            }
//
//            checkAccess(field, node);
//            Symbol type = sTable.get(field.getType());
//
//            if (type == null)
//                return sTable.get("unknown");
//
//            node.setType(type);
//            node.setFieldSymbol(field);
//
//            return type;
//        }
//
//        //check a variable being declared is not used in its own declaration
//        if (symbol == curDeclVar)
//        {
//            error("Variable has not been declared: ", node);
//            return sTable.get(symbol.getType());
//        }
//        //if it is a local variable check it is not a forward reference
//        if (symbol instanceof VariableSymbol)
//        {
//            if (symbol.getLineNumber() > node.getLineNumber())
//            {
//                error("Variable has not been declared: ", node);
//                return sTable.get("unknown");
//            }
//            else if (symbol.getLineNumber() == node.getLineNumber() && symbol.getColumnNumber() >= node.getColumnNumber())
//            {
//                error("Variable has not been declared: ", node);
//                return sTable.get("unknown");
//            }
//        }
//        //check the kind
//        if (!(symbol instanceof VarSymbol || symbol instanceof FieldSymbol))
//        {
//            error("Variable has incorrect kind: '" + symbol.getKind() + "'. Variable: ", node);
//            return sTable.get("unknown");
//        }
//
//        //store the variable's static type in the node
//        //won't be null since the type has already been checked when the var was declared
//        node.setType(sTable.get(symbol.getType()));
//        node.setSymbol((VarSymbol)symbol);
//
//        //return the variable's static type
//        return node.getTypeSymbol();
    	
    	return null;
    }

    public Object visit(BlockNode node)
    {    	
        node.visitChildren(this);
    	
    	return null;
    }

    public Object visit(NewNode node)
    {
//        checkSuperCall(node);
//
//        Symbol className = sTable.get(node.getClassName());
//
//        //check the class exists
//        if (className == null || !(className instanceof ClassSymbol))
//        {
//            error("No such class: '" + node.getClassName() + "'. ", node);
//            return sTable.get("unknown");
//        }
//        ClassSymbol classSymbol = (ClassSymbol)className;
//
//        //check the parameters
//        String[] typeNames = ParamUtils.makeTypeArray(node.visitParams(this));
//
//        //check an approriate constructor exists
//        MethodSymbol methodSymbol = classSymbol.getConstructor(typeNames);
//        if (methodSymbol == null)
//        {
//            error("Constructor not found: '" + classSymbol.getName() + " (" + ParamUtils.makeList(typeNames) + ")'. ", node);
//            return(className);
//        }
//
//        //check we have access to it
//        checkAccess(methodSymbol, node);
//
//        node.setSymbol(methodSymbol);
//        node.setClassSymbol(classSymbol);
//
//        return className;
    	return null;
    }

    //only checks the method exists statically, the actual method to call is
    //determined at run time
    public Object visit(FuncCallNode node)
    {
//        checkSuperCall(node);
//
//        //check the parameters
//        String[] typeNames = ParamUtils.makeTypeArray(node.visitParams(this));
//
//        //check the target
//        Symbol target = (Symbol)node.visitTarget(this);
//        if (!(target instanceof ClassSymbol))
//        {
//            error("Method not defined: '" + node.getName() + " (" + ParamUtils.makeList(typeNames) + ")'. ", node);
//            return sTable.get("unknown");
//        }
//
//        //check the target has a method with the given parameters
//        ClassSymbol targetClass = (ClassSymbol)target;
//        MethodSymbol method = targetClass.getMethod(node.getName(), typeNames);
//        if (method == null)
//        {
//            error("Method not defined: '" + node.getName() + " (" + ParamUtils.makeList(typeNames) + ")'. ", node);
//            return sTable.get("unknown");
//        }
//
//        //check we have access
//        checkAccess(method, node);
//
//        //set the static method in the node
//        node.setSymbol(method);
//
//        //return the method's return type
//        Symbol returnType = sTable.get(method.getType());
//        if (returnType == null)
//            return sTable.get("unknown"); //the error will be caught when the method is resolved
//        else
//            return returnType;
    	return null;
    }

    public Object visit(ArgNode node)
    {
//        //check that the parameter's type exists
//        Symbol type = sTable.get(node.getType());
//        Symbol arg = curTable.get(node.getName());
//
//        //variable should have been added to symbol table in the first pass
//        if (arg == null)
//            throw new RuntimeException("Parameter should be in symbol table: " + node.getName());
//
//        //check that the type exists
//        if (type == null)
//        {
//            error("Type '" + node.getType() + "' does not exist: ", node);
//            arg.resolveType(sTable.get("unknown"));
//        }
//        else
//            arg.resolveType(type);
//
//        return sTable.get("void");
    	return null;
    }

    //the return type for the current node
    private IdType returnType = null;

    //weather there is a return type in the method
    private boolean returned = false;

    public Object visit(FunctionNode node)
    {
    	    	
//        //get the symbol and set up the symbol table
//        MethodSymbol symbol = node.getSymbol();
//        curTable = symbol.getSymbolTable();
//
//        //check the return type exists
//        Symbol returnType = sTable.get(node.getType());
//        if (returnType == null)
//        {
//            error("Type '" + node.getType() + "' does not exist: ", node);
//            symbol.resolveType(sTable.get("unknown"));
//        }
//        else
//            symbol.resolveType(returnType);
//
//
        visitParamsAndBody(node, returnType);
//
//        //make sure there's at elast one return statement if the type is not void
//        if (!returned && returnType != sTable.get("void"))
//            error("Method does not return. ", node);
//
//        return sTable.get("void");
    	return null;
    }

    //visits the parameters and body of a function
    private void visitParamsAndBody(FunctionNode node, IdType returnType)
    {
        //check the parameters
        node.visitParams(this);

        //setup return type checking
        returned = false;
        //this.returnType = returnType;

        node.visitBody(this);            	    	
    }


    public Object visit(ListNode node)
    {
        node.visitChildren(this);

    	return null;
    }

    public Object visit(IfNode node)
    {
//        checkSuperCall(node);
//
//        Symbol testType = (Symbol) node.visitTest(this);
//
//        if (testType != sTable.get("boolean"))
//            error("Test expression in if statement must be of type boolean, found: '" + testType.getKey() + "' in expression: ", node);
//
//        node.visitThen(this);
//
//        return sTable.get("void");
    	return null;
    }

//    public Object visit(PrintNode node)
//    {
//        checkSuperCall(node);
//
//        Symbol valueType = (Symbol)node.visitValue(this);
//
//        //type of the expression in the print statement must not be void
//        if (valueType == sTable.get("void"))
//            error("Argument to print statement must have type: found 'void'. ", node);
//
//        node.setType(valueType.getName());
//
//        return sTable.get("void");
//    }

    public Object visit(ReturnNode node)
    {
//        checkSuperCall(node);
//
//        Object valueType = node.visitValue(this);
//
//        if (valueType == null)
//        {
//            if (returnType != sTable.get("void"))
//                error("Return type does not match method declaration expected: '" + returnType.getName() + "' found: 'void'. ", node);
//        }
//        else
//        {
//            if (valueType instanceof TypeSymbol)
//            {
//                if (returnType instanceof ClassSymbol && valueType == sTable.get("null"))
//                {
//                    //this is OK - expects an object, but gets null
//                }
//                else if (valueType != returnType)
//                    error("Return type does not match method declaration expected: '" + returnType.getName() + "' found: '" + ((TypeSymbol)valueType).getName() + "'. ", node);
//            }
//            else if (!((ClassSymbol)valueType).symbolEquals(returnType))
//                error("Return type does not match method declaration expected: '" + returnType.getName() + "' found: '" + ((ClassSymbol)valueType).getName() + "'. ", node);
//        }
//
//        returned = true;
//
//        return sTable.get("void");
    	
    	return null;
    }

    public Object visit(DivNode node)
    {
//        return visitBinaryOp("/", "int", node);
    	return null;
    }

    public Object visit(AddNode node)
    {
        //return visitBinaryOp("+", "int", node);
    	return null;
    }

    public Object visit(SubNode node)
    {
        //return visitBinaryOp("-", "int", node);
    	return null;
    }

    public Object visit(MulNode node)
    {
        //return visitBinaryOp("*", "int", node);
    	return null;
    }

    public Object visit(OrNode node)
    {
        //return visitBinaryOp("||", "boolean", node);
    	return null;
    }

    public Object visit(AndNode node)
    {
        //return visitBinaryOp("&&", "boolean", node);
    	return null;
    }

    //shared functionality for &&, ||, +, -, / and * operators
//    private Symbol visitBinaryOp(String op, String type, BinaryNode node)
//    {
//        Symbol leftType = (Symbol) node.visitLeft(this);
//        Symbol rightType = (Symbol) node.visitRight(this);
//
//        if (leftType != sTable.get(type))
//            error("Left argument to operator " + op + " must be of type " + type + ", found: '" + leftType.getKey() + "' in expression: ", node);
//        if (rightType != sTable.get(type))
//            error("Right argument to operator " + op + " must be of type " + type + ", found: '" + rightType.getKey() + "' in expression: ", node);
//
//        return sTable.get(type);
//    	
//    	return null;
//    }

    public Object visit(EqNode node)
    {
        //return visitEqStyleNode("==", node);
    	return null;
    }

    public Object visit(NotEqNode node)
    {
        //return visitEqStyleNode("!=", node);
    	return null;
    }

    //common functionality for == and != operators
//    private Symbol visitEqStyleNode(String op, BinaryNode node)
//    {
//        Symbol leftType = (Symbol) node.visitLeft(this);
//        Symbol rightType = (Symbol) node.visitRight(this);
//
//        if (leftType == sTable.get("void") || rightType == sTable.get("void"))
//            error("Arguments to operator " + op + " must have type, found: 'void' in expression: ", node);
//        if (!((leftType == sTable.get("int") && rightType == sTable.get("int"))
//            || (leftType == sTable.get("boolean") && rightType == sTable.get("boolean"))
//            || (leftType.symbolEquals(sTable.get("Object")) && rightType.symbolEquals(sTable.get("Object")))))
//            error("Type mis-match in operator " + op + ", found: '" + leftType.getKey() + "' and '" + rightType.getKey() + "' in expression: ", node);
//
//        return sTable.get("boolean");
//    	return null;
//    }

    public Object visit(SignNode node)
    {
        //return visitUnaryOp("-", "int", node);
    	return null;
    }

    public Object visit(NotNode node)
    {
        //return visitUnaryOp("!", "boolean", node);
    	return null;
    }

    //shared functionality for - and ! operators
//    private Symbol visitUnaryOp(String op, String type, UnaryNode node)
//    {
//        Symbol childType = (Symbol)node.visitChild(this);
//
//        if (childType != sTable.get(type))
//            error("Argument to operator " + op + " must be of type " + type + ", found: '" + childType.getKey() + "' in expression: ", node);
//
//        return sTable.get(type);
//    	return null;
//    }

    public Object visit(BoolNode node)
    {
        //return sTable.get("boolean");
    	return null;
    }

    public Object visit(IntNode node)
    {
        //return sTable.get("int");
    	return null;
    }

    public Object visit(StringNode node)
    {
        //return sTable.get("String");
    	return null;
    }

    public Object visit(NullNode node)
    {
        //return sTable.get("null");
    	return null;
    }

	public Object visit(FloatNode node) {
		return null;
	}

	public Object visit(PrintNode node) {
		return null;
	}


	@Override
	public Object visit(NegNode node) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object visit(IfElseNode node) {
		// TODO Auto-generated method stub
		return null;
	}
}
