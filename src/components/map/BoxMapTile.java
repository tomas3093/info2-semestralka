package components.map;

import application.Game;
import components.IDamageable;
import components.Marks;
import components.inventory.Fruit;
import components.inventory.Gun;
import components.inventory.Item;
import graphics.Position;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Trieda definuje krabicu, ktora moze obdrzat poskodenie a ked sa znici, tak
 * dropne nahodny predmet
 *
 * @author tomas
 */
public class BoxMapTile extends MapTile implements IDamageable {

    private static final int BOX_DURABILITY_AMMO_BOX = 300;
    private static final int BOX_DURABILITY_SUPPLIES_BOX = 200;

    private int durability;

    /**
     * Vytvori konkretny typ krabice na danej pozicii
     *
     * @param mark znacka urcujuca typ krabice
     * @param position pozicia
     */
    public BoxMapTile(int mark, Position position) {
        super(mark, position);

        switch (mark) {
            case Marks.MARK_BOX_MAP_TILE_AMMO_BOX:
                this.durability = BOX_DURABILITY_AMMO_BOX;
                break;

            case Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX:
                this.durability = BOX_DURABILITY_SUPPLIES_BOX;
                break;
        }
    }

    /**
     * Vytvori konkretny typ krabice ako prazdnu instanciu
     *
     * @param mark
     */
    public BoxMapTile(int mark) {
        super(mark);
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        super.load(dataInput);
        this.durability = dataInput.readInt();
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        super.save(dataOutput);
        dataOutput.writeInt(durability);
    }

    @Override
    public final void update() {

        //AK BOL BOX ZNICENY
        if (durability <= 0) {
            if (!super.isToRemove()) {

                //VYTVORI PREDMET
                Position itemPosition = new Position(super.getPosition().x + (MapTile.MAP_TILE_SIZE / 2) - (Item.ITEM_MAX_SIZE / 2),
                        super.getPosition().y + (MapTile.MAP_TILE_SIZE / 2) - (Item.ITEM_MAX_SIZE / 2));

                Item hiddenItem = null;
                switch (super.getMark()) {
                    case Marks.MARK_BOX_MAP_TILE_AMMO_BOX:
                        hiddenItem = Gun.getRandomGun(itemPosition);
                        this.durability = BOX_DURABILITY_AMMO_BOX;
                        break;

                    case Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX:
                        hiddenItem = Fruit.getRandomFruit(itemPosition);
                        this.durability = BOX_DURABILITY_SUPPLIES_BOX;
                        break;
                }

                //ZAREGISTRUJE UKRYTY PREDMET DO HRY
                if (hiddenItem != null) {
                    Game.getInstance().addGameObject(hiddenItem);
                }

                super.setToRemove();
            }
        }
    }

    @Override
    public final boolean takeDamage(int damage) {
        this.durability -= damage;

        return this.durability <= 0;
    }
}
