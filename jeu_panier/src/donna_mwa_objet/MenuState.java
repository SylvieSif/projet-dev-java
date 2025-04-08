package donna_mwa_objet;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MenuState extends BasicGameState {

    private int stateID;
    private Image background;
    private int currentHighScore = 0;

    public MenuState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/background_menu1.jpg");
        try {
            String url = "http://localhost/phpJeuPanier.php?action=getScore&player=" + PlayState.selectedPlayer;
            String response = new Communicateur().get(url);
            currentHighScore = Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        

        if (input.isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(LaMain.PLAY_STATE);
        }
    }


    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        background.draw(0, 0, gc.getWidth(), gc.getHeight());
        g.setColor(Color.white);
        
        String text = "Appuyez sur entrer pour jouer!";
        String playerText = "Joueur " + PlayState.selectedPlayer;
        String scoreText = "Meilleur score: " + currentHighScore;
        
        g.drawString(playerText, (gc.getWidth() - g.getFont().getWidth(playerText)) / 2, 100);
        g.drawString(text, (gc.getWidth() - g.getFont().getWidth(text)) / 2, 150);
        g.drawString(scoreText, (gc.getWidth() - g.getFont().getWidth(scoreText)) / 2, 200);
    }

    @Override
    public int getID() {
        return stateID;
    }
}
