demo 说明

1、文件位置：
java代码：com.weondersgorup.esf.jms 包
配置文件：config/ext/jmsconfig.properties   applicationContext-jms.xml
jar包：新增 wlthint3client.jar  （from:/home/weblogic/Oracle/Middleware/wlserver_10.3/server/lib）

2、配置文件中jms配置分为activemq和weblogic，，，确定中间件后，需要注释掉不用的jms配置

3、testQueue为发送队列，responseQueue为回复队列，需要在中间件上提前配置好，，中间件的配置比较简单，可查看对应产品文档

3、MessageProduceTest.java为单元测试代码