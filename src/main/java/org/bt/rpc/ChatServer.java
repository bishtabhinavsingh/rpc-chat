package org.bt.rpc;

import org.bt.rpc.chat.ChatServiceOuterClass;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started. Listening on Port 8080");

        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 InputStream inputStream = clientSocket.getInputStream()) {

                DataInputStream dataInputStream = new DataInputStream(inputStream);
                while (true) {
                    int length = dataInputStream.readInt();
                    if (length > 0) {
                        byte[] messageByte = new byte[length];
                        dataInputStream.readFully(messageByte, 0, messageByte.length);
                        ChatServiceOuterClass.ChatMessage message = ChatServiceOuterClass.ChatMessage.parseFrom(messageByte);
                        StringBuffer sb = new StringBuffer();
//                        sb.append("At: "+ message.getTimestamp());
                        sb.append(" Received message: " + message.getMessageText());
                        System.out.println(sb);
                    }
                }
            } catch (EOFException e) {
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port 8080 or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
}