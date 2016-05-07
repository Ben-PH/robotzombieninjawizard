package rznw.game.spell.wizard;

import rznw.game.maincharacter.MainCharacter;
import rznw.game.spell.UndirectedSpell;
import rznw.map.GameWorld;

public class CleanseSpell extends UndirectedSpell
{
    public String getDisplayName()
    {
        return "Cleanse";
    }

    public String getDescription()
    {
        return "Has a chance to heal all status effects currently affecting you. MP cost decreases and chance to succeed increases as spell level increases.";
    }

    public void cast(GameWorld gameWorld, int spellPoints)
    {
        System.out.println("Casting Cleanse");
        MainCharacter character = gameWorld.getMainCharacter();
        character.getStatusEffects().healPoison();
        character.getStatusEffects().healConfusion();
    }

    public int getMPCost(MainCharacter character, int spellPoints)
    {
        return Math.max(200 - 10 * spellPoints, 1);
    }

    public String[] getStats(MainCharacter character, int spellPoints)
    {
        return new String[] {
            "MP cost: " + this.getMPCost(character, spellPoints)
        };
    }
}
