// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class ConRestNum extends ConRest {

    private String conName;
    private Integer conValue;

    public ConRestNum (String conName, Integer conValue) {
        this.conName=conName;
        this.conValue=conValue;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName=conName;
    }

    public Integer getConValue() {
        return conValue;
    }

    public void setConValue(Integer conValue) {
        this.conValue=conValue;
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
        buffer.append("ConRestNum(\n");

        buffer.append(" "+tab+conName);
        buffer.append("\n");

        buffer.append(" "+tab+conValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConRestNum]");
        return buffer.toString();
    }
}
