package sintatico;

import java.io.IOException;
import java.util.Scanner;

import lexico.Lexico;

public class TestSintatico {
	public static void main(String[] args) throws IOException {
		//try (Scanner scanner = new Scanner(System.in)) {
			//String nomeArquivo = scanner.next();
			String nomeArquivo = "teste_7.txt";	
			try {
				Lexico lexico = new Lexico(nomeArquivo);	
				Sintatico sintatico = new Sintatico(lexico);
				sintatico.execAnalisador();
				
			}
			catch(NullPointerException e){
				System.out.println("Erro: Não foi possivel executar o analisador lexico");
			}
		}
	//}
}
