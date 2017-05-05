package graphics.primitives;

import graphics.Position;
import graphics.Dimensions;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Obdlznik.
 * @author Michal Varga
 */
public class Obdlznik extends Tvar {
    
    private Dimensions rozmery;

    /**
     * Vytvori obdlnik s danou polohou a rozmermi.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param poloha Lavy horny roh obdlznika.
     * @param rozmery Rozmery obdlznika.
     */
    public Obdlznik(Graphics2D graphics, Position poloha, Dimensions rozmery) {
        super(graphics, poloha);
        
        this.rozmery = rozmery;
    }

    /**
     * Zmeni rozmery obdlznika.
     * Ak je obdlnznik viditelny, prekresli dotknute objekty.
     * @param rozmery Nove rozmery obdlznika.
     */
    public void zmenRozmery(Dimensions rozmery) {
        this.rozmery = rozmery;
        
        this.aktualizujPlatno(true);
    }
    
    /**
     * Zmeni dlzku obdlznika.
     * Ak je obdlznik viditelny, prekresli dotknute objekty.
     * @param dlzka Nova dlzka obdlznika.
     */
    public void zmenDlzku(int dlzka) {
        this.rozmery = new Dimensions(dlzka * 2, this.rozmery.height);
        
        this.aktualizujPlatno(true);
    }
    
    /**
     * Zmeni vysku obdlznika.
     * Ak je obdlznik viditelny, prekresli dotknute objekty.
     * @param vyska Nova vyska obdlznika.
     */
    public void zmenVysku(int vyska) {
        this.rozmery = new Dimensions(this.rozmery.width, vyska * 2);
        
        this.aktualizujPlatno(true);
    }

    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        graphics.fill(new Rectangle(this.getPosition().x, this.getPosition().y, this.rozmery.width, this.rozmery.height));
    }
}
