package components;

/**
 * Trieda definuje vsetky mozne smery otocenia
 *
 * @author tomas
 */
public enum Direction {
    UP(0), RIGHT(90), DOWN(180), LEFT(270);

    public final int angle;

    /**
     * Vytvori smer otocenia s prislusnym uhlom
     *
     * @param angle
     */
    private Direction(int angle) {
        this.angle = angle;
    }
}
