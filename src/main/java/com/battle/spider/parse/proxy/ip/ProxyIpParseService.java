package com.battle.spider.parse.proxy.ip;

import org.apache.http.HttpResponse;

/**
 * @author zhangxianbin
 */
public interface ProxyIpParseService {

	void parse(HttpResponse response, String type);
}
