package controle.conversores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

import controle.util.JSFUtil;

@FacesConverter(value = "converterData")
public class ConverterData implements Converter {

	/**
	 * Um formatter para as datas no padrão Português do Brasil.
	 */
	private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
			.withLocale(new Locale("pt", "br"));

	/**
	 * Método reescrito que retorna um {@link LocalDate}.
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			LocalDate data = null;

			try {
				data = LocalDate.parse(value, formatter);
			} catch (Exception e) {
				JSFUtil.retornarMensagemFatal("Entrada de data inválida, o padrão é dd/mm/yyyy", null, null);
			}
			return data;
		}
		return null;
	}

	/**
	 * Método reescrito que retorna uma data no formato {@link String} formatada
	 * para o padrão de data Brasileiro.
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent uiComponent, Object value) {
		if (value instanceof LocalDate) {
			LocalDate data = (LocalDate) value;
			if (data != null && data instanceof LocalDate) {
				uiComponent.getAttributes().put(data.format(formatter), data);
				return data.format(formatter);
			}
		}
		return "";
	}

}
