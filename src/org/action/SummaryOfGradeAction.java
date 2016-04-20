package org.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.model.TbWork;
import org.service.AddStudentService;
import org.service.WorkService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SummaryOfGradeAction extends ActionSupport {
   //��ĳ��ʦ������ѧ��work�еĳɼ��ֱ� ��ӳ����ܴ��� �ټ����Ծ�ɼ�
	   private WorkService workService;
	   private AddStudentService studentService; //5.13Ϊ�˲�ĳ��ʦ������ѧ����
	   private String cishu;
	   private String juanmian;
	   private String studentId;
    public void setWorkService(WorkService workService) {
		this.workService = workService;
	   }
	public void setStudentService(AddStudentService studentService) {
		this.studentService = studentService;
	}
	
	public String getCishu() {
		return cishu;
	}
	public void setCishu(String cishu) {
		this.cishu = cishu;
	}
	public String getJuanmian() {
		return juanmian;
	}
	public void setJuanmian(String juanmian) {
		this.juanmian = juanmian;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String execute()throws Exception{
		   //�ҵ��������е�work ��ȡgrade��ӣ�Ȼ�������ɼ����� upadate��TbStudent
		
		  HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
			response.setContentType("text/html;charset=UTF-8"); 
			response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
			PrintWriter out = response.getWriter();
		      Float sum=new Float(0);
       		  sum=studentService.find(getStudentId()).getTotalGrade();
       		  if(sum!=0){
       		   out.print("<script>alert('�Ѿ����Ϲ���')</script>");
			    out.print("<script>window.location.href='studentTotalGrade.action'</script>");
				out.flush();
				out.close();
				 return ERROR;
       		  }
			  List<TbWork> list=workService.findWorkOfSomeStudent(getStudentId());
			  if(list.size()<=0){
				  return ERROR;
			  }
			  for(TbWork w:list){
				  Float grade=new Float(0);
				  if(w.getGrade()!=null)
				  {
					  grade=w.getGrade();
				  }
			      sum=sum+grade;
			  }
			  Float totalGrade=sum/(Integer.parseInt(cishu))*new Float(0.3)+Float.parseFloat(juanmian)*new Float(0.7);
			  if(totalGrade>100){
				    out.print("<script>alert('����ʧ�ܣ��ܳɼ����ô���100��')</script>");
				    out.print("<script>window.location.href='studentTotalGrade.action'</script>");
					out.flush();
					out.close();
					 return ERROR;
			  }
			  studentService.update(getStudentId(), totalGrade);
			//  ActionContext.getContext().put("message", "�ύ�ɹ�����");

		    	out.print("<script>alert('���ϳɹ���')</script>");
			    out.print("<script>window.location.href='studentTotalGrade.action'</script>");
				out.flush();
				out.close();
				
			  return SUCCESS;
		}
}

