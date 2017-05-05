package components.bullets;

import application.Game;
import components.Direction;
import components.IDrawable;
import components.IUpdatable;
import components.Timer;
import components.characters.Character;
import graphics.Position;
import graphics.TovarenTvarov;
import graphics.primitives.Kruh;
import java.awt.Color;

/**
 * Reprezentuje strelu vystrelenu zo strelnej zbrane.
 *
 * @author tomas
 */
public class Bullet implements IDrawable, IUpdatable {

    private static final int NORMAL_BULLET_DEFAULT_SIZE = 5;
    private static final int NORMAL_BULLET_DEFAULT_SPEED = 15;
    private static final Color NORMAL_BULLET_DEFAULT_COLOR = Color.BLACK;

    private static final double NORMAL_BULLET_DEFAULT_TIME_TO_LIVE = 0.5;
    private static final int NORMAL_BULLET_DEFAULT_DAMAGE_VALUE = 50;

    private int width;
    private int height;
    private Kruh graphic;

    private Character owner;
    private Position position;
    private Direction movingDirection;
    private Timer timer;

    private int speed;
    private int damageValue;

    private boolean toRemove;

    public Bullet(Character owner, Position position) {
        this.owner = owner;
        this.position = position;
        this.speed = NORMAL_BULLET_DEFAULT_SPEED;
        this.movingDirection = this.owner.getAimDirection();
        this.damageValue = NORMAL_BULLET_DEFAULT_DAMAGE_VALUE;
        this.timer = new Timer(NORMAL_BULLET_DEFAULT_TIME_TO_LIVE);

        this.toRemove = false;

        this.width = NORMAL_BULLET_DEFAULT_SIZE;
        this.height = NORMAL_BULLET_DEFAULT_SIZE;
        this.graphic = TovarenTvarov.vytvorKruh(position.x, position.y, width, NORMAL_BULLET_DEFAULT_COLOR, true);
    }

    public final int getSpeed() {
        return speed;
    }

    public final int getDamageValue() {
        return damageValue;
    }

    public final Timer getTimer() {
        return timer;
    }

    public final Character getOwner() {
        return owner;
    }

    @Override
    public final void update() {

        int x = this.position.x;
        int y = this.position.y;

        switch (this.movingDirection) {
            case DOWN:
                y += this.getSpeed();
                break;

            case UP:
                y -= this.getSpeed();
                break;

            case RIGHT:
                x += this.getSpeed();
                break;

            case LEFT:
                x -= this.getSpeed();
                break;
        }

        this.setPosition(new Position(x, y));

        //Znizi cas casovaca
        this.timer.decrement();

        //ODSTRANENIE STRELY
        //Ak vyprsi casovac alebo narazi na prekazku
        if (this.getTimer().hasExpired() || !Game.getInstance().getCollisionHandler().isPositionValid(this)) {
            this.setToRemove();
        }
    }

    @Override
    public final void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public final Position getPosition() {
        return this.position;
    }

    @Override
    public final void setToRemove() {
        this.toRemove = true;
    }

    @Override
    public final boolean isToRemove() {
        return this.toRemove;
    }

    @Override
    public void draw() {
        //ak je rozdielna poloha objektu a jeho grafickej reprezentacie
        if (!this.getPosition().equals(this.graphic.getPosition())) {
            this.graphic.setPosition(this.getPosition());
        }
    }

    @Override
    public void hide() {
        this.graphic.hide();
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
