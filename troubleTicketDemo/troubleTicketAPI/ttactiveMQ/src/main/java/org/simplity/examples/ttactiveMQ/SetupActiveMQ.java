package org.simplity.examples.ttactiveMQ;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Setup the MQ create a Queue
 *
 */
public class SetupActiveMQ {
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			final QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ic
					.lookup("ConnectionFactory");
			TopicConnection queueConnection = (TopicConnection) connectionFactory.createConnection();
			TopicSession queueSession = queueConnection.createTopicSession(false,
					javax.jms.Session.DUPS_OK_ACKNOWLEDGE);
			queueConnection.start();
			Destination destination = queueSession.createTopic("jms/TTWriteQQ");
			Destination source = queueSession.createTopic("jms/TTReadQQ");
			queueConnection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
