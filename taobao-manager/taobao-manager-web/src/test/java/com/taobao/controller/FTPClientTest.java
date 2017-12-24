package com.taobao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FTPClientTest {
	
	@Test
	public void testFTP() throws Exception{
		//1、连接 ftp 服务器
		FTPClient client = new FTPClient();
		client.connect("192.168.6.135", 21);
		//2、登录 ftp 服务器
		client.login("ftpuser", "ftpuser");
		//3、读取本地文件
		String string = "D:\\pie.png";
		String location = string.replace("\\\\", "/");
		FileInputStream inputStream = new FileInputStream(new File(location));
		//4、上传文件
		//(1) 指定上传目录
		client.changeWorkingDirectory("/home/ftpuser/www/images");
		//(2) 指定文件类型
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		//第一个参数：文件在远程服务器的名称
		//第二个参数：文件流
		client.storeFile("hello.jpg", inputStream);
		//5、退出登录
		client.logout();
	}
}
