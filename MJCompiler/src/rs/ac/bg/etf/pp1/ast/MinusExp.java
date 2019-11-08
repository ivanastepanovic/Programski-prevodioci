// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class MinusExp extends Expr {

    private RestExpr RestExpr;

    public MinusExp (RestExpr RestExpr) {
        this.RestExpr=RestExpr;
        if(RestExpr!=null) RestExpr.setParent(this);
    }

    public RestExpr getRestExpr() {
        return RestExpr;
    }

    public void setRestExpr(RestExpr RestExpr) {
        this.RestExpr=RestExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(RestExpr!=null) RestExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RestExpr!=null) RestExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RestExpr!=null) RestExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MinusExp(\n");

        if(RestExpr!=null)
            buffer.append(RestExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MinusExp]");
        return buffer.toString();
    }
}
