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

public class CheckWorkAction extends ActionSupport{   //��ʾ�Ͻ�����ҵ��δ����
   private WorkService workService;
   private ClassService classService;     //5.13Ϊ�˲�ĳ��ʦ������ѧ����
   private AddStudentService studentService; //5.13Ϊ�˲�ĳ��ʦ������ѧ����
   
   private List<TbWork> list3;
   public void setWorkService(WorkService workService) {
	this.workService = workService;
   }
   public void setClassService(ClassService classService) {
	this.classService = classService;
}
public void setStudentService(AddStudentService studentService) {
	this.studentService = studentService;
}
public String execute()throws Exception{
	   //ͨ���ҵ�Ȩ�ޣ������ѧ�������Լ���
	   //�������ʦ�����ҵ���Ӧ�İ༶id��ͨ���༶id�ҵ����иð��ѧ��id��ͨ��ѧ��id�ҵ���Ӧ��work
	    Map session=ActionContext.getContext().getSession();
		TbLogin user=(TbLogin) session.get("user");
	    if(user.getIden()==1){//��ʦ
	    	 List<TbStudent> list=new ArrayList<TbStudent>();
	    	 list3=new ArrayList<TbWork>();
			 List<TbClass> list1=classService.getClassesOfSomeTeacher(user.getId());
			 for(TbClass tc:list1){
			   List<TbStudent> list2= studentService.findStudentOfSomeClass(tc.getId());
			   list.addAll(list2);
			 }
			 for(TbStudent s:list){
			   List<TbWork> list4=workService.findWorkOfSomeStudent(s.getStudentId());
			   list3.addAll(list4);
			 }
	    }
	    else{
	    	list3=new ArrayList<TbWork>();
	    	 list3=workService.findWorkOfSomeStudent(user.getId());
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
