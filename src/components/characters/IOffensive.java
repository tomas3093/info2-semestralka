package components.characters;

import components.inventory.Weapon;

/**
 * Rozhranie pre objekt, ktory je schopny zobrat si zbran a zautocit
 *
 * @author tomas
 */
public interface IOffensive {

    /**
     * Zoberie si danu zbran
     *
     * @param weapon
     */
    void equipWeapon(Weapon weapon);

    /**
     * Vykona utok pomocou aktualne drzanej zbrane
     */
    void performAttack();
}
