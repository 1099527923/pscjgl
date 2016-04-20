package org.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.model.TbClass;
import org.model.TbLogin;
import org.model.TbStudent;
import org.model.TbWork;
import org.service.AddStudentService;
import org.service.ClassService;
import org.service.WorkService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StudentTotalGradeAction extends ActionSupport  {
	//private AddStudentService addStudentService;
    private ClassService classService;      //��Ҫ��
    private AddStudentService studentService; //5.19Ϊ�˲�ĳ��ʦ������ѧ����
    private List<TbStudent> list3;  //5.19Ϊ�˲�ĳ��ʦ������ѧ����
    public void setClassService(ClassService classService) {  //��Ҫ��
		this.classService = classService;     //��Ҫ��
	}
	public void setStudentService(AddStudentService studentService) {
		this.studentService = studentService;
	}
	@Override
	public String execute()throws Exception{
//		List list=addStudentService.findAll();
//		if(list.size()>0){
//			Map request=(Map)ActionContext.getContext().get("request");
//			request.put("list", list);
//			return SUCCESS;
//		}else
//			return ERROR;
		 Map session=ActionContext.getContext().getSession();
		 TbLogin user=(TbLogin) session.get("user");
	   	 list3=new ArrayList<TbStudent>();
		 List<TbClass> list1=classService.getClassesOfSomeTeacher(user.getId());
		 for(TbClass tc:list1){
		   List<TbStudent> list2= studentService.findStudentOfSomeClass(tc.getId());
		   list3.addAll(list2);
		 }
		 if(list3.size()>0){
			 Map request=(Map)ActionContext.getContext().get("request");
			 request.put("list", list3);
			 return SUCCESS;
			}
			else
			 return ERROR;
		
	}
}
