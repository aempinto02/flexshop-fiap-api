package br.com.buzz.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Image implements Serializable {
	
	private static final long serialVersionUID = 4455018971092986200L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JsonIgnore
	private Catalogo catalogo;
	
	@OneToOne
	@JsonIgnore
	private Produto produto;
	
	@NotNull
	private String url;
	
	public Image() {}

	public Image(Long id, String url) {
		super();
		this.id = id;
		this.url = url;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
