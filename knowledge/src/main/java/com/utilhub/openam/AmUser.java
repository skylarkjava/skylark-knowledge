package com.utilhub.openam;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

import org.support.project.common.util.PropertyUtil;

public class AmUser {
	static final String roleEquals = "userdetails.role=";
	static final String nameEquals = "userdetails.attribute.name=";
	static final String valueEquals = "userdetails.attribute.value=";

	private String realm;
    private String uid;
    private String telephoneNumber;

    public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

    private String givenName;
    private String familyName;
    private String mail;
    private String description;


    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

     public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmUser)) return false;

        AmUser user = (AmUser) o;

        if (description != null ? !description.equals(user.description) : user.description != null) return false;
        if (mail != null ? !mail.equals(user.mail) : user.mail != null) return false;
        if (familyName != null ? !familyName.equals(user.familyName) : user.familyName != null) return false;
        if (givenName != null ? !givenName.equals(user.givenName) : user.givenName != null) return false;
        if (uid != null ? !uid.equals(user.uid) : user.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (givenName != null ? givenName.hashCode() : 0);
        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", mail='" + mail + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    private String role;

    public Boolean isAdmin() {
    	return "hudao".equals(this.uid);
    	//return this.role!=null && this.role.indexOf("Administrator")>-1;
    }

    public static AmUser parseFromString(String attributes){
    	AmUser user = new AmUser();
 		String[] lines = attributes.split("\n");
		String name = null;
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith(roleEquals)) {
				user.role = line.substring(roleEquals.length());
			} else if (line.startsWith(nameEquals)) {
				name = line.substring(nameEquals.length());
			}
			else if (line.startsWith(valueEquals)) {
				try {
					PropertyUtil.setPropertyValue(user, name, line.substring(valueEquals.length()));
				} catch (Exception e) {
				}
			}
		}
		return user;
    }

    public static AmUser parseFromMap(Map<String,?> attributes){
    	AmUser user = new AmUser();
    	for (String name : attributes.keySet()){
    		Object value = attributes.get(name);
			try {
				PropertyUtil.setPropertyValue(user, name, value);
			} catch (Exception e) {
			}
    	}

    	return user;
    }

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}
}
