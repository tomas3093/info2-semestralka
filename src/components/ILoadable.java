package components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Rozhranie pre objekt, ktory moze byt zapisany do suboru a znova
 * zrekonstruovany
 *
 * @author tomas
 */
public interface ILoadable {

    /**
     * Vrati znacku, ktora je unikatna pre konkretny objekt
     *
     * @return
     */
    int getMark();

    /**
     * Ulozi sa do daneho suboru
     *
     * @param dataOutput
     * @throws java.io.IOException
     */
    void save(DataOutputStream dataOutput) throws IOException;

    /**
     * Nacita sa z daneho suboru
     *
     * @param dataInput
     * @throws java.io.IOException
     */
    void load(DataInputStream dataInput) throws IOException;
}
