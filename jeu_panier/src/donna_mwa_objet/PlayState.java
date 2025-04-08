package donna_mwa_objet;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class PlayState extends BasicGameState {
    private int stateID;
    private Image background;
    private Panier panier;
    private ArrayList<Objet> objets;
    private int vie = 10;
    private int score = 0;
    private Random random;
    private boolean touchesInversees = false;
    private Timer timerTouchesInversees; // Timer pour rétablir les touches normales
    private Image screenCapture;
    private boolean paused = false;
    public static int a;
    private ArrayList<ScoreAnim> ScoreAnim = new ArrayList<>();
    private Animation animation;
    private SpriteSheet spriteSheet;
    private float explosionX = -100, explosionY = -100;
    public static int selectedPlayer = 1;
    public Communicateur communicator = new Communicateur();

    public PlayState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/background.jpg");
        panier = new Panier(gc.getWidth() / 2, gc.getHeight() - 196);
        objets = new ArrayList<>();
        random = new Random();
        spriteSheet = new SpriteSheet("resources/Explosion.png", 96, 96);
        animation = new Animation(spriteSheet, 100); 
        animation.setLooping(false); // Joue une seule fois
        animation.restart();

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        animation.update(delta);
        if (paused) {
        	if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (mouseX >= 300 && mouseX <= 500 && mouseY >= 200 && mouseY <= 300) {
                    paused = false; // Reprendre le jeu
                }
            }
        	return;
        }
        
        if (input.isKeyDown(Input.KEY_P)) {
            if (!paused) {
            	paused = true;
                try {
                    // Capturer l'écran comme une image
                    screenCapture = new Image(gc.getWidth(), gc.getHeight());
                    gc.getGraphics().copyArea(screenCapture, 0, 0);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }else {
            	paused = false;
            }
        }

        if (!touchesInversees) {
            if (input.isKeyDown(Input.KEY_LEFT)) {
                panier.paschasse_gauche(delta);
            }
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                panier.paschasse_droit(delta);
            }
            if (!input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT)) {
                panier.setRotation(0);
            }
        } else {
            if (input.isKeyDown(Input.KEY_LEFT)) {
                panier.paschasse_droit(delta);
            }
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                panier.paschasse_gauche(delta);
            }
            
            if (!input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT)) {
                panier.setRotation(0);
            }
        }

        // Génération d'objets aléatoires
        if (objets.size() < 2) {
            if (random.nextInt(100) < 2) {
                if (random.nextInt(10) < 1) {
                    objets.add(new ObjetTouchesInversees(random.nextInt(gc.getWidth() - 50), 0));
                }
                if (random.nextInt(15) < 1) {
                    objets.add(new Coeur(random.nextInt(gc.getWidth() - 50), 0));
                }
                    
                if(random.nextInt(15) < 1) {
                	objets.add(new Eclair(random.nextInt(gc.getWidth() - 50), 0));

                } else {
                    objets.add(new Objet(random.nextInt(gc.getWidth() - 50), 0));
                }
            }
        }

        ArrayList<Objet> recyclage = new ArrayList<>();
        for (Objet obj : objets) {
            obj.update(delta);

            if (panier.attrape(obj)) {
            	
            	if(obj instanceof Eclair) {
            		panier.accelerer();
            	}
            	
                if (obj instanceof ObjetTouchesInversees) {
                    activerTouchesInversees();
                }
            	
                if (obj instanceof Coeur) {
                    this.setVie(this.getVie() + 1);
                } else {
                    score += 10;
                    ScoreAnim.add(new ScoreAnim(panier.getY() + 40, panier.getY() - 20, "+10"));
                }
                
                recyclage.add(obj);
            } else if (obj.getY() > gc.getHeight()) {
                if (!(obj instanceof Coeur) && !(obj instanceof ObjetTouchesInversees) && !(obj instanceof Eclair)) {
                    explosionX = obj.getX();
                    explosionY = gc.getHeight() - 100; // Pour qu'elle ne sorte pas de l'écran
                    animation.restart();
                    vie--;
                }
                
                recyclage.add(obj);
            }
        }
        
        for (int i = 0; i < ScoreAnim.size(); i++) {
            if (!ScoreAnim.get(i).update(delta)) {
            	ScoreAnim.remove(i);
                i--; // Éviter les bugs de suppression
            }
        }

        objets.removeAll(recyclage);
        
        if (panier.getIsWaiting()) {
            long elapsed = System.currentTimeMillis() - panier.startTime;
            if (elapsed >= 3000) { // Après 3 secondes, on revient à la vitesse normale
                panier.setVitesse(1.25f);
                panier.setIsWaiting(false);
            }
        }


        if (vie == 0) {
            try {
                String url = "http://localhost/phpJeuPanier.php?action=submitScore&player=" + 
                            selectedPlayer + "&score=" + score;
                communicator.getAsync(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sbg.enterState(LaMain.GAME_OVERT_STATE);
            this.setVie(5);
            objets = new ArrayList<>();
            a = score;
            score = 0;
        }

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	background.draw(0, 0, gc.getWidth(), gc.getHeight());
    	panier.render(g);
    	animation.draw(explosionX, explosionY);

    	for (Objet obj : objets) {
    	    obj.render(g);
    	}

    	g.setColor(Color.white);
    	g.drawString("Score: " + score, 10, 30);
    	g.drawString("Vies: " + vie, 10, 50);
    	g.drawString("Temps restant: " + timerTouchesInversees, 10, 70);

    	if (paused) {
    	    // Léger voile noir semi-transparent (corrigé)
    	    g.setColor(new Color(0, 0, 0, 0.5f));
    	    g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

    	    // Dessiner le bouton
    	    g.setColor(Color.black);
    	    g.fillRect(300, 200, 200, 100);
    	    g.setColor(Color.white);
    	    g.drawString("Reprendre", 370, 240);
    	}

    	// Correction de la boucle sur les animations de score
    	for (ScoreAnim sa : ScoreAnim) {
    	    sa.render(g);
    	}

    }
    
    public void activerTouchesInversees() {
        if (!touchesInversees) {
            touchesInversees = true;
            System.out.println("Touches inversées activées !");

            // Créer un Timer pour désactiver les touches inversées après 10 secondes
            timerTouchesInversees = new Timer(10000, e -> {
                touchesInversees = false;
                System.out.println("Touches inversées désactivées !");
                timerTouchesInversees.stop(); // Arrêter le timer après exécution
            });
            timerTouchesInversees.setRepeats(false); // Exécuter une seule fois
            timerTouchesInversees.start(); // Démarrer le timer
        }
    }

    @Override
    public int getID() {
        return stateID;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int a) {
        this.vie = a;
    }
    
    
}
