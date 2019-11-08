// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class DesignatStatement extends DesignatorStatement {

    private Designator Designator;
    private DesignatorStmRest DesignatorStmRest;

    public DesignatStatement (Designator Designator, DesignatorStmRest DesignatorStmRest) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorStmRest=DesignatorStmRest;
        if(DesignatorStmRest!=null) DesignatorStmRest.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorStmRest getDesignatorStmRest() {
        return DesignatorStmRest;
    }

    public void setDesignatorStmRest(DesignatorStmRest DesignatorStmRest) {
        this.DesignatorStmRest=DesignatorStmRest;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorStmRest!=null) DesignatorStmRest.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorStmRest!=null) DesignatorStmRest.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorStmRest!=null) DesignatorStmRest.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatStatement(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStmRest!=null)
            buffer.append(DesignatorStmRest.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatStatement]");
        return buffer.toString();
    }
}
