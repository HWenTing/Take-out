package db.action.ShopAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.ShopService;

public class ShopOutMoney  extends ActionSupport implements ServletRequestAware {

	private HttpServletRequest request;
	private HttpSession session;
	private Double out_money;
	private  String shop_name;
	
	private static final long serialVersionUID = 1L;
	@Resource
	private ShopService shopService;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=shopService.ShopBalance_out_money(shop_name, out_money);
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
	
	public Double getOut_money() {
		return out_money;
	}
	public void setOut_money(Double out_money) {
		this.out_money = out_money;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	
}
