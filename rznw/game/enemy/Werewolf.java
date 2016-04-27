package rznw.game.enemy;

import rznw.game.enemy.action.EnemyActionCalculator;
import rznw.game.enemy.action.EnemyMeleeActionCalculator;
import rznw.game.maincharacter.MainCharacter;
import rznw.game.maincharacter.inventory.EquipmentGroup;
import rznw.game.maincharacter.inventory.InventoryItem;
import rznw.game.maincharacter.inventory.Potion;
import rznw.game.maincharacter.inventory.WoodenShield;
import rznw.map.element.EnemyMapElement;

public class Werewolf extends EnemyCharacter
{
    private static char mapCharacter = 'w';

    public Werewolf(int level)
    {
        super(level);
    }

    public Werewolf getNewInstance(int level)
    {
        return new Werewolf(level);
    }

    public int[] getStatSequence()
    {
        return new int[]{
          EnemyCharacter.STAT_DAMAGE,
          EnemyCharacter.STAT_DAMAGE,
          EnemyCharacter.STAT_ACCURACY
        };
    }

    public void generateMapElement(int row, int column)
    {
        this.mapElement = new EnemyMapElement(row, column, Werewolf.mapCharacter, this);
    }

    public InventoryItem getItemDrop()
    {
        return new Potion();
    }

    public EquipmentGroup getEquipmentDrops()
    {
        return new EquipmentGroup(new WoodenShield(), 1);
    }

    public EnemyActionCalculator getActionCalculator()
    {
        return new EnemyMeleeActionCalculator();
    }
}
