package unused.entitycopy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_menu")
public class OrderMenu {

	private int menu_id;
	private int order_id;
	
	@Id
	@Column(name = "menu_id", length=11)
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	
	@Id
	@Column(name = "order_id", length=11)
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
}
