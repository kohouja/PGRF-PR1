package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;


public class Renderer {
    private BufferedImage img;
    private Canvas canvas;
    private Polygon polygon;
    private static final int FPS = 1000 / 30;

    public BufferedImage getImg() {
        return img;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Renderer(BufferedImage img, Canvas canvas) {
        this.img = img;
        this.canvas = canvas;
        setLoop();
    }
    //zmena velikosti okna vcetne prekresleni obrazku za novy
    public void resize(BufferedImage newImg, Dimension size){
        newImg.setData(this.img.getData());
        this.img = newImg;
        this.canvas.setSize(size);
    }

    private void setLoop() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // říct plátnu, aby zobrazil aktuální img
                canvas.getGraphics().drawImage(img, 0, 0, null);
                // pro zájemce - co dělá observer - https://stackoverflow.com/a/1684476
            }
        }, 0, FPS);
    }

    public void clear() {
        // https://stackoverflow.com/a/5843470
        Graphics g = img.getGraphics();
        g.setColor(Color.BLACK);
        g.clearRect(0, 0, img.getWidth(), img.getHeight());
        clearPolygon();
    }

    public void drawPixel(int x, int y, int color) {
        // nastavit pixel do img
        img.setRGB(x, y, color);
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        float k = dy / (float) dx;
        // https://www.google.com/search?q=java+dividing+two+integers
        float q = y1 - k * x1;

        if (Math.abs(k) < 1) {
            // řídící osa X

            if (x2 < x1) {
                int x3 = x2;
                x2 = x1;
                x1 = x3;
            }

            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                int ry = Math.round(y);
                drawPixel(x, ry, color);
            }
        } else {
//             řídící osa Y
            if(y2 < y1){
                int y3 = y2;
                y2 = y1;
                y1 = y3;
            }
            for(int y=y1; y<= y2; y++){
                float x = (y-q)/k;
                int rx = Math.round(x);
                drawPixel(rx, y, color);
            }


        }
    }

    public void drawDDA(int x1, int y1, int x2, int y2, int color) {

        int dx = x2 - x1;
        int dy = y2 - y1;
        float x, y, k, h = 0, g = 0;

        k = dy / (float) dx;
        if (Math.abs(k) < 1) {
            // řídící osa X

            g = 1;
            h = k;
        } else {
            // řídící osa Y
            g = 1 / k;
            h = 1;
//            na nasledujici podminku jsem prisel sam ty kokso 4:07 rano || prohozeni ridici osy y
            if((x2>x1 && y2<y1) || (x2<x1 && y2>y1)){
                g = -g;
                h = -h;
            }
        }

        x = x1;
        y = y1;
//        na tohle jsem taky prisel sam ty kokso 3:30 rano || prohozeni ridici osy x
        if(x2<x1){
            for (int i = 0; i <= Math.max(Math.abs(dx), Math.abs(dy)); i++) {
                drawPixel(Math.round(x), Math.round(y), color);
                x -= g;
                y -= h;
            }
        }else{
            for (int i = 0; i <= Math.max(Math.abs(dx), Math.abs(dy)); i++) {
                drawPixel(Math.round(x), Math.round(y), color);
                x += g;
                y += h;
            }
        }


    }



    /*
    public void drawPolygon(List<Integer> points) {
        clear();
        drawLine(points.get(0), points.get(1), points.get(2), points.get(3));
        i += 2;
        // for cyklus po dvou se správným omezením
        drawLine(points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 3));
    }
    */

    public void drawIrregularPolygon(int x, int y, int color, Polygon polygon){
//        this.polygon = polygon;
        int[] point = new int[2];
        point[0] = x;
        point[1] = y;
        polygon.getPointsList().add(point);
        if(polygon.getPointsList().size() == 1){
           int[] drawedPixel;
           drawedPixel = polygon.getPointsList().get(0);
           drawPixel(drawedPixel[0], drawedPixel[1], color);
        }else if(polygon.getPointsList().size() > 1 && polygon.getPointsList().size() <= 3){
            int[] point0 = polygon.getPointsList().get(0);
            int[] pointBeforeLast = polygon.getPointsList().get(polygon.getPointsList().size()-2);
            int[] pointLast = polygon.getPointsList().get(polygon.getPointsList().size()-1);

            drawLine(point0[0], point0[1], pointLast[0], pointLast[1], color);
            drawLine(pointBeforeLast[0], pointBeforeLast[1], pointLast[0], pointLast[1], color);


        }else if(polygon.getPointsList().size() > 3){
            int[] point0 = polygon.getPointsList().get(0);
            int[] pointBeforeLast = polygon.getPointsList().get(polygon.getPointsList().size()-2);
            int[] pointLast = polygon.getPointsList().get(polygon.getPointsList().size()-1);
            drawLine(point0[0], point0[1], pointBeforeLast[0], pointBeforeLast[1], 0x000000);
            drawLine(point0[0], point0[1], pointLast[0], pointLast[1], color);
            drawLine(pointBeforeLast[0], pointBeforeLast[1], pointLast[0], pointLast[1], color);
        }

    }
    private void clearPolygon(){
        if(this.polygon != null) {
            this.polygon.getPointsList().clear();
        }
    }

}
