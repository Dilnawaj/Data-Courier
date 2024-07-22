package com.datacourier.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.datacourier.entities.FileData;

@Repository
public interface FileDataRepo extends JpaRepository<FileData,Long>{

	List<FileData> findByUserId(Long userId);
	List<FileData> findByReqId(Long reqId);
	

	@Query("select count(f) from FileData f where f.reqId =?1 and f.fileStatus =true")
	public int getCount(Long id);
	
}
