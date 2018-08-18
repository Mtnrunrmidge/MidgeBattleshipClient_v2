package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.util.*;
import javax.swing.ButtonGroup;

public class Game_Client extends JFrame{

    private GameHandler gameHandler;
    //general elements
    private int BOARD_SIZE = 10;
    private JComboBox shipSelection;
    private JRadioButton shipVerticalBox;
    private JRadioButton shipHorizontalBox;
    private JButton startNewGame;
    private JButton sendBoardToServerButton;
    private ArrayList<String> ships;

    private JLabel userbattleship;
    private JLabel usercarrier;
    private JLabel usercruiser;
    private JLabel usersubmarine;
    private JLabel userdestroyer;

    private JLabel oppbattleship;
    private JLabel oppcruiser;
    private JLabel oppcarrier;
    private JLabel oppsubmarine;
    private JLabel oppdestroyer;

    private int shipsToBePlacedCount;

    //main menu elements
    private JPanel mainPanel;

    //game board grid elements
    private JPanel gameBoardsPanel;
    private JPanel guessesGrid;
    private JPanel userGrid;
    private JButton[][] userSquares = new JButton[BOARD_SIZE][BOARD_SIZE];
    private JButton[][] guessesSquares = new JButton[BOARD_SIZE][BOARD_SIZE];

    public Game_Client(GameHandler gh){
        gameHandler = gh;
        init();
    }

    private void init(){
        setupFrame();
        setupMenu();
        addMainPanel();
        addGameControlPanel();
        addGameGrids();
        addShipsRemainingPanel();

        setVisible(true);
        pack();
        disableAllGamePlayControlComponents();
        disableTheGuessesBoard();
        disableThesendBoardButton();
        disableTheUserBoard();
    }

    private void addGameControlPanel(){
        JPanel controlOptionsPanel = new JPanel();
        controlOptionsPanel.setPreferredSize(new Dimension(250, 700));
        controlOptionsPanel.setBackground(Color.WHITE);

        //start button
        startNewGame = new JButton();
        startNewGame.setText("Start New Game");
        startNewGame.setPreferredSize(new Dimension(200, 50));
        controlOptionsPanel.add(startNewGame);
        startNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                try{
                    disableStartNewGameButton();
                    enableAllGamePlayControlComponents();
                    enableTheUserBoard();
                    shipsToBePlacedCount = 5;
                    gameHandler.requestNewGame();
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        });

        //place ships combo box options
        ships = new ArrayList<>();
        ships.add("Destroyer");
        ships.add("Cruiser");
        ships.add("Submarine");
        ships.add("Battleship");
        ships.add("Carrier");

        shipSelection = new JComboBox(ships.toArray());
        controlOptionsPanel.add(shipSelection);

        //ship alignment horizontal and vertical options
        JLabel shipAlignmentLabel = new JLabel("Place ship horizontally or vertically: ");
        ButtonGroup group = new ButtonGroup();
        shipHorizontalBox = new JRadioButton("Horizontally ");
        shipHorizontalBox.setSelected(true);
        shipVerticalBox = new JRadioButton("Vertically ");
        group.add(shipHorizontalBox);
        group.add(shipVerticalBox);

        sendBoardToServerButton = new JButton();
        sendBoardToServerButton.setText("Board Complete");
        sendBoardToServerButton.setPreferredSize(new Dimension(200, 50));
        controlOptionsPanel.add(sendBoardToServerButton);
        sendBoardToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                disableAllGamePlayControlComponents();
                disableThesendBoardButton();
                enableTheGuessesBoard();
                disableTheUserBoard();
                sendGameBoardToServer();
            }
        });

        controlOptionsPanel.add(shipAlignmentLabel);
        controlOptionsPanel.add(shipHorizontalBox);
        controlOptionsPanel.add(shipVerticalBox);

        mainPanel.add(controlOptionsPanel);
    }

    /**
    private void addGameStatusPanel(){
        statusPanel = new JPanel(new GridLayout(1,1));
        statusArea = new JLabel("The Label", SwingConstants.CENTER);
        statusArea.setText("Welcome! Let'splay Battleship");
        Font font = new Font("SansSerif", Font.BOLD, 24);
        statusArea.setFont(font);
        statusPanel.add(statusArea);
        mainPanel.add(statusPanel, BorderLayout.NORTH);
    }
**/
    private void addShipsRemainingPanel(){
        JPanel shipsRemainingPanel = new JPanel(new GridLayout(2, 1));
        shipsRemainingPanel.setPreferredSize(new Dimension(300, 700));

        JPanel opponentShipsPanel = new JPanel(new GridLayout(5, 1));
        opponentShipsPanel.setPreferredSize(new Dimension(200, 350));

        oppbattleship = new JLabel();
        oppcarrier = new JLabel();
        oppcruiser = new JLabel();
        oppsubmarine = new JLabel();
        oppdestroyer= new JLabel();

        oppbattleship.setHorizontalAlignment(SwingConstants.CENTER);
        oppcruiser.setHorizontalAlignment(SwingConstants.CENTER);
        oppcarrier.setHorizontalAlignment(SwingConstants.CENTER);
        oppsubmarine.setHorizontalAlignment(SwingConstants.CENTER);
        oppdestroyer.setHorizontalAlignment(SwingConstants.CENTER);

        oppbattleship.setIcon(new ImageIcon(getClass().getResource("/game/images/battleship.png")));
        oppcarrier.setIcon(new ImageIcon(getClass().getResource("/game/images/carrier.png")));
        oppcruiser.setIcon(new ImageIcon(getClass().getResource("/game/images/cruiser.png")));
        oppsubmarine.setIcon(new ImageIcon(getClass().getResource("/game/images/submarine.png")));
        oppdestroyer.setIcon(new ImageIcon(getClass().getResource("/game/images/destroyer.png")));

        oppbattleship.setPreferredSize(new Dimension(150, 50));
        oppsubmarine.setPreferredSize(new Dimension(150, 50));
        oppdestroyer.setPreferredSize(new Dimension(150, 50));
        oppcarrier.setPreferredSize(new Dimension(150, 50));
        oppcruiser.setPreferredSize(new Dimension(150, 50));

        oppcruiser.setVisible(true);

        opponentShipsPanel.add(oppcarrier);
        opponentShipsPanel.add(oppbattleship);
        opponentShipsPanel.add(oppsubmarine);
        opponentShipsPanel.add(oppcruiser);
        opponentShipsPanel.add(oppdestroyer);


        JPanel userShipsPanel = new JPanel(new GridLayout(5, 1));
        userShipsPanel.setPreferredSize(new Dimension(200, 350));
        userbattleship = new JLabel();
        usercarrier = new JLabel();
        usercruiser = new JLabel();
        usersubmarine = new JLabel();
        userdestroyer= new JLabel();

        userbattleship.setHorizontalAlignment(SwingConstants.CENTER);
        usercarrier.setHorizontalAlignment(SwingConstants.CENTER);
        userdestroyer.setHorizontalAlignment(SwingConstants.CENTER);
        usercruiser.setHorizontalAlignment(SwingConstants.CENTER);
        usersubmarine.setHorizontalAlignment(SwingConstants.CENTER);

        userbattleship.setIcon(new ImageIcon(getClass().getResource("/game/images/battleship.png")));
        usercarrier.setIcon(new ImageIcon(getClass().getResource("/game/images/carrier.png")));
        usercruiser.setIcon(new ImageIcon(getClass().getResource("/game/images/cruiser.png")));
        usersubmarine.setIcon(new ImageIcon(getClass().getResource("/game/images/submarine.png")));
        userdestroyer.setIcon(new ImageIcon(getClass().getResource("/game/images/destroyer.png")));

        userbattleship.setPreferredSize(new Dimension(50, 25));
        usersubmarine.setPreferredSize(new Dimension(50, 25));
        userdestroyer.setPreferredSize(new Dimension(50, 25));
        usercarrier.setPreferredSize(new Dimension(50, 25));
        usercruiser.setPreferredSize(new Dimension(50, 25));

        userShipsPanel.add(usercarrier);
        userShipsPanel.add(userbattleship);
        userShipsPanel.add(usersubmarine);
        userShipsPanel.add(usercruiser);
        userShipsPanel.add(userdestroyer);

        opponentShipsPanel.setBackground(Color.RED);
        userShipsPanel.setBackground(Color.BLUE);

        opponentShipsPanel.repaint();
        shipsRemainingPanel.add(opponentShipsPanel);
        shipsRemainingPanel.add(userShipsPanel);
        mainPanel.add(shipsRemainingPanel);
    }

    private void removeShipWhenPlacedFromCombobox(){
        if(shipSelection.getSelectedIndex() > -1){
            ships.remove(shipSelection.getSelectedIndex());
        }
    }

    private void noMoreShipsToPlace(){
        disableAllGamePlayControlComponents();
        enableTheSendBoardButton();
        disableTheUserBoard();
    }

    private void getText(ActionEvent event){
        Object o = event.getSource();
        JButton b = null;
        String buttonText = "";

        if(o instanceof JButton)
            b = (JButton)o;

        if(b != null)
            buttonText = b.getText();
        gameHandler.sendGuess(buttonText);
    }

    private void getLocationText(ActionEvent event){
        Object o = event.getSource();
        JButton b = null;
        String buttonText = "";
        String shipName = getShipName();
        String shipOrientation = getShipOrientation();

        if(o instanceof JButton) {
            b = (JButton) o;
        }

        if(!(b.getText().equals("Destroyer") || b.getText().equals("Cruiser") || b.getText().equals("Carrier") || b.getText().equals("Submarine") || b.getText().equals("Battleship"))) {
            buttonText = b.getText();
            placeShips(buttonText, shipOrientation, shipName);
        }
    }

    public void placeShips(String shipStartingLocation, String shipOrientation, String shipName){
        char r = shipStartingLocation.charAt(1);
        char c = shipStartingLocation.charAt(0);
        int row = Integer.parseInt(String.valueOf(r));
        int col = Integer.parseInt(String.valueOf(c));
        int shipLength = Ship.getShipLength(shipName);
        if(shipOrientation.equals("vertical")){
            int count = 0;
            int actualRow = row;
            while(count < shipLength){
                userSquares[actualRow][col].setText(shipName);
                userSquares[actualRow][col].setForeground(Color.BLACK);
                count++;
                actualRow++;
            }
        }else{
            int count = 0;
            int actualCol = col;
            while(count < shipLength){
                userSquares[row][actualCol].setText(shipName);
                userSquares[row][actualCol].setForeground(Color.BLACK);
                count++;
                actualCol++;
            }
        }
        decrementTheShipPlacedCount();

    }

    private String getShipName(){
        return shipSelection.getSelectedItem().toString();
    }
    private String getShipOrientation(){
        if (shipHorizontalBox.isSelected()) {
            return "horizontal";
        }else{
            return "vertical";
        }
    }

    private void enableStartNewGameButton(){
        startNewGame.setEnabled(true);
    }
    private void disableStartNewGameButton(){
        startNewGame.setEnabled(false);
    }

    private void enableAllGamePlayControlComponents(){
        shipSelection.setEnabled(true);
        shipVerticalBox.setEnabled(true);
        shipHorizontalBox.setEnabled(true);
    }

    private void disableAllGamePlayControlComponents(){
        shipSelection.setEnabled(false);
        shipVerticalBox.setEnabled(false);
        shipHorizontalBox.setEnabled(false);
    }

    private void enableTheSendBoardButton(){
        sendBoardToServerButton.setEnabled(true);
    }
    private void disableThesendBoardButton(){
        sendBoardToServerButton.setEnabled(false);
    }
    private void enableTheUserBoard(){
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                userSquares[col][row].setEnabled(true);
            }
        }

    }
    private void disableTheUserBoard(){
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                userSquares[col][row].setEnabled(false);
            }
        }
    }

    private void disableTheGuessesBoard(){
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                guessesSquares[col][row].setEnabled(false);
            }
        }

    }
    private void enableTheGuessesBoard(){
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                guessesSquares[col][row].setEnabled(true);
            }
        }

    }

    private void sendGameBoardToServer(){
        String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                board[col][row] = userSquares[col][row].getText();
            }
        }
        gameHandler.sendBoardToServerToBeginGame(board);
    }

    private void replaceShipImageWithSunkImage(String shipName, String userOrOpponent){
        if(shipName.toLowerCase().equals("destroyer")){
            if(userOrOpponent.equals("u")){
                userdestroyer.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_destroyer.png")));
            }else{
                oppdestroyer.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_destroyer.png")));
            }
        }else if(shipName.toLowerCase().equals("cruiser")){
            if(userOrOpponent.equals("u")){
                usercruiser.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_cruiser.png")));
            }else{
                oppcruiser.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_cruiser.png")));
            }
        }else if(shipName.toLowerCase().equals("carrier")){
            if(userOrOpponent.equals("u")){
                usercarrier.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_carrier.png")));
            }else{
                oppcarrier.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_carrier.png")));
            }
        }else if(shipName.toLowerCase().equals("submarine")){
            if(userOrOpponent.equals("u")){
                usersubmarine.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_submarine.png")));
            }else{
                oppsubmarine.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_submarine.png")));
            }
        }else{
            if(userOrOpponent.equals("u")){
                userbattleship.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_battleship.png")));
            }else{
                oppbattleship.setIcon(new ImageIcon(getClass().getResource("/game/images/sunk_battleship.png")));
            }
        }
    }

    private void addGameGrids(){
        GridBagConstraints gbc = new GridBagConstraints();
        guessesGrid = new JPanel(new GridBagLayout());
        userGrid = new JPanel(new GridBagLayout());

        guessesGrid.setBackground(Color.BLUE);
        userGrid.setBackground(Color.RED);

        //create and add board components
        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                guessesSquares[col][row] = new JButton();
                guessesSquares[col][row].setBackground(Color.GRAY);
                guessesSquares[col][row].setText("" + col + row);
                guessesSquares[col][row].setPreferredSize(new Dimension(30, 30));
                guessesSquares[col][row].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        getText(event);
                    }
                });
                gbc.gridx = row;
                gbc.gridy = col;
                guessesGrid.add(guessesSquares[col][row], gbc);

            }
        }

        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                userSquares[col][row] = new JButton();
                userSquares[col][row].setBackground(Color.GRAY);
                userSquares[col][row].setText("" + row + col);
                userSquares[col][row].setPreferredSize(new Dimension(30, 30));
                userSquares[col][row].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        getLocationText(event);
                        removeShipWhenPlacedFromCombobox();
                    }
                });
                gbc.gridx = row;
                gbc.gridy = col;
                userGrid.add(userSquares[col][row], gbc);
            }
        }

        guessesGrid.setPreferredSize(new Dimension(300, 320));
        userGrid.setPreferredSize(new Dimension(300, 320));
        gameBoardsPanel = new JPanel(new GridLayout(2, 1));
        gameBoardsPanel.setPreferredSize(new Dimension(350, 650));
        gameBoardsPanel.add(guessesGrid);
        gameBoardsPanel.add(userGrid);
        gameBoardsPanel.setVisible(true);
        mainPanel.add(gameBoardsPanel);
    }

    private void decrementTheShipPlacedCount(){
        shipsToBePlacedCount--;
        if(shipsToBePlacedCount == 0){
            noMoreShipsToPlace();
        }
    }
    private void addMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1190, 1190));
        mainPanel.setBackground(Color.BLACK);
        add(mainPanel);
        mainPanel.setVisible(true);
        setVisible(true);
        pack();
    }

    private void setupFrame(){
        setTitle("Battleship");
        setVisible(true);
        setPreferredSize(new Dimension(1200, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupMenu(){
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem gameInstructions = new JMenuItem("How To Play");

        file.add(newGame);
        file.add(quit);
        help.add(gameInstructions);
        menubar.add(file);
        menubar.add(help);
        setJMenuBar(menubar);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try{
                    gameHandler.requestNewGame();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        gameInstructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

            }
        });
    }

}
