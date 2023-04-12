package compilador;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		Lexico lexico = new Lexico("teste_1.txt");
		Token tokenAux;
		while(true) {
			tokenAux = lexico.scan();
			System.out.println(tokenAux);
			if(tokenAux.tag == ' ') {
				break;
			}
		}
		
	}

}
