package db.action.UserAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.OrderService;

public class GenerateOrder extends ActionSupport implements ServletRequestAware{

	private HttpServletRequest request;
	private HttpSession session;
	@Resource
	private OrderService orderService;
	private static final long serialVersionUID = 1L;
	
	private String user_name;
	private String shop_name;
	private Double price;
	private String CurrentTime;
	private String food_list;
	private String image_list;
	@Override
	public String execute() throws Exception {
		
		
		Map<String,Object> result=orderService.generateOrder(user_name, shop_name,price,CurrentTime,food_list,image_list);
		String state=(String) result.get("Result");

		if(state.equals("Success")) {
			
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		return state;
		
		
	}
	
	
	public String getCurrentTime() {
		return CurrentTime;
	}


	public void setCurrentTime(String currentTime) {
		CurrentTime = currentTime;
	}


	public String getImage_list() {
		return image_list;
	}


	public void setImage_list(String image_list) {
		this.image_list = image_list;
	}


	public String getFood_list() {
		return food_list;
	}


	public void setFood_list(String food_list) {
		this.food_list = food_list;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		this.session=request.getSession();
	}
	
	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
}
