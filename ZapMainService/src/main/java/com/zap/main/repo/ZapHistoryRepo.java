package com.zap.main.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zap.main.dao.History;
import com.zap.main.dao.ZapClient;
import com.zap.main.dao.ZapUser;


@Repository
public interface ZapHistoryRepo extends JpaRepository<History, Integer> 
{
	
	List<History> findAllByClient(List<ZapClient> client);
	
	List<History> findAllByUser(ZapUser user);
	
    List<History> findAllByUserAndSenddateGreaterThanEqual(ZapUser user, Date date);
	
}
