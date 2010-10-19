package example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Utils {
	
	static Vector<String> openFile(String vr) throws IOException {
		FileReader fr = new FileReader(vr);
		BufferedReader entrada = new BufferedReader(fr);
		String s;
		Vector<String> lista = new Vector<String>();
		while ((s = entrada.readLine()) != null) {
			lista.add(s);
		}
		entrada.close();
		return lista;
	}

}
