package br.com.fsma.loja.eletrodomesticos.bean.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Produto;

@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return (Produto) component.getAttributes().get(value);
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
			if (value instanceof Produto) {
				Produto produto = (Produto) value;
				if(produto != null && produto.getId() != null) {
					component.getAttributes().put(produto.getId().toString(), produto);
					return produto.getId().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
