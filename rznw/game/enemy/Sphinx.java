package rznw.game.enemy;

import rznw.game.maincharacter.MainCharacter;
import rznw.game.maincharacter.inventory.EquipmentGroup;
import rznw.game.maincharacter.inventory.InventoryItemGroup;
import rznw.game.maincharacter.inventory.ManaPotion;
import rznw.game.maincharacter.inventory.RiddleWand;
import rznw.map.element.EnemyMapElement;
import rznw.utility.RandomNumberGenerator;

public class Sphinx extends EnemyCharacter
{
    private static char mapCharacter = 's';

    public Sphinx(int level)
    {
        super(level);
    }

    public Sphinx getNewInstance(int level)
    {
        return new Sphinx(level);
    }

    public int[] getStatSequence()
    {
        return new int[]{
          EnemyCharacter.STAT_HEALTH,
          EnemyCharacter.STAT_ACCURACY,
          EnemyCharacter.STAT_SIGHT
        };
    }

    public void generateMapElement(int row, int column)
    {
        this.mapElement = new EnemyMapElement(row, column, Sphinx.mapCharacter, this);
    }

    public boolean isDroppingItems(MainCharacter mainCharacter)
    {
        int probability = 50 + 2 * mainCharacter.getSkillPoints(6);
        probability += this.getStatusEffects().getBonusDropProbability();
        System.out.println("Item drop probability: " + probability);
probability = 100;
        return RandomNumberGenerator.rollSucceeds(probability);
    }

    public InventoryItemGroup getItemDrops()
    {
        return new InventoryItemGroup(new ManaPotion(), 1);
    }

    public boolean isDroppingEquipment()
    {
        int probability = 10;
        probability += this.getStatusEffects().getBonusDropProbability();
        System.out.println("Equipment drop probability: " + probability);
probability = 100;
        return RandomNumberGenerator.rollSucceeds(probability);
    }

    public EquipmentGroup getEquipmentDrops()
    {
        return new EquipmentGroup(new RiddleWand(), 1);
    }

    public void damagedMainCharacter(MainCharacter mainCharacter)
    {
        System.out.println("Given a riddle by the Sphinx - you are confused");
        mainCharacter.getStatusEffects().confuse();
    }
}