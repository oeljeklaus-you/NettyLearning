package cn.edu.hust.java;

import java.io.*;
import java.net.Proxy;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",5000);
        System.out.println("客户端启动....");
        //socket.getOutputStream().write("hello".getBytes());
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入想要传递的信息:");

        String s;
        while(scanner.hasNext())
        {
            writer.println(scanner.nextLine());
            writer.flush();
        }
    }
}
