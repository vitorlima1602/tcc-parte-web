package controle.conversores;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.dao.AreaDAO;
import modelo.dominio.Area;

@FacesConverter(value = "converterArea", forClass = Area.class)
public class ConverterArea implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Método reescrito que retorna uma {@link Area}.
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Area) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	/**
	 * Método reescrito que retorna uma {@link String} (a id de uma
	 * {@link Area}).
	 */
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Area) {
			Area area = (Area) value;
			if (area != null && area instanceof Area && area.getId() != null) {
				uiComponent.getAttributes().put(area.getId().toString(), area);
				return area.getId().toString();
			}
		}
		return "";
	}

}
