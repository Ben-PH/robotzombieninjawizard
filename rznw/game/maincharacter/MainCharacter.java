package rznw.game.maincharacter;

import rznw.game.Character;
import rznw.game.enemy.EnemyCharacter;
import rznw.game.maincharacter.calculator.MainCharacterDamageDealtCalculator;
import rznw.game.maincharacter.calculator.MainCharacterDamageReceivedCalculator;
import rznw.game.maincharacter.calculator.MainCharacterDodgeCalculator;
import rznw.game.maincharacter.inventory.Equipment;
import rznw.game.maincharacter.inventory.Inventory;
import rznw.game.maincharacter.inventory.InventoryItem;
import rznw.game.maincharacter.inventory.InventoryItemGroup;
import rznw.game.maincharacter.inventory.Armor;
import rznw.game.maincharacter.inventory.Shield;
import rznw.game.maincharacter.inventory.Weapon;
import rznw.game.StatusEffects;
import rznw.game.skill.Skill;
import rznw.game.spell.SpellFactory;
import rznw.game.stat.Stat;
import rznw.map.GameWorld;
import rznw.utility.RandomNumberGenerator;

public abstract class MainCharacter extends Character
{
    public static final int MAX_LEVEL = 80;

    private int level = 0;
    private int experience = 0;
    private int pendingLevels = 0;

    private MainCharacterStats stats;
    private MainCharacterSkills skills;
    private MainCharacterSpells spells;

    private Inventory inventory;
    private Equipment equipment;

    private int HPSteps = 0;
    private int MPSteps = 0;
    private int manaRiverSteps = 0;

    public MainCharacter()
    {
        super(20, 20);

        this.stats = new MainCharacterStats();
        this.skills = new MainCharacterSkills();
        this.spells = new MainCharacterSpells(this);

        this.inventory = new Inventory(this);
        this.equipment = new Equipment(this);

        this.HP = this.getMaxHP();
        this.MP = this.getMaxMP();
    }

    public MainCharacterStats getStats()
    {
        return this.stats;
    }

    public MainCharacterSkills getSkills()
    {
        return this.skills;
    }

    public MainCharacterSpells getSpells()
    {
        return this.spells;
    }

    public abstract String getSpellCategory(int categoryNumber);

    public int getLevel()
    {
        return this.level;
    }

    public int getExperience()
    {
        return this.experience;
    }

    public int getMaxHP()
    {
        return 200 + 20 * this.stats.getStatPoints(Stat.STAT_HEALTH);
    }

    public int getMaxMP()
    {
        return 200 + 20 * this.stats.getStatPoints(Stat.STAT_MANA);
    }

    public int getDamage()
    {
        return new MainCharacterDamageDealtCalculator().getDamage(this);
    }

    public Inventory getInventory()
    {
        return this.inventory;
    }

    public Equipment getEquipment()
    {
        return this.equipment;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setPendingLevels(int pendingLevels)
    {
        this.pendingLevels = pendingLevels;
    }

    public int getPendingLevels()
    {
        return this.pendingLevels;
    }

    public void grantExperience(int experience)
    {
        this.experience += experience;
    }

    public void setExperience(int experience)
    {
        this.experience = experience;
    }

    public void resetStateAfterLevelUp()
    {
        this.fillHP();
        this.fillMP();
    }

    public abstract String getCharacterClass();

    public abstract int getCharacterClassNumber();

    public void useItem(int itemIndex, GameWorld gameWorld)
    {
        InventoryItem item = this.inventory.getItemGroup(itemIndex).getItem();
        this.inventory.removeItems(new InventoryItemGroup(item, 1));
        item.useOnCharacter(this, gameWorld);
    }

    public abstract SpellFactory getSpellFactory();

    public boolean meleeAttackHits()
    {
        int toHitPercent = 50 + 2 * this.stats.getStatPoints(Stat.STAT_ACCURACY);

        Weapon weapon = this.getEquipment().getEquippedWeapon();
        if (weapon != null)
        {
            System.out.println("Adjusting to hit percent");
            toHitPercent += weapon.getToHitBonus();
        }

        return RandomNumberGenerator.rollSucceeds(toHitPercent);
    }

    public boolean dodgesAttack()
    {
        return new MainCharacterDodgeCalculator().dodgesAttack(this);
    }

    public int getHPSteps()
    {
        return this.HPSteps;
    }

    public void setHPSteps(int HPSteps)
    {
        this.HPSteps = HPSteps;
    }

    public int getMPSteps()
    {
        return this.MPSteps;
    }

    public void setMPSteps(int MPSteps)
    {
        this.MPSteps = MPSteps;
    }

    public int getManaRiverSteps()
    {
        return this.manaRiverSteps;
    }

    public void setManaRiverSteps(int manaRiverSteps)
    {
        this.manaRiverSteps = manaRiverSteps;
    }

    public int getStepsForHeal()
    {
        return Math.max(1, 20 - this.stats.getStatPoints(Stat.STAT_PHYSICAL_REGENERATION));
    }

    public int getStepsForMPHeal()
    {
        return Math.max(1, 20 - this.stats.getStatPoints(Stat.STAT_MENTAL_REGENERATION));
    }

    public int getStepsForManaRiver()
    {
        return Math.max(1, 20 - this.skills.getSkillPoints(Skill.SKILL_MANA_RIVER));
    }

    public void incrementSteps()
    {
        new MainCharacterStepIncrementer().incrementSteps(this);
    }

    public int getViewRadius()
    {
        int equipmentBonus = 0;

        Shield shield = this.getEquipment().getEquippedShield();
        if (shield != null)
        {
            equipmentBonus = shield.getViewRadiusBonus();
        }

        return 2 + this.stats.getStatPoints(Stat.STAT_SIGHT) + equipmentBonus;
    }

    public int damage(int damage, Character damageSource, GameWorld gameWorld, int damageSourceType)
    {
        damage = new MainCharacterDamageReceivedCalculator().getDamage(this, damage, damageSource, damageSourceType);

        if (damage < 0) {
            this.HP += (-damage);
            return 0;
        }

        if (damage == 0)
        {
            return damage;
        }

        this.HP -= damage;

        new MainCharacterDamageResponder().respondToDamage(gameWorld, this, damageSource, damage);

        return damage;
    }

    public void heal(int HP)
    {
        int bonusHPPercent = 5 * this.stats.getStatPoints(Stat.STAT_LIFE_BOND);
        int bonusHP = (int)Math.floor(bonusHPPercent / 100.0 * HP);

        if (bonusHP > 0)
        {
            System.out.println("Bonus HP: " + bonusHP);
        }

        super.heal(HP + bonusHP);
    }

    public boolean isMainCharacter()
    {
        return true;
    }

    public void damagedEnemyCharacter(EnemyCharacter enemyCharacter, int damage, GameWorld gameWorld)
    {
        Weapon weapon = this.equipment.getEquippedWeapon();

        if (weapon != null)
        {
            weapon.damagedEnemyCharacter(this, enemyCharacter, damage, gameWorld);
        }
    }

    public void damagedByEnemyCharacter(EnemyCharacter enemyCharacter, int damage, GameWorld gameWorld)
    {
        Armor armor = this.equipment.getEquippedArmor();

        if (armor != null)
        {
            armor.damagedByEnemyCharacter(this, enemyCharacter, damage, gameWorld);
        }
    }
}
