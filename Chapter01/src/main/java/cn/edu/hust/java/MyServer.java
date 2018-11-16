package cn.edu.hust.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws IOException {
        //1.创建一个服务用于监听某个端口
        ServerSocket serverSocket=new ServerSocket(5000);
        System.out.println("服务器启动...");
        //2.对于accept()方法进行阻塞,直到建立一个请求连接
        Socket socket=serverSocket.accept();
        System.out.println("客户端:"+socket.getInetAddress().getHostName()+"已经连接到服务器....");
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        String request=null;
        System.out.println(reader.readLine());
        while((request=reader.readLine())!=null)
        {
            if("Done".equals(request))
            {
                writer.write("the server close");
                writer.flush();
                break;
            }
            System.out.println(request);
            writer.write("the server processes the message "+request+" \n");
            writer.flush();
        }
    }
}
