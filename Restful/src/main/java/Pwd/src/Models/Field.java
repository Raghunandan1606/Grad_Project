package Pwd.src.Models;

import javax.xml.bind.annotation.XmlElement;

public class Field {
	@XmlElement
	private String fieldId;
	@XmlElement
	private String fieldName;
	@XmlElement
	private String fieldEncrypted;
	@XmlElement
	private String fieldDecrypted;

	public Field() {
		// TODO Auto-generated constructor stub
	}

	public Field(String fieldId, String fieldName, String fieldEncrypted, String fieldDecrypted) {
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.fieldEncrypted = fieldEncrypted;
		this.fieldDecrypted = fieldDecrypted;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldEncrypted() {
		return fieldEncrypted;
	}

	public void setFieldEncrypted(String fieldEncrypted) {
		this.fieldEncrypted = fieldEncrypted;
	}

	public String getFieldDecrypted() {
		return fieldDecrypted;
	}

	public void setFieldDecrypted(String fieldDecrypted) {
		this.fieldDecrypted = fieldDecrypted;
	}

}
