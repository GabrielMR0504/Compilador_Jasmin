# Compilador_Jasmin
###Esse projeto tem como objetivo implementar em Java um compilador para a linguagem de programação Jasmin.

###A implementação foi separada em 4 partes:

##1- Analisador Léxico e Tabela de símbolos
##2- Analisador Sintático
##3- Analisador Semântico
##4- Gerador de Código

Gramática da Linguagem (Jasmin):

program ::= program identifier begin [decl-list] stmt-list end "."
decl-list ::= decl ";" { decl ";"}
decl ::= ident-list is type
ident-list ::= identifier {"," identifier}
type ::= int | float | char
stmt-list ::= stmt {";" stmt}
stmt ::= assign-stmt | if-stmt | while-stmt | repeat-stmt
 | read-stmt | write-stmt
assign-stmt ::= identifier "=" simple_expr
if-stmt ::= if condition then stmt-list end
 | if condition then stmt-list else stmt-list end
condition ::= expression
repeat-stmt ::= repeat stmt-list stmt-suffix
stmt-suffix ::= until condition
while-stmt ::= stmt-prefix stmt-list end
stmt-prefix ::= while condition do
read-stmt ::= read "(" identifier ")"
write-stmt ::= write "(" writable ")"
writable ::= simple-expr | literal
expression ::= simple-expr | simple-expr relop simple-expr
simple-expr ::= term | simple-expr addop term
term ::= factor-a | term mulop factor-a
fator-a ::= factor | "!" factor | "-" factor
factor ::= identifier | constant | "(" expression ")"
relop ::= "==" | ">" | ">=" | "<" | "<=" | "!="
addop ::= "+" | "-" | "||"
mulop ::= "*" | "/" | "&&"
constant ::= integer_const | float_const | char_const
Padrões dos tokens
digit ::= [0-9]
carac ::= um dos caracteres ASCII
caractere ::= um dos caracteres ASCII, exceto quebra de linha
integer_const ::= digit+
float_const ::= digit+
“.”digit+
char_const ::= " ‘ " carac " ’ "
literal ::= "{" caractere* "}"
identifier ::= letter (letter | digit | " _")*
letter ::= [A-Za-z]

Comandos para rodar os testes do analizador léxico:

Para executar o teste da análise léxica basta ir no terminal cmd e executar o comando,
dentro da pasta onde estiver o arquivo:
java -jar analizador_lexico.jar
Lembrando que para executar essa aplicação java em sua máquina é necessário ter
instalado o JDK. Site para instalação:
https://www.oracle.com/br/java/technologies/downloads/#jdk20-windows
Assim que o programa rodar, basta digitar no terminal o nome do arquivo de teste, como por
exemplo: “teste_1.txt” e pressione enter para executar o teste.