/**
* TODO
* @Project: gsyth
* @Title: ResponseQueueListener.java
* @Package com.wondersgroup.esf.jms
* @author jason.liu
* @Date 2016年5月20日 上午10:49:41
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
* TODO
* @ClassName: ResponseQueueListener
* @author jason.liu
*/
public class ResponseQueueListener implements MessageListener {


	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

		if (message instanceof TextMessage){
			// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
			TextMessage textMsg = (TextMessage) message;
			try {
				System.out.println("从回复队列收到的消息内容是：" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}else if(message instanceof MapMessage){
			
		}else if(message instanceof ObjectMessage){
			
		}else{
			
		}
	}

}
