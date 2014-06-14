package br.com.flexait.crud.model;

import javax.persistence.Entity;

import br.com.flexait.crud.model.Model;

@Entity
public class ModelImpl extends Model {
	private static final long serialVersionUID = 2349840598030756852L;
	String name;
}
