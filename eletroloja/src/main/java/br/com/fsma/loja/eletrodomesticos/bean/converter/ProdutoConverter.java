package br.com.fsma.loja.eletrodomesticos.bean.converter;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Produto;

@FacesConverter("produtoConverter")
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				String[] str = value.split("-");
				Produto p = new Produto();
				
				p.setId(Long.valueOf(str[0]));
				p.setNome(str[1]);
				p.setModelo(str[2]);
				p.setMarca(str[3]);
				p.setPreco(new BigDecimal(str[4]));
				return p;
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
			if (value != null) {
				return ((Produto) value).getId().toString() + "-" + ((Produto) value).getNome() + "-"
						+ ((Produto) value).getModelo() + "-" + ((Produto) value).getMarca()+"-"+((Produto) value).getPreco();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
