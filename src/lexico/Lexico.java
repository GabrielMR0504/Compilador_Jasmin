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
 		
	}

	private void readNextCh() throws IOException{
		ch = (char) file.read();
	}
	
	private boolean readNextCh(char ch) throws IOException{
		readNextCh();
		if(this.ch != ch) {
			file.seek(file.getFilePointer() - 1);
			this.ch = ch;
			return false;
		}
		this.ch =' ';
		return true;
	}
	
	public Token scan() throws IOException{


		
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
					tabelaSimbulos.put(dig, new Word(dig, Tag.FLOAT));
					return tabelaSimbulos.get(dig);
				}
				file.seek(file.getFilePointer()-1);
			}
			file.seek(file.getFilePointer()-1);
			tabelaSimbulos.put(dig, new Word(dig, Tag.INT));
			return tabelaSimbulos.get(dig);
		}
		
		
		//char_const
		if(ch == '\'') {
			String carac= "'";
			readNextCh();
			if(ch < 128 && ch != '\'') {
				carac += ch;
				readNextCh();
				if(ch == '\'') {
					carac += '\'';
					tabelaSimbulos.put(carac, new Word(carac, Tag.CHAR));
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
			return new Token((char)ch);
		}
		
		Token notSpecified = new Token(ch);
		reportError(notSpecified);
		return notSpecified;
		
	}



	private void reportError(Token notSpecified) {
		System.out.println("\nLexicon Error: Token " + "\"" + (char) notSpecified.tag + "\"" + " does not exist, line: " + line);
	}	
		
}
