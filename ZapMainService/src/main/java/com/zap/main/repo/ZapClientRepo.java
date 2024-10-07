package com.zap.main.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zap.main.dao.ZapClient;
import com.zap.main.dao.ZapUser;

@Repository
public interface ZapClientRepo extends JpaRepository<ZapClient, Integer> 
{
	
	List<ZapClient> findAllByUser(ZapUser user);
	
	 @Query("SELECT COUNT(z) FROM ZapClient z WHERE z.user.id = ?1")
	    Integer getTotalClientCount(Integer userId);
}