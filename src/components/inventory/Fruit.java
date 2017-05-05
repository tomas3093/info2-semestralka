package components.inventory;

import components.Marks;
import graphics.Position;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Trieda definuje predmet "Ovocie", ktory dokaze obnovit HP postavy, ktora ho
 * pouzije
 *
 * @author tomas
 */
public class Fruit extends Item {

    private static final List<Integer> FRUIT_LIST = initializeList();

    /**
     * Definuje vsetky mozne typy ovocia
     *
     * @return
     */
    private static List<Integer> initializeList() {
        List<Integer> list = new ArrayList<>();

        list.add(Marks.MARK_FRUIT_APPLE);
        list.add(Marks.MARK_FRUIT_CHERRIES);
        list.add(Marks.MARK_FRUIT_WATERMELON);

        return list;
    }

    /**
     * Vrati nahodne ovocie z preddefinovaneho zoznamu na danej pozicii
     *
     * @param position
     * @return
     */
    public static Fruit getRandomFruit(Position position) {
        Random random = new Random();
        int mark = random.nextInt(FRUIT_LIST.size());

        return new Fruit(FRUIT_LIST.get(mark), position);
    }

    private int value;

    /**
     * Vytvori konkretny typ ovocia na danej pozicii
     *
     * @param mark znacka urcujuca typ ovocia
     * @param position pozicia
     */
    public Fruit(int mark, Position position) {
        super(mark, position);

        ItemId itemId = Item.getItemId(mark);
        this.value = (Integer) itemId.optionalProperty;
    }

    /**
     * Vytvori konkretny typ ovocia ako prazdnu instanciu
     *
     * @param mark znacka urcujuca typ ovocia
     */
    public Fruit(int mark) {
        super(mark);

        ItemId itemId = Item.getItemId(mark);
        this.value = (Integer) itemId.optionalProperty;
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        super.load(dataInput);
        value = dataInput.readInt();
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        super.save(dataOutput);
        dataOutput.writeInt(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public void update() {
    }
}
