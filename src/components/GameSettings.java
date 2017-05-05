package components;

import graphics.Position;
import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Defaultne nastavenia hry a konstanty
 *
 * @author tomas
 */
public class GameSettings {

    public static final int GAME_FPS = 60;

    //Nastavenia okna
    public static final Color GAME_BACKGROUND_COLOR = new Color(255, 255, 255);
    public static final Color GAME_ACTIVE_WINDOW_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    public static final String GAME_TITLE = "Semestralka";
    public static final int GAME_WIDTH = 800;                                           //celkova sirka okna
    public static final int GAME_HEIGHT = 600;                                          //celkova vyska okna

    public static final int GAME_TOP_BAR_HEIGHT = 25;
    public static final int GAME_BOTTOM_BAR_HEIGHT = 80;

    //zaciatok suradnic pre herne objekty
    public static final Position GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER = new Position(0, GAME_TOP_BAR_HEIGHT);

    //rozmery okna pre herne objekty
    public static final int GAME_ACTIVE_WINDOW_WIDTH = GAME_WIDTH;
    public static final int GAME_ACTIVE_WINDOW_HEIGHT = GAME_HEIGHT - GAME_TOP_BAR_HEIGHT - GAME_BOTTOM_BAR_HEIGHT;

    //Pozicia inventara hraca
    public static final Position PLAYER_INVENTORY_POSITION = new Position(GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x + 20,
            GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y
            + GAME_ACTIVE_WINDOW_HEIGHT);

    //Pozicia HP Indikatora hraca
    public static final Position PLAYER_HP_INDICATOR_POSITION = new Position(GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.x,
            GAME_ACTIVE_WINDOW_LEFT_UPPER_CORNER.y
            - GAME_TOP_BAR_HEIGHT);

    // ###### Herne klavesy ######
    public static final int GAME_CONTROL_PLAYER1_KEY_UP = KeyEvent.VK_UP;
    public static final int GAME_CONTROL_PLAYER1_KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int GAME_CONTROL_PLAYER1_KEY_LEFT = KeyEvent.VK_LEFT;
    public static final int GAME_CONTROL_PLAYER1_KEY_RIGHT = KeyEvent.VK_RIGHT;

    public static final int GAME_CONTROL_PLAYER1_KEY_ATTACK = KeyEvent.VK_SPACE;

    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION1 = KeyEvent.VK_1;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION2 = KeyEvent.VK_2;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION3 = KeyEvent.VK_3;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION4 = KeyEvent.VK_4;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION5 = KeyEvent.VK_5;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION6 = KeyEvent.VK_6;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION7 = KeyEvent.VK_7;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION8 = KeyEvent.VK_8;
    public static final int GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION9 = KeyEvent.VK_9;

    public static final int GAME_CONTROL_EXIT_KEY = KeyEvent.VK_ESCAPE;
    public static final int GAME_CONTROL_SAVE_KEY = KeyEvent.VK_S;

    private GameSettings() {

    }
}
