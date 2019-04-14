package db.action.UserAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.User;
import db.service.UserService;
import net.sf.json.JSONObject;

public class SetUserInformation extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	
	private HttpSession session;

	@Resource
	private UserService userService;
	
	private  String user_name;
	
	private String user_nickname;//昵称
	
	private String user_mobile;//手机号
	
	private String user_gender;//性别
	
	private String user_email;//电子邮箱
	
	
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=userService.setUserInformation(user_name,user_nickname,user_mobile,user_gender,user_email);
		
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




	public String getUser_nickname() {
		return user_nickname;
	}




	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}




	public String getUser_mobile() {
		return user_mobile;
	}




	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}




	public String getUser_gender() {
		return user_gender;
	}




	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}




	public String getUser_email() {
		return user_email;
	}




	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

}
