package br.com.fsma.loja.eletrodomesticos.modelo.negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable=false)
	private Long id;
	
	@Column(name = "nome", length = 20, nullable = false, updatable=false)
	private String nome;
	
	@Column(name = "marca", length = 20, nullable = false, updatable=false)
	private String marca;

	@Column(name = "modelo", length = 20, nullable = false, updatable=false)
	private String modelo;
	
	@Column(name = "preco", nullable = false, updatable=true)
	private BigDecimal preco = new BigDecimal(0);
	
	@OneToMany(mappedBy="produto")
	private List<Item> itens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public double getPreco() {
		return preco.doubleValue();
	}
	
	public BigDecimal getBDPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = new BigDecimal(preco);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
