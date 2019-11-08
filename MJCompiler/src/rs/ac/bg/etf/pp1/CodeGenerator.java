package rs.ac.bg.etf.pp1;

import com.sun.org.apache.bcel.internal.generic.DUP2_X1;

import javafx.scene.control.cell.TextFieldTableCell;
import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;

import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int varCount;

	private int paramCnt;

	private int mainPc;

	private Struct lastType;

	public int getMainPc() {
		return mainPc;
	}

	/*
	 * Nesto treba da uradim kad obilazim progname enter 1 1 load 0 exit return
	 * 
	 * za one str, len, ord...
	 * 
	 * (non-Javadoc)
	 * 
	 * @see rs.ac.bg.etf.pp1.ast.VisitorAdaptor#visit(rs.ac.bg.etf.pp1.ast.Type)
	 */

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		lastType = type.struct;
	}

	@Override
	public void visit(MethodTypeName2 methodTypeName) {

		if ("main".equalsIgnoreCase(methodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);

		// Collect arguments and local variables.
		SyntaxNode methodNode = methodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		int brFormPar = methodTypeName.obj.getLevel();
		int ukupno = methodTypeName.obj.getLocalSymbols().size();
		
		// Generate the entry.
		Code.put(Code.enter);
		//Code.put(fpCnt.getCount());
		//Code.put(varCnt.getCount() + fpCnt.getCount());
		Code.put(brFormPar);
		Code.put(ukupno);
	}

	public void visit(MethodTypeName1 methodTypeName) {

		methodTypeName.obj.setAdr(Code.pc);

		// Collect arguments and local variables.
		SyntaxNode methodNode = methodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		
		int brFormPar = methodTypeName.obj.getLevel();
		int ukupno = methodTypeName.obj.getLocalSymbols().size();
		
		// Generate the entry.
		Code.put(Code.enter);
		//Code.put(fpCnt.getCount());
		//Code.put(varCnt.getCount() + fpCnt.getCount());
		Code.put(brFormPar);
		Code.put(ukupno);
	}

	@Override
	public void visit(VarDecl VarDecl) {
		varCount++;
	}

	@Override
	public void visit(FormalParamDecl FormalParam) {
		paramCnt++;
	}

//	@Override
//	public void visit(MethodDecl methodDecl) {
//		Code.put(Code.exit);
//		Code.put(Code.return_);
//	}

	@Override
	public void visit(ReturnExpr ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(ReturnNoExpr ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(DesigEqual assignment) {
		Designator desig = assignment.getDesignator();
		
		Code.store(desig.obj);	
	}

	public void visit(DesigInc inc) {
		DesignatStatement desigStm = (DesignatStatement) inc.getParent();
		Designator desig = desigStm.getDesignator();
		if (desig.obj.getKind() == Struct.Array) {
			Code.put(Code.dup2);
		}
		//Code.load(desig.obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(desig.obj);
	}

	public void visit(DesigDec dec) {
		DesignatStatement desigStm = (DesignatStatement) dec.getParent();
		Designator desig = desigStm.getDesignator();
		if (desig.obj.getKind() == Struct.Array) {
			Code.put(Code.dup2);
		}
		//Code.load(desig.obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(desig.obj);
	}

	// public void visit(Const Const) {
	// Code.load(new Obj(Obj.Con, "$", Const.struct, Const.getN1(), 0));
	// }

	/*
	public void visit(ConRestNum con) {
		Obj o = new Obj(Obj.Con, "$", lastType, con.getConValue(), 0);
		Code.load(o);
	}

	public void visit(ConRestBool con) {
		Obj o = new Obj(Obj.Con, "$", lastType, con.getConValue() ? 1 : 0, 0);
		Code.load(o);
	}

	public void visit(ConRestChar con) {
		Obj o = new Obj(Obj.Con, "$", lastType, con.getConValue(), 0);
		Code.load(o);
	}
	*/
	
	@Override
	public void visit(Designator Designator) {
		SyntaxNode parent = Designator.getParent();
		if (DesigEqual.class != parent.getClass() && FuncCall.class != parent.getClass()) {
			Code.load(Designator.obj);
		}
	}

	@Override
	public void visit(FuncCall FuncCall) {
		
		Obj functionObj = FuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset); // stavljanje povratne adrese
	}
	
	

	@Override
	public void visit(DesigNoActPars DesigNoActPars) {
		Obj functionObj = ((DesignatStatement)DesigNoActPars.getParent()).getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset); // stavljanje povratne adrese

		if (functionObj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	@Override
	public void visit(DesigActPars DesigActPars) {
		Obj functionObj = ((DesignatStatement)DesigActPars.getParent()).getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset); // stavljanje povratne adrese

		if (functionObj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	@Override
	public void visit(PrintStmt PrintStmt) {
		Code.put(Code.const_5);

		if (PrintStmt.getExpr().struct == Tab.charType)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}
	
	@Override
	public void visit(PrintCNT cnt) {
		//Code.load(cnt.getDesignator().obj); //adresa niza ce biti tamo vec
		Code.put(Code.dup); // adr adr
		Code.put(Code.arraylength); //adr duzina
		Code.loadConst(1);
		Code.put(Code.sub); //adr poz
		Code.put(Code.aload); //br Ponavljanja
		
		Code.put(Code.const_5);
		Code.put(Code.print);
	}

	@Override
	public void visit(AddExpr add) {
		Code.put(Code.add);
	}

	public void visit(SubExpr sub) {
		Code.put(Code.sub);
	}

	public void visit(FactNum num) {
		// Code.put(num.getNum());
		// Code.load(new Obj(Obj.Con, "$", Tab.intType, num.getNum(), 0));

		Obj c = Tab.insert(Obj.Con, "$", num.struct);
		c.setLevel(0);
		c.setAdr(num.getNum());
		Code.load(c);
	}

	public void visit(FactChar ch) {
		// Code.put(ch.getChr());
		Code.load(new Obj(Obj.Con, "$", Tab.charType, ch.getChr(), 0));
	}

	public void visit(FactBool b) {
		// Code.put(b.getB() ? 1: 0);
		Code.load(new Obj(Obj.Con, "$", new Struct(Struct.Bool), b.getB() ? 1 : 0, 0));
	}

	public void visit(MulFactor1 mul) {
		Code.put(Code.mul);
	}

	public void visit(MulFactor2 div) {
		Code.put(Code.div);
	}

	public void visit(MulFactor3 mod) {
		Code.put(Code.rem); // Ostatak
	}

	public void visit(ReadStmt readStm) {
		Designator desig = readStm.getDesignator();
		if (desig.obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(desig.obj);
	}

	public void visit(FactNew niz) {
		
		Code.put(Code.newarray);
		// broj elemenata je vec na steku kad se obilazio Expr?
		if (niz.getType().struct == Tab.charType)
			Code.put(0);
		else
			Code.put(1);
		// ono b kod newarray sa prezentacije
	}

	public void visit(Var var) {
		// Code.load(var.getDesignator().obj);
	}

	// Ako je u uglastim expr onda ce mi trebati vrednost niza da se stavi na expr
	// stek a taj expr je vec na njemu
	// ako nije onda hocu samo adresu niza
	public void visit(RestDesig2 niz) {
		// vrednost je vec na exp steku ali ne znam jel dobar red ili da zamenim sa
		// adresom

		// da li je taj niz lokalna ili globalna promenljiva

		Designator desig = niz.getDesignator();
		
		
		if (desig.getParent().getParent() instanceof DesignatStatement) {
		DesignatStatement parent1 = (DesignatStatement) desig.getParent().getParent();
		if (parent1.getDesignatorStmRest() instanceof DesigInc  || parent1.getDesignatorStmRest() instanceof DesigDec) {
			Code.load(desig.obj); //adresa
			
			Code.put(Code.dup_x1);
			Code.put(Code.pop);   //sad imam adr index
			
			Code.put(Code.dup);
			
		}
		}
		
		SyntaxNode parent = desig.getParent().getParent();
		if (parent.getClass() != DesigEqual.class) {

			if (desig.obj.getLevel() == 0) {
				Code.put(Code.getstatic);
				Code.put2(desig.obj.getAdr());
			} else if (desig.obj.getAdr() >= 0 && desig.obj.getAdr() <= 3)
				Code.put(Code.load_n + desig.obj.getAdr());

			else {
				Code.put(Code.load);
				Code.put(desig.obj.getAdr());
			}

			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			Code.put(Code.aload);
		} else {
			
			Code.load(desig.obj); //adresa
			Code.put(Code.dup_x1);
			Code.put(Code.pop);   // adresa indeks
		}
		
		
		
	}

	public void visit(RestDesig3 niz) {
		// samo hocu adresu na stek
		Code.load(niz.getDesignator().obj);
	}

	public void visit(DesigIndent desig) {
		SyntaxNode parent = desig.getParent();
		
		boolean poziv = false ;
		
		if (desig.obj.getKind() == Obj.Meth) poziv = true;
		
		if (parent.getClass() != DesigEqual.class && parent.getClass() != RestDesig2.class
				&& parent.getClass() != RestDesig1.class && !poziv && parent.getClass() != ReadStmt.class) {
			
			Code.load(desig.obj);
			
//			if (desig.obj.getType().getKind() == Struct.Array)
//				Code.put(desig.obj.getAdr());
//			else 
		}
	}
	
	public void visit(MethodDec krajMetode) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	
	public void visit (RestDesig1 enumDesig) {
		Designator d = enumDesig.getDesignator();
		SyntaxNode parent = enumDesig.getParent().getParent();
		if (DesigEqual.class != parent.getClass()) {
			
			Obj sacuvanEnum = d.obj;
			int adr = sacuvanEnum.getType().getMembersTable().searchKey(enumDesig.getN()).getAdr();
			Code.loadConst(adr);
		}
	}
}
