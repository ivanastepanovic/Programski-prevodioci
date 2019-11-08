// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class FormParDecl extends FormalParamDecl {

    private Type Type;
    private String parName;

    public FormParDecl (Type Type, String parName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.parName=parName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getParName() {
        return parName;
    }

    public void setParName(String parName) {
        this.parName=parName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+parName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParDecl]");
        return buffer.toString();
    }
}
