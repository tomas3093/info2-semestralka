package graphics.primitives;

import graphics.Position;
import java.awt.Color;

/**
 * Rozhranie tvarov.
 * @author Michal Varga
 */
public interface ITvar {
    
    /**
     * Zobrazi tvar.
     * Zobrazeny tvar je odteraz viditelny.
     */
    public void show();
    
    /**
     * Skryje tvar.
     * Skryty tvar nie je viac viditelny.
     */
    public void hide();
    
    /**
     * Zisti, ci je tvar viditelny.
     * @return true, ak je tvar viditelny, false inak.
     */
    public boolean isVisible();
    
    /**
     * Zmeni polohu tvaru.
     * Ak je tvar viditelny, prekresli dotknute objekty.
     * @param poloha Nova poloha tvaru.
     */
    public void setPosition(Position poloha);
    
    public Position getPosition();
    
    /**
     * Zmeni farbu tvaru.
     * Ak je tvar viditelny, prekresli dotknute objekty.
     * @param color Farba
     */
    public void setColor(Color color);
    
    /**
     * Zmeni farbu tvaru.
     * Ak je tvar viditelny, prekresli dotknute objekty.
     * @param R Cervena zlozka novej farby.
     * @param G Zelena zlozka novej farby.
     * @param B Modra zlozka novej farby.
     */
    public void setColor(int R, int G, int B);
    
}
