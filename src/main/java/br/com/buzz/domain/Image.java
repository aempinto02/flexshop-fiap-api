package br.com.buzz.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Image implements Serializable {
	
	private static final long serialVersionUID = 4455018971092986200L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@OneToOne
	@JsonIgnore
	private Catalogo catalogo;
	
	@OneToOne
	@JsonIgnore
	private Produto produto;
	
	@NotNull
	private String url;
	
	public Image(Long id, String url) {
		super();
		this.id = id;
		this.url = url;
	}
	
}
