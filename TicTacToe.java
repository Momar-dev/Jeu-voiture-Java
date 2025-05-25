import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.filechooser.*;
import javax.swing.ImageIcon;

public class TicTacToe {

    int bordWidth = 600;
    int bordHeight = 700;

    JFrame frame = new JFrame("Jeu de Déplacement");
    JLabel textLabel = new JLabel();
    JLabel objectifLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    
    // Boutons directionnels
    JButton upButton = new JButton("↑");
    JButton downButton = new JButton("↓");
    JButton leftButton = new JButton("←");
    JButton rightButton = new JButton("→");
    JButton newGameButton = new JButton("Nouvelle Partie");

    JButton[][] board = new JButton[3][3];
    
    ImageIcon personneIcon;
    ImageIcon homeIcon;
    ImageIcon arbreIcon;
    ImageIcon bananeIcon;
    
 
    int personneRow = 1;
    int personneCol = 1;
    
   
    String currentObjectif = "";
 
    Random random = new Random();
    

    boolean gameWon = false;

    TicTacToe() {
   
        try {
            personneIcon = new ImageIcon("personne.png");
            homeIcon = new ImageIcon("home.png");
            arbreIcon = new ImageIcon("arbre.png"); 
            bananeIcon = new ImageIcon("banane.png");
            
           
            personneIcon = resizeIcon(personneIcon, 80, 80);
            homeIcon = resizeIcon(homeIcon, 80, 80);
            arbreIcon = resizeIcon(arbreIcon, 80, 80);
            bananeIcon = resizeIcon(bananeIcon, 80, 80);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des images: " + e.getMessage());
        }
        
        frame.setVisible(true);
        frame.setSize(bordWidth, bordHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

       
        textPanel.setLayout(new GridLayout(2, 1));
        
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Jeu de Déplacement");
        textLabel.setOpaque(true);
        
        objectifLabel.setBackground(Color.darkGray);
        objectifLabel.setForeground(Color.yellow);
        objectifLabel.setFont(new Font("Arial", Font.BOLD, 24));
        objectifLabel.setHorizontalAlignment(JLabel.CENTER);
        objectifLabel.setOpaque(true);
        
        textPanel.add(textLabel);
        textPanel.add(objectifLabel);
        frame.add(textPanel, BorderLayout.NORTH);

       
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);

    
        initializeBoard();
        
       
        controlPanel.setLayout(new GridLayout(2, 3));
        

        configureDirectionalButton(upButton);
        configureDirectionalButton(downButton);
        configureDirectionalButton(leftButton);
        configureDirectionalButton(rightButton);
        
  
        controlPanel.add(new JLabel());
        controlPanel.add(upButton);
        controlPanel.add(new JLabel());
        controlPanel.add(leftButton);
        controlPanel.add(downButton);
        controlPanel.add(rightButton);
        
       
        newGameButton.addActionListener(e -> startNewGame());
        newGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(newGameButton, BorderLayout.SOUTH);
        
        frame.add(bottomPanel, BorderLayout.SOUTH);

        startNewGame();
    }
    
  
    private void configureDirectionalButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusable(false);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameWon) return;
                
                int newRow = personneRow;
                int newCol = personneCol;
                
                if (button == upButton && personneRow > 0) {
                    newRow--;
                } else if (button == downButton && personneRow < 2) {
                    newRow++;
                } else if (button == leftButton && personneCol > 0) {
                    newCol--;
                } else if (button == rightButton && personneCol < 2) {
                    newCol++;
                }
                
          
                if (newRow != personneRow || newCol != personneCol) {
                    movePersonne(newRow, newCol);
                    checkWin();
                }
            }
        });
    }
    

    void initializeBoard() {
      
        boardPanel.removeAll();
        
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                tile.setBackground(Color.white);
                tile.setFocusable(false);
            }
        }
        
     
        placeRandomDestinations();
        
   
        personneRow = 1;
        personneCol = 1;
        board[personneRow][personneCol].setIcon(personneIcon);
        
     
        boardPanel.revalidate();
        boardPanel.repaint();
    }
    
   
    void placeRandomDestinations() {
   
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setIcon(null);
                board[r][c].setName("");
            }
        }
        
    
        int homeRow, homeCol;
        do {
            homeRow = random.nextInt(3);
            homeCol = random.nextInt(3);
        } while (homeRow == 1 && homeCol == 1);
        
        board[homeRow][homeCol].setIcon(homeIcon);
        board[homeRow][homeCol].setName("home");
    
        int arbreRow, arbreCol;
        do {
            arbreRow = random.nextInt(3);
            arbreCol = random.nextInt(3);
        } while ((arbreRow == homeRow && arbreCol == homeCol) || 
                 (arbreRow == 1 && arbreCol == 1));
        
        board[arbreRow][arbreCol].setIcon(arbreIcon);
        board[arbreRow][arbreCol].setName("arbre");
        
      
        int bananeRow, bananeCol;
        do {
            bananeRow = random.nextInt(3);
            bananeCol = random.nextInt(3);
        } while ((bananeRow == homeRow && bananeCol == homeCol) || 
                 (bananeRow == arbreRow && bananeCol == arbreCol) || 
                 (bananeRow == 1 && bananeCol == 1));
        
        board[bananeRow][bananeCol].setIcon(bananeIcon);
        board[bananeRow][bananeCol].setName("banane");
    }
    
    void startNewGame() {
        gameWon = false;
        
       
        initializeBoard();
   
        int objectif = random.nextInt(3);
        if (objectif == 0) {
            currentObjectif = "home";
            objectifLabel.setText("Objectif: Allez à la maison!");
        } else if (objectif == 1) {
            currentObjectif = "arbre";
            objectifLabel.setText("Objectif: Allez à l'arbre!");
        } else {
            currentObjectif = "banane";
            objectifLabel.setText("Objectif: Allez à la banane!");
        }
        
        textLabel.setText("Utilisez les flèches pour vous déplacer");
    }
    
    void movePersonne(int newRow, int newCol) {
  
        String destinationName = board[newRow][newCol].getName();
        ImageIcon destinationIcon = (ImageIcon) board[newRow][newCol].getIcon();
        
    
        board[personneRow][personneCol].setIcon(null);
        
        board[newRow][newCol].setIcon(personneIcon);
        
   
        personneRow = newRow;
        personneCol = newCol;
    }
    
  
    void checkWin() {
      
        String currentPosition = board[personneRow][personneCol].getName();
        
        if (currentPosition.equals(currentObjectif)) {
            gameWon = true;
            textLabel.setText("BRAVO ! Vous avez gagné!");
            objectifLabel.setText("Appuyez sur 'Nouvelle Partie' pour recommencer");
        }
    }
    
 
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    
    public static void main(String[] args) {
        new TicTacToe();
    }
}