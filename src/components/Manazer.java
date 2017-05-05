package components;

import graphics.Platno;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

/**
 * Automaticky posiela spravy spravovanym objektom ak nastanu definovane
 * udalosti
 */
public class Manazer {

    private final List<Object> spravovaneObjekty;
    private final List<Integer> vymazaneObjekty;
    private long oldTick;

    //FPS HRY
    private static final long TICK_LENGTH = 1000000000 / GameSettings.GAME_FPS;

    private class ManazerKlaves extends KeyAdapter {

        /**
         * KeyPress events
         *
         * @param event event
         */
        @Override
        public void keyPressed(KeyEvent event) {

            switch (event.getKeyCode()) {
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_DOWN:
                    Manazer.this.posliSpravu("pressedPlayerOneDownKey");
                    break;
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_UP:
                    Manazer.this.posliSpravu("pressedPlayerOneUpKey");
                    break;
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_LEFT:
                    Manazer.this.posliSpravu("pressedPlayerOneLeftKey");
                    break;
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_RIGHT:
                    Manazer.this.posliSpravu("pressedPlayerOneRightKey");
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_ATTACK:
                    Manazer.this.posliSpravu("pressedPlayerOneAttackKey");
                    break;

                case GameSettings.GAME_CONTROL_EXIT_KEY:
                    Manazer.this.posliSpravu("pressedExitKey");
                    break;

                case GameSettings.GAME_CONTROL_SAVE_KEY:
                    Manazer.this.posliSpravu("pressedSaveKey");
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION1:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 1);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION2:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 2);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION3:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 3);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION4:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 4);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION5:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 5);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION6:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 6);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION7:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 7);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION8:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 8);
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_INVENTORY_SELECTION9:
                    Manazer.this.posliSpravu("pressedPlayerOneInventorySelection", 9);
                    break;

                default:
                    break;
            }
        }

        /**
         * KeyRelease events
         *
         * @param event event
         */
        @Override
        public void keyReleased(KeyEvent event) {
            switch (event.getKeyCode()) {
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_DOWN:
                    Manazer.this.posliSpravu("releasedPlayerOneDownKey");
                    break;
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_UP:
                    Manazer.this.posliSpravu("releasedPlayerOneUpKey");
                    break;
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_LEFT:
                    Manazer.this.posliSpravu("releasedPlayerOneLeftKey");
                    break;
                case GameSettings.GAME_CONTROL_PLAYER1_KEY_RIGHT:
                    Manazer.this.posliSpravu("releasedPlayerOneRightKey");
                    break;

                case GameSettings.GAME_CONTROL_PLAYER1_KEY_ATTACK:
                    Manazer.this.posliSpravu("releasedPlayerOneAttackKey");
                    break;

                default:
                    break;
            }
        }
    }

    private class ManazerCasovaca implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            long newTick = System.nanoTime();
            if (newTick - Manazer.this.oldTick >= Manazer.TICK_LENGTH || newTick < Manazer.TICK_LENGTH) {
                Manazer.this.oldTick = (newTick / Manazer.TICK_LENGTH) * Manazer.TICK_LENGTH;
                Manazer.this.posliSpravu("frame");
            }
        }
    }

    private class ManazerMysi extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            //left mouse
            if (event.getButton() == MouseEvent.BUTTON1) {
                Manazer.this.posliSpravu("kliknutieMysou", event.getX(), event.getY());
            }
            //right mouse
            if (event.getButton() == MouseEvent.BUTTON3) {
                Manazer.this.posliSpravu("kliknutieMysouPravym", event.getX(), event.getY());
            }
        }
    }

    private void posliSpravu(String selektor) {

        for (Object adresat : this.spravovaneObjekty) {
            try {
                if (adresat != null) {
                    Method sprava = adresat.getClass().getMethod(selektor);
                    sprava.invoke(adresat);
                }
            } catch (SecurityException e) {
                this.doNothing();
            } catch (NoSuchMethodException e) {
                this.doNothing();
            } catch (IllegalArgumentException e) {
                this.doNothing();
            } catch (IllegalAccessException e) {
                this.doNothing();
            } catch (InvocationTargetException e) {
                this.doNothing();
            }
        }
        this.removeDeletedObjects();

    }

    private void posliSpravu(String selektor, int prvyParameter) {

        for (Object adresat : this.spravovaneObjekty) {
            try {
                if (adresat != null) {
                    Method sprava = adresat.getClass().getMethod(selektor, Integer.TYPE);
                    sprava.invoke(adresat, prvyParameter);
                }
            } catch (SecurityException e) {
                this.doNothing();
            } catch (NoSuchMethodException e) {
                this.doNothing();
            } catch (IllegalArgumentException e) {
                this.doNothing();
            } catch (IllegalAccessException e) {
                this.doNothing();
            } catch (InvocationTargetException e) {
                this.doNothing();
            }
        }
        this.removeDeletedObjects();

    }

    private void posliSpravu(String selektor, int prvyParameter, int druhyParameter) {

        for (Object adresat : this.spravovaneObjekty) {
            try {
                if (adresat != null) {
                    Method sprava = adresat.getClass().getMethod(selektor, Integer.TYPE, Integer.TYPE);
                    sprava.invoke(adresat, prvyParameter, druhyParameter);
                }
            } catch (SecurityException e) {
                this.doNothing();
            } catch (NoSuchMethodException e) {
                this.doNothing();
            } catch (IllegalArgumentException e) {
                this.doNothing();
            } catch (IllegalAccessException e) {
                this.doNothing();
            } catch (InvocationTargetException e) {
                this.doNothing();
            }
        }
        this.removeDeletedObjects();

    }

    private void doNothing() {

    }

    private void removeDeletedObjects() {
        if (this.vymazaneObjekty.size() > 0) {
            Collections.sort(this.vymazaneObjekty, Collections.reverseOrder());
            for (int i = this.vymazaneObjekty.size() - 1; i >= 0; i--) {
                this.spravovaneObjekty.remove(this.vymazaneObjekty.get(i));
            }
            this.vymazaneObjekty.clear();
        }
    }

    /**
     * Vytvori novy manazer, ktory nespravuje zatial ziadne objekty.
     */
    public Manazer() {
        this.spravovaneObjekty = new ArrayList<>();
        this.vymazaneObjekty = new ArrayList<>();
        Platno.dajPlatno().addKeyListener(new ManazerKlaves());
        Platno.dajPlatno().addTimerListener(new ManazerCasovaca());
        Platno.dajPlatno().addMouseListener(new ManazerMysi());
    }

    /**
     * hra.gameObjects.gameParts.Manazer bude spravovat dany objekt.
     *
     * @param objekt
     */
    public void spravujObjekt(Object objekt) {
        this.spravovaneObjekty.add(objekt);
    }

    /**
     * hra.gameObjects.gameParts.Manazer prestane spravovat dany objekt.
     *
     * @param objekt
     */
    public void prestanSpravovatObjekt(Object objekt) {
        int index = this.spravovaneObjekty.indexOf(objekt);
        if (index >= 0) {
            this.spravovaneObjekty.set(index, null);
            this.vymazaneObjekty.add(index);
        }
    }
}
