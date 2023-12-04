package org.bt.rpc;

import org.bt.rpc.chat.ChatServiceOuterClass;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080);
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                ChatServiceOuterClass.ChatMessage message = ChatServiceOuterClass.ChatMessage.newBuilder()
                        .setSenderId("user123")
                        .setTimestamp(String.valueOf(System.currentTimeMillis()))
                        .setMessageText(userInput)
                        .build();

                byte[] messageBytes = message.toByteArray();
                dataOutputStream.writeInt(messageBytes.length);
                dataOutputStream.write(messageBytes);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to localhost");
            System.exit(1);
        }
    }
}
