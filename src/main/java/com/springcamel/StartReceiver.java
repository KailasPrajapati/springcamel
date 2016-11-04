package com.springcamel;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.springcamel.model.Person;

@Service("startReceiver")
public class StartReceiver {

	private CountDownLatch latch = new CountDownLatch(1);
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
	}
	
	public void receiveDaillyMessage(byte[] message) {
        System.out.println("Received Dailly<" + message + ">");
        Person person = SerializationUtils.deserialize(message);
        System.out.println("Person:"+person);
    }
	
	public void receiveInstanceMessage(byte[] message) {
        System.out.println("Received Instance<" + message + ">");
        Person person = SerializationUtils.deserialize(message);
        System.out.println("Person:"+person);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

	

}