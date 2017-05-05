package components.characters.monster;

import components.GameSettings;
import components.ILoadable;
import components.Marks;
import components.Timer;
import graphics.Position;
import components.characters.CharacterControl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Trieda definuje nahodne ovladanie nepriatelov
 *
 * @author tomas
 */
public class MonsterRandomControl extends CharacterControl implements ILoadable {

    private static final double MONSTER_MAX_MOVING_TIME = 3;

    private final Timer movementTimer;
    private int dX;
    private int dY;

    public MonsterRandomControl(Monster controlledMonster) {
        super(controlledMonster);
        this.movementTimer = new Timer(0);
    }

    public MonsterRandomControl() {
        super();
        this.movementTimer = new Timer(0);
    }

    @Override
    public Position getNewPosition() {
        if (this.movementTimer.hasExpired()) {
            setNewMovingDirection();
            setRandomTime();
        } else {
            this.movementTimer.decrement();
        }

        return new Position(controlledCharacter.getPosition().x + dX, controlledCharacter.getPosition().y + dY);
    }

    /**
     * Nastavi novy nahodny smer pohybu
     */
    private void setNewMovingDirection() {
        Random rand = new Random();
        int maxX = GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x + GameSettings.GAME_ACTIVE_WINDOW_WIDTH;
        int maxY = GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y + GameSettings.GAME_ACTIVE_WINDOW_HEIGHT;

        int x = rand.nextInt(maxX - GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x) - GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x;
        int y = rand.nextInt(maxY - GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y) - GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y;

        Position targetPosition = new Position(x, y);

        int distanceX = targetPosition.x - controlledCharacter.getPosition().x;
        int distanceY = targetPosition.y - controlledCharacter.getPosition().y;
        int distance = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        this.dX = (distanceX * controlledCharacter.getSpeed()) / distance;
        this.dY = (distanceY * controlledCharacter.getSpeed()) / distance;
    }

    /**
     * Nastavi novy nahodny cas pocas ktoreho sa snazi postava dostat na cielovu
     * poziciu
     */
    private void setRandomTime() {
        Random rand = new Random();
        double time = rand.nextDouble() * MONSTER_MAX_MOVING_TIME;

        this.movementTimer.setNewTime(time);
    }

    @Override
    public int getMark() {
        return Marks.MARK_CHARACTER_CONTROL_MONSTER_RANDOM_CONTROL;
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        Position characterPosition = super.controlledCharacter.getPosition();

        dataOutput.writeInt(characterPosition.x);
        dataOutput.writeInt(characterPosition.y);
        dataOutput.writeInt(super.controlledCharacter.getHp());
        dataOutput.writeInt(super.controlledCharacter.getMaxHp());

        dataOutput.writeInt(dX);
        dataOutput.writeInt(dY);
        dataOutput.writeDouble(this.movementTimer.getRemainingTime());
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        int x = dataInput.readInt();
        int y = dataInput.readInt();
        int hp = dataInput.readInt();
        int maxHp = dataInput.readInt();

        Monster monster = new Monster(new Position(x, y), hp, maxHp);
        super.controlledCharacter = monster;

        this.dX = dataInput.readInt();
        this.dY = dataInput.readInt();
        this.movementTimer.setNewTime(dataInput.readDouble());
    }
}
