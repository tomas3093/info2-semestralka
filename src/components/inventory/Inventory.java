package components.inventory;

import components.GameSettings;
import components.characters.player.Player;
import graphics.Position;
import graphics.TovarenTvarov;
import graphics.primitives.TextovePole;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventar sa dokaze vykreslit na zadanom mieste, pamata si ktoremu hracovi
 * patri a dokaze si pridavat a odoberat predmety
 *
 * @author tomas
 */
public class Inventory {

    private static final int INVENTORY_ITEM_CAPACITY = 9;
    private static final int INVENTORY_INDEX_LABELS_HEIGHT = 18;
    private static final double INVENTORY_ITEM_SPACING = 3.5;
    private static final Color INVENTORY_ITEM_HIGHLIGHTED_LABEL_COLOR = new Color(0, 178, 26);
    private static final Color INVENTORY_ITEM_DEFAULT_LABEL_COLOR = Color.gray;

    private final List<Item> items;
    private final Position position;
    private final Player owner;

    private final TextovePole[] inventoryIndexLabels;
    private final TextovePole itemInfoField;

    /**
     * Vytvori inventar daneho hraca na danej pozicii
     *
     * @param position
     * @param player
     */
    public Inventory(Position position, Player player) {
        this.items = new ArrayList<>();
        this.position = position;
        this.owner = player;

        //CISLA NAD POLOZKAMI INVENTARA
        this.inventoryIndexLabels = new TextovePole[INVENTORY_ITEM_CAPACITY];
        for (int i = 0; i < inventoryIndexLabels.length; i++) {
            inventoryIndexLabels[i] = TovarenTvarov.vytvorTextovePole(position.x + (int) Math.round(i * Item.ITEM_MAX_SIZE * INVENTORY_ITEM_SPACING),
                    position.y,
                    (i + 1) + "",
                    INVENTORY_INDEX_LABELS_HEIGHT,
                    true,
                    INVENTORY_ITEM_DEFAULT_LABEL_COLOR,
                    true);
        }

        //Info field pre popisy predmetov
        this.itemInfoField = TovarenTvarov.vytvorTextovePole(position.x,
                GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y
                + GameSettings.GAME_ACTIVE_WINDOW_HEIGHT
                + GameSettings.GAME_BOTTOM_BAR_HEIGHT - INVENTORY_INDEX_LABELS_HEIGHT - 5,
                "", 18, true, Color.BLACK, true);
    }

    public List<Item> getItems() {
        return items;
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Prida dany predmet do inventara
     *
     * @param newItem
     */
    public void addItem(Item newItem) {
        if (this.items.size() < INVENTORY_ITEM_CAPACITY) {
            this.items.add(newItem);
            setItemInfoField(newItem);
            redrawItems();
        }
    }

    /**
     * Odstrani dany predmet z inventara
     *
     * @param whichItem
     */
    public void removeItem(Item whichItem) {
        this.items.remove(whichItem);
        whichItem.setToRemove();

        redrawItems();
    }

    /**
     * Pouzije dany predmet v inventari
     *
     * @param which Ktory predmet (cislovanie od 1)
     */
    public void useItem(int which) {
        if (which <= this.items.size()) {

            Item itemToUse = this.items.get(which - 1);

            if (itemToUse instanceof Fruit) {
                this.owner.heal(((Fruit) itemToUse).getValue());
                removeItem(itemToUse);
            } else if (itemToUse instanceof Weapon) {
                this.owner.equipWeapon((Weapon) itemToUse);
            } else if (true) {
                //DALSIE DRUHY PREDMETOV
            }

            //ZOBRAZI POPIS PRE POUZITY PREDMET
            setItemInfoField(itemToUse);

            //ZVYRAZNI AKTUALNE EQUIPNUTU ZBRAN
            highlightItem(this.owner.getEquippedWeapon());
        }
    }

    /**
     * Zvyrazni dany predmet v inventari inou farbou a ostatne nastavi na
     * default
     *
     * @param item Ktory predmet sa ma zvyraznit
     */
    private void highlightItem(Item itemToHighlight) {
        for (int i = 0; i < this.inventoryIndexLabels.length; i++) {
            if (i < this.items.size()) {
                if (i == this.items.indexOf(itemToHighlight)) {
                    this.inventoryIndexLabels[i].setColor(INVENTORY_ITEM_HIGHLIGHTED_LABEL_COLOR);
                } else {
                    this.inventoryIndexLabels[i].setColor(INVENTORY_ITEM_DEFAULT_LABEL_COLOR);
                }
            } else {
                this.inventoryIndexLabels[i].setColor(INVENTORY_ITEM_DEFAULT_LABEL_COLOR);
            }
        }
    }

    /**
     * Zobrazi informacie o danom predmete
     *
     * @param item
     */
    private void setItemInfoField(Item item) {
        String result = item.getName() + " | " + item.getDescription();
        this.itemInfoField.setText(result);
    }

    /**
     * Prepocita pozicie a nanovo prekresli vsetky Itemy v inventari
     */
    private void redrawItems() {
        for (Item item : items) {
            int index = this.items.indexOf(item);

            int x = this.position.x + (int) Math.round(index * Item.ITEM_MAX_SIZE * INVENTORY_ITEM_SPACING);
            int y = this.position.y + INVENTORY_INDEX_LABELS_HEIGHT + 5;

            item.setPosition(new Position(x, y));
        }
    }
}
