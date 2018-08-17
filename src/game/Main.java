package game;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public Socket socket;
    public String host;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.startBattleShipGame();

    }

    private void startBattleShipGame() throws IOException{
        //setup connection
        host = "127.0.0.1";
        InetAddress ia = InetAddress.getByName(host);
        socket = new Socket(ia, 8088);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        GameHandler gh = new GameHandler(socket, bufferedWriter, bufferedReader, host);
        Game_Client client = new Game_Client(gh);
    }

}


