package components.inventory;

import components.IDrawable;
import components.ILoadable;
import components.IUpdatable;
import components.Marks;
import graphics.Position;
import graphics.TovarenTvarov;
import graphics.primitives.Obrazok;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reprezentuje vseobecny predmet, ktory je mozne vykreslit na platno,
 * aktualizovat a ulozit a nacitat zo suboru Kazdy typ predmetu ma svoju
 * unikatnu znacku, nazov, popis, texturu a pripadne dalsie volitelne parametre
 *
 * @author tomas
 */
public abstract class Item implements IDrawable, IUpdatable, ILoadable {

    public static final int ITEM_MAX_SIZE = 15;

    private static final Map<Integer, ItemId> ITEM_LIST = initializeMap();

    /**
     * Definuje vsetky mozne typy predmetov s ich vsetkymi parametrami
     *
     * @return
     */
    private static Map<Integer, ItemId> initializeMap() {
        Map<Integer, ItemId> myMap = new HashMap<>();
        List<ItemId> itemIds = new ArrayList<>();

        itemIds.add(new ItemId(Marks.MARK_GUN_PISTOL, "Pistol", "Slow, but better than none", "img/items/guns/pistol.png", 1.3));
        itemIds.add(new ItemId(Marks.MARK_GUN_RIFLE, "Rifle", "Very effective against multiple enemies", "img/items/guns/rifle.png", 0.2));
        itemIds.add(new ItemId(Marks.MARK_FRUIT_APPLE, "Apple", "Fresh apple gives you a little bit of health", "img/items/fruits/apple.png", 25));
        itemIds.add(new ItemId(Marks.MARK_FRUIT_CHERRIES, "Cherries", "These cherries gives you a lot of energy", "img/items/fruits/cherries.png", 10));
        itemIds.add(new ItemId(Marks.MARK_FRUIT_WATERMELON, "Watermelon", "Just big round watermelon", "img/items/fruits/watermelon.png", 15));

        for (ItemId itemId : itemIds) {
            myMap.put(itemId.mark, itemId);
        }

        return myMap;
    }

    /**
     * Vrati vsetky parametre pozadovaneho typu predmetu na zaklade zadanej
     * znacky
     *
     * @param mark
     * @return
     */
    public static ItemId getItemId(int mark) {
        return ITEM_LIST.get(mark);
    }

    private final int mark;

    private Position position;
    private String name;
    private String description;
    private String textureFile;
    private Obrazok graphic;

    private boolean toRemove;

    /**
     * Vytvori konkretny predmet s pozadovanymi parametrami na danej pozicii, na
     * zaklade zadanej znacky
     *
     * @param mark
     * @param position
     */
    public Item(int mark, Position position) {
        this.mark = mark;
        this.position = position;

        ItemId itemId = Item.getItemId(mark);
        this.name = itemId.name;
        this.description = itemId.description;
        this.textureFile = itemId.file;

        this.graphic = TovarenTvarov.vytvorObrazok(position.x, position.y, textureFile, true);

        this.toRemove = false;
    }

    /**
     * Vytvori prazdnu instanciu predmetu so zadanou znackou
     *
     * @param mark
     */
    public Item(int mark) {
        this.mark = mark;
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        int x = dataInput.readInt();
        int y = dataInput.readInt();
        this.position = new Position(x, y);

        this.name = dataInput.readUTF();
        this.description = dataInput.readUTF();
        this.textureFile = dataInput.readUTF();
        this.graphic = TovarenTvarov.vytvorObrazok(position.x, position.y, textureFile, true);

        this.toRemove = dataInput.readBoolean();
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        dataOutput.writeInt(position.x);
        dataOutput.writeInt(position.y);

        dataOutput.writeUTF(name);
        dataOutput.writeUTF(description);
        dataOutput.writeUTF(textureFile);

        dataOutput.writeBoolean(toRemove);
    }

    @Override
    public final int getMark() {
        return this.mark;
    }

    public final String getDescription() {
        return description;
    }

    public final String getName() {
        return name;
    }

    @Override
    public final Position getPosition() {
        return this.position;
    }

    @Override
    public final void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public final void draw() {
        //ak je rozdielna poloha objektu a jeho grafickej reprezentacie
        if (!this.getPosition().equals(this.graphic.getPosition())) {
            this.graphic.setPosition(this.getPosition());
        }
    }

    @Override
    public final void hide() {
        this.graphic.hide();
    }

    @Override
    public final int getWidth() {
        return this.graphic.getWidth();
    }

    @Override
    public final int getHeight() {
        return this.graphic.getHeight();
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
    public abstract void update();
}
