package org.service;

import java.util.List;

import org.model.TbClass;

public interface ClassService {
	  //����༶��Ϣ
	   public void save(TbClass tbclass);
	   //���ݰ༶ID�Ұ༶��Ϣ
	   public TbClass getOneClass(Integer classId);
	   //ͨ����ʦ�Ұ༶
	   public List<TbClass> getClassesOfSomeTeacher(String teacherId);
	   public List findAll();
	   public void update(TbClass tbclass);
		public void add(TbClass tbclass);
		   public void delete(Integer id);
		   public TbClass find(Integer id) ;
		   public TbClass findClassByName(String className);
}
