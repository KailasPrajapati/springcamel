package com.springcamel;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;



/*import org.apache.commons.lang3.SerializationUtils;*/
import org.junit.Test;
/*import org.springframework.util.SerializationUtils;*/

import org.springframework.amqp.utils.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.springcamel.model.Person;

public class TestSender {

	String queueNameDaily = "emfNsDaily";
	String queueNameInstance = "emfNsInstance";
	static String TASK_EXCHANGE_NAME = "emfNsMailExchange";
	
	@Test
	public void test1() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
	 
		channel.exchangeDeclare(TASK_EXCHANGE_NAME, "direct", 
			   true, 	/* durable */
			   false, 	/* autodelete */
			   null); 	/* */
		channel.queueDeclare(queueNameInstance, true, false, false, null);
    	channel.queueBind(queueNameInstance, TASK_EXCHANGE_NAME, queueNameInstance);
    	
		Person person = new Person();
		person.setId(3);
		person.setfName("Amit");
		person.setlName("Soni");
		byte[] messageBodyBytes = "Hello, world123!".getBytes();
		byte[] objectBytes = SerializationUtils.serialize(person);
	 
	 
		channel.basicPublish(TASK_EXCHANGE_NAME, queueNameInstance, MessageProperties.PERSISTENT_BASIC, objectBytes);
		System.out.println(" [x] Sent ");
	 
		channel.close();
		assertTrue(true);
	}
	
	@Test
	public void test2() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
	 
		channel.exchangeDeclare(TASK_EXCHANGE_NAME, "direct", 
			   true, 	/* durable */
			   false, 	/* autodelete */
			   null); 	/* */
		channel.queueDeclare(queueNameDaily, true, false, false, null);
    	channel.queueBind(queueNameDaily, TASK_EXCHANGE_NAME, queueNameDaily);
    	
		Person person = new Person();
		person.setId(1);
		person.setfName("Kailas");
		person.setlName("Prajapati");
		byte[] messageBodyBytes = "Hello, world123!".getBytes();
		byte[] objectBytes = SerializationUtils.serialize(person);
	 
	 
		channel.basicPublish(TASK_EXCHANGE_NAME, queueNameDaily, MessageProperties.PERSISTENT_BASIC, objectBytes);
		System.out.println(" [x] Sent ");
	 
		channel.close();
		assertTrue(true);
	}

}
