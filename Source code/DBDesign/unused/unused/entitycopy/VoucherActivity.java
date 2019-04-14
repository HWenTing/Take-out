package unused.entitycopy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="voucher_activity")
public class VoucherActivity {

	private Integer id;
	private Integer restaurant_id;
	private Integer need_pay;
	private Integer discount_money;
	private Integer need_to_use;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "voucher_activity_id", length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "restaurant_id", length = 11)
	public Integer getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(Integer restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	
	@Column(name = "voucher_need_pay", length = 20)
	public Integer getNeed_pay() {
		return need_pay;
	}
	public void setNeed_pay(Integer need_pay) {
		this.need_pay = need_pay;
	}
	
	@Column(name = "voucher_discount_money", length = 20)
	public Integer getDiscount_money() {
		return discount_money;
	}
	public void setDiscount_money(Integer discount_money) {
		this.discount_money = discount_money;
	}
	
	@Column(name = "voucher_need_to_use", length = 20)
	public Integer getNeed_to_use() {
		return need_to_use;
	}
	public void setNeed_to_use(Integer need_to_use) {
		this.need_to_use = need_to_use;
	}
	
}
