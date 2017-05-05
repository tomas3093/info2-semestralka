package graphics.primitives;

import graphics.Position;
import java.awt.Graphics2D;

/**
 * Lomena ciara.
 * @author Michal Varga
 */
public class LomenaCiara extends Tvar {
        
    private final int[] suradniceX;
    private final int[] suradniceY;
    private final int pocetBodov;
    
    /**
     * Vytvori lomenu ciaru pozostavajucu z bodov.
     * Poloha sa prepocita automaticky podla bodov.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param body Body tvoriace lomenu ciaru. Aby bola uzatvorena, musi sa posledny a prvy bod rovnat.
     */
    public LomenaCiara(Graphics2D graphics, Position[] body) {
        super(graphics, null);
        
        this.pocetBodov = body.length;
        
        this.suradniceX = new int[this.pocetBodov];
        this.suradniceY = new int[this.pocetBodov];
        
        for (int i = 0; i < this.pocetBodov; i++) {
            this.suradniceX[i] = body[i].x;
            this.suradniceY[i] = body[i].y;
        }
    }
    
    /**
     * Vrati aktualnu polohu tvaru.
     * Lazy initialization!
     * @return Aktualna poloha tvaru.
     */
    @Override
    public Position getPosition() {
        Position poloha = super.getPosition();
        
        if (poloha == null) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;

            for (int i = 0; i < this.pocetBodov; i++) {
                minX = Integer.min(minX, this.suradniceX[i]);
                minY = Integer.min(minY, this.suradniceY[i]);
            }
            
            poloha = new Position(minX, minY);

            super.setPosition(poloha);
        }
        
        return poloha;
    }

    @Override
    public void setPosition(Position poloha) {
        Position povodnaPoloha = getPosition();
        
        int deltaX = poloha.x - povodnaPoloha.x;
        int deltaY = poloha.y - povodnaPoloha.y;
        
        for (int i = 0; i < this.pocetBodov; i++) {
            this.suradniceX[i] = this.suradniceX[i] + deltaX;
            this.suradniceY[i] = this.suradniceY[i] + deltaY;
        }
        
        super.setPosition(poloha);
    }
    
    /**
     * Vrati pocet bodov lomenej ciary.
     * @return Pocet bodov lomenej ciary.
     */
    public int getPocetBodov() {
        return this.pocetBodov;
    }

    /**
     * Vrati x-ove suradnice bodov.
     * Nesmu byt rucne modifikovane!
     * @return X-ove suradnice bodov.
     */
    protected int[] getSuradniceX() {
        return this.suradniceX;
    }
    
    /**
     * Vrati y-ove suradnice bodov.
     * Nesmu byt rucne modifikovane!
     * @return Y-ove suradnice bodov.
     */
    protected int[] getSuradniceY() {
        return this.suradniceY;
    }
    
    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        graphics.drawPolyline(this.suradniceX, this.suradniceY, pocetBodov);
    }
    
}
