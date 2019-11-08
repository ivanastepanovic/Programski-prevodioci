// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class FactChar extends Factor {

    private Character chr;

    public FactChar (Character chr) {
        this.chr=chr;
    }

    public Character getChr() {
        return chr;
    }

    public void setChr(Character chr) {
        this.chr=chr;
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
        buffer.append("FactChar(\n");

        buffer.append(" "+tab+chr);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactChar]");
        return buffer.toString();
    }
}
