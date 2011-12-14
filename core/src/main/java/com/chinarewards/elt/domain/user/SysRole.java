/**
 * 
 */
package com.chinarewards.elt.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.model.user.UserRole;

/**
 * Stores the role of a user.
 * 
 * @author cyril
 * @since 0.2.0
 */
@Entity
public class SysRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2017666688884913104L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLENAME")
	private UserRole name;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the roleName
	 */
	public UserRole getName() {
		return name;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setName(UserRole name) {
		this.name = name;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
