package db.action.ShopAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.ShopService;

public class SetShopInformation extends ActionSupport implements ServletRequestAware{

	private HttpServletRequest request;
	
	private HttpSession session;

	@Resource
	private ShopService shopService;
	
	private String shop_name;
	private String shop_nickname; 
	private String shop_mobile;
	private String shop_license;
	private String shop_email;
	private String shop_tag;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=shopService.setShopInformation(shop_name,shop_nickname,shop_mobile,shop_license,shop_email,shop_tag);
		
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
	
	
	public String getShop_tag() {
		return shop_tag;
	}




	public void setShop_tag(String shop_tag) {
		this.shop_tag = shop_tag;
	}




	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getShop_nickname() {
		return shop_nickname;
	}
	public void setShop_nickname(String shop_nickname) {
		this.shop_nickname = shop_nickname;
	}
	public String getShop_mobile() {
		return shop_mobile;
	}
	public void setShop_mobile(String shop_mobile) {
		this.shop_mobile = shop_mobile;
	}
	public String getShop_license() {
		return shop_license;
	}
	public void setShop_license(String shop_license) {
		this.shop_license = shop_license;
	}
	public String getShop_email() {
		return shop_email;
	}
	public void setShop_email(String shop_email) {
		this.shop_email = shop_email;
	}
	
	
}
