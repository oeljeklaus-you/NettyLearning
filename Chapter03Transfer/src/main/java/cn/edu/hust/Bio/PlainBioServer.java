package cn.edu.hust.Bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PlainBioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9999);
        while(true)
        {
            final Socket socket=serverSocket.accept();
            System.out.println("Server recevied from client:"+socket.getInetAddress().getHostName());
            new Thread(new Runnable() {
                public void run() {
                    try {
                        BufferedReader bufferedReader
                                =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        System.out.println("the message from client:"+bufferedReader.readLine());
                        socket.getOutputStream().write("the server process".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
