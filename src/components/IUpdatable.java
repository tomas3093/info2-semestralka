package components;

/**
 * Rozhranie pre objekt, ktory moze aktualizovat svoj stav
 *
 * @author tomas
 */
public interface IUpdatable {

    /**
     * Aktualizuje poziciu, vnutorny stav objektu a zisti ci objekt netreba
     * odstranit
     */
    void update();

    /**
     * Nastavi objekt na odstranenie
     */
    void setToRemove();

    /**
     * Zisti ci je objekt nastaveny na odstranenie
     *
     * @return
     */
    boolean isToRemove();
}
