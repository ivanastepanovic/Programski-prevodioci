// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class FuncCall extends Factor {

    private Designator Designator;
    private MaybeActPars MaybeActPars;

    public FuncCall (Designator Designator, MaybeActPars MaybeActPars) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.MaybeActPars=MaybeActPars;
        if(MaybeActPars!=null) MaybeActPars.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public MaybeActPars getMaybeActPars() {
        return MaybeActPars;
    }

    public void setMaybeActPars(MaybeActPars MaybeActPars) {
        this.MaybeActPars=MaybeActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(MaybeActPars!=null) MaybeActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(MaybeActPars!=null) MaybeActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(MaybeActPars!=null) MaybeActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MaybeActPars!=null)
            buffer.append(MaybeActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncCall]");
        return buffer.toString();
    }
}
