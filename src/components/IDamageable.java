package components;

/**
 * Rozhranie pre objekt, ktory je schopny obdrzat poskodenie
 *
 * @author tomas
 */
public interface IDamageable {

    /**
     * Objektu sposobi dane poskodenie. Ak sa objekt tymto poskodenim znicil,
     * tak vrati true.
     *
     * @param damage hodnota poskodenia
     * @return Objekt sa znicil
     */
    boolean takeDamage(int damage);
}
