package donna_mwa_objet;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Panier {
    private float x, y;
    private Image image;
    private static float vitesse = 1.25f; // + c grand + le panier se rapproche de la vitesse de la lumière
    private boolean isWaiting = false;
	private float rotation = 0;
	public long startTime;
    
    public Panier(float x, float y) throws SlickException {
        this.x = x;
        this.y = y;
        this.image = new Image("resources/panier_petit.png");
    }
    
    public void paschasse_gauche(int delta) {
        x -= vitesse * delta;
        if(rotation == 0) {
        	rotation -=10;
        }
        if(rotation == -10) {
        	rotation = rotation;
        }
        if(rotation == 10) {
        	rotation -= 20;
        }
        if (x < 0-200) x = 1400;
    }
    
    public void paschasse_droit(int delta) {
        x += vitesse * delta;
        if(rotation == 0) {
        	rotation =10;
        }
        if(rotation == 10) {
        	rotation = rotation;
        }
        if(rotation == -10) {
        	rotation += 20;
        }
        if (x > 1400) x = 0;
    }
    
    public void ralentir() {
    	vitesse *= 0.5;
    	if (vitesse < 0.5f) { // Évite une accélération trop faible
        	vitesse = 0.5f;
    	}
    }
    
    public void accelerer() {
        vitesse *= 1.2;
        if (vitesse > 10.0f) { // Évite une accélération trop grande
            vitesse = 10.0f;
        }
        isWaiting = true;
        startTime = System.currentTimeMillis(); // Initialisation correcte du timer
    }

    
    public boolean attrape(Objet obj) {
        return (obj.getX() + obj.getSize() > x 
        		&& obj.getX() < x + 200 
        		&& obj.getY() + obj.getSize() > y);
    }
    
    public void render(Graphics g) {
    	image.setRotation(rotation);
    	image.draw(x, y - 50);
    }

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
    public static float getVitesse() {
		return vitesse;
	}

	public void setVitesse(float vitesse) {
		Panier.vitesse = vitesse;
	}

	public boolean getIsWaiting() {
		return isWaiting;
	}

	public void setIsWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}

	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}
}