package db.action.UserAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.UserService;

public class UserOutMoney extends ActionSupport implements ServletRequestAware {
	
	
	private HttpServletRequest request;
	private HttpSession session;
	private Double out_money;
	private  String user_name;


	private static final long serialVersionUID = 1L;
	@Resource
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=userService.UserBalance_out_money(user_name, out_money);
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
	
	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public Double getOut_money() {
		return out_money;
	}


	public void setOut_money(Double out_money) {
		this.out_money = out_money;
	}



}
