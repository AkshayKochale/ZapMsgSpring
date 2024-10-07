package com.zap.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zap.main.dao.ZapSubscription;
import com.zap.main.dao.ZapUser;

@Repository
public interface ZapSubscriptionRepo  extends JpaRepository<ZapSubscription, Integer>
{
	ZapSubscription findByUser(ZapUser user);
}
