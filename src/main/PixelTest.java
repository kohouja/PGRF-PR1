package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


public class PixelTest {

    private JFrame window;
    private BufferedImage img; // objekt pro zápis pixelů
    private Canvas canvas; // plátno pro vykreslení BufferedImage
    private Renderer renderer;
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    public PixelTest() {
        window = new JFrame();
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(800, 600); // velikost okna
        window.setLocationRelativeTo(null);// vycentrovat okno
        window.setTitle("PGRF1 cvičení"); // titulek okna
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                BufferedImage newImg = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                renderer.resize(newImg, size);
            }
        });
        // inicializace image, nastavení rozměrů (nastavení typu - pro nás nedůležité)
        img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        // inicializace plátna, do kterého budeme kreslit img
        canvas = new Canvas();

        window.add(canvas); // vložit plátno do okna
        window.setVisible(true); // zobrazit okno

        renderer = new Renderer(img, canvas);

//        renderer.d rawPixel(100, 50, Color.GREEN.getRGB());
        // 0x00ff00 == Color.GREEN.getRGB()
        // renderer.drawLine(0, 1, 8, 4, 0xffff00);
        Polygon polygon = new Polygon();
//        canvas.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
////                renderer.drawPixel(e.getX(), e.getY(), 0xffffff);
//                //points.add(e.getX());
//                //points.add(e.getY());
//                //renderer.drawPolygon(points);
//                renderer.drawIrregularPolygon(e.getX(), e.getY(), 0xffffff, polygon);
//            }
//        });
        renderer.mouseClicked = 0;
        RegularPolygon regularPolygon = new RegularPolygon(0,0);
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent d) {

                canvas.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        renderer.mouseClicked = e.getClickCount();
                        Point centerPoint = new Point(e.getX(), e.getY());
                        regularPolygon.setCenterPoint(centerPoint);
                        renderer.drawPixel(centerPoint.getX(), centerPoint.getY(), 0xffffff);
                    }
                });
                canvas.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        System.out.println(e.getKeyCode());
                        if (e.getKeyCode() == KeyEvent.VK_V) {
                           renderer.mouseClicked = KeyEvent.VK_V;
                        }
                    }
                });
                if (renderer.mouseClicked != 0){
                    renderer.drawRegularPolygon(regularPolygon.getCenterPoint().getX(), regularPolygon.getCenterPoint().getY(), d.getX(), d.getY(), renderer.mouseClicked, 0xffffff, regularPolygon);
                }
            }
        });


        //       smaze platno pri stisku klavesy c
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                // na klávesu C vymazat plátno
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    renderer.clear();
                }
            }
        });
        // chceme, aby canvas měl focus hned při spuštění
        canvas.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PixelTest::new);
        // https://www.google.com/search?q=SwingUtilities.invokeLater
        // https://www.javamex.com/tutorials/threads/invokelater.shtml
        // https://www.google.com/search?q=java+double+colon
    }
}
