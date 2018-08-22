package com.demo.project.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * socket实现服务端和客户端的对话
 * 			服务端
 */
public class clientServerTalkServer extends Thread{
	ServerSocket server = null;
	Socket socket = null;
	public clientServerTalkServer(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		super.run();
		try{
			System.out.println("wait client connect...");
			socket = server.accept();
			new sendMessThread().start();//连接并返回socket后，再启用发送消息线程
			System.out.println(socket.getInetAddress().getHostAddress()+"SUCCESS TO CONNECT...");
			InputStream in = socket.getInputStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len=in.read(buf))!=-1){
				System.out.println("client saying: "+new String(buf,0,len));
			}

		}catch (IOException e){
			e.printStackTrace();
		}
	}


	class sendMessThread extends Thread{
		@Override
		public void run(){
			super.run();
			Scanner scanner=null;
			OutputStream out = null;
			try{
				if(socket != null){
					scanner = new Scanner(System.in);
					out = socket.getOutputStream();
					String in = "";
					do {
						in = scanner.next();
						out.write(("server saying: "+in).getBytes());
						out.flush();//清空缓存区的内容
					}while (!in.equals("q"));
					scanner.close();
					try{
						out.close();
					}catch (IOException e){
						e.printStackTrace();
					}
				}
			}catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	//函数入口
	public static void main(String[] args) {
		clientServerTalkServer server = new clientServerTalkServer(5555);
		server.start();
	}
}
