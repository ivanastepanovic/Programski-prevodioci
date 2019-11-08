// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDecl extends ConstDeclList {

    private ConRest ConRest;

    public SingleConstDecl (ConRest ConRest) {
        this.ConRest=ConRest;
        if(ConRest!=null) ConRest.setParent(this);
    }

    public ConRest getConRest() {
        return ConRest;
    }

    public void setConRest(ConRest ConRest) {
        this.ConRest=ConRest;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConRest!=null) ConRest.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConRest!=null) ConRest.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConRest!=null) ConRest.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDecl(\n");

        if(ConRest!=null)
            buffer.append(ConRest.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDecl]");
        return buffer.toString();
    }
}
