package graphics.primitives;

import graphics.Position;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Obrazok.
 * @author Michal
 */
public class Obrazok extends Tvar {

    private int uhol;
    private BufferedImage obrazok;
    
    /**
     * Vytvori obrazok s danou polohou a rozmermi.
     * @param graphics Objekt zabezpecujuci kreslenie.
     * @param poloha Lavy horny roh obrazka.
     * @param subor Subor s obrazkom, ktory sa ma nacitat.
     */
    public Obrazok(Graphics2D graphics, Position poloha, String subor) {
        super(graphics, poloha);
        
        this.uhol = 0;
        obrazok = this.nacitajObrazokZoSuboru(subor);
    }
    
    /**
     * Vytvori obrazok s danou polohou a rozmermi
     * @param graphics2D Objekt zabezpecujuci kreslenie.
     * @param position Lavy horny roh obrazka.
     * @param bufferedImage Obrazok
     */
    public Obrazok(Graphics2D graphics2D, Position position, BufferedImage bufferedImage) {
        super(graphics2D, position);
        
        this.uhol = 0;
        this.obrazok = bufferedImage;
    }
    
    /**
     * Zmeni obrazok.
     * Ak je obrazok viditelny, prekresli dotknute objekty.
     * @param subor Subor s novym obrazkom.
     */
    public void zmenObrazok(String subor) {
        this.obrazok = this.nacitajObrazokZoSuboru(subor);
        
        this.aktualizujPlatno(true);
    }
    
    /**
     * Zmeni uhol obrazka.
     * Uhol sa zadava v stupnoch a rastie v smere hodinovych ruciciek.
     * Ak je obrazok viditelny, prekresli dotknute objekty.
     * @param uhol Novy uhol obrazka.
     */
    public void zmenUhol(int uhol) {
        this.uhol = uhol;
        
        this.aktualizujPlatno(true);
    }  
    
    /**
     * Vrati sirku obrazka.
     * @return Sirka obrazka.
     */
    public int getWidth() {
        return this.obrazok != null ? this.obrazok.getWidth() : -1;
    }
    
    /**
     * Vrati vysku obrazka.
     * @return Vyska obrazka.
     */
    public int getHeight() {
        return this.obrazok != null ? this.obrazok.getHeight() : -1;
    }   
    
    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        if (this.obrazok != null) {
            AffineTransform at = new AffineTransform();
            at.translate(this.getPosition().x + this.getWidth() / 2, this.getPosition().y + this.getHeight() / 2);
            at.rotate(this.uhol / 180.0 * Math.PI);
            at.translate(-this.getWidth() / 2, -this.getHeight()/ 2);
            
            graphics.drawImage(this.obrazok, at, null);
        }
    }
    
    /**
     * Nacita obrazok zo suboru.
     * Kontroluje existenciu suboru, ak subor neexistuje, obrazok nebude prideleny.
     * @param subor Subor s obrazkom, ktory sa ma nacitat.
     * @return Vytvoreny objekt reprezentujuci obrazok nacitany zo suboru.
     */
    private BufferedImage nacitajObrazokZoSuboru(String subor) {
        BufferedImage nacitanyOBrazok = null;
        
        try {
            nacitanyOBrazok = ImageIO.read(new File(subor));
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Subor " + subor + " sa nenasiel.");
        }        
        
        return nacitanyOBrazok;
    }
}
