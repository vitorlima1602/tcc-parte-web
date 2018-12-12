package controle.conversores;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.dao.AreaDAO;
import modelo.dao.SubAreaDAO;
import modelo.dominio.Area;
import modelo.dominio.SubArea;

@FacesConverter(value = "converterSubArea", forClass = SubArea.class)
public class ConverterSubArea implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Método reescrito que retorna uma {@link SubArea}.
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (SubArea) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	/**
	 * Método reescrito que retorna uma {@link String} (a id de uma
	 * {@link SubArea}).
	 */
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof SubArea) {
			SubArea subArea = (SubArea) value;
			if (subArea != null && subArea instanceof SubArea && subArea.getId() != null) {
				uiComponent.getAttributes().put(subArea.getId().toString(), subArea);
				return subArea.getId().toString();
			}
		}
		return "";
	}

}
