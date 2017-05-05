package components.characters;

import application.Game;
import components.IUpdatable;
import graphics.Position;

/**
 * Vseobecne ovladanie hernej postavy
 *
 * @author tomas
 */
public abstract class CharacterControl implements IUpdatable {

    protected Character controlledCharacter;
    protected boolean attacking;
    private boolean toRemove;

    public CharacterControl(Character controlledCharacter) {
        this.controlledCharacter = controlledCharacter;
        this.attacking = false;
        this.toRemove = false;
    }

    public CharacterControl() {
        this.controlledCharacter = null;
    }

    public Character getControlledCharacter() {
        return controlledCharacter;
    }

    /**
     * Vypocita a vrati novu poziciu ovladanej postavy
     *
     * @return
     */
    public abstract Position getNewPosition();

    @Override
    public final void update() {

        updateCharacterPosition();

        //UTOK
        if (attacking) {
            controlledCharacter.performAttack();
        }
        if (this.controlledCharacter.getEquippedWeapon() != null) {
            this.controlledCharacter.getEquippedWeapon().update();
        }

        //AK POSTAVA ZOMRELA
        if (controlledCharacter.getHp() <= 0) {
            setToRemove();
        }
    }

    @Override
    public final void setToRemove() {
        this.toRemove = true;
    }

    @Override
    public final boolean isToRemove() {
        return toRemove;
    }

    /**
     * Aktualizuje poziciu ovladanej postavy
     */
    private void updateCharacterPosition() {

        Position oldPosition = this.controlledCharacter.getPosition();
        Position newPosition = this.getNewPosition();

        //VALIDACIA NOVEJ POZICIE
        //Otestuje poziciu pre X-ovu suradnicu
        this.controlledCharacter.setPosition(new Position(newPosition.x, oldPosition.y));

        if (!Game.getInstance().getCollisionHandler().isPositionValid(controlledCharacter)) {
            newPosition = new Position(oldPosition.x, newPosition.y);
        }

        //Otestuje poziciu pre Y-ovu suradnicu
        this.controlledCharacter.setPosition(new Position(oldPosition.x, newPosition.y));

        if (!Game.getInstance().getCollisionHandler().isPositionValid(controlledCharacter)) {
            newPosition = new Position(newPosition.x, oldPosition.y);
        }

        //ZMENI POZICIU
        this.controlledCharacter.setPosition(newPosition);
    }
}
