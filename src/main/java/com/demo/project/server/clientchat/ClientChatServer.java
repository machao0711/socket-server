package com.demo.project.server.clientchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

import com.demo.project.constant.ServerConstant;
import com.demo.project.util.ServerUtil;

public class ClientChatServer {

	public static void main(String[] args) {
		new ServerOb(8080);
	}

}
class ServerOb{
	private int port;
	ServerSocket ss;
	public ServerOb(int p){
		this.port=p;
		try {
			ss = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){
			try {

				Socket soc = ss.accept();
				new Thread(new clientO(soc)).start();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(" ServerOb 构造函数  出错");
				e.printStackTrace();
			}
		}
	}
}
class clientO implements Runnable{
	public static HashMap<String, Socket> map= new HashMap<String, Socket>();
	private  String number = null;
	private Socket soc;
	private  DataInputStream in;
	private DataOutputStream out;
	public clientO(Socket s){
		this.soc=s;
		try {
			this.in=new DataInputStream(soc.getInputStream());
			this.out=new DataOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerUtil serverUtil=new ServerUtil();
		serverUtil.getKey(out, map, s, in, number);
	}

	@Override
	public void run() {
		String oth;
		try {
			while(true){
				oth = in.readUTF();
				ServerUtil serverUtil=new ServerUtil();
				if(oth.equalsIgnoreCase(ServerConstant.chat)){
					serverUtil.getOtherNumber(in, map, oth);
				}
				if(oth.equalsIgnoreCase(ServerConstant.file)){
					serverUtil.getFileransmission(in, map, oth);
				}
				if(oth.equalsIgnoreCase(ServerConstant.exit)){
					in.close();
					out.close();
					map.remove(number);
					soc.close();
					break;
				}
			}}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("run  中出错");
		}
	}



}
