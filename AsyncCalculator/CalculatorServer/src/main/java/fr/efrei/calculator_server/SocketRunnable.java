package fr.efrei.calculator_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SocketRunnable implements Runnable {

    private Socket socket;
    
    public SocketRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String operation = br.readLine();

            System.out.println(operation);

            Expression expression = new ExpressionBuilder(operation).build();
            double result = expression.evaluate();

            dos.writeDouble(result);
            dos.flush();

            socket.close();

            System.out.println("Socket disconnected");
        }
        catch(Exception e) {

        }
    }
}
