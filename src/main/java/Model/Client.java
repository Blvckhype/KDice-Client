package Model;

import Helper.ClientHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private long id;
    private Socket socket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private ClientHelper clientHelper;

    public Client(Socket socket) throws IOException {
        this.id = 0;
        this.socket = socket;
        this.outToServer = new DataOutputStream(socket.getOutputStream());
        this.inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.clientHelper = new ClientHelper();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getToServer() {
        return outToServer;
    }

    public BufferedReader getFromServer() {
        return inFromServer;
    }

    public ClientHelper getClientHelper() {
        return clientHelper;
    }

    public String readMessage() throws IOException {
        return this.getFromServer().readLine();

    }

    public void sendMessage(String message) throws IOException {
        this.getToServer().writeBytes(message);
        this.getToServer().flush();
    }


}
