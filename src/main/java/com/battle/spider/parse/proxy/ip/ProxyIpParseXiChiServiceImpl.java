package com.battle.spider.parse.proxy.ip;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.battle.spider.service.redis.RedisService;

/**
 * @author zhangxianbin
 * 
 *         解析的地址为:http://www.xicidaili.com/
 */
@Service("xichi")
public class ProxyIpParseXiChiServiceImpl implements ProxyIpParseService {

	@Value("${ipKeys}")
	private String ipKeys;

	@Resource
	private RedisService redisService;

	@Override
	public void parse(HttpResponse response, String type) {
		// EntityUtils
		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			try {
				InputStream in = entity.getContent();
				String resp = IOUtils.toString(in, Charset.forName("UTF-8"));
				Document doc = Jsoup.parse(resp);
				Elements eles = doc.select("#ip_list tr");
				List<String> ipPortList = new ArrayList<>();
				for (Element ele : eles) {
					Element ipEle = ele.child(1);
					Element portEle = ele.child(2);
					String tagName = ipEle.tagName();
					if ("th".equals(tagName)) {// 过滤th,剩下td
						continue;
					}
					String ip = ipEle.text();
					String port = portEle.text();
					ipPortList.add(ip + ":" + port);
				}
				if (!ipPortList.isEmpty()) {
					String[] ips = new String[ipPortList.size()];
					redisService.setAdd(ipKeys, ipPortList.toArray(ips));
				}
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			}

		}
	}
}
