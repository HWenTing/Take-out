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

import com.mysql.jdbc.Blob;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id")
	private Integer user_id;
	
	@Column(name = "user_name",length = 30)
    private String user_name;//登录账号
	
	@Column(name = "user_psd",length = 30)
    private String user_psd;//登录密码
	
	@Column(name = "user_nickname",length = 30)
    private String user_nickname;//昵称


	@Column(name = "user_mobile",length = 30)
    private String user_mobile;//手机号
	
	@Column(name = "user_gender",length = 10)
    private String user_gender;//性别
    
	@Column(name = "user_email",length = 30)
    private String user_email;//电子邮箱
	
	@Column(name = "user_balance")
    private Double user_balance=0.0;//用户余额
	

	@OneToOne(cascade= {CascadeType.ALL},fetch = FetchType.EAGER)//全加载
	@JoinColumn(name = "location_information_user")
	private Location_Information_User  location_information_user;

	@Column(name = "image")
	private Integer image;
	
	
	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_psd() {
		return user_psd;
	}

	public void setUser_psd(String user_psd) {
		this.user_psd = user_psd;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public Double getUser_balance() {
		return user_balance;
	}

	public void setUser_balance(Double user_balance) {
		this.user_balance = user_balance;
	}

	public Location_Information_User getLocation_information_user() {
		return location_information_user;
	}

	public void setLocation_information_user(Location_Information_User location_information_user) {
		this.location_information_user = location_information_user;
	}

	




}
