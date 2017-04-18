package com.battle.spider.handler;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.battle.spider.enums.ProxyIpEnums;
import com.battle.spider.model.ResponseDTO;
import com.battle.spider.parse.proxy.ip.ProxyIpParseService;
import com.battle.spider.service.redis.RedisService;
import com.battle.spider.util.HttpUtil;

/**
 * @author zhangxianbin
 */
@Service
public class ProxyIpHandler {

	@Value("${ipKeys}")
	private String ipKeys;

	@Resource
	private RedisService redisService;

	@Resource(name = "proxyIpParseServiceImpl")
	private ProxyIpParseService proxyIpParseService;

	public void handler() {

		String url = ProxyIpEnums.XiChi.getUrl();
		// 一次从redis取出5个代理
		Set<String> ipSets = redisService.getLimitKey(ipKeys, 5);
		ResponseDTO respDto = null;
		if (ipSets.isEmpty()) {
			respDto = HttpUtil.getWithHeads(url, false, null);
		} else {
			respDto = HttpUtil.getWithHeads(url, true, ipSets);
		}
		if (respDto.getInvalidIpSets() != null
				&& !respDto.getInvalidIpSets().isEmpty()) {
			redisService.removeSets(ipKeys, respDto.getInvalidIpSets());
		}
		if (respDto.getResponse() == null) {// 表示解析失败
			return;
		}
		// 解析
		proxyIpParseService.parse(respDto.getResponse(),
				ProxyIpEnums.XiChi.getByUrl(url));

	}
}
