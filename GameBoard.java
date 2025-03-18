import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;

public class GameBoard extends JFrame {
    public int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE];
    private String[][] piecesArray;

    // MergeSort and merge methods remain the same as provided

    public GameBoard() {
        setTitle("Poke Board");
        setSize(750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new BackgroundPanel("bg1.jpg"));
        setLayout(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new JPanel();
                squares[row][col].setOpaque(false);
                add(squares[row][col]);
            }
        }

        piecesArray = new String[32][4];
        loadPieces();

        populateBoard();

        // Add mouse listener to sort by HP on click
        getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mergeSort(piecesArray, 0, piecesArray.length - 1);
                // Update positions to reflect new order
                for (int i = 0; i < piecesArray.length; i++) {
                    piecesArray[i][2] = String.valueOf(i);
                }
                clearBoard();
                populateBoard();
            }
        });
    }

    private void clearBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col].removeAll();
                squares[row][col].revalidate();
                squares[row][col].repaint();
            }
        }
    }

    private void populateBoard() {
        int pieceRow = 0;
        int squareName = 0;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (pieceRow >= piecesArray.length) break;

                int pokePosition = Integer.parseInt(piecesArray[pieceRow][2]);

                if (squareName == pokePosition) {
                    String imagePath = piecesArray[pieceRow][0];
                    String hpText = piecesArray[pieceRow][1];

                    ImageIcon icon = new ImageIcon(imagePath);
                    Image scaledImage = icon.getImage().getScaledInstance(40, 42, Image.SCALE_SMOOTH);

                    JLabel pieceLabel = new JLabel(new ImageIcon(scaledImage));
                    JLabel textLabel = new JLabel(hpText, SwingConstants.CENTER);
                    textLabel.setForeground(Color.BLACK);
                    System.out.println("Placed 1 piece: "+hpText);
                    JPanel piecePanel = new JPanel(new BorderLayout());
                    piecePanel.setOpaque(false);
                    piecePanel.add(pieceLabel, BorderLayout.CENTER);
                    piecePanel.add(textLabel, BorderLayout.SOUTH);

                    squares[row][col].setLayout(new BorderLayout());
                    squares[row][col].add(piecePanel, BorderLayout.CENTER);

                    pieceRow++;
                }
                squareName++;
            }
        }
        System.out.println();

        revalidate();
        repaint();
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
    
        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void mergeSort(String[][] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(String[][] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        String[][] leftArray = new String[n1][4];
        String[][] rightArray = new String[n2][4];
    
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }
    
        int i = 0, j = 0, k = left;
    
        while (i < n1 && j < n2) {
            if (Integer.parseInt(leftArray[i][3]) <= Integer.parseInt(rightArray[j][3])) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }
    
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
    
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private void loadPieces() {
        piecesArray[0][0] = "abra.png"; piecesArray[0][1] = "Abra"; piecesArray[0][2]="0"; piecesArray[0][3]="63";
        piecesArray[1][0] = "bulbasaur.png"; piecesArray[1][1] = "Bulbasaur"; piecesArray[1][2]="1"; piecesArray[1][3]="1";
        piecesArray[2][0] = "charmander.png"; piecesArray[2][1] = "Charmander"; piecesArray[2][2]="2"; piecesArray[2][3]="4";
        piecesArray[3][0] = "clefairy.png"; piecesArray[3][1] = "Clefairy"; piecesArray[3][2]="3"; piecesArray[3][3]="35";
        piecesArray[4][0] = "cubone.png"; piecesArray[4][1] = "Cubone"; piecesArray[4][2]="4"; piecesArray[4][3]="104";
        piecesArray[5][0] = "doduo.png"; piecesArray[5][1] = "Doduo"; piecesArray[5][2]="5"; piecesArray[5][3]="84";
        piecesArray[6][0] = "eevee.png"; piecesArray[6][1] = "Eevee"; piecesArray[6][2]="6"; piecesArray[6][3]="133";
        piecesArray[7][0] = "espeon.png"; piecesArray[7][1] = "Espeon"; piecesArray[7][2]="7"; piecesArray[7][3]="196";
        piecesArray[8][0] = "exeggcute.png"; piecesArray[8][1] = "Exeggcute"; piecesArray[8][2]="8"; piecesArray[8][3]="102";
        piecesArray[9][0] = "exeggcutor.png"; piecesArray[9][1] = "Exeggcutor"; piecesArray[9][2]="9"; piecesArray[9][3]="103";
        piecesArray[10][0] = "farfetchd.png"; piecesArray[10][1] = "Farfetch'd"; piecesArray[10][2]="10"; piecesArray[10][3]="83";
        piecesArray[11][0] = "flareon.png"; piecesArray[11][1] = "Flareon"; piecesArray[11][2]="11"; piecesArray[11][3]="136";
        piecesArray[12][0] = "gengar.png"; piecesArray[12][1] = "Gengar"; piecesArray[12][2]="12"; piecesArray[12][3]="94";
        piecesArray[13][0] = "geodude.png"; piecesArray[13][1] = "Geodude"; piecesArray[13][2]="13"; piecesArray[13][3]="74";
        piecesArray[14][0] = "hitmonchan.png"; piecesArray[14][1] = "Hitmonchan"; piecesArray[14][2]="14"; piecesArray[14][3]="107";
        piecesArray[15][0] = "hypno.png"; piecesArray[15][1] = "Hypno"; piecesArray[15][2]="15"; piecesArray[15][3]="97";
        piecesArray[16][0] = "jigglypuff.png"; piecesArray[16][1] = "Jigglypuff"; piecesArray[16][2]="16"; piecesArray[16][3]="39";
        piecesArray[17][0] = "jolteon.png"; piecesArray[17][1] = "Jolteon"; piecesArray[17][2]="17"; piecesArray[17][3]="135";
        piecesArray[18][0] = "lickitung.png"; piecesArray[18][1] = "Lickitung"; piecesArray[18][2]="18"; piecesArray[18][3]="108";
        piecesArray[19][0] = "machamp.png"; piecesArray[19][1] = "Machamp"; piecesArray[19][2]="19"; piecesArray[19][3]="68";
        piecesArray[20][0] = "magneton.png"; piecesArray[20][1] = "Magneton"; piecesArray[20][2]="20"; piecesArray[20][3]="82";
        piecesArray[21][0] = "meowth.png"; piecesArray[21][1] = "Meowth"; piecesArray[21][2]="21"; piecesArray[21][3]="52";
        piecesArray[22][0] = "onix.png"; piecesArray[22][1] = "Onix"; piecesArray[22][2]="22"; piecesArray[22][3]="95";
        piecesArray[23][0] = "pidgey.png"; piecesArray[23][1] = "Pidgey"; piecesArray[23][2]="23"; piecesArray[23][3]="16";
        piecesArray[24][0] = "pikachu.png"; piecesArray[24][1] = "Pikachu"; piecesArray[24][2]="24"; piecesArray[24][3]="25";
        piecesArray[25][0] = "ponyta.png"; piecesArray[25][1] = "Ponyta"; piecesArray[25][2]="25"; piecesArray[25][3]="77";
        piecesArray[26][0] = "psyduck.png"; piecesArray[26][1] = "Psyduck"; piecesArray[26][2]="26"; piecesArray[26][3]="54";
        piecesArray[27][0] = "slowbro.png"; piecesArray[27][1] = "Slowbro"; piecesArray[27][2]="27"; piecesArray[27][3]="80";
        piecesArray[28][0] = "slowpoke.png"; piecesArray[28][1] = "Slowpoke"; piecesArray[28][2]="28"; piecesArray[28][3]="79";
        piecesArray[29][0] = "squirtle.png"; piecesArray[29][1] = "Squirtle"; piecesArray[29][2]="29"; piecesArray[29][3]="7";
        piecesArray[30][0] = "vaporeon.png"; piecesArray[30][1] = "Vaporeon"; piecesArray[30][2]="30"; piecesArray[30][3]="134";
        piecesArray[31][0] = "weepinbell.png"; piecesArray[31][1] = "Weepinbell"; piecesArray[31][2]="31"; piecesArray[31][3]="70";
       
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }
}