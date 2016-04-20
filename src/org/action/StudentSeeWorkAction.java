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

public class StudentSeeWorkAction extends ActionSupport{   //��ʾ�Ͻ�����ҵ��δ����
   private WorkService workService;
   private List<TbWork> list;
   public void setWorkService(WorkService workService) {
	this.workService = workService;
   }
public String execute()throws Exception{
	   //ѧ�������Լ���
	    Map session=ActionContext.getContext().getSession();
		TbLogin user=(TbLogin) session.get("user");
	    list=new ArrayList<TbWork>();
	    list=workService.findWorkOfSomeStudent(user.getId());
	    if(list.size()>0){
		 Map request=(Map)ActionContext.getContext().get("request");
		 request.put("list", list);
		 return SUCCESS;
		}
		else
		 return ERROR;
	}
}
