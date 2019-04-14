package db.action.ShopAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.ShopService;
import db.service.UserService;

public class Register extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	
	private HttpSession session;

	@Resource
	private ShopService shopService;
	

	private  String shop_name;
	

	private  String shop_psd;
	private Integer image;

	private static final long serialVersionUID = 1L;


	@Override
	public String execute() throws Exception {
		Map<String,Object> result=shopService.register(shop_name,shop_psd,image);
		
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

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_psd() {
		return shop_psd;
	}

	public void setShop_psd(String shop_psd) {
		this.shop_psd = shop_psd;
	}

}