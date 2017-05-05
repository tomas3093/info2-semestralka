package components.inventory;

import application.Game;
import components.Marks;
import components.bullets.Bullet;
import components.characters.Character;
import graphics.Position;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Trieda definuje predmet "Strelna zbran", ktory ak ho postava pouzije dokaze
 * vystrelit strelu
 *
 * @author tomas
 */
public class Gun extends Weapon {

    private static final List<Integer> GUN_LIST = initializeList();

    /**
     * Definuje vsetky mozne typy strelnych zbrani
     *
     * @return
     */
    private static List<Integer> initializeList() {
        List<Integer> list = new ArrayList<>();

        list.add(Marks.MARK_GUN_PISTOL);
        list.add(Marks.MARK_GUN_RIFLE);

        return list;
    }

    /**
     * Vrati nahodne ovocie z preddefinovaneho zoznamu na danej pozicii
     *
     * @param position
     * @return
     */
    public static Gun getRandomGun(Position position) {
        Random random = new Random();
        int mark = random.nextInt(GUN_LIST.size());

        return new Gun(GUN_LIST.get(mark), position);
    }

    /**
     * Vytvori konkretny typ strelnej zbrane na danej pozicii
     *
     * @param mark znacka urcujuca typ strelnej zbrane
     * @param position pozicia
     */
    private Gun(int mark, Position position) {
        super(mark, position);
    }

    /**
     * Vytvori konkretny typ strelnej zbrane ako prazdnu instanciu
     *
     * @param mark znacka urcujuca typ strelnej zbrane
     */
    public Gun(int mark) {
        super(mark);
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        super.load(dataInput);
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        super.save(dataOutput);
    }

    @Override
    public void use(Character usedBy) {
        if (super.getAttackDelayTimer().hasExpired()) {

            Position bulletPosition = new Position(usedBy.getPosition().x + (usedBy.getWidth() / 2),
                    usedBy.getPosition().y + (usedBy.getHeight() / 2));

            switch (usedBy.getAimDirection()) {
                case DOWN:
                    bulletPosition = new Position(bulletPosition.x, bulletPosition.y + usedBy.getHeight());
                    break;
                case UP:
                    bulletPosition = new Position(bulletPosition.x, bulletPosition.y - usedBy.getHeight());
                    break;
                case LEFT:
                    bulletPosition = new Position(bulletPosition.x - usedBy.getWidth(), bulletPosition.y);
                    break;
                case RIGHT:
                    bulletPosition = new Position(bulletPosition.x + usedBy.getWidth(), bulletPosition.y);
                    break;
            }

            Game.getInstance().addGameObject(new Bullet(usedBy, bulletPosition));

            super.getAttackDelayTimer().setNewTime(super.getAttackFrequency());
        }
    }
}
