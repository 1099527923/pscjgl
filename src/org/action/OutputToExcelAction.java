package org.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.model.TbClass;
import org.model.TbLogin;
import org.model.TbStudent;
import org.service.AddStudentService;
import org.service.ClassService;
import org.tool.DataToExcelFile;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OutputToExcelAction extends ActionSupport{
    private ClassService classService;      //��Ҫ��
    private AddStudentService studentService; //5.19Ϊ�˲�ĳ��ʦ������ѧ����    
    private List<TbStudent> list3;  //5.19Ϊ�˲�ĳ��ʦ������ѧ����
    private String output;
    public void setClassService(ClassService classService) {  //��Ҫ��
		this.classService = classService;     //��Ҫ��
	}


	public void setStudentService(AddStudentService studentService) {
		this.studentService = studentService;
	}




	public String getOutput() {
		return output;
	}


	public void setOutput(String output) {
		this.output = output;
	}


	@Override
	public String execute()throws Exception{
		 Map session=ActionContext.getContext().getSession();
		 TbLogin user=(TbLogin) session.get("user");
	   	 list3=new ArrayList<TbStudent>();
		 List<TbClass> list1=classService.getClassesOfSomeTeacher(user.getId());
		 for(TbClass tc:list1){
		   List<TbStudent> list2= studentService.findStudentOfSomeClass(tc.getId());
		   list3.addAll(list2);
		 }
 
		 HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
		//	response.setContentType("text/html;charset=UTF-8"); 
			response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
	//		response.reset();
				
		// �趨����ļ�ͷ  
		 response.setCharacterEncoding("GBK");  
	    response.setHeader("Content-disposition","attachment; filename="+new String("ѧ���ɼ�".getBytes("GB2312"),"8859_1")+".xls");//  
		 // �����������  
	     response.setContentType("application/vnd.ms-excel");                 
		 OutputStream os = response.getOutputStream();  
		 DataToExcelFile de=new DataToExcelFile();
 		boolean b=de.readDataToExcelFile(list3,os);
 		response.reset();
		if(b==true){
		 	return SUCCESS;
		}
		else{
			return ERROR;
		}
//		if(b==true){
//		out.print("<script>alert('�����ɹ���')</script>");
//		out.print("<script>window.location.href='studentTotalGrade.action'</script>");
//	   	out.flush();
//		out.close();
//    	return SUCCESS;
//    	}
//			
//		else{
//		out.print("<script>alert('����ʧ�ܣ�')</script>");
//	    out.print("<script>window.location.href='studentTotalGrade.action'</script>");
//		out.flush();
//		out.close();
//		return ERROR;
//		}
			
		
	}
}
