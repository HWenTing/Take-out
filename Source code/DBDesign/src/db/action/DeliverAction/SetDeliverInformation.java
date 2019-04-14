package db.action.DeliverAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.DeliverService;

public class SetDeliverInformation extends ActionSupport implements ServletRequestAware{
		
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	
	@Resource
	private DeliverService deliverService;
	
	private String deliver_name;
	private String deliver_mobile;
	private String deliver_nickname;
	private String deliver_IDcard;
	
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=deliverService.setDeliverInformation(deliver_name, deliver_mobile, deliver_nickname, deliver_IDcard);
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
	public String getDeliver_mobile() {
		return deliver_mobile;
	}
	public void setDeliver_mobile(String deliver_mobile) {
		this.deliver_mobile = deliver_mobile;
	}
	public String getDeliver_nickname() {
		return deliver_nickname;
	}
	public void setDeliver_nickname(String deliver_nickname) {
		this.deliver_nickname = deliver_nickname;
	}


	public String getDeliver_IDcard() {
		return deliver_IDcard;
	}


	public void setDeliver_IDcard(String deliver_IDcard) {
		this.deliver_IDcard = deliver_IDcard;
	}

	

}
