package br.com.fsma.loja.eletrodomesticos.bean.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Cliente;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return (Cliente) component.getAttributes().get(value);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Conversão", "Item inválido!"));
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			if (value instanceof Cliente) {
				Cliente cliente = (Cliente) value;
				if(cliente != null && cliente.getId() != null) {
					component.getAttributes().put(cliente.getId().toString(), cliente);
					return cliente.getId().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
