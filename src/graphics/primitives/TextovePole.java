package graphics.primitives;

import graphics.Position;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Textové pole, ktoré možno nakresliť na plátno.
 * Obsahuje textový reťazec, dokáže meniť polohu a typ písma
 *
 * @author  Tomáš Blázy
 * @version 1.0
 * @since   2016-12-11
 */
public class TextovePole extends Tvar {
        
    private String text;
    private int fontSize;
    private int style;

    /**
     * Konštruktor triedy TextovePole
     * @param graphics Objekt zabezpecujuci kreslenie
     * @param poloha Lavy horny roh textu
     * @param text Text, ktorý bude zobrazovať
     * @param fontSize Veľkosť textu
     * @param isBold Tucné písmo
     */
    public TextovePole(Graphics2D graphics, Position poloha, String text, int fontSize, boolean isBold) {
        
        super(graphics, poloha);
        this.text = text;
        this.fontSize = fontSize;

        if (isBold) {
            this.style = Font.BOLD;
        } else {
            this.style = Font.PLAIN;
        }
    }


    /**
     * (Textove pole) Zmeň text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        
        this.aktualizujPlatno(true);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    @Override
    protected void ibaZobraz(Graphics2D graphics) {
        
        Font oldFont = graphics.getFont();
        
        graphics.setFont(new Font("Times New Roman", this.style, this.fontSize));
        graphics.drawString(this.text, this.getPosition().x, this.getPosition().y + this.fontSize);
        
        graphics.setFont(oldFont);
    }
}
