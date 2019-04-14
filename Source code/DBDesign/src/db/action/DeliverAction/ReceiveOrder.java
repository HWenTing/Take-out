package db.action.DeliverAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.OrderService;

public class ReceiveOrder extends ActionSupport implements ServletRequestAware{
	private HttpServletRequest request;
	
	private HttpSession session;
	
	@Resource
	private OrderService orderService;

	private static final long serialVersionUID = 1L;
	private String deliver_name;
	private Integer order_id;
	
	@Override
	public String execute() throws Exception {
		
		
		Map<String,Object> result=orderService.receiveOrder(deliver_name, order_id);
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
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	
	

}
