package rznw.game.spell.zombie;

import rznw.game.maincharacter.MainCharacter;
import rznw.game.spell.Spell;
import rznw.map.GameWorld;

public class ThornSkinSpell extends Spell
{
    public void cast(GameWorld gameWorld, int spellPoints)
    {
        System.out.println("Casting Thorn Skin");

        MainCharacter character = gameWorld.getMainCharacter();
        int numTurns = 1 + (int)Math.floor(spellPoints / 4);

        character.getStatusEffects().enableThornSkin(numTurns);
    }

    public int getMPCost(MainCharacter character, int spellPoints)
    {
        return Math.max(200 - 10 * spellPoints, 1);
    }
}
