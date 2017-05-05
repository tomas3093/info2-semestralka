package application;

import components.SaverLoader;
import components.CollisionHandler;
import components.GameSettings;
import components.IDrawable;
import components.Manazer;
import graphics.TovarenTvarov;
import java.util.ArrayList;
import java.util.List;
import components.IUpdatable;
import components.characters.CharacterControl;
import components.characters.player.Player;
import components.characters.player.PlayerControl;
import components.map.MapGenerator;
import graphics.primitives.TextovePole;
import java.awt.Color;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 * Trieda reprezentuje hlavnu triedu v ktorej prebieha cela hra. V celej
 * aplikacii existuje iba jedina instancia.
 *
 * @author tomas
 */
public class Game {

    public static final Manazer MANAZER = new Manazer();
    private static Game instance;

    /**
     * Main metoda aplikacie. Otvori dialog, s volbou nacitania hry a potom
     * spusti celu hru.
     *
     * @param args parametre
     */
    public static void main(String[] args) {
        //UVODNY DIALOG
        if (JOptionPane.showConfirmDialog(null, "Do you want to load previous save?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // YES option
            //Nacita hru zo suboru
            SaverLoader.loadGame();

        } else {
            // NO option
            //Spusti novu hru
        }
        Game game = Game.getInstance();
    }

    /**
     * Tovarenska metoda, vytvori instanciu hry a zaregistruje ju v manazeri
     *
     * @return Instancia hry
     */
    public static Game getInstance() {
        if (Game.instance == null) {

            Game.instance = new Game();
            Game.MANAZER.spravujObjekt(Game.instance);
        }

        return Game.instance;
    }

    /**
     * Inicializuje hru s danymi hernymi objektami, ktore boli nacitane zo
     * suboru
     *
     * @param updatables Aktualizovatelne herne objekty
     * @param drawables Vykreslitelne herne objekty
     */
    public static void loadGame(ArrayList<IUpdatable> updatables, ArrayList<IDrawable> drawables) {
        Game.instance = new Game(updatables, drawables);
        Game.MANAZER.spravujObjekt(Game.instance);
    }

    private final List<IUpdatable> updatables;
    private final List<IDrawable> drawables;
    private final CollisionHandler collisionHandler;

    private final TextovePole notificationField;

    /**
     * Konstruktor pre inicializovanie novej instancie hry. Pomocou triedy
     * MapGenerator vytvori mapu a vsetky herne objekty a zaregistruje ich do
     * hry
     */
    private Game() {
        this.updatables = new ArrayList<>();
        this.drawables = new ArrayList<>();
        this.collisionHandler = new CollisionHandler(this.drawables);

        //Nastavi pozadie okna
        MapGenerator.createWindow();

        //NOTIFIKACNA LISTA
        this.notificationField = TovarenTvarov.vytvorTextovePole((int) Math.round(GameSettings.GAME_ACTIVE_WINDOW_WIDTH * (1 / 3.0)),
                0, "", 20, true, Color.RED, true);

        //### VYTVORI MAPU A VSETKY OBJEKTY ###
        Object[] gameObjects = MapGenerator.createMap();
        for (Object gameObject : gameObjects) {
            addGameObject(gameObject);
        }

        //Zaregistruje vsetky controllery v manazeri
        for (IUpdatable controller : updatables) {
            Game.MANAZER.spravujObjekt(controller);
        }
    }

    /**
     * Konstruktor pre vytvorenie hry z uz existujucich hernych objektov
     * (nacitanych napr. zo suboru)
     *
     * @param updatables Aktualizovatelne herne objekty
     * @param drawables Vykreslitelne herne objekty
     */
    private Game(List<IUpdatable> updatables, List<IDrawable> drawables) {
        this.updatables = updatables;
        this.drawables = drawables;
        this.collisionHandler = new CollisionHandler(drawables);

        //NOTIFIKACNA LISTA
        this.notificationField = TovarenTvarov.vytvorTextovePole((int) Math.round(GameSettings.GAME_ACTIVE_WINDOW_WIDTH * (1 / 3.0)),
                0, "Game loaded!", 20, true, Color.RED, true);

        //Zaregistruje vsetky controllery v manazeri
        for (IUpdatable controller : updatables) {
            Game.MANAZER.spravujObjekt(controller);
        }
    }

    public final CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    /**
     * Korektne zaregistruje do hry novy herny objekt a vsetky jeho zavisle
     * objekty
     *
     * @param object objekt, ktory sa ma pridat
     */
    public final void addGameObject(Object object) {
        if (object instanceof IUpdatable) {

            //AK je objekt CharacterControl, TAK ZAREGISTRUJ AJ POSTAVU
            if (object instanceof CharacterControl) {
                this.updatables.add((IUpdatable) object);
                addGameObject(((CharacterControl) object).getControlledCharacter());        //rekurzia
            } else {
                this.updatables.add((IUpdatable) object);
            }
        }

        if (object instanceof IDrawable) {
            this.drawables.add((IDrawable) object);
        }
    }

    /**
     * Hlavna slucka hry. Vola sa kazdy frame. Aktualizuje stav vsetkych
     * objektov a prekresluje platno
     */
    public final void frame() {
        this.update();
        this.draw();
    }

    /**
     * Zavola sa po stlaceni tlacidla pre ulozenie. Pomocou triedy SaverLoader
     * ulozi aktualny stav hry do suboru
     */
    public final void pressedSaveKey() {
        SaverLoader.saveGame(updatables, drawables);
        this.notificationField.setText("Game saved!");
    }

    /**
     * Aktualizuje stav hernych objektov a odstrani uz nepotrebne objekty
     */
    private void update() {

        //Update vsetkych aktualizovatelnych objektov
        for (IUpdatable updatable : updatables) {
            updatable.update();
        }

        //Odstrani nepotrebne objekty
        Iterator<IUpdatable> iterator = this.updatables.iterator();
        while (iterator.hasNext()) {
            IUpdatable object = iterator.next();

            if (object.isToRemove()) {
                removeGameObject(object);
            }
        }
    }

    /**
     * Prekresli vsetky objekty ktore sa zobrazuju na platne
     */
    private void draw() {
        for (IDrawable drawable : drawables) {
            drawable.draw();
        }
    }

    /**
     * Bezpecne odstrani herny objekt z hry a aj vsetky zavisle objekty
     *
     * @param object objekt, ktory sa ma odstranit
     */
    private void removeGameObject(Object object) {

        //ODSTRANENIE HRACA => KONIEC HRY
        if (object instanceof Player || object instanceof PlayerControl) {
            gameOver();
            return;
        }

        if (object instanceof CharacterControl) {
            removeGameObject(((CharacterControl) object).getControlledCharacter());     //Rekurzia
        }

        if (object instanceof IUpdatable) {
            this.updatables.remove((IUpdatable) object);
        }

        if (object instanceof IDrawable) {
            this.drawables.remove((IDrawable) object);
            ((IDrawable) object).hide();
        }

    }

    /**
     * KONIEC HRY
     */
    private void gameOver() {
        this.notificationField.setText("GAME OVER!");
        //System.exit(0);
    }
}
