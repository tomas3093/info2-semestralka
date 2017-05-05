package graphics.primitives;

import graphics.Position;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Kruh.
 * @author Michal Varga
 */
public class Kruh extends Tvar {
   
    private int polomer;

    /**
     * Vytvori kruh s danou polohou a polomerom.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param poloha Lavy horny roh stvorca opisaneho vytvaranemu kruhu.
     * @param polomer Polomer kruhu.
     */
    public Kruh(Graphics2D graphics, Position poloha, int polomer) {
        super(graphics, poloha);
        
        this.polomer = polomer;
    }

    /**
     * Zmeni polomer kruhu.
     * Ak je kruh viditelny, prekresli dotknute objekty.
     * @param polomer Novy polomer kruhu.
     */
    public void zmenPolomer(int polomer) {
        this.polomer = polomer;
        
        this.aktualizujPlatno(true);
    }

    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        graphics.fill(new Ellipse2D.Double(this.getPosition().x, this.getPosition().y, this.polomer * 2, this.polomer * 2));
    }
}
