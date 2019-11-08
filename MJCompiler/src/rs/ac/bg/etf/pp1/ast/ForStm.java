// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class ForStm extends Matched {

    private MaybeDesigStm MaybeDesigStm;
    private MaybeCondition MaybeCondition;
    private MaybeDesigStm MaybeDesigStm1;
    private Matched Matched;

    public ForStm (MaybeDesigStm MaybeDesigStm, MaybeCondition MaybeCondition, MaybeDesigStm MaybeDesigStm1, Matched Matched) {
        this.MaybeDesigStm=MaybeDesigStm;
        if(MaybeDesigStm!=null) MaybeDesigStm.setParent(this);
        this.MaybeCondition=MaybeCondition;
        if(MaybeCondition!=null) MaybeCondition.setParent(this);
        this.MaybeDesigStm1=MaybeDesigStm1;
        if(MaybeDesigStm1!=null) MaybeDesigStm1.setParent(this);
        this.Matched=Matched;
        if(Matched!=null) Matched.setParent(this);
    }

    public MaybeDesigStm getMaybeDesigStm() {
        return MaybeDesigStm;
    }

    public void setMaybeDesigStm(MaybeDesigStm MaybeDesigStm) {
        this.MaybeDesigStm=MaybeDesigStm;
    }

    public MaybeCondition getMaybeCondition() {
        return MaybeCondition;
    }

    public void setMaybeCondition(MaybeCondition MaybeCondition) {
        this.MaybeCondition=MaybeCondition;
    }

    public MaybeDesigStm getMaybeDesigStm1() {
        return MaybeDesigStm1;
    }

    public void setMaybeDesigStm1(MaybeDesigStm MaybeDesigStm1) {
        this.MaybeDesigStm1=MaybeDesigStm1;
    }

    public Matched getMatched() {
        return Matched;
    }

    public void setMatched(Matched Matched) {
        this.Matched=Matched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MaybeDesigStm!=null) MaybeDesigStm.accept(visitor);
        if(MaybeCondition!=null) MaybeCondition.accept(visitor);
        if(MaybeDesigStm1!=null) MaybeDesigStm1.accept(visitor);
        if(Matched!=null) Matched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MaybeDesigStm!=null) MaybeDesigStm.traverseTopDown(visitor);
        if(MaybeCondition!=null) MaybeCondition.traverseTopDown(visitor);
        if(MaybeDesigStm1!=null) MaybeDesigStm1.traverseTopDown(visitor);
        if(Matched!=null) Matched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MaybeDesigStm!=null) MaybeDesigStm.traverseBottomUp(visitor);
        if(MaybeCondition!=null) MaybeCondition.traverseBottomUp(visitor);
        if(MaybeDesigStm1!=null) MaybeDesigStm1.traverseBottomUp(visitor);
        if(Matched!=null) Matched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForStm(\n");

        if(MaybeDesigStm!=null)
            buffer.append(MaybeDesigStm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MaybeCondition!=null)
            buffer.append(MaybeCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MaybeDesigStm1!=null)
            buffer.append(MaybeDesigStm1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Matched!=null)
            buffer.append(Matched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForStm]");
        return buffer.toString();
    }
}
