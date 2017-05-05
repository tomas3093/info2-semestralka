package components.map;

import components.IDamageable;
import graphics.Position;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Trieda definuje stenu, ktora moze obdrzat poskodenie
 *
 * @author tomas
 */
public class WallMapTile extends MapTile implements IDamageable {

    private int durability;

    /**
     * Vytvori konkretny typ steny na danej pozicii a s danou odolnostou
     *
     * @param mark
     * @param position
     * @param durability
     */
    public WallMapTile(int mark, Position position, int durability) {
        super(mark, position);

        this.durability = durability;
    }

    /**
     * Vytvori konkretny typ steny ako prazdnu instanciu
     *
     * @param mark
     */
    public WallMapTile(int mark) {
        super(mark);
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        super.load(dataInput);
        durability = dataInput.readInt();
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        super.save(dataOutput);
        dataOutput.writeInt(durability);
    }

    @Override
    public final void update() {
        if (durability <= 0) {
            super.setToRemove();
        }
    }

    @Override
    public final boolean takeDamage(int damage) {
        this.durability -= damage;

        return this.durability <= 0;
    }
}
