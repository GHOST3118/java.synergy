/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gameproject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class GameProject extends JFrame {
    
    private static GameProject _game;
    private static GameCanvas _canvas;
    
    private static Image dnevnik;
    private static Image five;
    private static Image game_over;
    
    private static int score = 0;
    
    private static long last_frame_time;
    private static float drop_left = 200;
    private static float drop_top = -10;
    private static float drop_v = 200;
    
    public static void main(String[] args) throws IOException {
        _game = new GameProject();
        
        dnevnik = ImageIO.read( GameProject.class.getResourceAsStream("./content/dn.jpg") );
        game_over = ImageIO.read( GameProject.class.getResourceAsStream("./content/game_over.jpg") );
        five = ImageIO.read( GameProject.class.getResourceAsStream("./content/five.png") );
        
        _game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _game.setLocation(200, 200);
        _game.setSize(900, 600);
        _game.setResizable(false);
        
        last_frame_time = System.nanoTime();
        
        _canvas = new GameCanvas();
        _canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e ) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + game_over.getWidth(null);
                float drop_bottom = drop_top + game_over.getHeight(null);
                
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
                
                if (is_drop) {
                    drop_top = -100;
                    drop_left = (int) (Math.random() * (_canvas.getWidth() - game_over.getWidth(null)));
                    drop_v = drop_v + 10;
                    score++;
                    _game.setTitle("total score: "+score);
                }
            }
        });
        _game.add( _canvas );
        
        _game.setVisible(true);
    }
    
    public static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        
        drop_top = drop_top + drop_v * delta_time;
        
        g.drawImage(dnevnik, 100, 100, 300, 300, _game);
        g.drawImage(five, (int)drop_left, (int)drop_top, 100, 100, _game);
        
        if (drop_top > _game.getHeight()) g.drawImage(game_over, 100, 100, 300, 300, _game);
    }
    
    public static class GameCanvas extends JPanel {
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
    
}
