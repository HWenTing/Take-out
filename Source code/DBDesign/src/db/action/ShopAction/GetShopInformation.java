package db.action.ShopAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Shop;
import db.entity.User;
import db.service.ShopService;
import net.sf.json.JSONObject;

public class GetShopInformation extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	
	private HttpSession session;

	@Resource
	private ShopService shopService;
	
	

	private String shop_name;
	
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		
		Map<String,Object> result=shopService.getShopInformation(shop_name);
		String state=(String) result.get("Result");
		if(state.equals("Success")) {
			Shop shop = (Shop) result.get("shop");
			JSONObject jsonObject=new JSONObject();
			jsonObject.accumulate("shop", shop);
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
	
	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
}