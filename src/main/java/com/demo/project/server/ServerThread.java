package com.demo.project.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{//服务器线程处理类
	 Socket socket=null;
	 InputStream is=null;
	 BufferedReader br=null;
	 OutputStream os=null;
	 PrintWriter pw=null;
	  public ServerThread(Socket socket){
		 this.socket=socket;
	 }
	  public void run(){
		  try {
			    is=socket.getInputStream();//获得字节流
				br=new BufferedReader(new InputStreamReader(is));//字节流转化为字符流并添加缓冲
				String info=null;
				while((info=br.readLine())!=null){
					System.out.println("我是服务端,客户端说:"+info);
				}
				//必须要及时关闭，因为readline这个方法是以\r\n作为界定符的，
				//由于发送消息的那一端用的是PrintWriter的write()方法，这个方法并没加上\r\n,所以会一直等待
				socket.shutdownInput();
				//回复客户端
				os=socket.getOutputStream();
			    pw=new PrintWriter(os);
				pw.write("欢迎您!");
				pw.flush();
				
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (pw!=null)
			pw.close();
			try {
				if (os!=null)
				os.close();
				if (br!=null)
				br.close();
				if (is!=null)
				is.close();//关闭返回的 InputStream 将关闭关联套接字。 
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  }
	}

