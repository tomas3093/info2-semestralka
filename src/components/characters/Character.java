package components.characters;

import components.Direction;
import components.IDamageable;
import components.IDrawable;
import components.inventory.Weapon;
import graphics.Position;
import graphics.primitives.ITvar;
import graphics.primitives.Obrazok;

/**
 * Trieda definuje vseobecnu postavu, ktora sa vie vykreslit na platno, vie
 * zautocit a obdrzat poskodenie
 *
 * @author tomas
 */
public abstract class Character implements IDrawable, IOffensive, IDamageable {

    private Position position;
    private Direction aimDirection;

    private final int maxHp;
    private final int speed;
    private int hp;

    private ITvar graphic;

    private Weapon equippedWeapon;

    public Character(Position position, int speed, int hp, int maxHp, ITvar graphic, Weapon equippedWeapon) {
        this.position = position;
        this.aimDirection = Direction.UP;

        this.hp = hp;
        this.maxHp = maxHp;
        this.speed = speed;

        this.graphic = graphic;
        this.equippedWeapon = equippedWeapon;
    }

    @Override
    public void draw() {

        //ak je rozdielna poloha objektu a jeho grafickej reprezentacie
        if (!this.position.equals(this.graphic.getPosition())) {
            this.graphic.setPosition(this.position);
        }
    }

    @Override
    public final void hide() {
        this.graphic.hide();
    }

    public final int getHp() {
        return hp;
    }

    public final int getMaxHp() {
        return maxHp;
    }

    /**
     * Zvysi HP o danu hodnotu
     *
     * @param value
     */
    public void heal(int value) {
        this.hp += value;

        if (this.hp > this.maxHp) {
            this.hp = maxHp;
        }
    }

    @Override
    public boolean takeDamage(int damage) {
        this.hp -= damage;

        return this.hp <= 0;
    }

    public final int getSpeed() {
        return speed;
    }

    public final Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    @Override
    public final void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }

    public final Direction getAimDirection() {
        return aimDirection;
    }

    public final void setAimDirection(Direction aimDirection) {
        if (this.graphic instanceof Obrazok) {
            ((Obrazok) this.graphic).zmenUhol(aimDirection.angle);
        }

        this.aimDirection = aimDirection;
    }

    @Override
    public final Position getPosition() {
        return this.position;
    }

    @Override
    public final void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public final void performAttack() {
        if (this.equippedWeapon != null) {
            this.equippedWeapon.use(this);
        }
    }
}
