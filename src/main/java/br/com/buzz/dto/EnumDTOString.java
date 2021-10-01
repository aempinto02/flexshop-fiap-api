package br.com.buzz.dto;

import java.io.Serializable;

public class EnumDTOString implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EnumDTOString(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public EnumDTOString() {
		super();
	}

}
