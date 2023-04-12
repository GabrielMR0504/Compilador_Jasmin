package compilador;
import java.io.*;
import java.util.*;

public class Lexico {
	public static int line = 1;
	private char ch = ' ';
	private FileReader file;
	private Hashtable<String, Word> words = new Hashtable<String, Word>();
	private String outherTokens = "";
	private void reserveWord(Word word) {
		words.put(word.getLexema(), word);
	}
	
	
	
	public Lexico(String fileName) throws FileNotFoundException{
		try {
			file = new FileReader(fileName);
			
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
			return false;
		}
		this.ch =' ';
		return true;
	}
	
	public Token scan() throws IOException{
		while(true) {
			readNextCh();
			if(ch == ' ' || ch == '\t' || ch == '\b') {
				
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
		default:
			if(outherTokens.contains(""+ch)) {
				return new Token(ch);
			}
		}
		
		if(Character.isDigit(ch)) {
			int value = 0;
			do {
				value = 10*value + Character.digit(ch, 10);
				readNextCh();
			}
			while(Character.isDigit(ch));
			return new Number(value);
		}
		
		if(Character.isLetter(ch)) {
			String word = "";
			do {
				word += ch;
				readNextCh();
			}
			while(Character.isLetter(ch) || ch == '_' || Character.isDigit(ch));
			
			if(words.contains(word)) {return this.words.get(word);}
			words.put(word, new Word(word, Tag.ID));
			return words.get(word);
		}
		
		Token notSpecified = new Token(ch);
		ch = ' ';
		reportError(notSpecified);
		return notSpecified;
		
	}



	private void reportError(Token notSpecified) {
		System.out.println("Lexicon Error: Token " + (char) notSpecified.tag +"does not exist, line: " + line);
	}	
		
}
