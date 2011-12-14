/**
 * 
 */
package com.chinarewards.elt.domain.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 
 * @author cyril
 * @since 0.2.0
 */
@Entity
public class SysUserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9120436055236220802L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	private SysRole role;

	@ManyToOne(fetch = FetchType.LAZY)
	private SysUser user;

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
	 * @return the role
	 */
	public SysRole getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(SysRole role) {
		this.role = role;
	}

	/**
	 * @return the user
	 */
	public SysUser getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(SysUser user) {
		this.user = user;
	}

}
