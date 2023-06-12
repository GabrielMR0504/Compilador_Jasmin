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
		System.out.printf("Sucesso: Programa não contem erros sintaticos\n");
	}
	
	
	public void advance() {
	    try {
	        Word scannedWord = lex.scan();
	        if (scannedWord != null) {
	            word = scannedWord;
	            //System.out.println(word.getLexema());
	        }
	    } catch (IOException e) {

	    }
	}
	
	public void eat(int t) {
		if (word.tag == t)
			advance();
		else
	        error();
	}
	public void error() {
		System.out.printf("Erro sintatico no token: \"%s\", tag: %d, linha: %d\n", word.getLexema(), word.tag, Lexico.line);
		System.exit(0);
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
			case Tag.IF:
			case Tag.REPEAT:
			case Tag.WHILE:
			case Tag.READ:
			case Tag.WRITE:
				stmt();
				eat(';');
				stmtAux();
				break;
			default:
				error();
		}
	}


	private void stmtAux() {
		switch(word.tag) {
			case Tag.ID:
			case Tag.IF:
			case Tag.REPEAT:
			case Tag.WHILE:
			case Tag.READ:
			case Tag.WRITE:
				stmt();
				eat(';');
				stmtAux();
				break;
			default:
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
			case Tag.END:
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
			case Tag.IF:
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
			case '(':
			case '!':
			case '-':
			case Tag.INTEGER_CONST:
			case Tag.FLOAT_CONST:
			case Tag.CHAR_CONST:
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
			case '(':
			case '!':
			case '-':
			case Tag.INTEGER_CONST:
			case Tag.FLOAT_CONST:
			case Tag.CHAR_CONST:
				simpleExpr();
				break;
			default:
				error();
		}
	}
	
	private void expression() {
		switch(word.tag) {
			case Tag.ID:
			case '(':
			case '!':
			case '-':
			case Tag.INTEGER_CONST:
			case Tag.FLOAT_CONST:
			case Tag.CHAR_CONST:
				simpleExpr();
				relopExpr();
				break;
			default:
				error();
		}
	}

	private void relopExpr() {
		switch(word.tag) {
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
		}
	}

	private void simpleExpr() {
		switch(word.tag) {
			case Tag.ID:
			case '!':
			case '-':
			case '(':
			case Tag.INTEGER_CONST:
			case Tag.FLOAT_CONST:
			case Tag.CHAR_CONST:
				term();
				simpleAux();
				break;
			default:
				error();
		}
	}

	private void simpleAux() {
		switch(word.tag) {
			case ';':
			case Tag.END:
			case Tag.THEN:
			case Tag.UNTIL:
			case Tag.DO:
			case '(':
				break;
			case '-':
			case '+':
			case Tag.OR:	
				addop();
				term();
				simpleAux();
				break;
			default:
		}
	}

	private void term() {
		switch(word.tag) {
			case Tag.ID:
			case '!':
			case '-':
			case '(':
			case Tag.INTEGER_CONST:
			case Tag.FLOAT_CONST:
			case Tag.CHAR_CONST:
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
		}
		
	}

	private void factorA() {
		switch(word.tag) {
			case Tag.ID:
			case '(':
			case Tag.INTEGER_CONST:
			case Tag.FLOAT_CONST:
			case Tag.CHAR_CONST:
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
			case Tag.INTEGER_CONST:
			case Tag.FLOAT_CONST:
			case Tag.CHAR_CONST:
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
			case Tag.INTEGER_CONST:
				eat(Tag.INTEGER_CONST);
				break;
			case Tag.FLOAT_CONST:
				eat(Tag.FLOAT_CONST);
				break;
			case Tag.CHAR_CONST:
				eat(Tag.CHAR_CONST);
				break;
			default:
				error();
		}	
	}
}



