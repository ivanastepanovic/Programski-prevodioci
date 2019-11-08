// generated with ast extension for cup
// version 0.8
// 18/8/2019 12:14:8


package rs.ac.bg.etf.pp1.ast;

public class SubExpr extends RestExpr {

    private RestExpr RestExpr;
    private Term Term;

    public SubExpr (RestExpr RestExpr, Term Term) {
        this.RestExpr=RestExpr;
        if(RestExpr!=null) RestExpr.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public RestExpr getRestExpr() {
        return RestExpr;
    }

    public void setRestExpr(RestExpr RestExpr) {
        this.RestExpr=RestExpr;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(RestExpr!=null) RestExpr.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RestExpr!=null) RestExpr.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RestExpr!=null) RestExpr.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SubExpr(\n");

        if(RestExpr!=null)
            buffer.append(RestExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SubExpr]");
        return buffer.toString();
    }
}
