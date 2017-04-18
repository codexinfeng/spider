package com.battle.spider.enums;

/**
 * @author zhangxianbin
 */
public enum ProxyIpEnums {

	XiChi("http://www.xicidaili.com/nn", "xichi");
	private String url;

	private String type;

	private ProxyIpEnums(String url, String type) {
		this.url = url;
		this.type = type;
	}

	public String getByUrl(String url) {
		String type = "";
		for (ProxyIpEnums enums : ProxyIpEnums.values()) {
			if (url.equals(enums.url)) {
				return enums.type;

			}
		}
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
