package com.battle.spider.service.redis;

import java.util.Set;

/**
 * @author zhangxianbin
 */
public interface RedisService {

	/**
	 * set ���key
	 * 
	 * @param key
	 */
	void setAdd(String key, String[] values);

	/**
	 * ɾ��key����Ԫ��
	 * 
	 * @param key
	 * @param member
	 */
	void remove(String key, String member);

	/**
	 * ��ȡsetΪkey�ļ���
	 * 
	 * @param key
	 * @param limit
	 * @return
	 */
	Set<String> getLimitKey(String key, int limit);

	/**
	 * ɾ��key���values
	 * 
	 * @param key
	 * @param values
	 */
	void removeSets(String key, Set<String> values);
}
