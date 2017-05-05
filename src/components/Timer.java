package components;

/**
 * Casovac reprezentujuci cas v sekundach. Vnutorne pocita cas ako FPS.
 *
 * @author tomas
 */
public class Timer {

    private int remainingTime;

    public Timer(double timeInSeconds) {
        setNewTime(timeInSeconds);
    }

    /**
     * Nastavy novy cas, ktory sa bude odpocitavat
     *
     * @param timeInSeconds cas v sekundach
     */
    public final void setNewTime(double timeInSeconds) {
        this.remainingTime = (int) Math.round(GameSettings.GAME_FPS * timeInSeconds);
    }

    /**
     * Znizi cas o 1 "frame"
     */
    public void decrement() {
        if (this.remainingTime > 0) {
            this.remainingTime -= 1;
        }
    }

    /**
     * Zisti, ci uz nastaveny cas uplynul
     *
     * @return
     */
    public final boolean hasExpired() {
        return this.remainingTime == 0;
    }

    /**
     * Vrati zostavajuci cas v sekundach
     *
     * @return
     */
    public double getRemainingTime() {
        return this.remainingTime / (double) GameSettings.GAME_FPS;
    }

}
