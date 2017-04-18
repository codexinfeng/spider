package com.battle.spider.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.battle.spider.handler.ProxyIpHandler;

/**
 * @author zhangxianbin
 */
public class MainEntrance {

	public static void main(String[] args) {

		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		ProxyIpHandler handler = ac.getBean(ProxyIpHandler.class);
		while (true) {
			handler.handler();
			try {
				// ÿ�������ȥһ������
				Thread.sleep(5 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
