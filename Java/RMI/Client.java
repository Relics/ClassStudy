package lichen;

import java.io.*;
import java.math.BigInteger;
import java.rmi.*;
import java.util.Scanner;

public class Client {
   public static void main(String args[]) {
      try {
         int RMIPort=1234;				//RMI�˿ں�
         String hostName="localhost";	//
         Scanner scan=new Scanner(System.in);
         String userName;
         String userPassword;
         
         String registryURL = "rmi://" + hostName+ ":" + RMIPort + "/hello";  
         
         Interface a = (Interface)Naming.lookup(registryURL);
         System.out.println("�ɹ�����Զ�̽ӿ� ��" );
         
         int occurances=1;
         while(true){
        	 if(occurances==1){
        		 System.out.println("�𾴵��û����ã�");
	        	 System.out.println("�����������û�����");
	        	 userName=scan.next();
	        	 System.out.println("�������������룺");
	        	 userPassword=scan.next();
	        	 if(a.register(userName, userPassword))
	        		 break;//ע��ɹ�������
	        	 ++occurances;//�������ǵ�һ������
        	 }else{
        		 System.out.println("��������û����Ѿ����ڣ�");
	        	 System.out.println("���������������û�����");
	        	 userName=scan.next();
	        	 System.out.println("�����������������룺");
	        	 userPassword=scan.next();
	        	 if(a.register(userName, userPassword))
	        		 break;//ע��ɹ�������
        	 }
         }
      }
      catch (Exception e) {
         System.out.println("Exception in HelloClient: " + e);
      } 
   } //end main
}//end class

