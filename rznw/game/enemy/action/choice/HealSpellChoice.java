package rznw.game.enemy.action.choice;

import rznw.game.enemy.EnemyCharacter;
import rznw.game.enemy.EnemyCharacterWithSpell;
import rznw.game.enemy.action.EnemyAction;
import rznw.game.enemy.action.EnemySpellAction;
import rznw.game.enemy.spell.EnemySpell;
import rznw.game.maincharacter.MainCharacter;
import rznw.map.GameWorld;
import rznw.map.element.MapElement;

public class HealSpellChoice implements EnemyActionChoice
{
    private static final int RADIUS = 2;

    public EnemyAction getAction(GameWorld gameWorld, EnemyCharacter enemyCharacter)
    {
        System.out.println("In getSpellAction");

        MainCharacter mainCharacter = gameWorld.getMainCharacter();

        MapElement mainCharacterMapElement = mainCharacter.getMapElement();
        MapElement enemyMapElement = enemyCharacter.getMapElement();

        if (enemyCharacter.getHP() == enemyCharacter.getMaxHP())
        {
            return null;
        }

        if (Math.abs(mainCharacterMapElement.getRow() - enemyMapElement.getRow()) > HealSpellChoice.RADIUS || Math.abs(mainCharacterMapElement.getColumn() - enemyMapElement.getColumn()) > HealSpellChoice.RADIUS)
        {
            return null;
        }

        int spellPoints = ((EnemyCharacterWithSpell)enemyCharacter).getSpellPoints(0);

        if (spellPoints == 0)
        {
            return null;
        }

        EnemySpell enemySpell = ((EnemyCharacterWithSpell)enemyCharacter).getSpell(0);
        int MPCost = enemySpell.getMPCost(spellPoints);

        if (MPCost > enemyCharacter.getMP())
        {
            return null;
        }

        return new EnemySpellAction(((EnemyCharacterWithSpell)enemyCharacter).getSpell(0), spellPoints);
    }
}
