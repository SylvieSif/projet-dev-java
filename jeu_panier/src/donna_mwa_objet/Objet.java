package donna_mwa_objet;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Objet {
	
    protected float x, y;
    private float speed;
    protected Image image;
    protected static final int size = 50;

	public Objet(float x, float y) throws SlickException {
        this.x = x;
        this.y = y;
        this.speed = 0.4f + (float) Math.random() * 0.2f;
        this.image = new Image("resources/objet.png");
    }
    
    public void update(int delta) {
        y += speed * delta;
    }
    
    public void render(Graphics g) {
        image.draw(x, y, size, size);
        
    }
    
    
    
    public float getX() { 
    	return x; 
    }
    
    public float getY() { 
    	return y; 
    }
    
    public int getSize() { 
    	return size; 
    }
}
