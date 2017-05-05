package components.map;

import components.GameSettings;
import components.Marks;
import components.characters.monster.Monster;
import components.characters.monster.MonsterIntelligentControl;
import components.characters.monster.MonsterRandomControl;
import components.characters.player.Player;
import components.characters.player.PlayerControl;
import components.inventory.Fruit;
import components.inventory.Gun;
import graphics.Position;
import graphics.TovarenTvarov;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Trieda sa stara o vytvorenie mapy, hernych objektov a nastavenie vzhladu okna
 *
 * @author tomas
 */
public class MapGenerator {

    /**
     * Vygeneruje vsetky herne objekty
     *
     * @return zoznam vygenerovanych objektov
     */
    public static Object[] createMap() {

        Position leftUpperCorner = GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER;

        ArrayList<Object> gameObjects = new ArrayList<>();

        for (int i = leftUpperCorner.x + 80; i < 400; i += 70) {
            gameObjects.add(new WallMapTile(Marks.MARK_WALL, new Position(i, leftUpperCorner.y + 60), 800));
            gameObjects.add(new BoxMapTile(Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX, new Position(i + 35, leftUpperCorner.y + 60)));
        }

        for (int i = leftUpperCorner.y + 140; i < 400; i += 35) {
            gameObjects.add(new WallMapTile(Marks.MARK_WALL, new Position(300, i), 500));
        }

        for (int i = leftUpperCorner.y + 50; i < 400; i += 105) {
            gameObjects.add(new WallMapTile(Marks.MARK_WALL, new Position(leftUpperCorner.x + 650, i), 1000));
            gameObjects.add(new BoxMapTile(Marks.MARK_BOX_MAP_TILE_AMMO_BOX, new Position(leftUpperCorner.x + 650, i + 35)));
            gameObjects.add(new BoxMapTile(Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX, new Position(leftUpperCorner.x + 650, i + 70)));
        }

        //POSTAVY
        Player player = new Player(new Position(100, 150), 70, 100);
        gameObjects.add(new PlayerControl(player));

        Monster monster = new Monster(new Position(200, 300), 100, 100);
        gameObjects.add(new MonsterRandomControl(monster));

        Monster monster2 = new Monster(new Position(450, 300), 100, 100);
        gameObjects.add(new MonsterIntelligentControl(monster2));

        Monster monster3 = new Monster(new Position(550, 200), 100, 100);
        gameObjects.add(new MonsterIntelligentControl(monster3));

        WallMapTile wall = new WallMapTile(Marks.MARK_WALL, new Position(200, 400), 500);
        gameObjects.add(wall);

        BoxMapTile box = new BoxMapTile(Marks.MARK_BOX_MAP_TILE_AMMO_BOX, new Position(250, 400));
        gameObjects.add(box);

        Fruit fruit1 = Fruit.getRandomFruit(new Position(450, 400));
        gameObjects.add(fruit1);

        Fruit fruit2 = Fruit.getRandomFruit(new Position(500, 430));
        gameObjects.add(fruit2);

        Gun gun = Gun.getRandomGun(new Position(550, 400));
        gameObjects.add(gun);

        return gameObjects.toArray();
    }

    /**
     * Nastavi pozadie inventara a vrchnej listy
     */
    public static void createWindow() {

        //POZADIE INVENTARA
        TovarenTvarov.vytvorObdlznik(GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x,
                GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y
                + GameSettings.GAME_ACTIVE_WINDOW_HEIGHT,
                GameSettings.GAME_ACTIVE_WINDOW_WIDTH,
                GameSettings.GAME_BOTTOM_BAR_HEIGHT,
                Color.lightGray,
                true);

        //POZADIE VRCHNEJ LISTY
        TovarenTvarov.vytvorObdlznik(GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x,
                GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y - GameSettings.GAME_TOP_BAR_HEIGHT,
                GameSettings.GAME_ACTIVE_WINDOW_WIDTH,
                GameSettings.GAME_TOP_BAR_HEIGHT,
                Color.lightGray,
                true);
    }
}
