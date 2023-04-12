package compilador;

public class Tag {
	public final static int
	//Palavras reservadas 
	PROGRAM = 256,
	BEGIN 	= 257,
	END		= 258,
	IS		= 259,
	INT 	= 260,
	FLOAT	= 261,
	CHAR	= 262,
	IF 		= 263,
	THEN	= 264,
	ELSE	= 265,
	REPEAT 	= 266,
	UNTIL	= 267,
	WHILE	= 267,
	READ 	= 268,
	WRITE	= 268,

	//Operadores e pontuacao
	AND = 269, // &&
	OR 	= 270, // ||
	EQ 	= 271, // ==
	NE 	= 274, // !=
	GE	= 275, // >=
	LE	= 276, // <=
	
	// Outros tokens
	NUM = 277,
	ID	= 278;
	
}
