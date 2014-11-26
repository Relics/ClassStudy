package lichen;

import java.io.*;
import java.math.BigInteger;
import java.rmi.*;
import java.util.Scanner;

public class Client {
   public static void main(String args[]) {
      try {
         int RMIPort=1234;				//RMI端口号
         String hostName="localhost";	//
         Scanner scan=new Scanner(System.in);
         String userName;
         String userPassword;
         
         String registryURL = "rmi://" + hostName+ ":" + RMIPort + "/hello";  
         
         Interface a = (Interface)Naming.lookup(registryURL);
         System.out.println("成功连接远程接口 ！" );
         
         int occurances=1;
         while(true){
        	 if(occurances==1){
        		 System.out.println("尊敬的用户您好！");
	        	 System.out.println("请输入您的用户名：");
	        	 userName=scan.next();
	        	 System.out.println("请输入您的密码：");
	        	 userPassword=scan.next();
	        	 if(a.register(userName, userPassword))
	        		 break;//注册成功就跳出
	        	 ++occurances;//表明不是第一次输入
        	 }else{
        		 System.out.println("您输入的用户名已经存在！");
	        	 System.out.println("请重新输入您的用户名：");
	        	 userName=scan.next();
	        	 System.out.println("请重新输入您的密码：");
	        	 userPassword=scan.next();
	        	 if(a.register(userName, userPassword))
	        		 break;//注册成功就跳出
        	 }
         }
      }
      catch (Exception e) {
         System.out.println("Exception in HelloClient: " + e);
      } 
   } //end main
}//end class

