package com.battle.spider.service.redis;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zhangxianbin
 */
@Service
public class RedisServiceImpl implements RedisService {

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void setAdd(String key, String[] values) {
		redisTemplate.opsForSet().add(key, values);
	}

	@Override
	public void remove(String key, String member) {
		redisTemplate.opsForSet().remove(key, member);
	}

	@Override
	public Set<String> getLimitKey(String key, int limit) {
		return redisTemplate.opsForSet().distinctRandomMembers(key, limit);
	}

	@Override
	public void removeSets(String key, Set<String> values) {
		redisTemplate.opsForSet().remove(key, values);

	}

}
