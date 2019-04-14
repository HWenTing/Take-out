package db.action.UserAction;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Order;
import db.entity.Shop;
import db.service.UserService;
import net.sf.json.JSONObject;

public class GetAllOrder extends ActionSupport implements ServletRequestAware{
	private HttpServletRequest request;
	private HttpSession session;
	@Resource
	private UserService userService;
	private static final long serialVersionUID = 1L;
	private String user_name;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=userService.getAllOrder(user_name);
		String state=(String) result.get("Result");
		if(state.equals("Success")) {
			List<Order> orderList = (List<Order>) result.get("orderList");
			JSONObject jsonObject=new JSONObject();
			jsonObject.accumulate("orderList", orderList);
			request.setAttribute("data", jsonObject.toString());
		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		return state;
		
	}
	
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		this.session=request.getSession();
	}
}
