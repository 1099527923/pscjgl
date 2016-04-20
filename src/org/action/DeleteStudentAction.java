package org.action;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.model.TbStudent;
import org.service.AddStudentService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteStudentAction extends ActionSupport{
	private AddStudentService addStudentService;
	private TbStudent student; //5.13 22����� Ϊ��ɾ��ĳ��ѧ����
	
	public void setAddStudentService(AddStudentService addStudentService) {
		this.addStudentService = addStudentService;
	}
		
 	public TbStudent getStudent() {
		return student;
	}
	public void setStudent(TbStudent student) {
		this.student = student;
	}
	@Override
	public String execute()throws Exception{
		HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
		response.setContentType("text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
		PrintWriter out = response.getWriter();
		String id=student.getStudentId();
    	addStudentService.delete(id);
    	out.print("<script>alert('ɾ���ɹ���')</script>");
	    out.print("<script>window.location.href='showStudent.action'</script>");
		out.flush();
		out.close();
    	return SUCCESS;
	
	}
}

