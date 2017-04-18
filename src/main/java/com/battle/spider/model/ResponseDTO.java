package com.battle.spider.model;

import java.util.Set;

import org.apache.http.HttpResponse;

/**
 * @author zhangxianbin
 */
public class ResponseDTO {

	/**
	 * ���󷵻�
	 */
	private HttpResponse response;

	/**
	 * ��Ч�Ĵ�����
	 */
	private Set<String> invalidIpSets;

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public Set<String> getInvalidIpSets() {
		return invalidIpSets;
	}

	public void setInvalidIpSets(Set<String> invalidIpSets) {
		this.invalidIpSets = invalidIpSets;
	}

}
