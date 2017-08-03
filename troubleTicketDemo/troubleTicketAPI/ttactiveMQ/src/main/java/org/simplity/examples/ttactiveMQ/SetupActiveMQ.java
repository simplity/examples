package org.simplity.examples.ttactiveMQ;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Setup the MQ create a Queue
 *
 */
public class SetupActiveMQ {
	final static Logger logger = LoggerFactory.getLogger(SetupActiveMQ.class);
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			final QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ic
					.lookup("ConnectionFactory");
			QueueConnection queueConnection = (QueueConnection) connectionFactory.createConnection();
			QueueSession queueSession = queueConnection.createQueueSession(false,
					javax.jms.Session.DUPS_OK_ACKNOWLEDGE);
			queueConnection.start();
			queueSession.createQueue("jms/TTWriteQQ");
			queueSession.createQueue("jms/TTReadQQ");
			queueConnection.close();
		} catch (JMSException e) {
			logger.error("JMS error",e);
		} catch (NamingException e) {
			logger.error("JMS error",e);
		}
	}
}
