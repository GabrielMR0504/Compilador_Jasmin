package sintatico;

import java.io.IOException;

import lexico.Lexico;
import lexico.Tag;
import lexico.Token;
import lexico.Word;

public class Sintatico {

	private Word word = null;
	private Lexico lex = null;
	
	public Sintatico(Lexico lexico){
		this.lex = lexico;
	}
	
	public void execAnalisador() {
		advance();
		program();
	}
	
	
	public void advance() {
		try {
			word = lex.scan();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eat(int t) {
		if (word.tag == t)
			advance();
		else
	        error();
	}
	public void error() {
		System.out.printf("Erro sintatico no token: %s tag: %d", word.getLexema(), word.tag);
	}
	
	public void program() {
		switch(word.tag) {
			case Tag.PROGRAM:
				eat(Tag.PROGRAM);
				eat(Tag.ID);
				declList();
				eat(Tag.BEGIN);
				stmtList();
				eat(Tag.END);
				eat('.');
				break;
			default:
				error();
		}
	}
	
	public void declList() {
		switch(word.tag) {
			case Tag.ID:
				declListAux();
				break;
			case Tag.BEGIN:
				declListAux();
				break;
			default:
				error();
		}
	}
	
	public void declListAux() {
		switch(word.tag) {
		case Tag.ID:
			decl();
			eat(';');
			declListAux();
			break;
		case Tag.BEGIN:
			break;
		default:
			error();
		}
	}

	private void decl() {
		switch(word.tag) {
		case Tag.ID:
			identList();
			eat(Tag.IS);
			type();
			break;
		default:
			error();
		}
		
	}

	private void identList() {
		switch(word.tag) {
		case Tag.ID:
			eat(Tag.ID);
			identListAux();
			break;
		default:
			error();
		}		
	}

	private void identListAux() {
		switch(word.tag) {
		case Tag.IS:
			break;
		case ',':
			eat(',');
			eat(Tag.ID);
			identListAux();
			break;
		default:
			error();
		}	
	}
	
	private void type() {
		switch(word.tag) {
		case Tag.INT:
			eat(Tag.INT);
			break;
		case Tag.FLOAT:
			eat(Tag.FLOAT);
			break;
		case Tag.CHAR:
			eat(Tag.CHAR);
			break;
		default:
			error();
		}	
	}
	
	private void stmtList() {
		switch(word.tag) {
		case Tag.ID:
			stmt();
			stmtAux();
			break;
		case Tag.IF:
			stmt();
			stmtAux();
			break;
		case Tag.REPEAT:
			stmt();
			stmtAux();
			break;
		case Tag.WHILE:
			stmt();
			stmtAux();
			break;
		case Tag.READ:
			stmt();
			stmtAux();
			break;
		case Tag.WRITE:
			stmt();
			stmtAux();
			break;
		default:
			error();
		}
	}


	private void stmtAux() {
		switch(word.tag) {
		case Tag.END:

			break;
		case ';':

			break;
		case Tag.ELSE:

			break;
		case Tag.UNTIL:

			break;
		default:
			error();
		}
		
	}
	
	private void stmt() {
		switch(word.tag) {
			case Tag.ID:
				assignStmt();
				break;
			case Tag.IF:
				ifStmt();
				break;
			case Tag.REPEAT:
				repeatStmt();
				break;
			case Tag.WHILE:
				whileStmt();
				break;
			case Tag.READ:
				readStmt();
				break;
			case Tag.WRITE:
				writeStmt();
				break;
			default:
				error();
			}
		
		
	}

	private void assignStmt() {
		switch(word.tag) {
			case Tag.ID:
				eat(Tag.ID);
				eat('=');
				simpleExpr();
				break;
			default:
				error();
		}
	}
	
	private void ifStmt() {
		switch(word.tag) {
			case Tag.ID:
				eat(Tag.IF);
				condition();
				eat(Tag.THEN);
				stmtList();
				elseStmt();
				break;
			default:
				error();
		}
	}
	

	private void elseStmt() {
		switch(word.tag) {
			case Tag.END:
				eat(Tag.END);
				break;
			case Tag.ELSE:
				eat(Tag.ELSE);
				stmtList();
				eat(Tag.END);
				break;
			default:
				error();
		}
	}

	private void condition() {
		switch(word.tag) {
			case Tag.ID:
				expression();
				break;
			case '!':
				expression();
				break;
			case '-':
				expression();
				break;
			default:
				error();
		}
	}
	
	
	

	private void repeatStmt() {
		switch(word.tag) {
			case Tag.REPEAT:
				eat(Tag.REPEAT);
				stmtList();
				stmtSuffix();
				break;
			default:
				error();
		}
		
	}
	
	private void stmtSuffix() {
		switch(word.tag) {
			case Tag.UNTIL:
				eat(Tag.UNTIL);
				condition();
				break;
			default:
				error();
		}
	}

	private void whileStmt() {
		switch(word.tag) {
			case Tag.WHILE:
				stmtPrefix();
				stmtList();
				eat(Tag.END);
				break;
			default:
				error();
		}
	}
	
	
	
	private void stmtPrefix() {
		switch(word.tag) {
			case Tag.WHILE:
				eat(Tag.WHILE);
				condition();
				eat(Tag.DO);
				break;
			default:
				error();
		}
	}
	
	private void readStmt() {
		switch(word.tag) {
			case Tag.READ:
				eat(Tag.READ);
				eat('(');
				eat(Tag.ID);
				eat(')');
				break;
			default:
				error();
		}
	}
	
	private void writeStmt() {
		switch(word.tag) {
			case Tag.WRITE:
				eat(Tag.WRITE);
				eat('(');
				writable();
				eat(')');
				break;
			default:
				error();
		}
	}

	private void writable() {
		switch(word.tag) {
			case Tag.LITERAL:
				eat(Tag.LITERAL);
				break;
			case Tag.ID:
			case '!':
			case '-':
				simpleExpr();
				break;
			default:
				error();
		}
	}
	
	private void expression() {
		switch(word.tag) {
			case Tag.ID:
			case '!':
			case '-':
				simpleExpr();
				relopExpr();
				break;
			default:
				error();
		}
	}

	private void relopExpr() {
		switch(word.tag) {
			case Tag.THEN:
			case Tag.DO:
			case ')':
				break;
			case Tag.EQ:
			case '>':
			case Tag.GE:
			case '<':
			case Tag.LE:
			case Tag.NE:
				relop();
				simpleExpr();
				break;
			default:
				error();
		}
	}


	private void simpleExpr() {
		switch(word.tag) {
			case Tag.ID:
			case '!':
			case '-':
				term();
				simpleAux();
				break;
			default:
				error();
		}
	}

	private void simpleAux() {
		switch(word.tag) {
			case Tag.THEN:
			case Tag.DO:
			case Tag.EQ:
			case '>':
			case Tag.GE:
			case '<':
			case Tag.LE:
			case Tag.NE:
				break;
			case '-':
			case '+':
			case Tag.OR:	
				addop();
				term();
				simpleAux();
				break;
			default:
				error();
		}
	}

	private void term() {
		switch(word.tag) {
			case Tag.ID:
			case '!':
			case '-':
				factorA();
				termAux();
				break;
			default:
				error();
		}
	}

	private void termAux() {
		switch(word.tag) {
			case '-':
			case '+':
			case Tag.OR:
				break;
			case '*':
			case '/':
			case Tag.AND:
				mulop();
				factorA();
				termAux();
				break;
			default:
				error();
		}
		
	}

	private void factorA() {
		switch(word.tag) {
			case Tag.ID:
				factor();
				break;
			case '!':
				eat('!');
				factor();
				break;
			case '-':
				eat('-');
				factor();
				break;
			default:
				error();
		}
	}

	private void factor() {
		switch(word.tag) {
			case Tag.ID:
				eat(Tag.ID);
				break;
			case '(':
				eat('(');
				expression();
				eat(')');
				break;
			case Tag.INT:
			case Tag.FLOAT:
			case Tag.CHAR:
				constant();
				break;
			default:
				error();
		}
	}

	private void relop() {
		switch(word.tag) {
			case Tag.EQ:
				eat(Tag.EQ);
				break;
			case '>':
				eat('>');
				break;
			case Tag.GE:
				eat(Tag.GE);
				break;
			case '<':
				eat('<');
				break;
			case Tag.LE:
				eat(Tag.LE);
				break;
			case Tag.NE:
				eat(Tag.NE);
				break;
			default:
				error();
		}	
	}
	
	private void addop() {
		switch(word.tag) {
			case '-':
				eat('-');
				break;
			case '+':
				eat('+');
				break;
			case Tag.OR:
				eat(Tag.OR);
				break;
			default:
				error();
		}	
	}
	

	private void mulop() {
		switch(word.tag) {
			case '*':
				eat('*');
				break;
			case '/':
				eat('/');
				break;
			case Tag.AND:
				eat(Tag.AND);
				break;
			default:
				error();
		}	
	}
	
	private void constant() {
		switch(word.tag) {
		case Tag.INT:
			eat(Tag.INT);
			break;
		case Tag.FLOAT:
			eat(Tag.FLOAT);
			break;
		case Tag.CHAR:
			eat(Tag.CHAR);
			break;
		default:
			error();
		}	
	}
}



