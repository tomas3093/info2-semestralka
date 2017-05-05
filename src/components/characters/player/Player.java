package components.characters.player;

import components.GameSettings;
import components.characters.Character;
import components.inventory.Inventory;
import graphics.Position;
import graphics.TovarenTvarov;
import graphics.primitives.TextovePole;
import java.awt.Color;

/**
 * Trieda definuje hraca
 *
 * @author tomas
 */
public class Player extends Character {

    private static final int PLAYER_DEFAULT_SIZE = 30;
    private static final int PLAYER_DEFAULT_SPEED = 8;
    private static final String PLAYER_TEXTURE_FILE_PATH = "img/characters/hero.png";

    private int width;
    private int height;

    private Inventory inventory;
    private TextovePole hpIndicator;

    public Player(Position position, int hp, int maxHp) {
        super(position, PLAYER_DEFAULT_SPEED, hp, maxHp, TovarenTvarov.vytvorObrazok(position.x, position.y, PLAYER_TEXTURE_FILE_PATH, true), null);

        this.width = PLAYER_DEFAULT_SIZE;
        this.height = PLAYER_DEFAULT_SIZE;

        this.inventory = new Inventory(GameSettings.PLAYER_INVENTORY_POSITION, this);
        this.hpIndicator = TovarenTvarov.vytvorTextovePole(GameSettings.PLAYER_HP_INDICATOR_POSITION.x,
                GameSettings.PLAYER_HP_INDICATOR_POSITION.y,
                "HP: " + super.getHp(), 18, true, Color.RED, true);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public TextovePole getHpIndicator() {
        return hpIndicator;
    }

    @Override
    public boolean takeDamage(int damage) {
        boolean result = super.takeDamage(damage);
        this.hpIndicator.setText("HP: " + super.getHp());

        return result;
    }

    @Override
    public void heal(int value) {
        super.heal(value);
        this.hpIndicator.setText("HP: " + super.getHp());
    }

    @Override
    public final void draw() {
        super.draw();

        this.hpIndicator.setText("HP: " + super.getHp());
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
