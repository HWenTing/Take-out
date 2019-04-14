package db.action.UserAction;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.User;
import db.service.UserService;
import net.sf.json.JSONObject;

public class GetUserInformation extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	
	private HttpSession session;

	@Resource
	private UserService userService;
	
	private String user_name;
	
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		
		Map<String,Object> result=userService.getUserInformation(user_name);
		String state=(String) result.get("Result");
		if(state.equals("Success")) {
			User user = (User) result.get("user");
			JSONObject jsonObject=new JSONObject();
			jsonObject.accumulate("user", user);
			//getsession 浏览器不关就起作用，request 一次请求
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
