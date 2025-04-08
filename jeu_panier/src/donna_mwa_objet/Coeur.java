package donna_mwa_objet;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Coeur extends Objet {
	
	public Coeur(float x,float y) throws SlickException {
		super(x, y);
		this.image=new Image("resources/coeur.png");
		// TODO Auto-generated constructor stub
	}

}
