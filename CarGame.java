import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CarGame {

    int boardWidth = 1000;
    int boardHeight = 700; 

    JFrame frame = new JFrame("Course à la Maison ");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel(); 
    
   
    JButton upButton = new JButton("▲");
    JButton downButton = new JButton("▼");
    JButton leftButton = new JButton("◄");
    JButton rightButton = new JButton("►");
    JButton resetButton = new JButton("Recommencer");

    JButton[][] board = new JButton[3][3];
    String car = "AUTO";
    String house = "MAISON";
    String obstacle = "X";
    String empty = "";
    
   
    ImageIcon carIcon = null;
    ImageIcon houseIcon = null;
    
    int carPosition = -1; 
    int housePosition = -1; 
    

    int carRow = -1;
    int carCol = -1;
    
  
    boolean[][] obstacles = new boolean[3][3];
    
    boolean gameOver = false;
    int moves = 0;
    int maxMoves = 3;
    

    int numObstacles = 3; 

    CarGame() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 18));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Conduisez la voiture à la maison en évitant les obstacles !");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);
     
        setupControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);
        
      
        try {
            carIcon = new ImageIcon(ImageIO.read(new File("car.png")));
            houseIcon = new ImageIcon(ImageIO.read(new File("home.png")));
            
  
            carIcon = resizeIcon(carIcon, 80, 80);
            houseIcon = resizeIcon(houseIcon, 80, 80);
            
            System.out.println("Images chargées avec succès!");
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des images: " + e.getMessage());
            System.out.println("Utilisation du texte à la place");
        }

        setupBoard();
        resetGame(); 
    }

   
    void setupControlPanel() {
        controlPanel.setLayout(new GridLayout(3, 3));
        controlPanel.setBackground(Color.lightGray);
        controlPanel.setPreferredSize(new Dimension(boardWidth, 150));
        

        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setBackground(Color.lightGray);
        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setBackground(Color.lightGray);
        JPanel emptyPanel3 = new JPanel();
        emptyPanel3.setBackground(Color.lightGray);
        JPanel emptyPanel4 = new JPanel();
        emptyPanel4.setBackground(Color.lightGray);
        
        upButton.setFont(new Font("Arial", Font.BOLD, 20));
        downButton.setFont(new Font("Arial", Font.BOLD, 20));
        leftButton.setFont(new Font("Arial", Font.BOLD, 20));
        rightButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(new Color(255, 200, 0));
        resetButton.setForeground(Color.black);
        

        upButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && carRow > 0) {
                 
                    if (!obstacles[carRow-1][carCol]) {
                        moveCarDirection(-1, 0); 
                    }
                }
            }
        });
        
        downButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && carRow < 2) {
                  
                    if (!obstacles[carRow+1][carCol]) {
                        moveCarDirection(1, 0);
                    }
                }
            }
        });
        
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && carCol > 0) {
                  
                    if (!obstacles[carRow][carCol-1]) {
                        moveCarDirection(0, -1); 
                    }
                }
            }
        });
        
        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && carCol < 2) {
                
                    if (!obstacles[carRow][carCol+1]) {
                        moveCarDirection(0, 1);
                    }
                }
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        
 
        controlPanel.add(emptyPanel1);
        controlPanel.add(upButton);
        controlPanel.add(emptyPanel2);
        
      
        controlPanel.add(leftButton);
        controlPanel.add(downButton);
        controlPanel.add(rightButton);
       
        controlPanel.add(emptyPanel3);
        controlPanel.add(resetButton);
        controlPanel.add(emptyPanel4);
    }
    
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    
    void setupBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                tile.setBackground(new Color(50, 150, 50)); 
                tile.setForeground(Color.black);
                tile.setFont(new Font("Arial", Font.BOLD, 16)); 
                tile.setFocusable(false);
                final String coords = "(" + r + "," + c + ")";
                tile.setText(coords);
            }
        }
    }
    
    void resetGame() {
        gameOver = false;
        moves = 0;
        

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setIcon(null);
                board[r][c].setText("(" + r + "," + c + ")");
                board[r][c].setFont(new Font("Arial", Font.BOLD, 16));
                board[r][c].setBackground(new Color(50, 150, 50));
                board[r][c].setForeground(Color.black);
                obstacles[r][c] = false; 
            }
        }
        
 
        upButton.setEnabled(true);
        downButton.setEnabled(true);
        leftButton.setEnabled(true);
        rightButton.setEnabled(true);

        placeObstacles();
        placeCarAndHouse();
        updateButtonStatus(); 
    }
    
    void placeObstacles() {
        Random random = new Random();
        int obstaclesPlaced = 0;
        
        while (obstaclesPlaced < numObstacles) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);
   
            if (obstacles[row][col]) {
                continue;
            }
            
            obstacles[row][col] = true;
            board[row][col].setText("X");
            board[row][col].setFont(new Font("Arial", Font.BOLD, 30));
            board[row][col].setForeground(Color.red);
            
            obstaclesPlaced++;
        }
    }
    
    void placeCarAndHouse() {
        Random random = new Random();
        
    
        do {
            carRow = random.nextInt(3);
            carCol = random.nextInt(3);
            carPosition = carRow * 3 + carCol;
        } while (obstacles[carRow][carCol]);
        
      
        if (carIcon != null) {
            board[carRow][carCol].setText("");
            board[carRow][carCol].setIcon(carIcon);
        } else {
            board[carRow][carCol].setText(car);
            board[carRow][carCol].setFont(new Font("Arial", Font.BOLD, 20));
        }
        
      
        int houseRow, houseCol;
        do {
            houseRow = random.nextInt(3);
            houseCol = random.nextInt(3);
            housePosition = houseRow * 3 + houseCol;
        } while (housePosition == carPosition || obstacles[houseRow][houseCol]);
        
      
        if (houseIcon != null) {
            board[houseRow][houseCol].setText("");
            board[houseRow][houseCol].setIcon(houseIcon);
        } else {
            board[houseRow][houseCol].setText(house);
            board[houseRow][houseCol].setFont(new Font("Arial", Font.BOLD, 20));
        }
        
 
        updateMovesText();
    }
    
    void updateMovesText() {
        textLabel.setText("Évitez les obstacles X ! Mouvements: " + (maxMoves - moves));
    }
    
 
    void updateButtonStatus() {
        upButton.setEnabled(carRow > 0 && !obstacles[carRow-1][carCol]);
        downButton.setEnabled(carRow < 2 && !obstacles[carRow+1][carCol]);
        leftButton.setEnabled(carCol > 0 && !obstacles[carRow][carCol-1]);
        rightButton.setEnabled(carCol < 2 && !obstacles[carRow][carCol+1]);
    }
    
    void moveCarDirection(int rowChange, int colChange) {
     
        int newRow = carRow + rowChange;
        int newCol = carCol + colChange;
        
  
        if (newRow < 0 || newRow >= 3 || newCol < 0 || newCol >= 3 || obstacles[newRow][newCol]) {
            return; 
        }
        
        board[carRow][carCol].setIcon(null);
        board[carRow][carCol].setText("(" + carRow + "," + carCol + ")");
        board[carRow][carCol].setFont(new Font("Arial", Font.BOLD, 16));

        carRow = newRow;
        carCol = newCol;
        carPosition = carRow * 3 + carCol;

        boolean isHouse = carPosition == housePosition;
        
    
        if (!isHouse) {
            if (carIcon != null) {
                board[carRow][carCol].setText("");
                board[carRow][carCol].setIcon(carIcon);
            } else {
                board[carRow][carCol].setText(car);
                board[carRow][carCol].setFont(new Font("Arial", Font.BOLD, 20));
            }
        }
        
       
        moves++;
        updateMovesText();
        updateButtonStatus();
        checkGameStatus();
    }
    
    void checkGameStatus() {

        if (carPosition == housePosition) {
            gameWon();
            return;
        }
        if (moves >= maxMoves) {
            gameLost();
        }
    }
    
    void gameWon() {
        textLabel.setText("Félicitations ! Vous avez atteint la maison !");
        gameOver = true;
        
      
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
     
        board[carRow][carCol].setBackground(Color.green);
        board[carRow][carCol].setForeground(Color.blue);
   
        board[carRow][carCol].setIcon(null);
        board[carRow][carCol].setText("GAGNÉ!");
        board[carRow][carCol].setFont(new Font("Arial", Font.BOLD, 24));
    }
    
    void gameLost() {
        textLabel.setText("Perdu ! Vous avez épuisé tous vos mouvements!");
        gameOver = true;
        
    
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (!obstacles[r][c]) { 
                    board[r][c].setBackground(new Color(200, 50, 50));
                }
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CarGame();
            }
        });
    }
}