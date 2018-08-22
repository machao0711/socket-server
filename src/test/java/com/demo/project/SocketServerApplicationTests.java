package com.demo.project;

import java.net.InetAddress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SocketServerApplicationTests {

	@Test
	public void contextLoads() throws Exception {
		//获取本机的InetAddress实例
		InetAddress address =InetAddress.getLocalHost();
		String name=address.getHostName();//获取计算机名
		System.out.println(name);
		String ip=address.getHostAddress();//获取IP地址
		System.out.println(ip);
		byte[] bytes = address.getAddress();//获取字节数组形式的IP地址,以点分隔的四部分
		System.out.println(bytes.toString());
		//获取其他主机的InetAddress实例
		//InetAddress address2 =InetAddress.getByName("其他主机名");
		//InetAddress address3 =InetAddress.getByName("IP地址");
	}

}
