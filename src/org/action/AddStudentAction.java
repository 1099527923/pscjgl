package org.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.model.TbClass;
import org.model.TbLogin;
import org.model.TbStudent;
import org.service.AddStudentService;
import org.service.ClassService;
import org.service.LoginService;
import org.tool.ExcelOperationUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class AddStudentAction extends ActionSupport{
 	private AddStudentService addStudentService;
	private TbStudent tbStudent;
//	private TbStudent student; //5.13 22����� Ϊ��ɾ��ĳ��ѧ����
	private String upload; //���ģ� 
	private LoginService loginService;  //5��12��21��46�ּ�
	private ClassService classService;      //��Ҫ��
	    public void setClassService(ClassService classService) {  //��Ҫ��
			this.classService = classService;     //��Ҫ��
		}
	public void setAddStudentService(AddStudentService addStudentService) {
		this.addStudentService = addStudentService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public TbStudent getTbStudent() {
		return tbStudent;
	}
	public void setTbStudent(TbStudent tbStudent) {
		this.tbStudent = tbStudent;
	}
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	
// 	public TbStudent getStudent() {
//		return student;
//	}
//	public void setStudent(TbStudent student) {
//		this.student = student;
//	}
//	public String deleteStudent() throws Exception{  //5.13 23.22ʱɾ��ѧ����� 
//    	String id=student.getStudentId();
//    	addStudentService.delete(id);
//    	return SUCCESS;
//    }
	@Override
	public String execute()throws Exception{
//		Map session=(Map)ActionContext.getContext().getSession();
//		session.put("file", upload);
//	    return SUCCESS;
		ExcelOperationUtil eo=new ExcelOperationUtil();
	    List<TbStudent> list=eo.readExcelData(upload);
	    
	  //5.26 ��excel������£���ʦֻ��������Լ��̵İ��ѧ��������Ա������  
	    Map session=ActionContext.getContext().getSession();
		 TbLogin user=(TbLogin) session.get("user");
	    if(user.getIden()==1){
	    	//List<TbStudent> list3=new ArrayList<TbStudent>();
	    	List<TbStudent> delList = new ArrayList<TbStudent>();//����װ��Ҫɾ����Ԫ��
	    	List<TbClass> list1=classService.getClassesOfSomeTeacher(user.getId());
	    	
//	    	for(int j = 0;j<list.size();++j){  
//	    		  if(!list1.contains(list.get(j).getTbclass())){  
//	    		       list.remove(j);  
//	    		       --j;  
//	    		  }  	        }   
//	    		}
	    	
	    	//Collection<User> users =  new CopyOnWriteArrayList<User>()
	      	for(TbStudent s:list){
	      		if(!list1.contains(s.getTbclass())){ //contains�Ƚ�contains��������� o.equals(elementData[i])����������elementData[i]�Ǹ�object���ʵ����Ҳ����˵���ڵ���list.contains(user)ʱʵ���ϱȽϵ���user.equals(object) �⵱Ȼ�᷵��false��
	      			delList.add(s);               //��д��TbClass ��equals�����Ϳ�����
	      		}
	      	}
	      	list.removeAll(delList);   //������ˡ������� �㶨
	    	
	    	
//	    	List<TbClass> list1=classService.getClassesOfSomeTeacher(user.getId());
//	    	Iterator<TbStudent> ite=list.iterator();
//	    	while(ite.hasNext()){
//	    	TbStudent s=(TbStudent) ite.next();
//	    	if(!list1.contains(s.getTbclass()))
//	    	ite.remove();
	     }
	    
	    
		HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
		response.setContentType("text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
		PrintWriter out = response.getWriter();
		 boolean b=addStudentService.saveStudentFromExcel(list);
		 if((list.size()>0)&&(b==true))
		 {   	
    	out.print("<script>alert('��ӳɹ���')</script>");
	    out.print("<script>window.location.href='showStudent.action'</script>");
		out.flush();
		out.close();
    	return SUCCESS;
    	}
		 else{
			 out.print("<script>alert('���ʧ�ܣ�')</script>");
			    out.print("<script>window.location.href='showStudent.action'</script>");
				out.flush();
				out.close();
		    	return ERROR;
			 
		 }
	    
	   
	//    boolean b=addStudentService.saveStudentFromExcel(list);
    	
         //	    for(int i=0;i<list.size();i++){
        //	     	 loginService.addOne(list.get(i).getStudentId(), list.get(i).getStudentId(),2);
          //	        }
//	    if(b==true)
//		return SUCCESS;
//	    else
//	    return ERROR;
	
		
}
}
