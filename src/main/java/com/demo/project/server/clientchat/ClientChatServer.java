package com.demo.project.server.clientchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientChatServer {
	private int port;
	ServerSocket ss;
	public ClientChatServer(int p){
		this.port=p;
		try {
			ss = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){
			try {
				Socket soc = ss.accept();
				new Thread(new ServerRun(soc)).start();

			} catch (IOException e) {
				System.out.println("ClientChatServer 构造函数  出错");
				e.printStackTrace();
			}
		}
	}
}
