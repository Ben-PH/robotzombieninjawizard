package rznw.game.spell.ninja;

import rznw.game.Character;
import rznw.game.maincharacter.KillBonusGranter;
import rznw.game.maincharacter.MainCharacter;
import rznw.game.spell.Spell;
import rznw.map.GameWorld;
import rznw.map.Map;
import rznw.map.element.EnemyMapElement;
import rznw.map.element.MapElement;
import rznw.map.element.Projectile;

public class ShurikenStarSpell extends Spell
{
    private KillBonusGranter killBonusGranter;

    public ShurikenStarSpell()
    {
        this.killBonusGranter = new KillBonusGranter();
    }

    public boolean canCast(MainCharacter character)
    {
        return character.getSpellPoints(5) > 0 && character.getMP() >= this.getMPCost(character);
    }

    public void cast(GameWorld gameWorld)
    {
        System.out.println("Casting Shuriken Star");
        MainCharacter character = gameWorld.getMainCharacter();
        character.setMP(character.getMP() - this.getMPCost(character));

        int shurikensRemaining = 8;
        Projectile[] projectiles = new Projectile[8];
        for (int i = 0; i < projectiles.length; i++)
        {
            projectiles[i] = new Projectile(character.getMapElement().getRow(), character.getMapElement().getColumn());
        }
        int[] deltaRow = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] deltaColumn = {-1, 0, 1, -1, 1, -1, 0, 1};

        Map map = gameWorld.getMap();

        while (shurikensRemaining > 0)
        {
            for (int i = 0; i < projectiles.length; i++)
            {
                Projectile projectile = projectiles[i];
                if (projectile == null)
                {
                    continue;
                }

                projectile.setRow(projectile.getRow() + deltaRow[i]);
                projectile.setColumn(projectile.getColumn() + deltaColumn[i]);

                MapElement collisionElement = map.getElement(projectile.getRow(), projectile.getColumn());
                if (collisionElement != null)
                {
                    if (collisionElement instanceof EnemyMapElement)
                    {
                        System.out.println("Shuriken Star hit: " + collisionElement.getRow() + "," + collisionElement.getColumn());
                        int damage = 60 + 15 * character.getSpellPoints(5);
                        Character enemy = ((EnemyMapElement)collisionElement).getCharacter();
                        enemy.damage(damage);

                        if (enemy.isDead())
                        {
                            this.killBonusGranter.grantKillBonuses(character, enemy);
                            map.setElement(collisionElement.getRow(), collisionElement.getColumn(), null);
                        }
                    } else {
                        System.out.println("Shuriken Star miss: " + collisionElement.getRow() + "," + collisionElement.getColumn());
                    }

                    shurikensRemaining--;
                    projectiles[i] = null;
                }
            }
        }
    }

    private int getMPCost(MainCharacter character)
    {
        int spellLevel = character.getSpellPoints(5);
        return Math.max(200 - 10 * spellLevel, 1);
    }
}