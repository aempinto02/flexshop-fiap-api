package br.com.buzz.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@AllArgsConstructor
public class Item implements Serializable {

	private static final long serialVersionUID = 6120526242455645790L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull
	@OneToOne
	private Produto produto;
	
	@NotNull
	private short unidades = 0;
	
}
