package db.action.UserAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Location_Information_User;
import db.service.UserService;
import net.sf.json.JSONObject;

public class SetUserLocation extends ActionSupport implements ServletRequestAware{

	private HttpServletRequest request;
	
	private HttpSession session;
	
	private String user_name;
	private String province;
	private String city;
	private String county;
	private String specific_location;
	private static final long serialVersionUID = 1L;
	@Resource
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		
		
		Map<String,Object> result=userService.setUserLocation(user_name, province, city, county, specific_location);
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getSpecific_location() {
		return specific_location;
	}

	public void setSpecific_location(String specific_location) {
		this.specific_location = specific_location;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
