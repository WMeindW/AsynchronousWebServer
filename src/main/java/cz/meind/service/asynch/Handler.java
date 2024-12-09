package cz.meind.service.asynch;

import java.net.Socket;

public class Handler {
    private Socket client;

    private Thread thread;

    public void handle(Socket c){
        client = c;
        thread = new Thread(this::run);
    }

    private void run(){

    }
}
