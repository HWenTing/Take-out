package db.action.DeliverAction;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import db.service.DeliverService;

public class Register extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	private HttpSession session;
	
	@Resource
	private DeliverService deliverSercive;
	
	private  String deliver_name;
	private  String deliver_psd;

	private static final long serialVersionUID = 1L;

	private Integer image;
	@Override
	public String execute() throws Exception {
		Map<String,Object> result=deliverSercive.register(deliver_name,deliver_psd,image);
		
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

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public void setDeliver_name(String deliver_name) {
		this.deliver_name = deliver_name;
	}

	public String getDeliver_psd() {
		return deliver_psd;
	}

	public void setDeliver_psd(String deliver_psd) {
		this.deliver_psd = deliver_psd;
	}
}