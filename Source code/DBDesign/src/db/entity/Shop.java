package db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "shop")
public class Shop {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "shop_id")
	private Integer shop_id;
	
	@Column(name = "shop_name",length = 30)
    private String shop_name;//µ«¬º’À∫≈
	
	@Column(name = "shop_psd",length = 30)
    private String shop_psd;//µ«¬º√‹¬Î
	
	@Column(name = "shop_nickname",length = 30)
    private String shop_nickname;//Í«≥∆


	@Column(name = "shop_mobile",length = 30)
    private String shop_mobile;// ÷ª˙∫≈
	
	@Column(name = "shop_license",length = 10)
    private String shop_license;//”™“µ÷¥’’
    
	@Column(name = "shop_email",length = 30)
    private String shop_email;//µÁ◊”” œ‰
	
	@Column(name = "shop_balance")
    private Double shop_balance=0.0;//”√ªß”‡∂Ó

	@Column(name = "shop_tag")
    private String shop_tag = "default";//
	
	
	@OneToOne(cascade= {CascadeType.ALL},fetch = FetchType.EAGER)//»´º”‘ÿ
	@JoinColumn(name = "location_information_shop")
	private Location_Information_Shop  location_information_shop;
	
	@Column(name = "image")
	private Integer image;

	
	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public Location_Information_Shop getLocation_information_shop() {
		return location_information_shop;
	}

	public void setLocation_information_shop(Location_Information_Shop location_information_shop) {
		this.location_information_shop = location_information_shop;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_psd() {
		return shop_psd;
	}

	public void setShop_psd(String shop_psd) {
		this.shop_psd = shop_psd;
	}

	public String getShop_nickname() {
		return shop_nickname;
	}

	public void setShop_nickname(String shop_nickname) {
		this.shop_nickname = shop_nickname;
	}

	public String getShop_mobile() {
		return shop_mobile;
	}

	public void setShop_mobile(String shop_mobile) {
		this.shop_mobile = shop_mobile;
	}

	public String getShop_license() {
		return shop_license;
	}

	public void setShop_license(String shop_license) {
		this.shop_license = shop_license;
	}

	public String getShop_email() {
		return shop_email;
	}

	public void setShop_email(String shop_email) {
		this.shop_email = shop_email;
	}

	public Double getShop_balance() {
		return shop_balance;
	}

	public void setShop_balance(Double shop_balance) {
		this.shop_balance = shop_balance;
	}

	public Integer getShop_id() {
		return shop_id;
	}

	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_tag() {
		return shop_tag;
	}

	public void setShop_tag(String shop_tag) {
		this.shop_tag = shop_tag;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
