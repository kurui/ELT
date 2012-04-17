package com.chinarewards.elt.domain.org;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.model.staff.ImportCodeType;

/**
 * The code information for importing a file
 * 
 * @author 
 * @since 2.0.0 2010-09-21
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
public class ImportStaffCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1363023065629778139L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	private Long code;

	private String message;
	
	private Long priority;
	
	private String parserMethod;
	
	@Enumerated(EnumType.STRING)
	private ImportCodeType type;

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
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Long code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the priority
	 */
	public Long getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	/**
	 * @return the type
	 */
	public ImportCodeType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ImportCodeType type) {
		this.type = type;
	}

	/**
	 * @return the parserMethod
	 */
	public String getParserMethod() {
		return parserMethod;
	}

	/**
	 * @param parserMethod the parserMethod to set
	 */
	public void setParserMethod(String parserMethod) {
		this.parserMethod = parserMethod;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportStaffCode [id=" + id + ", code=" + code + ", message="
				+ message + ", priority=" + priority + ", parserMethod="
				+ parserMethod + ", type=" + type + "]";
	}

}
