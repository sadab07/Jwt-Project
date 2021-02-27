package com.JWTSptingWebApp.TaskDetail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.TaskDetail.model.TaskDetail;
import com.JWTSptingWebApp.taskmng.model.taskfilter;



@Repository
public interface TaskDetailRepository extends JpaRepository<TaskDetail, String>{
	
	taskfilter taskfilter=new taskfilter();
	//0
	@Query(value = "SELECT * FROM task_details WHERE subject LIKE %?% AND project_name LIKE %?%  AND verified_by LIKE %?% AND assigned_to LIKE %?% AND priority LIKE %?%",nativeQuery = true)
	public List<TaskDetail> findByForGlobal(String subject,String projectName,String varifiedBY,String assignedTo,String priority);
	
	//old
	@Query(value = "SELECT * FROM task_details WHERE status_text='not started' and subject LIKE %?% AND project_name LIKE %?%  AND verified_by LIKE %?% AND assigned_to LIKE %?% AND priority LIKE %?%",nativeQuery = true)
	public List<TaskDetail> findForTodo(String subject,String projectName,String varifiedBY,String assignedTo,String priority);
	
	
	@Query(value = "SELECT * FROM task_details WHERE subject LIKE %?% AND project_name LIKE %?%  AND verified_by LIKE %?% AND assigned_to LIKE %?% AND priority LIKE %?% ",nativeQuery = true)
	public List<TaskDetail> findForinProgress(String subject,String projectName,String varifiedBY,String assignedTo,String priority);
	
	@Query(value = "SELECT * FROM task_details WHERE status_text='Completed' and subject LIKE %?% AND project_name LIKE %?%  AND verified_by LIKE %?% AND assigned_to LIKE %?% AND priority LIKE %?%",nativeQuery = true)
	public List<TaskDetail> findForcompleted(String subject,String projectName,String varifiedBY,String assignedTo,String priority);
	
	@Query(value = "SELECT * FROM task_details WHERE subject LIKE %?% ",nativeQuery = true)
	public List<TaskDetail> findTaskBySubjectOnlyGlobal(String subject);
	
	@Query(value = "SELECT * FROM task_details WHERE assinged_to=?",nativeQuery = true)
	public List<TaskDetail> findTaskByassignedToOnlyGlobal(String assignedTo);

	@Query("SELECT t FROM TaskDetail t WHERE t.subject=?1")
	public TaskDetail findByToken(String subject);
}
