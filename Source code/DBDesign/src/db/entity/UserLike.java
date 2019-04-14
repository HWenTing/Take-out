package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "userlike")
public class UserLike {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "userlike_id")
	private Integer userlike_id;
	
	@Column(name = "user_name",length = 30)
    private String user_name;//µÇÂ¼ÕËºÅ
	

	@Column(name = "shop_name",length = 30)
    private String shop_name;
	
	
	public Integer getUserlike_id() {
		return userlike_id;
	}

	public void setUserlike_id(Integer userlike_id) {
		this.userlike_id = userlike_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	
}

