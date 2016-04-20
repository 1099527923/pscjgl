package org.action;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.model.TbClass;
import org.model.TbStudent;
import org.service.AddStudentService;
import org.service.ClassService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
	public class DeleteClassAction extends ActionSupport{
		private AddStudentService addStudentService;
		private ClassService classService;
		private TbClass tbClass; //5.13 22����� Ϊ��ɾ��ĳ��ѧ����
		public void setAddStudentService(AddStudentService addStudentService) {
			this.addStudentService = addStudentService;
		}
		public void setClassService(ClassService classService) {
			this.classService = classService;
		}
		public TbClass getTbClass() {
			return tbClass;
		}

		public void setTbClass(TbClass tbClass) {
			this.tbClass = tbClass;
		}

		@Override
		public String execute()throws Exception{
			HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
			response.setContentType("text/html;charset=UTF-8"); 
			response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
			PrintWriter out = response.getWriter();
			Integer id=tbClass.getId();
			List<TbStudent> list=addStudentService.findStudentOfSomeClass(id);
			if(list.size()<=0){
	    	  classService.delete(id);
	    	  out.print("<script>alert('ɾ���ɹ���')</script>");
	    	}
	    	else{
	    		out.print("<script>alert('ɾ��ʧ�ܣ�')</script>");
	    	}
		    out.print("<script>window.location.href='seeClass.action'</script>");
			out.flush();
			out.close();
	    	return SUCCESS;
		
		}
	}

