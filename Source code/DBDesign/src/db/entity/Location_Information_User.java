package db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "location_information_user")
public class Location_Information_User {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "location_information_id")
	private Integer location_information_id;
	
	@Column(name = "province")
	private String province;
	
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "county")
	private String county;
	
	@Column(name = "specific_location")
	private String specific_location;

	public Integer getLocation_information_id() {
		return location_information_id;
	}

	public void setLocation_information_id(Integer location_information_id) {
		this.location_information_id = location_information_id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getSpecific_location() {
		return specific_location;
	}

	public void setSpecific_location(String specific_location) {
		this.specific_location = specific_location;
	}


	
	
	
	
	
}
