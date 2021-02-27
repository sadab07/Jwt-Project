package com.JWTSptingWebApp.TaskDetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.TaskDetail.model.TaskFeedbackDetail;

@Repository
public interface TaskFeedbackDetailRepository extends JpaRepository<TaskFeedbackDetail, Integer>{

}
