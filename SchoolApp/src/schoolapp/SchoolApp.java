/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolapp;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class SchoolApp {
    
    private static MainForm form;
    private static Image fx;
    private static long lastFrameTime;
    private static List<Particle> particle;
    
    public static void main(String[] args) throws IOException {
        form = new MainForm();
        fx = ImageIO.read(SchoolApp.class.getResourceAsStream("./textures/part.png"));
        

        particle = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            particle.add(new Particle());
        }
    
        form.add(new ParticleCanvas());

        form.setVisible(true);
    }
    
    public static void onRepaint(Graphics g) {
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;

        for (Particle _particle : particle) {
            _particle.update(deltaTime);
            g.drawImage(fx, (int)_particle.x, (int)_particle.y, 10, 10, form);
        }
    }
    
    public static class ParticleCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
    
    public static class Particle {
        float x;
        float y;
        float speed;
        
        public Particle() {
            Random random = new Random();
            
            x = random.nextInt(400); 
            y = random.nextInt(100) - 100;
            speed = 50 + random.nextFloat() * 150;
        }
        
        public void update(float deltaTime) {
            y += speed * deltaTime;
            if (y > 400) {
                y = -10;
                x = new Random().nextInt(400);
            }
        }
    }
  
}
