package graphics.primitives;

import graphics.Platno;
import graphics.Position;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Adapter pre rozhranie ITvar.
 * Adapter definuje vychodziu funkcnost rozhrania.
 * Triedy pozadujuce rozhranie ITvar nemusia implementovat vsetko same, mozu dedit Tvar.
 * @author Michal Varga
 */
public abstract class Tvar implements ITvar {
    
    private final Graphics2D graphics;
    private Color farba;
    private boolean jeViditelny;
    private Position poloha;
    
    /**
     * Chraneny konstruktor.
     * Abstraktnu triedu nemozno vytvarat priamo.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param poloha Vychodzia poloha tvaru.
     */
    protected Tvar(Graphics2D graphics, Position poloha) {
        this.graphics = graphics;
        this.farba = new Color(0, 0, 0);
        this.jeViditelny = false;
        this.poloha = poloha;
    }

    /**
     * Vrati aktualnu polohu tvaru.
     * @return Aktualna poloha tvaru.
     */
    @Override
    public Position getPosition() {
        return this.poloha;
    }

    @Override
    public void setPosition(Position poloha) {
        this.poloha = poloha;

        this.aktualizujPlatno(true);
    }

    @Override
    public void show() {
        this.jeViditelny = true;

        Color originalColor = this.graphics.getColor();
        this.graphics.setColor(this.farba);
        this.ibaZobraz(this.graphics);
        this.graphics.setColor(originalColor);

        Platno platno = Platno.dajPlatno();
        platno.pridajTvar(this, false);
    }

    @Override
    public void hide() {
        this.jeViditelny = false;

        Platno platno = Platno.dajPlatno();
        platno.odoberTvar(this);
    }

    @Override
    public boolean isVisible() {
        return this.jeViditelny;
    }

    @Override
    public void setColor(Color color) {
        this.farba = color;

        this.aktualizujPlatno(false);
    }

    @Override
    public void setColor(int R, int G, int B) {
        this.farba = new Color(R, G, B);

        this.aktualizujPlatno(false);
    }

    /**
     * Ak je tvar viditelny, tak aktualizuje platno.
     * @param prekresliCelePlatno Ak true, prekresli sa cele platno, v opacnom pripade sa prekresli iba tento tvar.
     */
    protected void aktualizujPlatno(boolean prekresliCelePlatno) {
        if (this.jeViditelny) {
            if (prekresliCelePlatno) {
                Platno.dajPlatno().prekresliVsetko();
            } else {
                this.show();
            }
        }
    }

    /**
     * Volana z metody zobraz().
     * Tato metoda sa moze spolahnut na spravne nastavenie viditelnosti, farby a pod.
     * @param graphics Objekt, pomocou ktoreho sa ma nakreslit.
     */
    protected abstract void ibaZobraz(Graphics2D graphics); 

}
