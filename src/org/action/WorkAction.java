package org.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.model.TbLogin;
import org.model.TbWork;
import org.service.WorkService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WorkAction extends ActionSupport{
	private WorkService workService;
	private File upload;  //�ϴ����ļ�����
	private Integer cishu;
	private String liuyan;
	private TbWork tbwork=new TbWork();

	private  String uploadFileName;  			//�ļ���
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
    
	

	public Integer getCishu() {
		return cishu;
	}

	public void setCishu(Integer cishu) {
		this.cishu = cishu;
	}

	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	public String getLiuyan() {
		return liuyan;
	}

	public void setLiuyan(String liuyan) {
		this.liuyan = liuyan;
	}

	@Override
	public String execute() throws Exception {
	
		
 
		    Map session = ActionContext.getContext().getSession(); 
	        TbLogin user=(TbLogin) session.get("user");
	        String studentId=user.getId();   
	        tbwork.setStudentId(studentId); //������
	        
	        HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);    
			response.setContentType("text/html;charset=UTF-8"); 
			response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
			PrintWriter out = response.getWriter();
	        
	        if(workService.findOne(studentId,cishu)!=null){
	        	out.print("<script>alert('�ϴ�ʧ�ܣ�')</script>");
			    out.print("<script>window.location.href='uploadwork.jsp'</script>");
				out.flush();
				out.close();
				response.reset();
				return ERROR;
	        }
	//[һ]        
//	        String filePath=upload.getName();
//	        String fileName="";
//	        int startIndex=filePath.lastIndexOf("\\");
//	        if(startIndex!=-1){
//	        	fileName=filePath.substring(startIndex+1);
//	        }
//	        else{
//	        	fileName=filePath;
//	        }
//	        File saveFile=new File(uploadPath,fileName);
//	        FileItem fm;
	   //������     
//	      //  String uploadFileName=upload.getName().toString();
//	        String prefix=uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
//	        
//	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//	        String date=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��        
//	        String FileName=studentId+date+prefix;  //�ļ���     
//            // ȡ���������ϴ��ļ���ŵ�·��
//	        String path = ServletActionContext.getServletContext().getRealPath("/work/");
//	        // ȡ���ϴ��ļ�������·��
//	     //   String FilePath = path + File.separator + FileName;
//	        String FilePath = path + FileName;
	        String path = ServletActionContext.getServletContext().getRealPath("/work/");
	        // ȡ���ϴ��ļ�������·��
	        String FilePath = path + File.separator +uploadFileName;
			if(upload!=null){
			 InputStream is=new FileInputStream(getUpload());
			 OutputStream os=new FileOutputStream(FilePath);
			 byte buffer[]=new byte[1024];
			 int count=0;
			 while((count=is.read(buffer))>0){
				os.write(buffer,0,count);
			}
			 os.close();
			 is.close();			
			}
		  tbwork.setPath(FilePath);
		  tbwork.setCishu(cishu);
		  tbwork.setLiuyan(liuyan);
		  boolean b=workService.uploadwork(tbwork.getPath(),tbwork.getStudentId(),tbwork.getCishu(),tbwork.getLiuyan());//��Ҫ��
		
			if(b==true){
			  	out.print("<script>alert('�ϴ��ɹ���')</script>");
			    out.print("<script>window.location.href='studentSeeWork.action'</script>");
				out.flush();
				out.close();
				response.reset();
				return SUCCESS;
			}
			else{
			  	out.print("<script>alert('�ϴ�ʧ�ܣ�')</script>");
			    out.print("<script>window.location.href='uploadwork.jsp'</script>");
				out.flush();
				out.close();
				response.reset();
				return ERROR;
			}
	  
//          if(b==true)
//        	return SUCCESS;
//	      else
//		    return ERROR;
		
	}
}
