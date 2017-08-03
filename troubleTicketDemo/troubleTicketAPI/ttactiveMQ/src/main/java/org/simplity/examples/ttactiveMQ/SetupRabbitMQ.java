package org.simplity.examples.ttactiveMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * Setup the MQ create a Queue
 *
 */
public class SetupRabbitMQ {
	final static Logger logger = LoggerFactory.getLogger(SetupRabbitMQ.class);
	public static void main(String[] args) {
		Connection connection = null;
		Channel channel = null;
		try {		
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare("dynamicQueues/TTWriteQQ", true, false, false, null);
			channel.queueDeclare("dynamicQueues/TTReadQQ", true, false, false, null);
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				channel.close();
				connection.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		} 
	}
}
