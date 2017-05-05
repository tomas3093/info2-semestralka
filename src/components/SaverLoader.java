package components;

import application.Game;
import components.characters.CharacterControl;
import components.characters.monster.MonsterIntelligentControl;
import components.characters.monster.MonsterRandomControl;
import components.characters.player.Player;
import components.characters.player.PlayerControl;
import components.inventory.Fruit;
import components.inventory.Gun;
import components.inventory.Item;
import components.map.BoxMapTile;
import components.map.MapGenerator;
import components.map.WallMapTile;
import graphics.Position;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Trieda sa stara o ukladanie a nacitavanie hry zo suboru
 *
 * @author tomas
 */
public class SaverLoader {

    private static final File SAVE_FILE = new File("saves/file.bin");
    private static final Map<Integer, ILoadable> PROTOTYPES = initializeMap();

    /**
     * Inicializuje HashMap s prototypmi objektov, ktore su schopne sa ulozit a
     * nacitat Ako kluce su pouzite unikatne znacky z triedy Marks a ako hodnoty
     * su prazdne instancie konkretnych objektov
     *
     * @return
     */
    private static Map<Integer, ILoadable> initializeMap() {
        Map<Integer, ILoadable> myMap = new HashMap<>();

        myMap.put(Marks.MARK_WALL, new WallMapTile(Marks.MARK_WALL));
        myMap.put(Marks.MARK_BOX_MAP_TILE_AMMO_BOX, new BoxMapTile(Marks.MARK_BOX_MAP_TILE_AMMO_BOX));
        myMap.put(Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX, new BoxMapTile(Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX));
        myMap.put(Marks.MARK_FRUIT_APPLE, new Fruit(Marks.MARK_FRUIT_APPLE));
        myMap.put(Marks.MARK_FRUIT_CHERRIES, new Fruit(Marks.MARK_FRUIT_CHERRIES));
        myMap.put(Marks.MARK_FRUIT_WATERMELON, new Fruit(Marks.MARK_FRUIT_WATERMELON));
        myMap.put(Marks.MARK_GUN_PISTOL, new Gun(Marks.MARK_GUN_PISTOL));
        myMap.put(Marks.MARK_GUN_RIFLE, new Gun(Marks.MARK_GUN_RIFLE));
        myMap.put(Marks.MARK_CHARACTER_CONTROL_MONSTER_INTELLIGENT_CONTROL, new MonsterIntelligentControl());
        myMap.put(Marks.MARK_CHARACTER_CONTROL_MONSTER_RANDOM_CONTROL, new MonsterRandomControl());

        return myMap;
    }

    /**
     * Ulozi vsetky herne objekty a cely stav hry.
     *
     * @param updatables aktualizovatelne objekty
     * @param drawables vykreslitelne objekty
     */
    public static void saveGame(List<IUpdatable> updatables, List<IDrawable> drawables) {

        //ZOSTAVI ZOZNAM VSETKYCH HERNYCH OBJEKTOV
        ArrayList<Object> gameObjects = new ArrayList<>();
        gameObjects.addAll(updatables);
        for (IDrawable drawable : drawables) {
            if (!gameObjects.contains(drawable)) {
                gameObjects.add(drawable);
            }
        }

        //### VYTVARANIE SAVE SUBORU ###
        try {
            try (FileOutputStream fos = new FileOutputStream(SAVE_FILE); DataOutputStream dos = new DataOutputStream(fos)) {

                //ULOZI POZICIU HRACA
                Player player = null;
                Iterator<Object> iterator = gameObjects.iterator();
                while (iterator.hasNext()) {
                    Object gameObject = iterator.next();

                    //AK JE TO HRAC
                    if (gameObject instanceof Player) {
                        player = (Player) gameObject;
                        iterator.remove();
                    }
                }

                if (player != null) {
                    dos.writeInt(player.getPosition().x);
                    dos.writeInt(player.getPosition().y);
                    dos.writeInt(player.getHp());
                    dos.writeInt(player.getMaxHp());

                    //ULOZI INVENTAR HRACA
                    List<Item> inventoryItems = player.getInventory().getItems();
                    dos.writeInt(inventoryItems.size());                                //POCET POLOZIEK

                    Iterator<Item> iteratorItem = inventoryItems.iterator();
                    while (iteratorItem.hasNext()) {
                        Item item = (Item) iteratorItem.next();

                        dos.writeInt(item.getMark());                                   //ZNACKA
                        item.save(dos);                                                 //ULOZENIE OBJEKTU

                        gameObjects.remove(item);                                       //ODSTRANI OBJEKT ZO ZOZNAMU
                    }
                }

                //ULOZI VSETKY OSTATNE OBJEKTY Z PROTOTYPOV
                for (Map.Entry<Integer, ILoadable> entry : PROTOTYPES.entrySet()) {
                    ILoadable loadableObject = entry.getValue();
                    saveAllInstancesOfClass(gameObjects, loadableObject.getClass(), dos);
                }
            }

        } catch (IOException e) {
        }
    }

    /**
     * Nacita vsetky ulozene objekty a stav hry zo suboru a nacita ho do
     * instancie Game
     */
    public static void loadGame() {
        ArrayList<IUpdatable> updatables = new ArrayList<>();
        ArrayList<IDrawable> drawables = new ArrayList<>();

        MapGenerator.createWindow();

        try {
            try (FileInputStream fis = new FileInputStream(SAVE_FILE); DataInputStream dis = new DataInputStream(fis)) {

                //NACITA HRACA
                int x = dis.readInt();
                int y = dis.readInt();
                int hp = dis.readInt();
                int maxHp = dis.readInt();

                Player player = new Player(new Position(x, y), hp, maxHp);
                PlayerControl playerControl = new PlayerControl(player);
                drawables.add(player);
                updatables.add(playerControl);

                //NACITA INVENTAR
                int count = dis.readInt();                      //POCET PREDMETOV
                for (int i = 0; i < count; i++) {
                    int mark = dis.readInt();                   //NACITA ZNACKU
                    Item item = (Item) createObjectCopy(mark);  //VYTVORI PRAZDNU INSTANCIU
                    item.load(dis);                             //NACITA INSTANCIU
                    updatables.add(item);
                    drawables.add(item);
                    player.getInventory().addItem(item);        //PRIDA PREDMET DO INVENTARA
                }

                //NACITA VSETKY OSTATNE OBJEKTY Z PROTOTYPOV
                while (dis.available() > 0) {
                    int mark = dis.readInt();
                    List<? extends Object> loadedObjects = loadAllInstancesOfClass(mark, dis);

                    //VSETKY CONTROLLERY OKREM PLAYERA
                    if ((PROTOTYPES.get(mark) instanceof CharacterControl) && !(PROTOTYPES.get(mark) instanceof PlayerControl)) {
                        for (Object loadedObject : loadedObjects) {
                            updatables.add((CharacterControl) loadedObject);
                            drawables.add(((CharacterControl) loadedObject).getControlledCharacter());
                        }
                        continue;
                    }

                    if (PROTOTYPES.get(mark) instanceof IUpdatable) {
                        updatables.addAll((List<IUpdatable>) loadedObjects);
                    }

                    if (PROTOTYPES.get(mark) instanceof IDrawable) {
                        drawables.addAll((List<IDrawable>) loadedObjects);
                    }
                }

            }

        } catch (IOException e) {
        }

        //NACITA HRU
        Game.loadGame(updatables, drawables);
    }

    /**
     * Ulozi do suboru vsetky instancie danej triedy z daneho zoznamu a tieto
     * instancie nasledne zo zoznamu odstrani
     *
     * @param <T> objektovy typ - iba objekty, ktore su schopne sa ulozit
     * @param gameObjects zoznam vsetkych objektov, z ktoreho sa maju vybrat
     * @param type trieda, ktorej instancie sa maju ulozit
     * @param dos objekt, pomocou ktoreho sa maju objekty zapisat
     * @throws IOException
     */
    private static <T extends ILoadable> void saveAllInstancesOfClass(ArrayList<Object> gameObjects, Class<T> type, DataOutputStream dos) throws IOException {

        //Zisti kolko objektov sa ide zapisovat 
        Iterator<Object> iterator = gameObjects.iterator();
        ArrayList<T> elementsToSave = new ArrayList<>();
        while (iterator.hasNext()) {
            Object gameObject = iterator.next();

            //OBJECT INSTANCEOF GENERIC TYPE
            if (gameObject.getClass().isAssignableFrom(type)) {
                elementsToSave.add((T) gameObject);
                iterator.remove();
            }
        }

        //Ulozi vsetky objekty daneho generickeho typu
        if (!elementsToSave.isEmpty()) {
            dos.writeInt(elementsToSave.get(0).getMark());      //ZNACKA TYPU ELEMENTU
            dos.writeInt(elementsToSave.size());                //POCET ZAPISANYCH ELEMENTOV
            for (T element : elementsToSave) {
                element.save(dos);                              //ULOZENIE JEDNOTLIVYCH ELEMENTOV
            }
        }

    }

    /**
     * Nacita instancie danej triedy na zaklade zadanej znacky a vrati zoznam
     * nacitanych instancii
     *
     * @param mark znacka, reprezentujuca typ objektov, ktore sa maju nacitat
     * @param dis objekt, pomocou ktoreho sa nacitavaju instancie
     * @return List nacitanych hernych objektov
     * @throws IOException
     */
    private static List<ILoadable> loadAllInstancesOfClass(int mark, DataInputStream dis) throws IOException {

        List<ILoadable> loadedObjects = new ArrayList<>();

        int count = dis.readInt();
        for (int j = 0; j < count; j++) {
            ILoadable gameObject = SaverLoader.createObjectCopy(mark);
            gameObject.load(dis);

            loadedObjects.add(gameObject);
        }

        return loadedObjects;
    }

    /**
     * Na zaklade zadanej znacky vrati novu instanciu daneho objektu
     *
     * @param mark znacka identifikujuca o aky typ objektov sa jedna
     * @return nova prazdna instancia pozadovaneho objektu
     */
    private static ILoadable createObjectCopy(int mark) {
        switch (mark) {

            //BOXES
            case Marks.MARK_BOX_MAP_TILE_AMMO_BOX:
            case Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX:
                return new BoxMapTile(mark);

            //FRUITS
            case Marks.MARK_FRUIT_APPLE:
            case Marks.MARK_FRUIT_CHERRIES:
            case Marks.MARK_FRUIT_WATERMELON:
                return new Fruit(mark);

            //GUNS
            case Marks.MARK_GUN_PISTOL:
            case Marks.MARK_GUN_RIFLE:
                return new Gun(mark);

            //WALLS
            case Marks.MARK_WALL:
                return new WallMapTile(mark);

            //CHARACTER CONTROLS
            case Marks.MARK_CHARACTER_CONTROL_MONSTER_INTELLIGENT_CONTROL:
                return new MonsterIntelligentControl();
            case Marks.MARK_CHARACTER_CONTROL_MONSTER_RANDOM_CONTROL:
                return new MonsterRandomControl();

            default:
                return null;
        }
    }
}
