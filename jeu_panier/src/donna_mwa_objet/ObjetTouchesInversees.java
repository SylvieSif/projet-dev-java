package donna_mwa_objet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ObjetTouchesInversees extends Objet {


	public ObjetTouchesInversees(float x, float y) throws SlickException {
		super(x, y);
		this.image = new Image("resources/PotionNoBackground.png");
	}

}
