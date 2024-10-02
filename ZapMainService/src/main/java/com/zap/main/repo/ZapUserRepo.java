package com.zap.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zap.main.dao.ZapUser;

@Repository
public interface ZapUserRepo extends JpaRepository<ZapUser, Integer> 
{
	ZapUser findByUsername(String username);
	ZapUser findByEmail(String email);
}
