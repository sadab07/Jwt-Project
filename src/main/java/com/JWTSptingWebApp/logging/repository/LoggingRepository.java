package com.JWTSptingWebApp.logging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JWTSptingWebApp.logging.model.LoggingDetail;

public interface LoggingRepository extends JpaRepository<LoggingDetail,Long>{

}
