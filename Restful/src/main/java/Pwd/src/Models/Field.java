package Pwd.src.Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//Field POJO for Model
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)

public class Field {
	@XmlElement(name = "fieldId")
	private String fieldId;
	@XmlElement(name = "fieldName")
	private String fieldName;
	@XmlElement
	private String fieldEncrypted;
	@XmlElement(name = "fieldDecrypted")
	private String fieldDecrypted;

	public Field() {
		// TODO Auto-generated constructor stub
	}

	public Field(String fieldIds, String fieldNames, String fieldEncrypteds, String fieldDecrypteds) {
		this.fieldId = fieldIds;
		this.fieldName = fieldNames;
		this.fieldEncrypted = fieldEncrypteds;
		this.fieldDecrypted = fieldDecrypteds;
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
