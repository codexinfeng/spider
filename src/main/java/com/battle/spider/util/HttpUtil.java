package com.battle.spider.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.battle.spider.model.ResponseDTO;
import com.battle.spider.service.redis.RedisService;

/**
 * @author zhangxianbin
 */
public class HttpUtil {

	@Resource
	private RedisService redisService;

	public static HttpResponse get(String url) {

		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse resp = client.execute(get);
			System.out.println(resp.getStatusLine());
			return resp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static HttpResponse getWithHeads(String url) {

		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.addHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		try {
			HttpResponse resp = client.execute(get);
			System.out.println(resp.getStatusLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static HttpResponse getWithHeads(String url,
			Map<String, String> headMap) {

		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();
		HttpGet get = new HttpGet(url);
		if (headMap != null && !headMap.isEmpty()) {
			for (Entry<String, String> entry : headMap.entrySet()) {
				get.addHeader(entry.getKey(), entry.getValue());
			}
		}
		try {
			HttpResponse resp = client.execute(get);
			System.out.println(resp.getStatusLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// 如果是代理的header,每五分钟跑一次
	public static ResponseDTO getWithHeads(String url, boolean withProxy,
			Set<String> proxySets) {
		ResponseDTO dto = new ResponseDTO();
		Set<String> invalidIps = new HashSet<String>();
		dto.setInvalidIpSets(invalidIps);
		// 创建Client
		HttpClient client = HttpClients.createDefault();
		// 创建get
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.addHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		long start = System.currentTimeMillis();
		if (withProxy && proxySets != null && !proxySets.isEmpty()) {// 默认表示使用代理
			for (String proxySet : proxySets) {// 从redis或者其它形式下拿到的代理
				if (proxySet == null || proxySet == "") {
					continue;
				}
				String[] proxyStrs = proxySet.split(":");
				String proxyIp = proxyStrs[0];
				int proxyPort = Integer.valueOf(proxyStrs[1]);
				for (int j = 0; j < 3; j++) {// 同一个代理去尝试三次
					HttpHost proxy = new HttpHost(proxyIp, proxyPort);
					RequestConfig config = RequestConfig.custom()
							.setConnectTimeout(1000)
							.setConnectionRequestTimeout(1000)
							.setSocketTimeout(1000)// 数据传输处理时间
							// socket建立连接的时间
							.setProxy(proxy).build();
					get.setConfig(config);
					try {
						HttpResponse resp = client.execute(get);
						if (resp.getStatusLine().getStatusCode() == 200) {
							dto.setResponse(resp);
							return dto;
						}
					} catch (IOException e) {
						System.out.println("代理处问题了");
						if (j == 2) {// 代理尝试了三次也没有结果
							invalidIps.add(proxySet);
						}
					}
				}
			}

		} else {
			try {
				HttpResponse resp = client.execute(get);
				System.out.println(resp.getStatusLine());
				if (resp.getStatusLine().getStatusCode() == 200) {
					dto.setResponse(resp);
					return dto;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			System.out.println("用时:" + (end - start) / 1000);
		}

		return dto;
	}

	public static void main(String[] args) {
		ResponseDTO respDto = getWithHeads("http://www.xicidaili.com/nn", true,
				new HashSet<>());
		if (respDto.getResponse() == null) {
			System.out.println("没有找到数据");
		} else {
			System.out.println("数据没问题");
		}
	}

}
