package com.demo.project.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.project.server.clientchat.ClientChatServer;

@SpringBootTest
public class ServerTest {
	@Test
	public void clientTalkToServer(){
		new ClientChatServer(5555);
	}

}
