// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class LiFunc extends NumIdentCall {

    private String I1;
    private ActPars ActPars;

    public LiFunc (String I1, ActPars ActPars) {
        this.I1=I1;
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LiFunc(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LiFunc]");
        return buffer.toString();
    }
}
