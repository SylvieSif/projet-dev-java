package donna_mwa_objet;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameOverState extends BasicGameState {

    private int stateID;

    public GameOverState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(LaMain.MENU_STATE);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        g.setColor(Color.white);

        String text = "mskn ta perdu!";
        int textWidth = g.getFont().getWidth(text);
        int textHeight = g.getFont().getHeight(text);
        int x = (gc.getWidth() - textWidth) / 2;
        int y = (gc.getHeight() - textHeight) / 2; 
        g.drawString(text, x, y);
    }

    @Override
    public int getID() {
        return stateID;
    }
}
