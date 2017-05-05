package graphics;

import graphics.primitives.LomenaCiara;
import graphics.primitives.Obrazok;
import graphics.primitives.Elipsa;
import graphics.primitives.Stvorec;
import graphics.primitives.Polygon;
import graphics.primitives.Obdlznik;
import graphics.primitives.Kruh;
import graphics.primitives.TextovePole;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Rutinna trieda zastresujuca relativne komplikovane vytvaranie tvarov.
 * Spolupracuje s Platnom.
 *
 * @author Michal Varga
 */
public class TovarenTvarov {

    /**
     * Vytvori a vrati kruh s pozadovanymi vlastnostami.
     *
     * @param polohaX X-ova suradnica laveho horneho rohu stvorca.
     * @param polohaY Y-ova suradnica laveho horneho rohu stvorca.
     * @param strana Dlzka strany stvorca.
     * @param farba Farba stvorca.
     * @param viditelny Priznak, ci ma byt stvorec ihned zobrazeny.
     * @return Vytvoreny obdlznik s danymi vlastnostami.
     */
    public static Stvorec vytvorStvorec(int polohaX, int polohaY, int strana, Color farba, boolean viditelny) {
        Stvorec result = new Stvorec(getGraphics(), new Position(polohaX, polohaY), strana);
        result.setColor(farba);
        if (viditelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati obdlznik s pozadovanymi vlastnostami.
     *
     * @param polohaX X-ova suradnica laveho horneho rohu obdlznika.
     * @param polohaY Y-ova suradnica laveho horneho rohu obdlznika.
     * @param dlzka Dlzka obdlznika.
     * @param vyska Vyska obdlznika.
     * @param farba Farba obdlznika.
     * @param vidtelny Priznak, ci ma byt obdlznik ihned zobrazeny.
     * @return Vytvoreny obdlznik s danymi vlastnostami.
     */
    public static Obdlznik vytvorObdlznik(int polohaX, int polohaY, int dlzka, int vyska, Color farba, boolean vidtelny) {
        Obdlznik result = new Obdlznik(getGraphics(), new Position(polohaX, polohaY), new Dimensions(dlzka, vyska));
        result.setColor(farba);
        if (vidtelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati kruh s pozadovanymi vlastnostami.
     *
     * @param polohaX X-ova suradnica laveho horneho rohu stvroca opisaneho
     * vytvaranemu kruhu.
     * @param polohaY Y-ova suradnica laveho horneho rohu stvroca opisaneho
     * vytvaranemu kruhu.
     * @param polomer Polomer kruhu.
     * @param farba Farba kruhu.
     * @param vidtelny Priznak, ci ma byt kruh ihned zobrazeny.
     * @return Vytvoreny kruh s danymi vlastnostami.
     */
    public static Kruh vytvorKruh(int polohaX, int polohaY, int polomer, Color farba, boolean vidtelny) {
        Kruh result = new Kruh(getGraphics(), new Position(polohaX, polohaY), polomer);
        result.setColor(farba);
        if (vidtelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati obdlznik s pozadovanymi vlastnostami.
     *
     * @param polohaX X-ova suradnica laveho horneho rohu obdlznika opisaneho
     * vytvaranej elipse.
     * @param polohaY Y-ova suradnica laveho horneho rohu obdlznika opisaneho
     * vytvaranej elipse.
     * @param polomerX Polomer elipsy na osy X.
     * @param polomerY Polomer elipsy na osy Y.
     * @param farba Farba elipsy.
     * @param vidtelny Priznak, ci ma byt elipsa ihned zobrazena.
     * @return Vytvorena elipsa s danymi vlastnostami.
     */
    public static Elipsa vytvorElipsu(int polohaX, int polohaY, int polomerX, int polomerY, Color farba, boolean vidtelny) {
        Elipsa result = new Elipsa(getGraphics(), new Position(polohaX, polohaY), new Dimensions(polomerX * 2, polomerY * 2));
        result.setColor(farba);
        if (vidtelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati obrazok s pozadovanymi vlastnostami.
     *
     * @param polohaX X-ova suradnica laveho horneho rohu obrazka.
     * @param polohaY Y-ova suradnica laveho horneho rohu obrazka.
     * @param subor Subor s obrazkom.
     * @param viditelny Priznak, ci ma byt obrazok ihned zobrazeny.
     * @return Vytvoreny obrazok s danymi vlastnostami.
     */
    public static Obrazok vytvorObrazok(int polohaX, int polohaY, String subor, boolean viditelny) {
        Obrazok result = new Obrazok(getGraphics(), new Position(polohaX, polohaY), subor);
        if (viditelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati obrazok s pozadovanymi vlastnostami.
     *
     * @param polohaX X-ova suradnica laveho horneho rohu obrazka.
     * @param polohaY Y-ova suradnica laveho horneho rohu obrazka.
     * @param bufferedImage
     * @param viditelny Priznak, ci ma byt obrazok ihned zobrazeny.
     * @return Vytvoreny obrazok s danymi vlastnostami.
     */
    public static Obrazok vytvorObrazok(int polohaX, int polohaY, BufferedImage bufferedImage, boolean viditelny) {
        Obrazok result = new Obrazok(getGraphics(), new Position(polohaX, polohaY), bufferedImage);
        if (viditelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati lomenu ciaru s pozadovanymi vlastnostami.
     *
     * @param body Body tvoriace lomenu ciaru. Aby bola uzatvorena, musi sa
     * posledny a prvy bod rovnat.
     * @param farba Farba lomenej ciary.
     * @param vidtelny Priznak, ci ma byt lomena ciara ihned zobrazena.
     * @return Vytvorena lomena ciara s danymi vlastnostami.
     */
    public static LomenaCiara vytvorLomenuCiaru(Position[] body, Color farba, boolean vidtelny) {
        LomenaCiara result = new LomenaCiara(getGraphics(), body);
        result.setColor(farba);
        if (vidtelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati polygon s pozadovanymi vlastnostami.
     *
     * @param body Body tvoriace polygon.
     * @param farba Farba polygonu.
     * @param vidtelny Priznak, ci ma byt polygon ihned zobrazeny.
     * @return Vytvoreny polygon s danymi vlastnostami.
     */
    public static Polygon vytvorPolygon(Position[] body, Color farba, boolean vidtelny) {
        Polygon result = new Polygon(getGraphics(), body);
        result.setColor(farba);
        if (vidtelny) {
            result.show();
        }
        return result;
    }

    /**
     * Vytvori a vrati textove pole s pozadovanymi vlastnostami
     *
     * @param polohaX X-ova suradnica laveho horneho rohu textu
     * @param polohaY Y-ova suradnica laveho horneho rohu textu
     * @param text text, ktory obsahuje textove pole
     * @param fontVelkost velkost pisma
     * @param isBold priznak, ci ma byt pismo tucne
     * @param farba farba textu
     * @param viditelny priznak, ci ma byt text ihned zobrazeny
     * @return Vytvorene textove pole s danymi vlastnostami
     */
    public static TextovePole vytvorTextovePole(int polohaX, int polohaY, String text, int fontVelkost, boolean isBold, Color farba, boolean viditelny) {
        TextovePole result = new TextovePole(getGraphics(), new Position(polohaX, polohaY), text, fontVelkost, isBold);
        result.setColor(farba);
        if (viditelny) {
            result.show();
        }
        return result;
    }

    /**
     * Ziska z platna instanciu Graphics2D pomocou ktorej je mozne na platno
     * kreslit.
     *
     * @return Instanciu Graphics2D pomocou ktorej je mozne na platno kreslit.
     */
    protected static Graphics2D getGraphics() {
        Platno platno = Platno.dajPlatno();
        return platno.getGraphics();
    }

    /**
     * Sukromny konstruktor znemoznuje vytvaranie instancii.
     */
    private TovarenTvarov() {
    }
}
