package components.inventory;

import components.characters.Character;
import components.Timer;
import graphics.Position;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Trieda definuje vseobecnu zbran, ktoru mozno pouzit
 *
 * @author tomas
 */
public abstract class Weapon extends Item {

    private final Timer attackDelayTimer;
    private double attackFrequency;

    public Weapon(int mark, Position position) {
        super(mark, position);

        this.attackDelayTimer = new Timer(0);

        ItemId itemId = Item.getItemId(mark);
        this.attackFrequency = (Double) itemId.optionalProperty;
    }

    public Weapon(int mark) {
        super(mark);
        attackDelayTimer = new Timer(0);

        ItemId itemId = Item.getItemId(mark);
        this.attackFrequency = (Double) itemId.optionalProperty;
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        super.load(dataInput);
        double remainingTime = dataInput.readDouble();

        this.attackDelayTimer.setNewTime(remainingTime);
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        super.save(dataOutput);
        dataOutput.writeDouble(this.attackDelayTimer.getRemainingTime());
    }

    public double getAttackFrequency() {
        return attackFrequency;
    }

    public Timer getAttackDelayTimer() {
        return attackDelayTimer;
    }

    @Override
    public void update() {
        if (!this.getAttackDelayTimer().hasExpired()) {
            this.getAttackDelayTimer().decrement();
        }
    }

    /**
     * Pouzije danu zbran
     *
     * @param usedBy Postava, ktora pouzila zbran
     */
    public abstract void use(Character usedBy);
}
