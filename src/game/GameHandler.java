package game;

import javax.swing.*;
import message.MessageFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class GameHandler {
    public String host;
    public int BOARD_SIZE = 10;
    public BufferedWriter bufferedWriter;
    public BufferedReader bufferedReader;
    public String username;
    public Socket socket;
    GameHandler gameHandler;
    public MessageHandler messageHandler;
    public String[][] userBoard;
    public String[][] guessesBoard;

    public GameHandler(Socket socket, BufferedWriter bw, BufferedReader br, String host){
        this.socket = socket;
        this.bufferedWriter = bw;
        this.bufferedReader = br;
        this.host = host;
        fillBoardsWithEmpty();
    }

    public GameHandler getGameHandler(){
        return this.gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler){

        this.gameHandler = gameHandler;
    }

    public void requestNewGame() throws IOException{
        //get username
        username = promptUserForUserName();

        //send username to the server with request to begin game.
        login(username, messageHandler);
        //TODO


        //get response from server.
    }

    private void login(String username, MessageHandler messageHandler) throws IOException {
        messageHandler.sendMessage(MessageFactory.sendUsernameMessage(username));
        bufferedWriter.flush();
    }
    private String promptUserForUserName(){
        String username = "";
        try{
            username = JOptionPane.showInputDialog("Username: ");
        }catch(Exception e){
            e.printStackTrace();
        }
        return username;
    }

    private void fillBoardsWithEmpty(){
        userBoard = new String[BOARD_SIZE][BOARD_SIZE];
        guessesBoard = new String[BOARD_SIZE][BOARD_SIZE];
        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                userBoard[row][col] = "Empty";
                guessesBoard[row][col] = "Empty";
            }
        }
    }

    public void placeShips(String shipStartingLocation, String shipOrientation, String shipName){
        char r = shipStartingLocation.charAt(0);
        char c = shipStartingLocation.charAt(1);
        int row = Integer.parseInt(String.valueOf(r));
        int col = Integer.parseInt(String.valueOf(c));
        int shipLength = Ship.getShipLength(shipName);
        if(shipOrientation.equals("vertical")){
            int count = 0;
            int actualRow = row;
            while(count < shipLength){
                userBoard[actualRow][col] = shipName;
                count++;
                actualRow++;
            }
        }else{
            int count = 0;
            int actualCol = row;
            while(count < shipLength){
                userBoard[row][actualCol] = shipName;
                count++;
                actualCol++;
            }
        }


    }
    private void disableAllComponents(){

    }

    public void shipHasBeenSunk(String sunkenShipName){

    }

    private void updateSunkenShipImage(){

    }
    public void sendGuess(String buttonLabel){
        char row = buttonLabel.charAt(0);
        char col = buttonLabel.charAt(1);
        int r =Integer.parseInt(String.valueOf(row));
        int c =Integer.parseInt(String.valueOf(col));
        //TODO messageHandler.sendMessage(MessageFactory.sendGuessMessage(row, col));
    }
    public void isEndOfGame(){

    }

    public void determineWinner(){

    }

    public void boardIsSetAndReadyToBeSentToServer(){

    }

}
