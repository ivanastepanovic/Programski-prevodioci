package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;


public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	boolean returnFound = false;
	int nVars;
	Struct lastType;
	
	int lastEnumAddr = 0;
	int arrNumCheck = 0;
	
	boolean prviActPar = true;
	
	ArrayList<ArrayList<Struct>> actPars = new ArrayList<ArrayList<Struct>>();
	boolean paramEx= false; // da li je act param kome jurim vrednost

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(ProgName progName) {
		if(Tab.currentScope.findSymbol(progName.getPName()) != null) {
			report_error("Vec postoji", progName);
			progName.obj = Tab.noObj;
		}
		else {
			progName.obj = Tab.insert(Obj.Prog, progName.getPName(), Tab.noType);	
		}
		Tab.openScope();
	}

	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		
		Obj glavna = Tab.find("main");
		if (glavna.getType() != Tab.noType ) report_error("Ne postoji main metoda koja je VOID!", program);
		if (glavna.getLevel() != 0) report_error("Mora postojati metoda main bez argumenata!", program);
		if (glavna == Tab.noObj) report_error("Ne postoji metoda main", program);
		
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		
		//Provera da li ima main metode
		
	}

	/*
	 * public void visit(VarDecl varDecl) { report_info("Deklarisana promenljiva "+
	 * varDecl.getVarName(), varDecl); Obj varNode = Tab.insert(Obj.Var,
	 * varDecl.getVarName(), varDecl.getType().struct); }
	 * 
	 */

	// -----------------------------------------------------------------------------------------

	public void visit(SingleVar var) {
		int kind = Obj.Var;
		String name = var.getVarName();
		Obj singleVar = Tab.currentScope.findSymbol(name);
		if (singleVar != null) {
			report_error("Promenljiva " + name + " vec postoji u tabeli simbola!", var);
			singleVar= Tab.noObj;
		} else {
			singleVar = Tab.insert(kind, name, lastType);
		}
	}

	public void visit(MulVar var) {
		int kind = Obj.Var;
		String name = var.getVarName();
		Obj singleVar = Tab.currentScope.findSymbol(name);
		if (singleVar != null) {
			report_error("Promenljiva " + name + " vec postoji u tabeli simbola!", var);
			singleVar= Tab.noObj;
		} else {
			singleVar = Tab.insert(kind, name, lastType);
		}
	}

	public void visit(SingleVarArr var) {
		int kind = Obj.Var;
		String name = var.getVarName();
		Obj singleVar = Tab.currentScope.findSymbol(name);
		if (singleVar != null) {
			report_error("Promenljiva " + name + " vec postoji u tabeli simbola!", var);
			singleVar = Tab.noObj;
		} else {
			singleVar = Tab.insert(kind, name, new Struct(Struct.Array, lastType));
		}
	}

	public void visit(MulVarArr var) {
		int kind = Obj.Var;
		String name = var.getVarName();
		Obj singleVar = Tab.currentScope.findSymbol(name);
		if (singleVar != null) {
			report_error("Promenljiva " + name + " vec postoji u tabeli simbola!", var);
			singleVar = Tab.noObj;
		} else {
			singleVar = Tab.insert(kind, name, new Struct(Struct.Array, lastType));
		}
	}

	// -----------------------------------------------------------------------------------

	public void visit(ConRestNum con) {
		int kind = Obj.Con;
		String name = con.getConName();
		Obj conDecl = Tab.currentScope.findSymbol(name);
		if (conDecl != null) {
			report_error("Konstanta " + name + " vec postoji u tabeli simbola!", con);
			conDecl= Tab.noObj;
		} else if (lastType != Tab.intType){
			report_error("Tip konstante -" + con.getConName() + "- nije adekvatan", con);
		}
		else {
			conDecl = Tab.insert(kind, name, lastType);
			conDecl.setAdr(con.getConValue());
		}
	}

	public void visit(ConRestBool con) {
		int kind = Obj.Con;
		String name = con.getConName();
		Obj conDecl = Tab.currentScope.findSymbol(name);
		if (conDecl != null) {
			report_error("Konstanta " + name + " vec postoji u tabeli simbola!", con);
			conDecl = Tab.noObj;
		//} else if (lastType.compatibleWith(new Struct(Struct.Bool))){
		} else if (lastType.compatibleWith(Tab.find("bool").getType())){
			report_error("Tip konstante -" + con.getConName() + "- nije adekvatan", con);
		}
		else {
			conDecl = Tab.insert(kind, name, lastType);
			if (con.getConValue())
				conDecl.setAdr(1);
			else
				conDecl.setAdr(0);
		}
	}
	
	public void visit(ConRestChar con) {
		int kind = Obj.Con;
		String name = con.getConName();
		Obj conDecl = Tab.currentScope.findSymbol(name);
		if (conDecl != null) {
			report_error("Konstanta " + name + " vec postoji u tabeli simbola!", con);
			conDecl= Tab.noObj;
		} else if (lastType.compatibleWith(Tab.charType)){
			report_error("Tip konstante -" + con.getConName() + "- nije adekvatan", con);
		} else {
			conDecl = Tab.insert(kind, name, lastType);
			conDecl.setAdr(con.getConValue());
		}
	}

	// -------------------------------------------------------------------------

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
		}
		lastType = type.struct;
	}

	//---------------------------------------------------------------------------------
	
	//Kad se naidje na init listu za niz - popunjavam elemente
	//treba jos i niz[nesto] = .... da tu pamtim sta je postavljeno za elem
	public void visit() {
		
	}
	
	//-------------------------------------------------------------------------
	//za enum
	public void visit(EnumName enumName) {
		if (Tab.currentScope.findSymbol(enumName.getEnumName()) != null) {
			report_error("Enum sa imenom -" + enumName.getEnumName() + "- vec postoji", enumName);
			enumName.obj = Tab.noObj;
		}
		else {
			enumName.obj = Tab.insert(Obj.Type, enumName.getEnumName(), new Struct(Struct.Enum));
		}
		
		Tab.openScope();
	}
	
	public void visit(EnumDecl enumDecl) {
		Tab.chainLocalSymbols(enumDecl.getEnumName().obj.getType());
		Tab.closeScope();

	}
	
	public void visit(EIdent eident) {
		Obj novi= Tab.currentScope.findSymbol(eident.getEName());
		if (novi != null) {
			report_error(eident.getEName() + " vec postoji!", eident);
			novi = Tab.noObj;
		}
		else {
			novi = Tab.insert(Obj.Con, eident.getEName(), Tab.intType);
			novi.setAdr(lastEnumAddr++);
		}
	}
	
	public void visit(EMultiIdent eident) {
		Obj novi= Tab.currentScope.findSymbol(eident.getEName());
		if (novi != null) {
			report_error(eident.getEName() + " vec postoji!", eident);
			novi = Tab.noObj;
		}
		else {
			novi = Tab.insert(Obj.Con, eident.getEName(), Tab.intType);
			novi.setAdr(lastEnumAddr++);	
		}
		
	}
	
	public void visit(EValue eident) {
		int num= eident.getNum();
		if (num<= lastEnumAddr) {
			report_error("Greska: Nekorektna navedena vrednost za " + eident.getEName() + ".",  eident);
			return;
		}
		Obj novi = Tab.currentScope.findSymbol(eident.getEName());
		if (novi != null) {
			report_error(eident.getEName() + " vec postoji!", eident);
			novi = Tab.noObj;
		}
		else {
			novi = Tab.insert(Obj.Con, eident.getEName(), Tab.intType);
			novi.setAdr(num);
			lastEnumAddr = num + 1;
		}	
	}
	
	public void visit(EMultiValue eident) {
		int num= eident.getNum();
		if (num<= lastEnumAddr) {
			report_error("Greska: Nekorektna navedena vrednost za " + eident.getEName() + ".",  eident);
			return;
		}
		Obj novi = Tab.currentScope.findSymbol(eident.getEName());
		if (novi != null) {
			report_error(eident.getEName() + " vec postoji!", eident);
			novi = Tab.noObj;
		}
		else {
			novi = Tab.insert(Obj.Con, eident.getEName(), Tab.intType);
			novi.setAdr(num);
			lastEnumAddr = num + 1;
		}	
		
	}
	
	
	
	
	//------------------------------------------------------------------------ 
	
	public void visit(MethodDec methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funcija " + currentMethod.getName()
					+ " nema return iskaz!", null);
		}

		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		returnFound = false;
		currentMethod = null;
	}

	public void visit(MethodTypeName1 methodTypeName) {
		currentMethod = Tab.find(methodTypeName.getMethName());
		if (currentMethod != Tab.noObj) {
			report_error("Metoda -" + methodTypeName.getMethName() + "- vec postoji!", methodTypeName);
			//currentMethod = Tab.noObj;
		}
		else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getType().struct);
		}
		
		methodTypeName.obj = currentMethod;
		
		Tab.openScope();
	}
	
	public void visit(MethodTypeName2 methodTypeName) {
		currentMethod = Tab.find(methodTypeName.getMethName());
		if (currentMethod != Tab.noObj) {
			report_error("Metoda -" + methodTypeName.getMethName() + "- vec postoji!", methodTypeName);
			currentMethod = Tab.noObj;
		}
		else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), Tab.noType);
		}
		
		methodTypeName.obj = currentMethod;
		Tab.openScope();
	}
	
	public void visit(FormParDecl formPar) {
		//dodajem novi parametar u trenutni scope
		Obj fp = new Obj(Obj.Var, formPar.getParName(), formPar.getType().struct);
		Obj tab = Tab.currentScope.findSymbol(formPar.getParName());
		if (tab!= null) {
			report_error("Greska na liniji " + formPar.getLine() + " : "
					+ "Naziv formalnog parametra -" +formPar.getParName() + "- vec postoji u metodi " 
					+ currentMethod.getName(), formPar);
		}
		else {
			Tab.insert(Obj.Var, formPar.getParName(), formPar.getType().struct).setFpPos(Tab.currentScope.getnVars());
		}
	}

	public void visit (FormParDeclArr formPar) {
		Struct type = new Struct(Struct.Array, formPar.getType().struct);
		Obj fp = new Obj(Obj.Var, formPar.getParName(), type );
		Obj tab = Tab.currentScope.findSymbol(formPar.getParName());
		if (tab!= null) {
			report_error("Greska na liniji " + formPar.getLine() + " : "
					+ "Naziv formalnog parametra -" +formPar.getParName() + "- vec postoji u metodi " 
					+ currentMethod.getName(), formPar);
		}
		else {
			Tab.insert(Obj.Var, formPar.getParName(), type).setFpPos(Tab.currentScope.getnVars());
		}
	}
	
	
	public void visit(PrintStmt printStmt) {
		printCallCount++;
		Struct tip= printStmt.getExpr().struct;
		if (!(tip == Tab.intType || tip== Tab.charType || tip.getKind() == Struct.Bool) ){
			report_error("Argument metode print mora biti int, char ili bool.", printStmt);
		}
	}

	public void visit(ReturnExpr returnExpr) {
		returnFound = true;
		Struct currMethType = currentMethod.getType();
		
		Struct exprStruct = returnExpr.getExpr().struct;
		if (!currMethType.compatibleWith(exprStruct)) {
			report_error("Greska na liniji " + returnExpr.getLine() + " : "
					+ "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
					+ currentMethod.getName(), null);
		}
	}

	/*
	public void visit(ProcCall procCall) {
		Obj func = procCall.getDesignator().obj;
		if (Obj.Meth != func.getKind()) {
			report_error("Greska na liniji " + procCall.getLine() + " : ime " + func.getName() + " nije funkcija!",
					null);
		}
	}
	*/
	
	 
	public void visit(AddExpr addExpr) {
		Struct te = addExpr.getRestExpr().struct;
		Struct t = addExpr.getTerm().struct;
		if ((te.getKind() == Struct.Enum || te == Tab.intType) && (t.getKind() == Struct.Enum || t == Tab.intType))
			addExpr.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje.",
					null);
			addExpr.struct = Tab.noType;
		}
	}
	
	public void visit(SubExpr addExpr) {
		Struct te = addExpr.getRestExpr().struct;
		Struct t = addExpr.getTerm().struct;
		if ((te.getKind() == Struct.Enum || te == Tab.intType) && (t.getKind() == Struct.Enum || t == Tab.intType))
			addExpr.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje.",
					null);
			addExpr.struct = Tab.noType;
		}
	}
	
	public void visit(MinusExp ex) {
		Struct t = ex.getRestExpr().struct;
		if (t.getKind() == Struct.Enum || t == Tab.intType)
			ex.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + ex.getLine() + " : nekompatibilni tipovi.",
					null);
			ex.struct = Tab.noType;
		}
	}

	public void visit(TermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
	}
	
	public void visit(SingleFactor fact) {
		fact.struct = fact.getFactor().struct ;
	}
	
	public void visit(FactNum num) {
		num.struct = Tab.intType;
	}
	
	public void visit(FactBool f) {
		//f.struct = new Struct(Struct.Bool);
		f.struct = Tab.find("bool").getType();
		
		
	}
	
	public void visit(FactChar f) {
		f.struct = Tab.charType;
	}
	
	public void visit(MulFactor1 addExpr) {
		Struct te = addExpr.getFactor().struct;
		Struct t = addExpr.getTerm().struct;
		if ((te.getKind() == Struct.Enum || te == Tab.intType) && (t.getKind() == Struct.Enum || t == Tab.intType))
			addExpr.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu.",
					null);
			addExpr.struct = Tab.noType;
		}
	}
	
	public void visit(MulFactor2 addExpr) {
		Struct te = addExpr.getFactor().struct;
		Struct t = addExpr.getTerm().struct;
		if ((te.getKind() == Struct.Enum || te == Tab.intType) && (t.getKind() == Struct.Enum || t == Tab.intType))
			addExpr.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu.",
					null);
			addExpr.struct = Tab.noType;
		}
	}
	
	public void visit(MulFactor3 addExpr) {
		Struct te = addExpr.getFactor().struct;
		Struct t = addExpr.getTerm().struct;
		if ((te.getKind() == Struct.Enum || te == Tab.intType) && (t.getKind() == Struct.Enum || t == Tab.intType))
			addExpr.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu.",
					null);
			addExpr.struct = Tab.noType;
		}
	}
	
	/*Podeljen term na SingleFactor i MulFactor
	 * 
	  public void visit(Term term) {
		term.struct = term.getFactor().struct;
	  }
	*/
	
	
	/*	public void visit(Const cnst) {
		cnst.struct = Tab.intType;
	}
	*/

	public void visit(Var var) {
		var.struct = var.getDesignator().obj.getType();
	}

 
 
	public void visit(FuncCall funcCall) {
		
		Obj func = funcCall.getDesignator().obj;
		if (Obj.Meth == func.getKind()) {
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = func.getType();
			
			Collection<Obj> locals = func.getLocalSymbols();
			
			//if (locals.size() != actPars.size()) {
			int i = actPars.size() - 1;
			if (func.getLevel() != actPars.get(i).size()) {
				report_error("Nije adekvatan broj argumenata! Greska ", funcCall);
			} else {
				List<Struct> actParsList = actPars.get(i);
				for(Obj formArg: locals) {
					Struct actPar = actParsList.remove(0);
					if (!actPar.assignableTo(formArg.getType())) {
						if (formArg.getType().getKind() == Struct.Enum ) {
							//onda proverim da li je stvarni bas iz tog enuma?
						}
						else 
						report_error("Nekompatibilan parametar " + formArg.getName(), funcCall);
					}
					if (actParsList.isEmpty()) {
						actPars.remove(i);
						break;
					}
				}
				
			}
			
			
			
		} else {
			report_error("Greska na liniji " + funcCall.getLine() + " : ime " + func.getName() + " nije funkcija!",
					null);
			funcCall.struct = Tab.noType;
		}

	}
	

	public void visit(DesigIndent designator) {
		
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) {
			report_error("Greska na liniji " + designator.getLine() + " : ime " + designator.getName()
					+ " nije deklarisano! ", null);
		}
		designator.obj = obj;
		
		/*
		if (actPars.isEmpty() ==false && prviActPar==true && obj.getKind() == 3) {
			actPars.get(actPars.size() - 1).add(obj.getType());
		}
		*/
		
		if (obj.getKind() == 0) {
			//Konstanta
			report_info("Detektovano koriscenje konstante " + obj.getName() + " u liniji " + designator.getLine(), designator);
		}
		if (obj.getKind() == 1) {
			//Var
			if (obj.getLevel() == 0)
				report_info("Detektovano koriscenje globalne promenljive " + obj.getName() , designator);
			else 
				report_info("Detektovano koriscenje lokalne promenljive " + obj.getName() , designator);

		}
		

	}
	
	public void visit(RestDesig2 restDesig2) {
		Struct type = restDesig2.getDesignator().obj.getType();
		if (type.getKind() != Struct.Array) {
			report_error("Greska na liniji " + restDesig2.getLine() + " Ocekivani tip je niz. ", restDesig2);
			restDesig2.obj= Tab.noObj;
		}
		else {
			restDesig2.obj = new Obj(Obj.Elem, "", restDesig2.getDesignator().obj.getType().getElemType());
		}
	}
	
	public void visit(RestDesig3 restDesig2) {
		Struct type = restDesig2.getDesignator().obj.getType();
		if (type.getKind() != Struct.Array) {
			report_error("Greska na liniji " + restDesig2.getLine() + " Ocekivani tip je niz. ", restDesig2);
			restDesig2.obj= Tab.noObj;
		}
		else {
			restDesig2.obj = new Obj(Obj.Elem, "", restDesig2.getDesignator().obj.getType().getElemType());
		}
	}
	
	public void visit(RestDesig1 restDesig1) {
		Struct en = restDesig1.getDesignator().obj.getType();
		if (en.getKind() != Struct.Enum) {
			report_error("Greska na liniji " + restDesig1.getLine() + ". "+ restDesig1.getDesignator().obj.getName() + " nije enum.", restDesig1);
			restDesig1.obj = Tab.noObj;
		}
		
		restDesig1.obj = en.getMembersTable().searchKey(restDesig1.getN());
		if (restDesig1.obj == null) {
			report_error("Greska na liniji " + restDesig1.getLine() + ". "+ restDesig1.getN() + " nije clan enuma.", restDesig1);
			restDesig1.obj = Tab.noObj;
			
		}
	}
	
	public void visit(DesigEqual assignment) {
		Designator desig = assignment.getDesignator();
		
		Struct str1 = assignment.getExpr().struct;
		Struct str2 = desig.obj.getType();
		if (str2.getKind() == Struct.Enum) str2 = Tab.intType; //mozda
		
		Obj izTabele = Tab.find(desig.obj.getName());
		boolean jePromenljiva = desig.obj == izTabele;
		boolean jeElem = false;
		if (desig instanceof RestDesig2) jeElem = true;
		
		if (!(jeElem || jePromenljiva)) report_error("Leva strana dodele mora biti promenljiva ili element niza.", assignment);
		
		if (!str1.assignableTo(str2))
			report_error(
					"Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi u dodeli vrednosti ",
					null);
	}

	public void visit(ConditionFactRel relop) {
		if (relop.getExpr().struct.getKind() == Struct.Array ) {
			if (relop.getExpr1().struct.getKind() != Struct.Array) 
				report_error("Oba izraza moraju biti nizovi.Greska na liniji " + relop.getLine(), relop);
			else {
				RelOp rel = relop.getRelOp();
				if (!(rel instanceof RelEquCmp  || rel instanceof RelNotEqu))
					report_error("Relacioni operator moze biti samo jednakost ili razlika. Greska na liniji "+ relop.getLine(), relop);
			}
		}
		if (!relop.getExpr().struct.assignableTo(relop.getExpr1().struct)) //kompat?
			report_error(
					"Greska na liniji " + relop.getLine() + " : " + " nekompatibilni tipovi u dodeli vrednosti ",
					null);
	}
	
	public void visit (ConditionFact cond) {
		if (cond.getExpr().struct.getKind() != Struct.Bool)
			report_error("Uslov bi morao biti boolean vrednost.", cond);
	}
	
	public void visit(Exp e) {
		e.struct= e.getRestExpr().struct;
	}
	
	public void visit(FactNew arr) {
		/*
		SingleFactor arr1 = (SingleFactor)(arr.getParent());
		TermExpr arr2 = (TermExpr) arr1.getParent();
		Exp arr3 = (Exp) arr2.getParent();
		DesigEqual arr4 = (DesigEqual) arr3.getParent();
		DesignatStatement arr5 = (DesignatStatement) arr4.getParent();
		Obj a = Tab.find(arr5.getDesignator().obj.getName()); //niz iz tabele
		*/
		if (! arr.getExpr().struct.compatibleWith(Tab.intType)) {
			report_error("Indeks niza mora biti broj", arr);
		}
		arr.struct = new Struct(Struct.Array, arr.getType().struct);
		
	}
	
	public void visit(SingleActPar actPar) {
		//otvaram listu za parametre svaki put kad se pozove neka metoda
		actPars.add(new ArrayList<Struct>());
		actPars.get(actPars.size() - 1).add(actPar.getExpr().struct);
		
		//provera za orc, chr, len
		/*
		String naziv = currentMethod.getName();
		if (naziv.equals("chr")) {
			if (actPar.getExpr().struct != Tab.intType) {
				report_error("Argument metode chr mora biti int.", actPar);
			}
		}
		if (naziv.equals("ord")) {
			if (actPar.getExpr().struct != Tab.charType) {
				report_error("Argument metode ord mora biti char.", actPar);
			}
		}
		if (naziv.equals("len")) {
			if (actPar.getExpr().struct.getKind() !=  Struct.Array) {
				report_error("Argument metode len mora biti niz.", actPar);
			}
		}
		*/
	}
	
	public void visit(MulActPar actPar) {
		actPars.get(actPars.size() - 1).add(actPar.getExpr().struct);
	}
	
	public void visit(DesigNoActPars met) {
		DesignatStatement ds = (DesignatStatement)met.getParent();
		Obj func = ds.getDesignator().obj;
		if (Obj.Meth == func.getKind()) {
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + met.getLine(), null);
			//funcCall.struct = func.getType();
		} else {
			report_error("Greska na liniji " + met.getLine() + " : ime " + func.getName() + " nije funkcija!",
					null);
			//funcCall.struct = Tab.noType;
		}		
		
		if (func.getLevel() != 0) {
			report_error("Nije adekvatan broj argumenata!", met);
		}
	}
	
	public void visit(DesigActPars met) {
		//poziv metode, if Parent mogu da dohvatim njen naziv da alociram niz act parametara
		DesignatStatement ds = (DesignatStatement)met.getParent();
		Obj func = ds.getDesignator().obj;
		if (Obj.Meth == func.getKind()) {
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + met.getLine(), null);
			//funcCall.struct = func.getType();
		} else {
			report_error("Greska na liniji " + met.getLine() + " : ime " + func.getName() + " nije funkcija!",
					null);
			//funcCall.struct = Tab.noType;
		}

		Collection<Obj> locals = func.getLocalSymbols();
		
		//if (locals.size() != actPars.size()) {
		int i = actPars.size() - 1;
		if (func.getLevel() != actPars.get(i).size()) {
			report_error("Nije adekvatan broj argumenata! Greska ", met);
		} else {
			List<Struct> actParsList = actPars.get(i);
			for(Obj formArg: locals) {
				Struct actPar = actParsList.remove(0);
				if (!actPar.assignableTo(formArg.getType())) {
					report_error("Nekompatibilan parametar " + formArg.getName(), met);
				}
				if (actParsList.isEmpty()) {
					actPars.remove(i);
					break;
				}
			}
			
		}
		
		
//		int i = 0;
//		for (Obj s : locals) {
//			Struct a = s.getType();
//			Struct b = actPars.get(i++);
//			
//			if (!a.compatibleWith(b)) {
//				report_error("Argumenti nisu kompatibilni! Greska na liniji " + met.getLine(), met);	
//				actPars = new ArrayList<Struct>();
//				return;
//			}
//			
//			if (i == actPars.size()) break;
//		}
		
		//actPars.remove(actPars.size() - 1);
		//prviActPar = true;
		
	}
	
	public void visit(FPList fp) {
		currentMethod.setLevel(Tab.currentScope.getnVars());
	}
	
	public void visit(NoFormParam fp) {
		currentMethod.setLevel(0);
	}
	
	public void visit(NoActParams noap) {
		FuncCall fc = (FuncCall)noap.getParent();
		String funName = fc.getDesignator().obj.getName();
		Collection<Obj> locals = Tab.find(funName).getLocalSymbols();
		int num = Tab.find(funName).getLevel();
		if (num != 0) {
			report_error("Nije adekvatan broj argumenata! Greska na liniji " + noap.getLine(), noap);
			return;
		}
	}
	
	public void visit(ActParams ap) {
		prviActPar = true;
		
/*		FuncCall fc = (FuncCall)ap.getParent();
		String funName = fc.getDesignator().obj.getName();
		Collection<Obj> locals = Tab.find(funName).getLocalSymbols();
		int num = Tab.find(funName).getLevel();
		if (num != actPars.size()) {
			report_error("Nije adekvatan broj argumenata! Greska na liniji " + ap.getLine(), ap);
			return;
		}
		
		int i= actPars.size() - 1;
		for (Obj s : locals) {
			Struct a = s.getType();
			Struct b = actPars.get(i--);
			
			if (!a.compatibleWith(b)) {
				report_error("Argumenti nisu kompatibilni! Greska na liniji " + ap.getLine(), ap);	
				return;
			}
			if (i<0) break;
		}
		
		actPars = new ArrayList<Struct>(); */
	}
	
	public void visit(FactEx f) {
		f.struct = f.getExpr().struct;
	}
	
	public void visit (BreakStm bre) {
		//mora biti u for petlji
		Matched parent1 = (Matched) bre.getParent();
		Matched parent2 = (Matched) parent1.getParent();
		
		if (!(parent2 instanceof ForStm))
			report_error("Break se moze naci samo unutar for petlje.", bre);
		
	}
	
	public void visit (ContinueStm con) {
		//mora biti u for petlji
		Matched parent1 = (Matched) con.getParent();
		Matched parent2 = (Matched) parent1.getParent();
		
		if (!(parent2 instanceof ForStm))
			report_error("Continue se moze naci samo unutar for petlje.", con);
		
	}
	
	public void visit(ReadStmt read) {
		
		Designator desig = read.getDesignator();
		

		//Obj izTabele = Tab.find(desig.obj.getName()); //ovako ili u current scope-u?
		//boolean jePromenljiva = desig.obj == izTabele;
		
		boolean jePromenljiva = desig.obj.getKind() == Obj.Var;
		boolean jeElem = false;
		if (desig instanceof RestDesig2) jeElem = true;
		
		if (!(jeElem || jePromenljiva)) report_error("Read argument mora biti promenljiva ili element niza.", read);
		
		Struct tip = null;
		if (jePromenljiva) {
			tip = desig.obj.getType();
			
		}
		else if (jeElem) {
			Designator desigNiz = ((RestDesig2)desig).getDesignator();
			tip = desigNiz.obj.getType().getElemType();
		}
		if (!(tip == Tab.intType || tip== Tab.charType) ){
			report_error("Argument metode read mora biti int, char ili bool.", read);
		}
	}
	


	public boolean passed() {
		return !errorDetected;
	}
	
}
