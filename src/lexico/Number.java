package lexico;

public class Number extends Token{
	public final int value;
	public Number(int value) {
		super(Tag.NUM);
		this.value = value;
	}
	
	public String toString(){
		return "" + value;
	}
}
