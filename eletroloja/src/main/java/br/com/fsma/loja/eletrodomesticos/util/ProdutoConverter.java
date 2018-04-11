package br.com.fsma.loja.eletrodomesticos.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.fsma.loja.eletrodomesticos.bean.VendaBean;
import br.com.fsma.loja.eletrodomesticos.modelo.negocio.Produto;

@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				//System.out.println(context.getExternalContext().getApplicationMap().get("vendaBean").getClass());
				//System.out.println(((UISelectItems)component.getChildren().get(0)).getValue().getClass().getName());
				@SuppressWarnings("unchecked")
				List<Produto> produtos = (List<Produto>) ((UISelectItems)component.getChildren().get(0)).getValue();
				
				value = value.replaceAll(" ", "");
				String nome = value.substring(0, value.indexOf("-"));
				String modelo = value.substring(value.indexOf("-") + 1, value.indexOf("("));
				String marca = value.substring(value.indexOf("(") + 1, value.length() - 1); 

				Produto produto = produtos.parallelStream().filter(
						p -> (p.getMarca().equals(marca) 
									&& p.getModelo().equals(modelo)
									&& p.getNome().equals(nome))).findFirst().orElse(null);
				return produto;
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
		if (value != null) {
			return ((Produto) value).getNome() + " - " + ((Produto) value).getModelo() + " (" + ((Produto) value).getMarca() + ")";
		}
		return null;
	}
}
