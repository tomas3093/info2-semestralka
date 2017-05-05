package graphics;

import components.GameSettings;
import graphics.primitives.ITvar;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Okno, do ktoreho je mozne kreslit pomocou tvarov. Je navrhnute ako jedinacik.
 *
 * @author Michal Varga
 */
public class Platno {

    private static Platno platno;

    /**
     * Tovarenska metoda na ziskanie instancie platna.
     *
     * @return Instancia platna.
     */
    public static Platno dajPlatno() {
        if (Platno.platno == null) {
            Platno.platno = new Platno(GameSettings.GAME_TITLE, GameSettings.GAME_WIDTH, GameSettings.GAME_HEIGHT, GameSettings.GAME_BACKGROUND_COLOR);
        }

        Platno.platno.zmenViditelnost(true);
        return Platno.platno;
    }

    private final JFrame frame;
    private final CanvasAdapter adapter;
    private final CanvasPane canvas;
    private Image canvasImage;
    private Graphics2D graphics;
    private final Color farbaPozadia;
    private List<ITvar> tvary;
    private boolean prekreslujePlatno;
    private Timer timer;

    /**
     * Sukromny konstruktor znemoznuje priame vytvaranie instancie jedinacika.
     *
     * @param titulok Titulok okna.
     * @param sirka Sirka okna.
     * @param vyska Vyska okna.
     * @param farbaPozadia Farba pozadia okna.
     */
    private Platno(String titulok, int sirka, int vyska, Color farbaPozadia) {
        this.frame = new JFrame();
        this.adapter = new CanvasAdapter();
        this.canvas = new CanvasPane();
        this.frame.setContentPane(this.canvas);
        this.frame.setTitle(titulok);
        this.canvas.setPreferredSize(new Dimension(sirka, vyska));
        this.frame.addWindowListener(this.adapter);
        this.frame.setResizable(false);
        this.frame.pack();

        this.graphics = null;

        this.farbaPozadia = farbaPozadia;

        this.tvary = new ArrayList<>();

        this.prekreslujePlatno = true;

        this.timer = new Timer(25, null);
        this.timer.start();
    }

    /**
     * Vrati instanciu Graphics2D pomocou ktorej je mozne na platno kreslit.
     *
     * @return instancia Graphics2D pomocou ktorej je mozne na platno kreslit.
     */
    public Graphics2D getGraphics() {
        return graphics;
    }

    public void addKeyListener(KeyListener listener) {
        this.frame.addKeyListener(listener);
    }

    public void addTimerListener(ActionListener listener) {
        this.timer.addActionListener(listener);
    }

    public void addMouseListener(MouseListener listener) {
        this.canvas.addMouseListener(listener);
    }

    /**
     * Zmeni viditelnost platna.
     *
     * @param jeViditelne Priznak, ci je platno viditelne.
     */
    public void zmenViditelnost(boolean jeViditelne) {
        if (this.graphics == null) {
            Dimension size = this.canvas.getSize();
            this.canvasImage = this.canvas.createImage(size.width, size.height);
            this.graphics = (Graphics2D) this.canvasImage.getGraphics();
            this.graphics.setColor(this.farbaPozadia);
            this.graphics.fillRect(0, 0, size.width, size.height);

            this.prekresliVsetko();
        }
        this.frame.setVisible(jeViditelne);
    }

    /**
     * Prida na platno tvar.
     *
     * @param tvar Pridavany tvar.
     * @param zobrazTvar Ak true, necha tvar zobrazit.
     */
    public void pridajTvar(ITvar tvar, boolean zobrazTvar) {
        if (!this.tvary.contains(tvar)) {
            this.tvary.add(tvar);
            if (zobrazTvar) {
                tvar.show();
            }
        }

        this.prekresliPlatno();
    }

    /**
     * Odoberie tvar z platna. Vzdy prekresli platno.
     *
     * @param tvar
     */
    public void odoberTvar(ITvar tvar) {
        if (this.tvary.contains(tvar)) {
            this.tvary.remove(tvar);
            this.prekresliVsetko();
        }

        this.prekresliPlatno();
    }

    /**
     * Prekresli vsetky tvary na platne. Vsetky registrovane tvary budu
     * zobrazene.
     */
    public void prekresliVsetko() {
        Color originalColor = this.graphics.getColor();

        this.graphics.setColor(this.farbaPozadia);
        this.graphics.fill(new Rectangle(0, 0, this.frame.getWidth(), this.frame.getHeight()));
        this.graphics.setColor(originalColor);

        this.neprekreslujPlatno();

        for (ITvar tvar : this.tvary) {
            if (tvar.isVisible()) {
                tvar.show();
            }
        }

        this.prekreslujPlatno();

        this.prekresliPlatno();
    }

    /**
     * Odstrani z platna vsetky tvary. Vsetky registrovane tvary budu skryte.
     */
    public void vymazVsetko() {
        List<ITvar> povodneTvary = this.tvary;
        this.tvary = new ArrayList<>();

        this.neprekreslujPlatno();

        for (ITvar tvar : povodneTvary) {
            tvar.hide();
        }

        this.prekreslujPlatno();

        prekresliVsetko();
    }

    private void neprekreslujPlatno() {
        this.prekreslujePlatno = false;
    }

    private void prekreslujPlatno() {
        this.prekreslujePlatno = true;
    }

    private void prekresliPlatno() {
        if (this.prekreslujePlatno) {
            this.canvas.repaint();
        }
    }

    /**
     * Vnorena trieda rozsirujuca WindowAdapter definujuca spravanie pri
     * jedntlivych udalostiach. Vypne aplikaciu po zatvoreni platna.
     */
    private class CanvasAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    };

    /**
     * Vnorena trieda rozsirujuca JPanel definujuca canvas Platna. Upravuje
     * metodu paint, ktora iba prekresli panel obrazkom canvasImage vytvorenom v
     * Platne.
     */
    private class CanvasPane extends JPanel {

        @Override
        public void paint(Graphics graphic) {
            graphic.drawImage(Platno.this.canvasImage, 0, 0, null);
        }
    }

}
