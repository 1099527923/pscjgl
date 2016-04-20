package org.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.model.TbLogin;
import org.service.LoginService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ModifyPasswordAction extends ActionSupport{
	private LoginService loginService;
	private String newpassword;
	private String oldpassword;
	private TbLogin login;
	public TbLogin getLogin() {
		return login;
	}
	public void setLogin(TbLogin login) {
		this.login = login;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
//	public void validate(){
//		if(oldpassword==null||oldpassword.equals(""))
//			addFieldError("oldpassword","ԭ�����벻��Ϊ�գ�");
//		if(newpassword==null||newpassword.equals(""))
//			addFieldError("newpassword","�����벻��Ϊ�գ�");
//		if(login.getPassword()==null||(login.getPassword().equals("")))
//		addFieldError("login.password","���벻��Ϊ�գ�");
//		if(!(login.getPassword().equals(getNewpassword()))){
//		addFieldError("login.password","�������벻һ�£�");
//		}
//	}
	
	@Override
	public String execute()throws Exception{
	    Map session=ActionContext.getContext().getSession();
		TbLogin user=(TbLogin) session.get("user");
		
		HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
		response.setContentType("text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
		PrintWriter out = response.getWriter();
		
		login.setId(user.getId());
		login.setIden(user.getIden());
		if((login.getPassword().equals(getNewpassword()))&&(getOldpassword().equals(user.getPassword()))){
		    login.setPassword(getNewpassword());
			loginService.modify(login);
			Map session1=ActionContext.getContext().getSession();
			session1.put("user", user);
		//	ActionContext.getContext().put("message", "�޸ĳɹ�����");
			  out.print("<script>alert('�޸ĳɹ���')</script>");
			//  out.print("<script>window.location.href='modifypassword.jsp'</script>");
			  out.flush();
			  out.close();
		    return SUCCESS;
		}
		else
	{
			
			
		  out.print("<script>alert('�������')</script>");
		  out.print("<script>window.location.href='modifypassword.jsp'</script>");
		  out.flush();
		  out.close();
		  return null;
	}
		//	return ERROR;
	}
}


