package br.com.flexait.crud.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Model implements IModel {

	private static final long serialVersionUID = 2150386543436864041L;
	
	@Id @GeneratedValue
	protected Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Calendar createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedAt;
	
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
	
	public Calendar getCreatedAt() {
		return createdAt;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + "]";
	}
	
	@PrePersist
	public void beforeInsert() {
		createdAt = Calendar.getInstance();
	}
	
	@PreUpdate
	public void beforeUpdate() {
		updatedAt = Calendar.getInstance();
	}
	
}