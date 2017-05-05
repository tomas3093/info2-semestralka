package components.inventory;

/**
 * Trieda zhromazduje jednotlive parametre jednotlivych typov predmetov
 *
 * @author tomas
 */
public class ItemId {

    public final int mark;
    public final String name;
    public final String description;
    public final String file;
    public final Object optionalProperty;

    /**
     * Konstruktor s jednotlivymi parametrami predmetu
     *
     * @param mark
     * @param name
     * @param description
     * @param file
     */
    public ItemId(int mark, String name, String description, String file) {
        this.mark = mark;

        this.name = name;
        this.description = description;
        this.file = file;
        this.optionalProperty = null;
    }

    /**
     * Konstruktor s jednotlivymi parametrami a volitelnym parametrom predmetu
     *
     * @param mark
     * @param name
     * @param description
     * @param file
     * @param optionalProperty
     */
    public ItemId(int mark, String name, String description, String file, Object optionalProperty) {
        this.mark = mark;

        this.name = name;
        this.description = description;
        this.file = file;
        this.optionalProperty = optionalProperty;
    }
}
