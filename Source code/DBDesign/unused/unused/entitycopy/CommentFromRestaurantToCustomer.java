package unused.entitycopy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="comment_from_r_to_cu")
public class CommentFromRestaurantToCustomer {

	private Integer restaurant_id;
	private Integer customer_id;
	private String comment;
	
	@Id
	@Column(name = "restaurant_id", length=20)
	public Integer getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(Integer restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	
	@Id
	@Column(name = "customer_id", length=20)
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	
	@Column(name = "comment", length=500)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
