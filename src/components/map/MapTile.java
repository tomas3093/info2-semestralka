package components.map;

import components.ILoadable;
import components.IDrawable;
import components.IUpdatable;
import components.Marks;
import graphics.Position;
import graphics.TovarenTvarov;
import graphics.primitives.Obrazok;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Trieda definujuca vseobecnu mapovu dlazdicu, ktora sa dokaze vykreslit na
 * platno, aktualizovat a ulozit a nacitat zo suboru
 *
 * @author tomas
 */
public abstract class MapTile implements IDrawable, IUpdatable, ILoadable {

    public static final int MAP_TILE_SIZE = 35;

    private static final String TEXTURE_FILE_PATH_WALL = "img/map/wall.png";
    private static final String TEXTURE_FILE_PATH_AMMO_BOX = "img/map/ammoBox.png";
    private static final String TEXTURE_FILE_PATH_SUPPLIES_BOX = "img/map/suppliesBox.png";

    private int mark;

    private Position position;
    private String textureFile;
    private Obrazok graphic;

    private boolean toRemove;

    /**
     * Vytvori konkretny typ mapovej dlazdice na danej pozicii
     *
     * @param mark znacka, identifikujuca typ znacky
     * @param position pozicia
     */
    public MapTile(int mark, Position position) {
        this.mark = mark;
        this.position = position;

        switch (mark) {
            case Marks.MARK_WALL:
                this.textureFile = MapTile.TEXTURE_FILE_PATH_WALL;
                break;

            case Marks.MARK_BOX_MAP_TILE_AMMO_BOX:
                this.textureFile = MapTile.TEXTURE_FILE_PATH_AMMO_BOX;
                break;

            case Marks.MARK_BOX_MAP_TILE_SUPPLIES_BOX:
                this.textureFile = MapTile.TEXTURE_FILE_PATH_SUPPLIES_BOX;
                break;

            default:
                this.textureFile = "";
                break;
        }

        this.graphic = TovarenTvarov.vytvorObrazok(position.x, position.y, textureFile, true);

        this.toRemove = false;
    }

    /**
     * Vytvori konkretny typ mapovej dlazdice ako prazdnu instanciu
     *
     * @param mark
     */
    public MapTile(int mark) {
        this.mark = mark;
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        int x = dataInput.readInt();
        int y = dataInput.readInt();
        this.position = new Position(x, y);
        this.textureFile = dataInput.readUTF();
        this.graphic = TovarenTvarov.vytvorObrazok(position.x, position.y, textureFile, true);
        this.toRemove = dataInput.readBoolean();
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        dataOutput.writeInt(position.x);
        dataOutput.writeInt(position.y);
        dataOutput.writeUTF(textureFile);
        dataOutput.writeBoolean(toRemove);
    }

    @Override
    public final int getMark() {
        return this.mark;
    }

    @Override
    public final Position getPosition() {
        return position;
    }

    @Override
    public final void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public final int getHeight() {
        return this.graphic.getHeight();
    }

    @Override
    public final int getWidth() {
        return this.graphic.getWidth();
    }

    @Override
    public final void draw() {
        //ak je rozdielna poloha objektu a jeho grafickej reprezentacie
        if (!this.getPosition().equals(this.graphic.getPosition())) {
            this.graphic.setPosition(this.getPosition());
        }
    }

    @Override
    public final void hide() {
        this.graphic.hide();
    }

    @Override
    public abstract void update();

    @Override
    public final void setToRemove() {
        this.toRemove = true;
    }

    @Override
    public final boolean isToRemove() {
        return toRemove;
    }
}
