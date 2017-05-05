package graphics.primitives;

import graphics.Position;
import java.awt.Graphics2D;

/**
 * Polygon.
 * @author Michal Varga
 */
public class Polygon extends LomenaCiara {
    
    /**
     * Vytvori polygon pozostavajuci z bodov.
     * Poloha sa prepocita automaticky podla bodov.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param body Body tvoriace polygon.
     */
    public Polygon(Graphics2D graphics, Position[] body) {
        super(graphics, body);
    }
    
    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        java.awt.Polygon poly = new java.awt.Polygon(this.getSuradniceX(), this.getSuradniceY(), this.getPocetBodov());
        graphics.fill(poly);
    }
    
}
