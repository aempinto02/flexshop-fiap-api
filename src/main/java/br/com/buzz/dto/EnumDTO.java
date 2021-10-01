package br.com.buzz.dto;

import java.io.Serializable;

public class EnumDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EnumDTO(Integer id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public EnumDTO() {
		super();
	}

}
