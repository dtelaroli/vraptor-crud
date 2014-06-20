package br.com.flexait.crud.model;

import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Model implements IModel {

	private static final long serialVersionUID = 2150386543436864041L;
	
	@Id @GeneratedValue
	protected Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	protected Calendar createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Calendar updatedAt;
	
	public Model() {
		this(null);
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
	
	@PrePersist @PreUpdate
	public void beforeSave() {
		createdAt = Calendar.getInstance();
		updatedAt = Calendar.getInstance();
	}
	
}