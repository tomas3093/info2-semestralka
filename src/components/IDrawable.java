package components;

import graphics.Position;

/**
 * Rozhranie pre objekt, ktory sa da vykreslit na platno
 *
 * @author tomas
 */
public interface IDrawable {

    /**
     * Vykresli objekt s aktualnymi parametrami na platno
     */
    void draw();

    /**
     * Ak je objekt vykresleny na platne, tak ho skryje
     */
    void hide();

    /**
     * Vrati poziciu objektu typu Position
     *
     * @return pozicia
     */
    Position getPosition();

    /**
     * Zmeni poziciu objektu
     *
     * @param position nova pozicia
     */
    void setPosition(Position position);

    /**
     * Vrati najvacsiu sirku objektu
     *
     * @return sirka
     */
    int getWidth();

    /**
     * Vrati najvacsiu vysku objektu
     *
     * @return vyska
     */
    int getHeight();

}
