package db.action.UserAction;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.entity.Shop;
import db.service.UserService;
import net.sf.json.JSONObject;

public class GetShopByShopTag extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	private HttpSession session;
	@Resource
	private UserService userService;
	private static final long serialVersionUID = 1L;
	private String ShopTag;
	@Override
	public String execute() throws Exception {
		
		Map<String,Object> result=userService.getShopByShopTag(ShopTag);
		String state=(String) result.get("Result");
		if(state.equals("Success")) {
			List<Shop> shopList = (List<Shop>) result.get("shopList");
			JSONObject jsonObject=new JSONObject();
			jsonObject.accumulate("shopList", shopList);
			
			//getsession 浏览器不关就起作用，request 一次请求
			request.setAttribute("data", jsonObject.toString());

		}else if(state.equals("Error")){
			request.setAttribute("Reason", result.get("Reason"));
		}
		return state;
	}

	
	public String getShopTag() {
		return ShopTag;
	}


	public void setShopTag(String shopTag) {
		ShopTag = shopTag;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		this.session=request.getSession();
	}
}
