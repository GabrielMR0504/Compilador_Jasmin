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
	DO  	= 271,
	//Operadores e pontuacao
	AND = 272, // &&
	OR 	= 273, // ||
	EQ 	= 274, // ==
	NE 	= 275, // !=
	GE	= 276, // >=
	LE	= 277, // <=
	
	// Outros tokens
	NUM = 278,
	ID	= 279,
	LITERAL = 280;
	
}
