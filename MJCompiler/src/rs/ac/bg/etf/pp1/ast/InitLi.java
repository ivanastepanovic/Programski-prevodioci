// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class InitLi extends InitList {

    private ElemList ElemList;

    public InitLi (ElemList ElemList) {
        this.ElemList=ElemList;
        if(ElemList!=null) ElemList.setParent(this);
    }

    public ElemList getElemList() {
        return ElemList;
    }

    public void setElemList(ElemList ElemList) {
        this.ElemList=ElemList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ElemList!=null) ElemList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ElemList!=null) ElemList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ElemList!=null) ElemList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InitLi(\n");

        if(ElemList!=null)
            buffer.append(ElemList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InitLi]");
        return buffer.toString();
    }
}
