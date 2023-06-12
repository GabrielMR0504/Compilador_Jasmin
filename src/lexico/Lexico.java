package lexico;
import java.io.RandomAccessFile;
import java.io.*;
import java.util.*;

public class Lexico {
	public static int line = 1;
	private char ch = ' ';
	private RandomAccessFile file;
	private Hashtable<String, Word> tabelaSimbulos = new Hashtable<String, Word>();
	private String outherTokens = "";
	private void reserveWord(Word word) {
		tabelaSimbulos.put(word.getLexema(), word);
	}
	
	
	
	public Lexico(String fileName) throws FileNotFoundException{
		try {
			file = new RandomAccessFile(fileName, "r");
			
		}
		catch(FileNotFoundException e) {
			System.out.println("Arquivo não encontrado");
		}
		
		outherTokens = ".;,=()!-<>+*/'{}";
		
		reserveWord(new Word("program", Tag.PROGRAM));
 		reserveWord(new Word("begin", Tag.BEGIN));
 		reserveWord(new Word("end", Tag.END));
 		reserveWord(new Word("is", Tag.IS));
 		reserveWord(new Word("int", Tag.INT));
 		reserveWord(new Word("float", Tag.FLOAT));
 		reserveWord(new Word("char", Tag.CHAR));
 		reserveWord(new Word("if", Tag.IF));
 		reserveWord(new Word("then", Tag.THEN));
 		reserveWord(new Word("repeat", Tag.REPEAT));
 		reserveWord(new Word("until", Tag.UNTIL));
 		reserveWord(new Word("while", Tag.WHILE));
 		reserveWord(new Word("read", Tag.READ));
 		reserveWord(new Word("write", Tag.WRITE));
 		reserveWord(new Word("do", Tag.DO));
 		reserveWord(new Word("else", Tag.ELSE));
 		
	}

	private void readNextCh() throws IOException {
	    int nextChar = file.read();
	    ch = (nextChar != -1) ? (char) nextChar : (char) -1;
	}
	
	private boolean readNextCh(char ch) throws IOException{
		char aux_c = this.ch;
		readNextCh();
		if(this.ch != ch) {
			file.seek(file.getFilePointer() - 1);
			this.ch = aux_c;
			return false;
		}
		this.ch =' ';
		return true;
	}
	
	public Word scan() throws IOException{


		
		while(true) {
			
			if(ch == 65535) {
				return null;
			}
			
			readNextCh();
			if(ch == ' ' || ch == '\r' || ch == '\t' || ch == '\b') {	
			}
			else if(ch == '\n'){
				line++;
			}
			else if(ch == 65535) {
				return null;
			}
			

			//comentario do codigo
			else if (ch == 47) { 	// 47 = '/'
				readNextCh();
				if (ch == '*') {
					int ch_before;
					do {
						ch_before = ch;
						if (ch_before == (char) -1) {
							break; // Sai do loop quando chegar ao final do arquivo
						}
						readNextCh();
					} while (ch_before != '*' || ch != 47);
						if(ch != 65535) readNextCh();
				}
				else {
					file.seek(file.getFilePointer() - 2);
					readNextCh();
					break;
				}
			}
			else {
				break;
			}
			
			
			
		}
		
		

		
		switch(ch) {
		case '&':
			if (readNextCh(ch)) {return Word.and;} else {break;} 
		case '|':
			if (readNextCh(ch)) {return Word.or;} else {break;} 
		case '=':
			if (readNextCh(ch)) {return Word.eq;} else {break;} 
		case '!':
			if (readNextCh('=')) {return Word.ne;} else {break;} 
		case '>':
			if (readNextCh('=')) {return Word.ge;} else {break;} 
		case '<':
			if (readNextCh('=')) {return Word.le;} else {break;} 
		}
		
		
		//integer_const e float_const
		if(Character.isDigit(ch)) {
			String dig = "";
			do {
				dig += ch;
				readNextCh();
			}
			while(Character.isDigit(ch));
			
			if(ch == '.') {
				readNextCh();
				if(Character.isDigit(ch)) {
					dig += '.';
					do {
						dig += ch;
						readNextCh();
					}
					while(Character.isDigit(ch));
					file.seek(file.getFilePointer()-1);
					tabelaSimbulos.put(dig, new Word(dig, Tag.FLOAT_CONST));
					return tabelaSimbulos.get(dig);
				}
				file.seek(file.getFilePointer()-1);
			}
			file.seek(file.getFilePointer()-1);
			tabelaSimbulos.put(dig, new Word(dig, Tag.INTEGER_CONST));
			return tabelaSimbulos.get(dig);
		}
		
		
		//char_const
		if(ch == '\'') {
			String carac= "";
			readNextCh();
			if(ch < 128 && ch != '\'') {
				carac += ch;
				readNextCh();
				if(ch == '\'') {
					tabelaSimbulos.put(carac, new Word(carac, Tag.CHAR_CONST));
					return tabelaSimbulos.get(carac);
				}
				file.seek(file.getFilePointer() - 1);
			}
			file.seek(file.getFilePointer() - 1);
			
		}
		
		
		//literal
		if(ch == '{') {
			String literal= "{";
			int cont = 1;
			readNextCh();
			while(ch < 128 && ch != '}' && ch !='\n') {
				cont++;
				literal += ch;
				readNextCh();
			}
			if(ch == '}') {
				literal += '}';
				tabelaSimbulos.put(literal, new Word(literal, Tag.LITERAL));
				return tabelaSimbulos.get(literal);
			}
			file.seek(file.getFilePointer() - cont);
		}
		
		
		//identifier
		if(Character.isLetter(ch) & ch < 128) {
			String word = "";
			do {
				word += ch;
				readNextCh();
			}
			while((Character.isLetter(ch) || ch == '_' || Character.isDigit(ch))  && ch < 128);
			file.seek(file.getFilePointer() - 1);
			if(tabelaSimbulos.containsKey(word)) {return this.tabelaSimbulos.get(word);}
			
			tabelaSimbulos.put(word, new Word(word, Tag.ID));
			return tabelaSimbulos.get(word);
		}
		
		if(outherTokens.contains(""+(char)ch)) {
			return new Word(ch+"", ch);
		}
		
		Word notSpecified = new Word(ch+"", ch);
		reportError(notSpecified);
		return notSpecified;
		
	}



	private void reportError(Token notSpecified) {
		System.out.println("\nLexicon Error: Token " + "\"" + (char) notSpecified.tag + "\"" + " does not exist, line: " + line);
	}	
		
}
