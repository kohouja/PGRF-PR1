package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PixelTest {

    private JFrame window;
    private BufferedImage img;
    private JPanel panel;

    public PixelTest(){
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(800,600);

        panel = new JPanel();
        window.add(panel);

        img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        window.setVisible(true);
        drawPixel(100,50, Color.GREEN.getRGB());
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                drawPixel(100, 50, Color.GREEN.getRGB());
            }
        });
    }


    private void drawPixel(int x, int y, int color){
            img.setRGB(x,y,color);
            panel.getGraphics().drawImage(img, 0,0,null);

    }

    public static void main(String... args){
        new PixelTest();
    }
}
