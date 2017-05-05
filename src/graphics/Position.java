package graphics;

/**
 * Prepravka definujuca poziciu v 2 rozmernom priestore. Nemenitelny objekt.
 *
 * @author blazy
 */
public class Position {

    public final int x;
    public final int y;

    /**
     * Vytvori 2D poziciu.
     *
     * @param x X-ova suradnica
     * @param y Y-ova suradnica
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            return (((Position) o).x == this.x) && (((Position) o).y == this.y);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.x;
        hash = 23 * hash + this.y;
        return hash;
    }
}
