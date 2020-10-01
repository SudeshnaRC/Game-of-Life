import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PixelGenerator{
    public void PixelGenerator() {

    }

    static int width = 240;
    static int height = 240;

    public String createImage(int[][] board,int count){

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        //File f = null;


        for(int r = 0; r < 12; r++){
            for(int c= 0; c < 12; c++){
                if(board[r][c] == 1){
                    g.setColor(Color.BLUE);
                    g.drawRect(r*20, c*20, 20, 20);
                    g.fillRect(r*20, c*20, 20, 20);
                    //img.setRGB(r*20, c*20, 20, 20, b);
                }
                else{
                    g.setColor(Color.WHITE);
                    g.drawRect(r*20, c*20, 20, 20);
                    g.fillRect(r*20, c*20, 20, 20);
                    //img.setRGB(r*20, c*20, 20, 20, white);
                }
            }
        }




        try{
            File f = new File("tile"+count+".png");
            //f.open();
            ImageIO.write(img, "png", f);
            //f.close();
        }catch(IOException e){
            System.out.println("Error: " + e);
        }

        return "tile"+count+".png";
    }

}//class ends here
