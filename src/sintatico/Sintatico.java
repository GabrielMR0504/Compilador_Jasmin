package sintatico;

import java.io.IOException;

import lexico.Lexico;
import lexico.Tag;
import lexico.Token;

public class Sintatico {

	private Token tok = null;
	private Lexico lex = null;
	
	public Sintatico(Lexico lexico){
		this.lex = lexico;
	}
	
	public void execAnalisador() throws IOException {
		advance();
		program();
	}
	
	public void advance() throws IOException{
		tok = lex.scan();
	}
	public void eat(int t) {
		if (tok.tag == t)
			try {
				advance();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
	        error();
	}
	public void error() {
		System.out.printf("Erro no token: %d", tok);
	}
	
	public void program() {
		switch(tok.tag) {
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
		switch(tok.tag) {
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
		switch(tok.tag) {
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
		switch(tok.tag) {
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
		switch(tok.tag) {
		case Tag.ID:
			eat(Tag.ID);
			identListAux();
			break;
		default:
			error();
		}		
	}

	private void identListAux() {
		switch(tok.tag) {
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
		switch(tok.tag) {
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
		switch(tok.tag) {
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
		switch(tok.tag) {
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
		// TODO Auto-generated method stub
		
	}
	
	
	/*
	switch(tok.tag) {
	case Tag:

		break;
	default:
		error();
	}
	*/
	

	
	
}



