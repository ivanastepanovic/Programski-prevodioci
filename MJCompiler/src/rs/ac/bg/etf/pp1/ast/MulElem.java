// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class MulElem extends ElemList {

    private ElemList ElemList;
    private NumIdentCall NumIdentCall;

    public MulElem (ElemList ElemList, NumIdentCall NumIdentCall) {
        this.ElemList=ElemList;
        if(ElemList!=null) ElemList.setParent(this);
        this.NumIdentCall=NumIdentCall;
        if(NumIdentCall!=null) NumIdentCall.setParent(this);
    }

    public ElemList getElemList() {
        return ElemList;
    }

    public void setElemList(ElemList ElemList) {
        this.ElemList=ElemList;
    }

    public NumIdentCall getNumIdentCall() {
        return NumIdentCall;
    }

    public void setNumIdentCall(NumIdentCall NumIdentCall) {
        this.NumIdentCall=NumIdentCall;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ElemList!=null) ElemList.accept(visitor);
        if(NumIdentCall!=null) NumIdentCall.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ElemList!=null) ElemList.traverseTopDown(visitor);
        if(NumIdentCall!=null) NumIdentCall.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ElemList!=null) ElemList.traverseBottomUp(visitor);
        if(NumIdentCall!=null) NumIdentCall.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MulElem(\n");

        if(ElemList!=null)
            buffer.append(ElemList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NumIdentCall!=null)
            buffer.append(NumIdentCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulElem]");
        return buffer.toString();
    }
}
