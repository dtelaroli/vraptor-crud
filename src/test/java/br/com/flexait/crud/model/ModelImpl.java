package br.com.flexait.crud.model;

import javax.persistence.Entity;

@Entity
public class ModelImpl extends Model {
	
	private static final long serialVersionUID = 2349840598030756852L;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
