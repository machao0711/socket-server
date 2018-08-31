package com.demo.project.server.clientchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import com.demo.project.constant.ServerConstant;
import com.demo.project.util.ServerUtil;

public class ServerRun implements Runnable{
	public static HashMap<String, Socket> map= new HashMap<String, Socket>();
	private  String number = null;
	private Socket soc;
	private  DataInputStream in;
	private DataOutputStream out;
	public ServerRun(Socket s){
		this.soc=s;
		try {
			this.in=new DataInputStream(soc.getInputStream());
			this.out=new DataOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerUtil serverUtil=new ServerUtil();
		number=serverUtil.getNumber(in, number);
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
					serverUtil.getOtherNumber(in, map, number);
				}
				if(oth.equalsIgnoreCase(ServerConstant.file)){
					serverUtil.getFileransmission(in, map, number);
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
