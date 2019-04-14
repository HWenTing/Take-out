package db.action.ShopAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.OrderService;
import db.service.ShopService;
import net.sf.json.JSONObject;

public class GetFoodByOrder extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	@Resource
	private OrderService orderSercive;
	private Integer order_id;
	
	@Override
	public String execute() throws Exception {
		
		Map<String,Object> result=orderSercive.getFoodByOrder(order_id);
		
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
	
	
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	
}
