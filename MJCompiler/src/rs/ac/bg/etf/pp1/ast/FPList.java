// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class FPList extends FormPars {

    private FormalParamList FormalParamList;

    public FPList (FormalParamList FormalParamList) {
        this.FormalParamList=FormalParamList;
        if(FormalParamList!=null) FormalParamList.setParent(this);
    }

    public FormalParamList getFormalParamList() {
        return FormalParamList;
    }

    public void setFormalParamList(FormalParamList FormalParamList) {
        this.FormalParamList=FormalParamList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParamList!=null) FormalParamList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParamList!=null) FormalParamList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParamList!=null) FormalParamList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FPList(\n");

        if(FormalParamList!=null)
            buffer.append(FormalParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FPList]");
        return buffer.toString();
    }
}
