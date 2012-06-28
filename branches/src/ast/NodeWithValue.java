package ast;
import compiler.Visitor;

public interface NodeWithValue
{
    public Object visitValue(Visitor v);
    public String getType();
    public String getName();
}