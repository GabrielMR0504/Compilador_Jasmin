package lexico;
import java.io.IOException;
import java.util.Scanner;

public class TestLexico {

	public static void main(String[] args) throws IOException {
		
		try (Scanner scanner = new Scanner(System.in)) {
			String nomeArquivo = scanner.next();
			try {
				Lexico lexico = new Lexico(nomeArquivo);	
				Token tokenAux;
				while (true) {
				    tokenAux = lexico.scan();
				    if (tokenAux == null) {
				        break;
				    }
				    if (tokenAux instanceof Word) {
				        Word palavra = (Word) tokenAux;
				        System.out.print(" "+palavra.getLexema());
				    } else if (tokenAux instanceof Number) {
				        Number numero = (Number) tokenAux;
				        System.out.print(" "+numero.toString());
				    } else {
				        char tagChar = (char) tokenAux.tag;
				        System.out.print(" "+tagChar);
				    }
				}
			}
			catch(NullPointerException e){
				System.out.println("Não foi possivel executar o analisador lexico");
			}
		}

		
	}

}
