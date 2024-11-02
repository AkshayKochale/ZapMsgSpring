package com.zap.notification.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zap.notification.dao.ZapClient;
import com.zap.notification.dao.ZapUser;

@Repository
public interface ZapClientRepo extends JpaRepository<ZapClient, Integer> 
{
	
	List<ZapClient> findAllByUser(ZapUser user);
	
	ZapClient findByClientname(String clientname);
	
	ZapClient findByClientnameAndUser(String clientname, ZapUser user);
	
	 @Query("SELECT COUNT(z) FROM ZapClient z WHERE z.user.id = ?1")
	    Integer getTotalClientCount(Integer userId);
}
