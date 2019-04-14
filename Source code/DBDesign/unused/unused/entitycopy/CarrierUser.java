package unused.entitycopy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="carrier_user")
public class CarrierUser {
	
	private Integer carrier_id;
	private String username;
	
	@Column(name = "carrier_id", length=11)
	public Integer getCarrier_id() {
		return carrier_id;
	}
	public void setCarrier_id(Integer carrier_id) {
		this.carrier_id = carrier_id;
	}
	
	@Id
	@Column(name = "username", length=20)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
