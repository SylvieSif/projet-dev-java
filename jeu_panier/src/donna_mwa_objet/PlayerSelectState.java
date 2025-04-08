package donna_mwa_objet;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class PlayerSelectState extends BasicGameState {
    private int stateID;
    private Image background;
    private int player1HighScore = 0;
    private int player2HighScore = 0;
    private Communicateur communicator;

    public PlayerSelectState(int stateID) {
        this.stateID = stateID;
        this.communicator = new Communicateur();
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/background_menu1.jpg");

        fetchHighScores();
    }

    private void fetchHighScores() {
        try {
            String url = "http://localhost/phpJeuPanier.php?action=getScores";
            String response = communicator.get(url);

            String[] scores = response.split(",");
            if (scores.length == 2) {
                player1HighScore = Integer.parseInt(scores[0]);
                player2HighScore = Integer.parseInt(scores[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        
        if (input.isKeyPressed(Input.KEY_1)) {
            PlayState.selectedPlayer = 1;
            sbg.enterState(LaMain.MENU_STATE);
        } else if (input.isKeyPressed(Input.KEY_2)) {
            PlayState.selectedPlayer = 2;
            sbg.enterState(LaMain.MENU_STATE);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        background.draw(0, 0, gc.getWidth(), gc.getHeight());
        g.setColor(Color.white);
        

        String title = "Sélection de Joueur";
        g.drawString(title, (gc.getWidth() - g.getFont().getWidth(title)) / 2, 100);
        
        String player1Text = "1 - Joueur 1 (Meilleur Score: " + player1HighScore + ")";
        String player2Text = "2 - Joueur 2 (Meilleur Score: " + player2HighScore + ")";
        
        g.drawString(player1Text, (gc.getWidth() - g.getFont().getWidth(player1Text)) / 2, 200);
        g.drawString(player2Text, (gc.getWidth() - g.getFont().getWidth(player2Text)) / 2, 250);
    }

    @Override
    public int getID() {
        return stateID;
    }
}