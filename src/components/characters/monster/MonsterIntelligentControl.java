package components.characters.monster;

import components.ILoadable;
import components.Marks;
import components.characters.CharacterControl;
import graphics.Position;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Trieda definuje inteligentne ovladanie nepriatelov
 *
 * @author tomas
 */
public class MonsterIntelligentControl extends CharacterControl implements ILoadable {

    public MonsterIntelligentControl(Monster controlledCharacter) {
        super(controlledCharacter);
    }

    public MonsterIntelligentControl() {
        super();
    }

    @Override
    public Position getNewPosition() {
        return controlledCharacter.getPosition();
    }

    @Override
    public void save(DataOutputStream dataOutput) throws IOException {
        Position characterPosition = super.controlledCharacter.getPosition();

        dataOutput.writeInt(characterPosition.x);
        dataOutput.writeInt(characterPosition.y);
        dataOutput.writeInt(super.controlledCharacter.getHp());
        dataOutput.writeInt(super.controlledCharacter.getMaxHp());
    }

    @Override
    public void load(DataInputStream dataInput) throws IOException {
        int x = dataInput.readInt();
        int y = dataInput.readInt();
        int hp = dataInput.readInt();
        int maxHp = dataInput.readInt();

        Monster monster = new Monster(new Position(x, y), hp, maxHp);
        super.controlledCharacter = monster;
    }

    @Override
    public int getMark() {
        return Marks.MARK_CHARACTER_CONTROL_MONSTER_INTELLIGENT_CONTROL;
    }

}
