/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Pavkin Vladimir
 */
public class Game extends JFrame {
    private static Game game;
    
    private static Image library;
    private static Image book;
    private static Image end_game;
    
    private static int score = 0;
    private static long last_frame_time;
    private static float drop_left = 200;
    private static float drop_top = -10;
    private static float drop_v = 200;
    
    public static void main(String[] args) throws IOException {
        
        library = ImageIO.read(Game.class.getResourceAsStream("library.png") );
        book = ImageIO.read(Game.class.getResourceAsStream("book.png") );
        end_game = ImageIO.read(Game.class.getResourceAsStream("game_over.png") );
        
        game = new Game();
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setLocation(200, 50);
        game.setSize(1280, 760);
        game.setResizable(false);
        
        last_frame_time = System.nanoTime();
        
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                float drop_right = drop_left + end_game.getWidth(null);
                float drop_bottom = drop_top + end_game.getHeight(null);
                
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
                
                if(is_drop) {
                    drop_top = -10;
                    drop_left = (int)(Math.random() * (game_field.getWidth() - end_game.getWidth(null)));
                    drop_v = drop_v + 10;
                    score++;
                    game.setTitle("Total Books Collected: " + score);
                }
            }
        });
        
        game.add( game_field );
        
        game.setVisible(true);
    }
    
    public static void onRepaint( Graphics g ) {
        long current_time = System.nanoTime();
        float delta_time = ( current_time - last_frame_time ) * 0.000000001f;
        last_frame_time = current_time;
        drop_top = drop_top + drop_v * delta_time;
        
        g.drawImage(library, 300, 300, game);
        g.drawImage(book, (int)drop_left, (int)drop_top, 100, 100, game);
        
        if ( drop_top > game.getHeight() ) g.drawImage(end_game, 300, 100, game);
    }
    
    public static class GameField extends JPanel {
        @Override
        protected void paintComponent( Graphics g ) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
    
}
