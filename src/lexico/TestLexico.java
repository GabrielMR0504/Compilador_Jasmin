package lexico;
import java.io.IOException;
import java.util.Scanner;

public class TestLexico {

	public static void main(String[] args) throws IOException {
		
		try (Scanner scanner = new Scanner(System.in)) {
			//String nomeArquivo = scanner.next();
			try {
				Lexico lexico = new Lexico("teste_3.txt");	
				Token tokenAux;
				while (true) {
				    tokenAux = lexico.scan();
				    if (tokenAux == null) {
				        break;
				    }
				    if (tokenAux instanceof Word) {
				        Word palavra = (Word) tokenAux;
				        System.out.println(palavra.getLexema());
				    } else if (tokenAux instanceof Number) {
				        Number numero = (Number) tokenAux;
				        System.out.println(numero.toString());
				    } else {
				        char tagChar = (char) tokenAux.tag;
				        System.out.println(tagChar);
				    }
				}
			}
			catch(NullPointerException e){
				System.out.println("Não foi possivel executar o analisador lexico");
			}
		}

		
	}

}
