package donna_mwa_objet;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ScoreAnim {
    private float x, y; // Position du texte
    private String text; // Contenu du texte ("+10")
    private int tempsDeVie; // Temps avant disparition (en ms)
    private float opacite; // Transparence (1.0 = opaque, 0.0 = invisible)

    public ScoreAnim(float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.tempsDeVie = 1000; // Durée de vie : 1 seconde
        this.opacite = 1.0f; // Opaque au début
    }

    public boolean update(int delta) {
        y -= 0.05f * delta; // Monte légèrement
        tempsDeVie -= delta; // Diminue le temps de vie
        opacite = tempsDeVie / 1000.0f; // Diminue progressivement l’opacité

        return tempsDeVie > 0; // Retourne false quand le texte doit disparaître
    }

    public void render(Graphics g) {
        g.setColor(new Color(0, 0, 0, opacite));
        g.drawString(text, x, y);
    }
}

