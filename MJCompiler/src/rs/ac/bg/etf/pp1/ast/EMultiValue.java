// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class EMultiValue extends EnumList {

    private EnumList EnumList;
    private String eName;
    private Integer num;

    public EMultiValue (EnumList EnumList, String eName, Integer num) {
        this.EnumList=EnumList;
        if(EnumList!=null) EnumList.setParent(this);
        this.eName=eName;
        this.num=num;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num=num;
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
        buffer.append("EMultiValue(\n");

        if(EnumList!=null)
            buffer.append(EnumList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+eName);
        buffer.append("\n");

        buffer.append(" "+tab+num);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EMultiValue]");
        return buffer.toString();
    }
}
