package unused.entitycopy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="requiring_discount")
public class RequiringDiscount {

	private Integer id;
	private Integer restaurant_id;
	private Integer requiring_money;
	private Integer discount_money;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "requiring_discount_id", length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "restaurant_id", length=11)
	public Integer getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(Integer restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	
	@Column(name = "requiring_money", length = 11)
	public Integer getRequiring_money() {
		return requiring_money;
	}
	public void setRequiring_money(Integer requiring_money) {
		this.requiring_money = requiring_money;
	}
	
	@Column(name = "discount_money", length = 11)
	public Integer getDiscount_money() {
		return discount_money;
	}
	public void setDiscount_money(Integer discount_money) {
		this.discount_money = discount_money;
	}
	
}
