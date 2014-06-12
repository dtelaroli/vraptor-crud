package br.com.caelum.vraptor.crud.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Model implements IModel {

	private static final long serialVersionUID = 2150386543436864041L;
	
	@Id @GeneratedValue
	protected Long id;
	
	public Model() {
	}
	
	public Model(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + "]";
	}
	
}