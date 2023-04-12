package compilador;

public class Word extends Token{
	private String lexema = "";
	public static final Word and = new Word("&&", Tag.AND);
	public static final Word or = new Word("||", Tag.OR);
	public static final Word eq = new Word("==", Tag.EQ);
	public static final Word ge = new Word(">=", Tag.GE);
	public static final Word le = new Word("<=", Tag.LE);
	public static final Word ne = new Word("!=", Tag.NE);
	
	public Word(String lexema, int tag){
		super(tag);
		this.lexema = lexema;
	}
	
	public String toString(){
		return "" + lexema;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

}
