1:主要功能为爬去代理网站中的ip地址放入redis
2:项目入口在:com.battle.spider.main.MainEntrance
3:如果需要新增加爬去代理ip站点,需要在ProxyIpEnums 增加枚举,url为需要爬去的url,type为新增的service
4:新增继承ProxyIpParseService接口的service,类上添加注释@Service(type)
5:放入redis有两种方式,默认使用5.1
   5.1：一种是在applicationContext.xml里面引入spring-redis.xml直接使用redis
   5.2：一种是在applicationContext.xml里面引入spring-redis-sentinel.xml使用redis-sentinel
