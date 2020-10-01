import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

class Solution {
    static PixelGenerator pg = new PixelGenerator();
    //static boolean allZeros;

    public static void toGif(File[] images)throws IOException{
        BufferedImage first = ImageIO.read(images[0]);

        ImageOutputStream output = new FileImageOutputStream(new File("tilesGIF.gif"));

        GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 250, true);
        writer.writeToSequence(first);

        for (File image : images) {
            BufferedImage next = ImageIO.read(image);
            writer.writeToSequence(next);
        }

        writer.close();
        output.close();
    }
    public static void main(String[] args) throws IOException {
        int[][] board = new int[12][12];
        File[] images = new File[100];
        int count = 0;
        //allZeros = false;

        board = generateBoard(board);
        images[count] = new File(pg.createImage(board, count));
        count++;

        while(count < 100) { //@TODO: make it check 0s
            System.out.println("Tick Tock, Tick Tock");
      /*
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }*/
            board = lifeCycle(board);
            images[count] = new File(pg.createImage(board, count));
            count++;
        }

        toGif(images);


        for(int i = 0; i<100; i++){
            File file = new File("tile"+i+".png");

            if(file.delete())
            {
                System.out.println("File deleted successfully");
            }
            else
            {
                System.out.println("Failed to delete the file");
            }
        }
    }

    static int[][] generateBoard(int[][] board) {
        Random rand = new Random();
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 12; c++) {
                board[r][c] = rand.nextInt(2);
            }
            System.out.println(Arrays.toString(board[r]));
        }
        return board;
    }


    static int[][] lifeCycle(int[][] board) {
        int[][] newBoard = new int[12][12];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {

                boolean isAlive = false;
                if (board[r][c] == 1) {
                    isAlive = true;
                }

                int aliveNeighbour = checkNeighbours(board, r, c, isAlive);

                //loneliness
                if (aliveNeighbour < 2 && isAlive) {
                    newBoard[r][c] = 0;
                }
                //overpop
                else if (aliveNeighbour > 3 && isAlive) {
                    newBoard[r][c] = 0;
                }
                //birth
                else if (aliveNeighbour == 3 && !isAlive) {
                    newBoard[r][c] = 1;
                }
                else {
                    newBoard[r][c] = board[r][c];
                }
            }
        }

        for (int[] row : newBoard) System.out.println(Arrays.toString(row));

        return newBoard;
    }

    static int checkNeighbours(int[][] board, int r, int c, boolean isAlive) {
        int aliveNeighbour = 0;
        //check neighbors
        for (int x = -1; x < 2; x++) {
            if (r == 0 && x == -1) {continue;}
            else if (r == 11 && x == 1) {continue;}

            for (int y = -1; y < 2; y++) {
                if (c == 0 && y == -1) {continue;}
                else if (c == 11 && y == 1) {continue;}

                aliveNeighbour += board[r + x][c + y];
            }
        }

        if (isAlive) {aliveNeighbour--;}

        return aliveNeighbour;
    }

}