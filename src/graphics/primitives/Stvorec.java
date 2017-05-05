package graphics.primitives;

import graphics.Position;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Stvorec.
 * @author Michal
 */
public class Stvorec extends Tvar {
    
    private int strana;

    /**
     * Vytvori stvorec s danou polohou a stranou.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param poloha Lavy horny roh stvorca.
     * @param strana Dlzka strany stvorca.
     */
    public Stvorec(Graphics2D graphics, Position poloha, int strana) {
        super(graphics, poloha);
        
        this.strana = strana;
    }

    /**
     * Zmeni stranu stvorca.
     * Ak je stvorec viditelny, prekresli dotknute objekty.
     * @param strana Nova strana stvorca.
     */
    public void zmenStranu(int strana) {
        this.strana = strana;
        
        this.aktualizujPlatno(true);
    }

    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        graphics.fill(new Rectangle(this.getPosition().x, this.getPosition().y, this.strana, this.strana));
    }
}
