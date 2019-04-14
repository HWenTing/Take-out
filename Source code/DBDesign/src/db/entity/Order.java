package db.entity;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "myorder")
public class Order {
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "order_id")
	private Integer order_id;
	
	@Column(name = "order_time")
	private String order_time;
	
	
	@Column(name = "order_price")
	private Double order_price;
	
	@Column(name = "shop_name")
	private String shop_name;
	
	@Column(name = "deliver_name")
	private String deliver_name;
	
	@Column(name = "user_name")
	private String user_name;
	
	@OneToOne(cascade= {CascadeType.ALL},fetch = FetchType.EAGER)//全加载
	@JoinColumn(name = "location_information_user")
	private Location_Information_User  location_information_user;
	
	@OneToOne(cascade= {CascadeType.ALL},fetch = FetchType.EAGER)//全加载
	@JoinColumn(name = "location_information_shop")
	private Location_Information_Shop  location_information_shop;
	
	
	/*
	 * ini 生成，需要被商家激活
	 * available 商家确认过可被接单的
	 * ing 正在配送
	 * ed 完成配送
	 */
	@Column(name = "order_state")
	private String order_state="ini";
	

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}



	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}



	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getDeliver_name() {
		return deliver_name;
	}

	public void setDeliver_name(String deliver_name) {
		this.deliver_name = deliver_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public Location_Information_User getLocation_information_user() {
		return location_information_user;
	}

	public void setLocation_information_user(Location_Information_User location_information_user) {
		this.location_information_user = location_information_user;
	}

	public Location_Information_Shop getLocation_information_shop() {
		return location_information_shop;
	}

	public void setLocation_information_shop(Location_Information_Shop location_information_shop) {
		this.location_information_shop = location_information_shop;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}



	
	
}
