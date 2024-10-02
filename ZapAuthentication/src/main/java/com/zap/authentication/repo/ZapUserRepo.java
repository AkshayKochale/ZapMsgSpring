package com.zap.authentication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zap.authentication.dao.ZapUser;

@Repository
public interface ZapUserRepo extends JpaRepository<ZapUser, Integer> 
{
	ZapUser findByUsername(String username);
	ZapUser findByEmail(String email);
}
