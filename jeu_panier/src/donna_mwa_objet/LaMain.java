package donna_mwa_objet;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class LaMain extends StateBasedGame {
    public static final int PLAYER_SELECT_STATE = 0;
    public static final int MENU_STATE = 1;
    public static final int PLAY_STATE = 2;
    public static final int GAME_OVERT_STATE = 3;
    
    public LaMain(String title) {
        super(title);
        this.addState(new PlayerSelectState(PLAYER_SELECT_STATE));
        this.addState(new MenuState(MENU_STATE));
        this.addState(new PlayState(PLAY_STATE));
        this.addState(new GameOverState(GAME_OVERT_STATE));
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(PLAYER_SELECT_STATE).init(gc, this);
        this.getState(MENU_STATE).init(gc, this);
        this.getState(PLAY_STATE).init(gc, this);
        this.getState(GAME_OVERT_STATE).init(gc, this);
        this.enterState(PLAYER_SELECT_STATE);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new LaMain("Jeu du panier"));
            app.setDisplayMode(1400, 800, false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}