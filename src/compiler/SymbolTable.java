package compiler;

import java.util.*;
import it.m2j.*;

public class SymbolTable
{
    //storage for the entries
    HashMap<String,ArrayList<SymbolDesc> > storage = new HashMap<String,ArrayList<SymbolDesc> >();    

    /**
     * create a symbol table for the global (top level) scope
     */
    public SymbolTable()
    {
        
    }

    public boolean putVariable(String id, IdType type, int blockNumber)
    {    	 
    	boolean bRet = false;
    	ArrayList<SymbolDesc> varDesc = this.getSpecific(id, IdType.VARIABLE);        	
    	
    	if (varDesc.size() == 0) //create a new entry in the table
    	{    		
        	ArrayList<SymbolDesc> descList = new ArrayList<SymbolDesc>();
        	SymbolDesc symbol = new SymbolDesc();

        	symbol.setVariableSymbol(type, blockNumber);

        	descList.add(symbol);

        	storage.put(id, descList);
        	
        	bRet = true;
    	}
    	else
    	{
    		boolean blockFound = false;
    		Iterator<SymbolDesc> it = varDesc.listIterator();
		    
			while (it.hasNext()) {
				SymbolDesc s = it.next();
			      
			      if (s.getBlock() <= blockNumber){
			      	blockFound = true;	//Block Found
			      	bRet = false;
			      	break;
			      }		   
			}

		    if(blockFound == false) //Block not found
			{
		    	SymbolDesc symbol = new SymbolDesc();
		    	symbol.setVariableSymbol(type, blockNumber);
		    	this.get(id).add(symbol);
		    	bRet = true;
			}
    	}

    	return bRet;
    }
    
    public boolean putFunction(String id, IdType type, ArrayList<IdType> params)
    {
    	boolean bRet = false;
    	ArrayList<SymbolDesc> funDesc = this.getSpecific(id, IdType.FUNCTION);
    	
    	if (funDesc.size() == 0) //create a new entry in the table
    	{
	    	SymbolDesc symbol = new SymbolDesc();
	    	ArrayList<SymbolDesc> descList = new ArrayList<SymbolDesc>();    	
	
	    	symbol.setFunctionSymbol(type, params);
	
	    	descList.add(symbol);
	
	    	storage.put(id, descList);
	    	
	    	bRet = true;
    	}
    	
    	return bRet;
    }

    /**
     * get an Array of Symbols from the symbol table for the specified identifier
     * @param key the symbol's key
     * @return null if the symbol is not in the symbol table
     */
    public ArrayList<SymbolDesc> get(String key)
    {    	    	    	    	
    	return storage.get(key);    	
    }    
        
    /**
     * get symbol descriptors from the symbol table for a specific kind
     * @param key the symbol's key
     * @return symbol descriptors
     */
    public ArrayList<SymbolDesc> getSpecific(String key, IdType kind)
    {
    	ArrayList<SymbolDesc> tmpDesc = new ArrayList<SymbolDesc>();
    	ArrayList<SymbolDesc> varDesc;
    	
    	varDesc = storage.get(key);
    	
        if (varDesc != null){ //Identifier found
			
        	Iterator<SymbolDesc> it = varDesc.listIterator();			
			
			while (it.hasNext()) {
				SymbolDesc s = it.next();

				if(s.getKind() == kind)
					tmpDesc.add(s);
			}
		}

        return tmpDesc;
    }  
}