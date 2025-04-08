package donna_mwa_objet;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Eclair extends Objet{

	public Eclair(float x, float y) throws SlickException {
		super(x, y);
		this.image = new Image("resources/eclair.png");
	}

}
