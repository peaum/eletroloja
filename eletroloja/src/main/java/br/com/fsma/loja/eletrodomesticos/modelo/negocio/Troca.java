package br.com.fsma.loja.eletrodomesticos.modelo.negocio;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_troca")
public class Troca implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable=false)
	private Long id;
	
	@Column(name = "data_troca", nullable = false, updatable=false)
	private LocalDate dataTroca;

	@ManyToOne
	@JoinColumn(name="cliente_id", nullable=false, updatable=false)
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name="venda_id", nullable=false, updatable=false)
	private Venda venda;
	
	@OneToOne
	@JoinColumn(name="itemVenda_id", nullable=false, updatable=false)
	private Item itemVenda;
	
	@Column(name = "motivo", nullable=false, updatable=false)
	private String motivo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataTroca() {
		return dataTroca;
	}

	public void setDataTroca(LocalDate dataTroca) {
		this.dataTroca = dataTroca;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Item getItemVenda() {
		return itemVenda;
	}

	public void setItemVenda(Item itemVenda) {
		this.itemVenda = itemVenda;
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
		Troca other = (Troca) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDescricao() {
		return motivo;
	}

	public void setDescricao(String descricao) {
		this.motivo = descricao;
	}
	
}
