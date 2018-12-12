package controle.validadores;

import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

@FacesValidator(value = "dataRegistrarProblemaValidator")
public class DataRegistrarProblemaValidator implements Validator {

	/**
	 * Validator se a data passada não é superior a data de hoje.
	 */
	@Override
	public void validate(FacesContext contexto, UIComponent campo, Object valor) throws ValidatorException {
		if (valor != null && valor instanceof LocalDate) {
			LocalDate data = (LocalDate) valor;
			LocalDate hoje = LocalDate.now();
			if (data.isAfter(hoje)) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data não pode ser superior a "
								+ hoje.getDayOfMonth() + "/" + hoje.getMonthValue() + "/" + hoje.getYear(), null));
			}
		}

	}

}
