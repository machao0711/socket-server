package com.demo.project.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class ServerUtil {
	public String getNumber(DataInputStream in,String number){

		try {
			number=in.readUTF();//1
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	public void  getKey(DataOutputStream out,HashMap<String, Socket> map,Socket soc,DataInputStream in,String number){
		map.put(getNumber(in, number), soc);//得到帐号保存Map
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

	public  void getFileransmission(DataInputStream in,HashMap<String, Socket> map,String number){
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
	public void  getOtherNumber(DataInputStream in,HashMap<String, Socket> map,String number){
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
