package com.demo.project.server.clientchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getKey();
	}
	public String getNumber(){

		try {
			number=in.readUTF();//1
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	public void  getKey(){
		map.put(getNumber(), soc);//得到帐号保存Map
		Set<String> ke = map.keySet();
		if(ke!=null)
			try {
				out.writeUTF(ke.toString());//2
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String oth;
		try {


			while(true){
				oth = in.readUTF();

				if(oth.equalsIgnoreCase("l")){
					getOtherNumber();
				}
				if(oth.equalsIgnoreCase("w")){
					getFileransmission();
				}
				if(oth.equalsIgnoreCase("t")){
					in.close();
					out.close();
					map.remove(number);
					soc.close();
					break;
				}

			}}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("run  中出错");
		}
	}
	public  void getFileransmission(){
		while(true){
			try {
				String oth = in.readUTF();//传输对象编号
				if(oth.equalsIgnoreCase("bye")){
					return;
				}
				Socket other = map.get(oth);//取得传输对象socket
				String fileName = in.readUTF();//传输文件名

				DataOutputStream asd = new DataOutputStream(other.getOutputStream());
				asd.writeUTF("w");
				asd.flush();
				asd.writeUTF(number+"====="+fileName);
				asd.flush();
				// 文件字节传输
				byte[] b= new byte[1024];
				int i=0;
				while((i=in.read(b))!=-1){
					asd.write(b, 0, i);
					asd.flush();
				}



			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(number+"文件传输出错");
			}
		}
	}
	public void  getOtherNumber(){
		while(true){
			try {    
				System.out.println("hhhhhhhhhhhhhhhhhh");
				String oth = in.readUTF();//4
				System.out.println(oth);
				Socket other = map.get(oth);
				DataOutputStream asd = new DataOutputStream(other.getOutputStream());

				if(oth.equalsIgnoreCase("bye")){
					return;
				}
				String value = in.readUTF();//5

				String str=number +"对你说：\r\n"+value;

				asd.writeUTF("l");
				asd.flush();
				asd.writeUTF(str);
				asd.flush();
				if(value.equalsIgnoreCase("bye")){
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(number+"聊天传输出错");
			}
		}
	}


}
