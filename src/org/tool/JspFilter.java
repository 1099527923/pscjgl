package org.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.model.RoleResource;
import org.model.TbLogin;
import org.service.RoleResourceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class JspFilter implements Filter{
	//FilterConfig�����ڷ���Filter��������Ϣ
	 private FilterConfig config;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		 this.config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	//��jspҳ����й��ˣ�δ��¼��������¼ҳ��
		   HttpServletRequest httpRequest = (HttpServletRequest)request;
 	       HttpSession session = httpRequest.getSession();  
		  if(session.getAttribute("user")==null){
			  
//			response.setContentType("text/html;charset=UTF-8");  
//			response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
//			PrintWriter out = response.getWriter();
//			out.println("<script>alert('�㻹δ��¼��')</script>");
//			out.flush();
//			out.close();
			  
//		    HttpServletResponse res=(HttpServletResponse)response;  //������������
//		    res.sendRedirect("../login.jsp");  //������������
			response.setContentType("text/html;charset=UTF-8");  
			response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
			PrintWriter out = response.getWriter();
			out.print("<script>alert('�㻹δ��¼��');window.location.href='../login.jsp'</script>");
			out.flush();
			out.close();
		  
		  }
		
		    URL xmlpath = this.getClass().getClassLoader().getResource("applicationContext.xml"); 
	        ApplicationContext ctx=new FileSystemXmlApplicationContext(xmlpath.toString());
	        RoleResourceService rrs=null;
		    rrs=(RoleResourceService) ctx.getBean("roleResourceService");
//		    HttpServletRequest httpRequest = (HttpServletRequest)request; //5.21Ų������ȥ��
//		    HttpSession session = httpRequest.getSession();  //5.21Ų������ȥ��
	    	TbLogin user=(TbLogin)session.getAttribute("user");
		//	String uri=ServletActionContext.getRequest().getRequestURL().toString();
	        HttpServletRequest hrequest = (HttpServletRequest)request;
	        String s=hrequest.getServletPath();
	    		   //Filterֻ����ʽ����������Ȼ���е�Ŀ�ĵ�ַ  
			String u=s.substring(s.lastIndexOf('/')+1);
			List list=rrs.find(user.getIden());
			boolean authentificated=false;
			for(int i=0;i<list.size();i++){
				RoleResource rr=(RoleResource)list.get(i);
				if(rr.getResources().equalsIgnoreCase(u)){
					authentificated=true;
					break;
					
				}
				
			}
			if(authentificated==false){
			//	throw new RuntimeException(new LoginException("����Ȩ���ʸ�ҳ��"));
				
			
				response.setContentType("text/html;charset=UTF-8");  
				response.setCharacterEncoding("UTF-8");//��ֹ��������Ϣ��������  
				PrintWriter out = response.getWriter();
				out.println("<script>alert('����Ȩ���ʸ�ҳ�棡')</script>");
				out.flush();
				out.close();
		}
	
		   chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.config = config;

		
	}

}
