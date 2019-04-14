package db.action.ShopAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.dao.FoodDao;
import db.service.OrderService;
import db.service.ShopService;
import net.sf.json.JSONObject;

public class GetFoodByShopName extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	@Resource
	private ShopService shopSercive;
	private String shop_name;
	
	@Override
	public String execute() throws Exception {
		
		Map<String,Object> result=shopSercive.getFoodByShopName(shop_name);
		
		String state=(String) result.get("Result");

		if(state.equals("Success")) {
			
			JSONObject jsonObject=new JSONObject();
			jsonObject.accumulate("foodList", result.get("foodList"));
			request.setAttribute("data", jsonObject.toString());
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
	
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
}