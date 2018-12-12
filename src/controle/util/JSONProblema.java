package controle.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;

import modelo.dominio.Problema;

public class JSONProblema {

	/**
	 * Método que recebe um {@link List} de {@link Problema}, transforma em um
	 * json e como saída é um objeto do tipo {@link String}.
	 * 
	 * @param lista
	 *            - um {@link List} de {@link Problema}.
	 * @return - uma {@link String} no formato json utilizando a lib
	 *         {@link Gson}.
	 */
	public static String jsonProblemas(List<Problema> lista) {
		List<String> parametros = new ArrayList<String>();
		String localizacao;
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
				.withLocale(new Locale("pt", "br"));
		for (Problema problema : lista) {
			localizacao = problema.getLocal().toString();
			String[] coordenadas = localizacao.split("[POINT (]|[ ]|[)]");
			parametros.add(coordenadas[8]);
			parametros.add(coordenadas[7]);
			parametros.add(problema.getId().toString());
			parametros.add(problema.getSubArea().getArea().getNome());
			parametros.add(problema.getSubArea().getNome());
			parametros.add(problema.getData().format(formatter));
			parametros.add(problema.getDescricao());
			parametros.add(problema.getResposta());
		}

		return new Gson().toJson(parametros);
	}
}
