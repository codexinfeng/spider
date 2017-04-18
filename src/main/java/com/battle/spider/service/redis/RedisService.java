package com.battle.spider.service.redis;

import java.util.Set;

/**
 * @author zhangxianbin
 */
public interface RedisService {

	/**
	 * set 添加key
	 * 
	 * @param key
	 */
	void setAdd(String key, String[] values);

	/**
	 * 删除key里面元素
	 * 
	 * @param key
	 * @param member
	 */
	void remove(String key, String member);

	/**
	 * 获取set为key的集合
	 * 
	 * @param key
	 * @param limit
	 * @return
	 */
	Set<String> getLimitKey(String key, int limit);

	/**
	 * 删除key里的values
	 * 
	 * @param key
	 * @param values
	 */
	void removeSets(String key, Set<String> values);
}
