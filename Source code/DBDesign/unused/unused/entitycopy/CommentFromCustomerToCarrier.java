package unused.entitycopy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="comment_from_cu_to_ca")
public class CommentFromCustomerToCarrier {

	private Integer carrier_id;
	private Integer customer_id;
	private String comment;
	
	@Id
	@Column(name = "carrier_id", length=20)
	public Integer getCarrier_id() {
		return carrier_id;
	}
	public void setCarrier_id(Integer carrier_id) {
		this.carrier_id = carrier_id;
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
