package com.battle.spider.parse.proxy.ip;

import org.apache.http.HttpResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author zhangxianbin
 */
@Service("proxyIpParseServiceImpl")
public class ProxyIpParseServiceImpl implements ProxyIpParseService,
		ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void parse(HttpResponse response, String type) {
		ProxyIpParseService service = (ProxyIpParseService) applicationContext
				.getBean(type);
		service.parse(response, type);
	}

}
