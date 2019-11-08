// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class EValue extends EnumList {

    private String eName;
    private Integer num;

    public EValue (String eName, Integer num) {
        this.eName=eName;
        this.num=num;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EValue(\n");

        buffer.append(" "+tab+eName);
        buffer.append("\n");

        buffer.append(" "+tab+num);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EValue]");
        return buffer.toString();
    }
}
