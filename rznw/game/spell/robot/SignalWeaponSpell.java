package rznw.game.spell.robot;

import rznw.game.maincharacter.MainCharacter;
import rznw.game.spell.Spell;
import rznw.map.GameWorld;

public class SignalWeaponSpell extends Spell
{
    public void cast(GameWorld gameWorld, int spellPoints)
    {
        System.out.println("Casting Signal Weapon");

        int numTurns = 2 + (int)Math.floor(spellPoints / 4);

        MainCharacter character = gameWorld.getMainCharacter();
        character.getStatusEffects().enableSignalWeapon(numTurns);
    }

    public int getMPCost(MainCharacter character, int spellPoints)
    {
        return Math.max(200 - 10 * spellPoints, 1);
    }
}
