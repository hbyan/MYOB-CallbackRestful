package au.com.gsn.outlook.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GSN_OUTLOOK_EMAILLIST")
public class OutlookEmailTable implements Serializable{
	
	private static final long serialVersionUID = 204016091047987458L;

	@Id
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name = "ACTIVE")
	private String active;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
