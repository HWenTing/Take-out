package db.action.DeliverAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.DeliverService;

public class DeliverOutMoney extends ActionSupport implements ServletRequestAware {
	private HttpServletRequest request;
	
	private HttpSession session;
	
	@Resource
	private DeliverService deliverService;
	private String deliver_name;
	private Double out_money;
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=deliverService.DeliverBalance_out_money(deliver_name, out_money);
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
	
	public String getDeliver_name() {
		return deliver_name;
	}
	public void setDeliver_name(String deliver_name) {
		this.deliver_name = deliver_name;
	}
	public Double getOut_money() {
		return out_money;
	}
	public void setOut_money(Double out_money) {
		this.out_money = out_money;
	}
	
	
}
