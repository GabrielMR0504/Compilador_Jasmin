package lexico;

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
	WHILE	= 268,
	READ 	= 269,
	WRITE	= 270,

	//Operadores e pontuacao
	AND = 271, // &&
	OR 	= 272, // ||
	EQ 	= 273, // ==
	NE 	= 274, // !=
	GE	= 275, // >=
	LE	= 276, // <=
	
	// Outros tokens
	NUM = 277,
	ID	= 278;
	
}
