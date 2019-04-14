package db.action.ShopAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.OrderService;
import db.service.ShopService;

public class AddFood extends ActionSupport implements ServletRequestAware{
	

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	@Resource
	private ShopService shopService;

	private String shop_name;
	private String food_name;
	private Double food_price;
	private String food_description;
	private Integer food_image;
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=shopService.addFood(shop_name, food_name, food_price, food_description,food_image);
		
		String state=(String) result.get("Result");

		if(state.equals("Success")) {
			
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		return state;
		
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		this.session=request.getSession();
	}




	public Integer getFood_image() {
		return food_image;
	}

	public void setFood_image(Integer food_image) {
		this.food_image = food_image;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public Double getFood_price() {
		return food_price;
	}

	public void setFood_price(Double food_price) {
		this.food_price = food_price;
	}

	public String getFood_description() {
		return food_description;
	}

	public void setFood_description(String food_description) {
		this.food_description = food_description;
	}

}