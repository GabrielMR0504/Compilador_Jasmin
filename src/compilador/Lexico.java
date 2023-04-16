package compilador;
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
			file = new RandomAccessFile(fileName, "rw");
			
		}
		catch(FileNotFoundException e) {
			System.out.println("Arquivo não encontrado");
			throw e;
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
		if(ch == 65535) {
			return null;
		}
		
		while(true) {
			readNextCh();
			if(ch == ' ' || ch == '\r' || ch == '\t' || ch == '\b') {
				
			}
			else if(ch == '\n'){
				line++;
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
		
		if(Character.isDigit(ch)) {
			int value = 0;
			do {
				value = 10*value + Character.digit(ch, 10);
				readNextCh();
			}
			while(Character.isDigit(ch));
			file.seek(file.getFilePointer()-1);
			return new Number(value);
		}
		
		if(Character.isLetter(ch) & ch < 128) {
			String word = "";
			do {
				word += ch;
				readNextCh();
			}
			while((Character.isLetter(ch) || ch == '_' || Character.isDigit(ch))  && ch < 128);
			file.seek(file.getFilePointer() - 1);
			if(tabelaSimbulos.contains(word)) {return this.tabelaSimbulos.get(word);}
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
