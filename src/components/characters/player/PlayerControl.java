package components.characters.player;

import components.Direction;
import graphics.Position;
import components.characters.CharacterControl;

/**
 * Trieda definuje ovladanie hraca pomocou klavesnice
 *
 * @author tomas
 */
public class PlayerControl extends CharacterControl {

    protected boolean pressingDown;
    protected boolean pressingLeft;
    protected boolean pressingRight;
    protected boolean pressingUp;

    public PlayerControl(Player controlledPlayer) {
        super(controlledPlayer);

        this.pressingDown = false;
        this.pressingLeft = false;
        this.pressingRight = false;
        this.pressingUp = false;
    }

    @Override
    public Position getNewPosition() {
        int x = this.controlledCharacter.getPosition().x;
        int y = this.controlledCharacter.getPosition().y;
        int distance = controlledCharacter.getSpeed();

        //Ovladanie
        if (pressingDown) {
            y += distance;
        }
        if (pressingUp) {
            y -= distance;
        }
        if (pressingLeft) {
            x -= distance;
        }
        if (pressingRight) {
            x += distance;
        }

        return new Position(x, y);
    }

    public void pressedPlayerOneDownKey() {
        this.pressingDown = true;
        this.controlledCharacter.setAimDirection(Direction.DOWN);
    }

    public void pressedPlayerOneLeftKey() {
        this.pressingLeft = true;
        this.controlledCharacter.setAimDirection(Direction.LEFT);
    }

    public void pressedPlayerOneRightKey() {
        this.pressingRight = true;
        this.controlledCharacter.setAimDirection(Direction.RIGHT);
    }

    public void pressedPlayerOneUpKey() {
        this.pressingUp = true;
        this.controlledCharacter.setAimDirection(Direction.UP);
    }

    public void pressedPlayerOneAttackKey() {
        this.attacking = true;
    }

    public void releasedPlayerOneDownKey() {
        this.pressingDown = false;
    }

    public void releasedPlayerOneLeftKey() {
        this.pressingLeft = false;
    }

    public void releasedPlayerOneRightKey() {
        this.pressingRight = false;
    }

    public void releasedPlayerOneUpKey() {
        this.pressingUp = false;
    }

    public void releasedPlayerOneAttackKey() {
        this.attacking = false;
    }

    //INVENTORY SELECTION
    public void pressedPlayerOneInventorySelection(int selection) {
        ((Player) this.controlledCharacter).getInventory().useItem(selection);
    }
}
