package components;

import components.bullets.Bullet;
import components.characters.Character;
import components.characters.player.Player;
import components.inventory.Item;
import components.map.BoxMapTile;
import components.map.WallMapTile;
import java.util.List;

/**
 * Trieda deteguje kolizie vykreslitelnych objektov na platne a korektne na ne
 * reaguje
 *
 * @author tomas
 */
public class CollisionHandler {

    private final List<IDrawable> drawables;

    /**
     * Inicializuje instanciu so zoznamom vsetkych vykreslitelnych objektov na
     * platne
     *
     * @param drawables vykreslitelne objekty na platne
     */
    public CollisionHandler(List<IDrawable> drawables) {
        this.drawables = drawables;
    }

    /**
     * Zisti ci je pozicia vykreslitelneho objektu na platne platna Kontroluje
     * ci objekt nie je mimo hranic okna a ci nekoliduje s inym objektom
     *
     * @param object vykreslitelny objekt
     * @return
     */
    public boolean isPositionValid(IDrawable object) {
        return !(isOutOfWindowBounds(object) || collidingWithOthers(object));
    }

    /**
     * Zisti ci sa dany objekt nachadza mimo hranic okna
     *
     * @param object
     */
    private boolean isOutOfWindowBounds(IDrawable object) {
        int windowLeftUpperX = GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x;
        int windowLeftUpperY = GameSettings.GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y;
        int windowWidth = GameSettings.GAME_ACTIVE_WINDOW_WIDTH;
        int windowHeight = GameSettings.GAME_ACTIVE_WINDOW_HEIGHT;

        return (object.getPosition().x < windowLeftUpperX)
                || (object.getPosition().x + object.getWidth() > windowLeftUpperX + windowWidth)
                || (object.getPosition().y < windowLeftUpperY)
                || (object.getPosition().y + object.getHeight() > windowLeftUpperY + windowHeight);
    }

    /**
     * Zisti ci dany objekt koliduje s inym objektom na platne a korektne na
     * prislusnu koliziu zareaguje
     *
     * @param object
     */
    private boolean collidingWithOthers(IDrawable object) {

        //HRAC
        if (object instanceof Player) {

            for (IDrawable drawable : drawables) {
                if (object != drawable) {

                    //KOLIDUJE S PREDMETOM
                    if (drawable instanceof Item && areColliding(object, drawable)) {
                        ((Player) object).getInventory().addItem((Item) drawable);
                        return true;
                    }
                }
            }
        }

        //POSTAVA
        if (object instanceof Character) {

            for (IDrawable drawable : drawables) {
                if (object != drawable) {

                    //KOLIDUJE S WALL
                    if (drawable instanceof WallMapTile && areColliding(object, drawable)) {
                        return true;
                    }

                    //KOLIDUJE S BOXOM
                    if (drawable instanceof BoxMapTile && areColliding(object, drawable)) {
                        return true;
                    }

                    //KOLIDUJE S INOU POSTAVOU
                    if (drawable instanceof Character && areColliding(object, drawable)) {
                        return true;
                    }
                }
            }
        }

        //STRELA
        if (object instanceof Bullet) {

            for (IDrawable drawable : drawables) {
                if (object != drawable) {

                    //KOLIDUJE S POSKODITELNYM OBJEKTOM
                    if (drawable instanceof IDamageable && areColliding(object, drawable)) {
                        ((IDamageable) drawable).takeDamage(((Bullet) object).getDamageValue());
                        ((Bullet) object).setToRemove();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Zisti ci dane dva vykreslitelne objekty spolu koliduju na platne
     *
     * @param object1
     * @param object2
     * @return
     */
    private boolean areColliding(IDrawable object1, IDrawable object2) {
        boolean collisionX = (object1.getPosition().x + object1.getWidth() < object2.getPosition().x)
                ^ (object2.getPosition().x + object2.getWidth() < object1.getPosition().x);

        boolean collisionY = (object1.getPosition().y + object1.getHeight() < object2.getPosition().y)
                ^ (object2.getPosition().y + object2.getHeight() < object1.getPosition().y);

        return !collisionX && !collisionY;
    }
}
