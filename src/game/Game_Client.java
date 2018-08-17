package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;
import javax.swing.ButtonGroup;

public class Game_Client extends JFrame{

    private GameHandler gameHandler;
    //general elements
    private int BOARD_SIZE = 10;
    private JComboBox shipSelection;
    private JRadioButton shipVerticalBox;
    private JRadioButton shipHorizontalBox;
    private JButton startNewGame;

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

    //main menu elements
    private JPanel mainPanel;

    //game board grid elements
    private JPanel gameBoardsPanel;
    private JPanel guessesGrid;
    private JPanel userGrid;
    private JButton[][] squares = new JButton[BOARD_SIZE][BOARD_SIZE];

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
                    gameHandler.requestNewGame();
                    disableStartNewGameButton();
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        });

        //place ships combo box options
        String[] ships = {"Choose Ship to Place: ", "2 - Destroyer", "3 - Cruiser", "3 - Submarine", "4 - Battleship", "5 - Carrier"};
        shipSelection = new JComboBox(ships);
        controlOptionsPanel.add(shipSelection);

        //ship alignment horizontal and vertical options
        JLabel shipAlignmentLabel = new JLabel("Place ship horizontally or vertically: ");
        ButtonGroup group = new ButtonGroup();
        shipHorizontalBox = new JRadioButton("Horizontally ");
        shipHorizontalBox.setSelected(true);
        shipVerticalBox = new JRadioButton("Vertically ");
        group.add(shipHorizontalBox);
        group.add(shipVerticalBox);

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

    private void disableStartNewGameButton(){
        startNewGame.setEnabled(false);
    }

    private void getLocationText(ActionEvent event){
        Object o = event.getSource();
        JButton b = null;
        String buttonText = "";
        String shipName = getShipName();
        String shipOrientation = getShipOrientation();

        if(o instanceof JButton)
            b = (JButton)o;

        if(b != null)
            buttonText = b.getText();
        gameHandler.placeShips(buttonText, shipOrientation, shipName);
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

    private void enableAllGameComponents(){

    }
    private void enableTheGuessesBoard(){

    }

    private void disableAllGameComponents(){

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
                squares[row][col] = new JButton();
                squares[row][col].setBackground(Color.GRAY);
                squares[row][col].setText("" + row + col);
                squares[row][col].setPreferredSize(new Dimension(30, 30));
                squares[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        getText(event);
                    }
                });
                gbc.gridx = row;
                gbc.gridy = col;
                guessesGrid.add(squares[row][col], gbc);

            }
        }

        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                squares[row][col] = new JButton();
                squares[row][col].setBackground(Color.GRAY);
                squares[row][col].setText("" + row + col);
                squares[row][col].setPreferredSize(new Dimension(30, 30));
                squares[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        getLocationText(event);
                    }
                });
                gbc.gridx = row + 15;
                gbc.gridy = col;
                userGrid.add(squares[row][col], gbc);
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
