// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class EMultiIdent extends EnumList {

    private EnumList EnumList;
    private String eName;

    public EMultiIdent (EnumList EnumList, String eName) {
        this.EnumList=EnumList;
        if(EnumList!=null) EnumList.setParent(this);
        this.eName=eName;
    }

    public EnumList getEnumList() {
        return EnumList;
    }

    public void setEnumList(EnumList EnumList) {
        this.EnumList=EnumList;
    }

    public String getEName() {
        return eName;
    }

    public void setEName(String eName) {
        this.eName=eName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumList!=null) EnumList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumList!=null) EnumList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumList!=null) EnumList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EMultiIdent(\n");

        if(EnumList!=null)
            buffer.append(EnumList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+eName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EMultiIdent]");
        return buffer.toString();
    }
}
