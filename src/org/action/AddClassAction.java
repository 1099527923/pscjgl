package org.action;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.model.TbClass;
import org.service.AddTeacherService;
import org.service.ClassService;
public class AddClassAction extends ActionSupport{
	   private ClassService classService;
	  private AddTeacherService addTeacherService; //5.24��ʾ��ѡ����ʦ
	   private TbClass tbClass;

	   public TbClass getTbClass() {
		return tbClass;
	}

	public void setTbClass(TbClass tbClass) {
		this.tbClass = tbClass;
	}

	public void setClassService(ClassService classService) {
		this.classService = classService;
	}
	   
public void setAddTeacherService(AddTeacherService addTeacherService) {
		this.addTeacherService = addTeacherService;
	}

	@Override
	public String execute() throws Exception {
	
		HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
		response.setContentType("text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
		PrintWriter out = response.getWriter();
		if(tbClass.getClassName().isEmpty())
		{
			out.print("<script>alert('�༶������Ϊ�գ�')</script>");
			 out.print("<script>window.location.href='addClassView.action'</script>");
			out.flush();
			out.close();
			
			return ERROR;
		}
		if(tbClass.getNumber()==null)
		{
			out.print("<script>alert('��������Ϊ�գ�')</script>");
			out.print("<script>window.location.href='addClassView.action'</script>");
			out.flush();
			out.close();
			
			return ERROR;
		}
    	TbClass tbclass1=new TbClass();
		tbclass1.setClassName(tbClass.getClassName());
		tbclass1.setNumber(tbClass.getNumber());
		tbclass1.setTeacher(addTeacherService.find(tbClass.getTeacher().getTeacherId()));
		classService.add(tbClass);       //�ǲ��ǳ����ˡ�
		
	
    	out.print("<script>alert('��ӳɹ���')</script>");
	    out.print("<script>window.location.href='seeClass.action'</script>");
		out.flush();
		out.close();
		
		return SUCCESS;
		
	   }
	}
