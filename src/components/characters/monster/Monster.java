package components.characters.monster;

import components.characters.Character;
import graphics.Position;
import graphics.TovarenTvarov;
import java.awt.Color;

/**
 * Trieda definuje priseru
 *
 * @author tomas
 */
public class Monster extends Character {

    private static final int MONSTER_DEFAULT_SIZE = 30;
    private static final int MONSTER_DEFAULT_SPEED = 5;
    private static final Color MONSTER_DEFAULT_COLOR = Color.RED;

    private int width;
    private int height;

    public Monster(Position position, int hp, int maxHp) {
        super(position, MONSTER_DEFAULT_SPEED, hp, maxHp, TovarenTvarov.vytvorKruh(position.x, position.y, MONSTER_DEFAULT_SIZE / 2, MONSTER_DEFAULT_COLOR, true), null);

        this.width = MONSTER_DEFAULT_SIZE;
        this.height = MONSTER_DEFAULT_SIZE;
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
