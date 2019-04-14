package db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "deliver")
public class Deliver {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "deliver_id")
	private Integer deliver_id;
	
	@Column(name = "deliver_name",length = 30)
    private String deliver_name;//µÇÂ¼ÕËºÅ
	
	@Column(name = "deliver_psd",length = 30)
    private String deliver_psd;//µÇÂ¼ÃÜÂë
	
	@Column(name = "deliver_nickname",length = 30)
    private String deliver_nickname;//êÇ³Æ
	
	@Column(name = "deliver_mobile",length = 30)
    private String deliver_mobile;//ÊÖ»úºÅÂë
	
	@Column(name = "deliver_IDcard",length = 30)
	private String deliver_IDcard;
	

	@Column(name = "deliver_balance")
    private Double deliver_balance=0.0;//ÓÃ»§Óà¶î
	


	@Column(name = "image")
	private Integer image;

	
	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public String getDeliver_IDcard() {
		return deliver_IDcard;
	}

	public void setDeliver_IDcard(String deliver_IDcard) {
		this.deliver_IDcard = deliver_IDcard;
	}

	public String getDeliver_name() {
		return deliver_name;
	}

	public void setDeliver_name(String deliver_name) {
		this.deliver_name = deliver_name;
	}

	public String getDeliver_psd() {
		return deliver_psd;
	}

	public void setDeliver_psd(String deliver_psd) {
		this.deliver_psd = deliver_psd;
	}

	public String getDeliver_nickname() {
		return deliver_nickname;
	}

	public void setDeliver_nickname(String deliver_nickname) {
		this.deliver_nickname = deliver_nickname;
	}


	public Double getDeliver_balance() {
		return deliver_balance;
	}

	public void setDeliver_balance(Double deliver_balance) {
		this.deliver_balance = deliver_balance;
	}



	public Integer getDeliver_id() {
		return deliver_id;
	}

	public void setDeliver_id(Integer deliver_id) {
		this.deliver_id = deliver_id;
	}

	public String getDeliver_mobile() {
		return deliver_mobile;
	}

	public void setDeliver_mobile(String deliver_mobile) {
		this.deliver_mobile = deliver_mobile;
	}

	
	
	
	
	
}
