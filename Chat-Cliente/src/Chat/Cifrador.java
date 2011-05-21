package Chat;

public class Cifrador {

	public Cifrador() {
	}

	static void Crypt(StringBuffer input, StringBuffer key) {
		int sbox[];
		int chave[];
		sbox = new int[256];
		chave = new int[256];
		int i, j, t, x;
		char temp, k;
		i = 0;
		j = 0;
		k = 0;
		t = 0;
		x = 0;
		temp = 0;

		for (i = 0; i < 256; i++) {
			sbox[i] = i;
		}

		j = 0;
		if (key.length() > 0) {
			for (i = 0; i < 256; i++) {
				if (j == key.length())
					j = 0;
				
				chave[i] = key.charAt(j++);	
			}
		}

		j = 0;
		for (i = 0; i < 256; i++) {
			j = (j + sbox[i] + chave[i]) % 256;
			temp = (char) sbox[i];
			sbox[i] = sbox[j];
			sbox[j] = temp;
		}

		i = j = 0;
		for (x = 0; x < input.length(); x++) {
			i = (i + 1) % 256;
			j = (j + sbox[i]) % 256;
			temp = (char) sbox[i];
			sbox[i] = sbox[j];
			sbox[j] = temp;
			//Faz toda essa misturada para pegar um numero randômico e fazer um xor com a mensagem original.
			t = (sbox[i] + sbox[j]) % 256;
			// get the random byte
			k = (char) sbox[t];
			// xor with the data and done
			input.setCharAt(x, (char) (input.charAt(x) ^ k));
		}
	}

}
