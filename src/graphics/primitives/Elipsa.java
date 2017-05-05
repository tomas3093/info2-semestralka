package graphics.primitives;

import graphics.Position;
import graphics.Dimensions;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Elipsa.
 * @author Michal
 */
public class Elipsa extends Tvar{
   
    private Dimensions rozmery;

    /**
     * Vytvori kruh s danou polohou a polomerom.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param poloha Lavy horny roh obdlznika opisaneho vytvaranej elipse.
     * @param rozmery Rozmery obdlznika opisaneho vytvaranej elipse.
     */
    public Elipsa(Graphics2D graphics, Position poloha, Dimensions rozmery) {
        super(graphics, poloha);
        
        this.rozmery = rozmery;
    }

    /**
     * Zmeni rozmery elipsy.
     * Ak je elipsa viditelna, prekresli dotknute objekty.
     * @param rozmery Nove rozmery obdlznika opisaneho vytvaranej elipse.
     */
    public void zmenRozmery(Dimensions rozmery) {
        this.rozmery = rozmery;
        
        this.aktualizujPlatno(true);
    }
    
    /**
     * Zmeni polomer elipsy na ose X.
     * Ak je elipsa viditelna, prekresli dotknute objekty.
     * @param polomerX Novy polomer elipsy na ose X.
     */
    public void zmenPolomerX(int polomerX) {
        this.rozmery = new Dimensions(polomerX * 2, this.rozmery.height);
        
        this.aktualizujPlatno(true);
    }
    
    /**
     * Zmeni polomer elipsy na ose Y.
     * Ak je elipsa viditelna, prekresli dotknute objekty.
     * @param polomerY Novy polomer elipsy na ose Y.
     */
    public void zmenPolomerY(int polomerY) {
        this.rozmery = new Dimensions(this.rozmery.width, polomerY * 2);
        
        this.aktualizujPlatno(true);
    }

    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        graphics.fill(new Ellipse2D.Double(this.getPosition().x, this.getPosition().y, this.rozmery.width * 2, this.rozmery.height * 2));
    }
}
