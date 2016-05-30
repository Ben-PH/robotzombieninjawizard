package rznw.game.maincharacter.inventory;

import rznw.game.maincharacter.MainCharacter;
import rznw.utility.RandomNumberGenerator;

import java.util.Arrays;
import java.util.Vector;

public class RandomInventoryGenerator
{
    private static Vector<InventoryItemGroup> selectedItems;
    private static Vector<EquipmentGroup> selectedEquipments;

    public static void handleRegeneration(MainCharacter mainCharacter, int numItems, int numEquipments)
    {
        if (mainCharacter.getStatusEffects().regenerateShopEnabled())
        {
            mainCharacter.getStatusEffects().disableRegenerateShop();

            RandomInventoryGenerator.regenerateSelectedItems(numItems);
            RandomInventoryGenerator.regenerateSelectedEquipments(numEquipments);
        }
    }

    public static Vector<InventoryItemGroup> getRandomItems()
    {
        return RandomInventoryGenerator.selectedItems;
    }

    private static void regenerateSelectedItems(int numItems)
    {
        InventoryItemGroup[] possibleItemsArray = new InventoryItemGroup[] {
            new InventoryItemGroup(new Bomb(), 1),
            new InventoryItemGroup(new FullManaPotion(), 1),
            new InventoryItemGroup(new FullPotion(), 1),
            new InventoryItemGroup(new Herb(), 1),
            new InventoryItemGroup(new ManaPotion(), 1),
            new InventoryItemGroup(new Potion(), 1),
            new InventoryItemGroup(new SanityDrop(), 1),
            new InventoryItemGroup(new XRayDrop(), 1)
        };
        Vector<InventoryItemGroup> possibleItems = new Vector<InventoryItemGroup>(Arrays.asList(possibleItemsArray));
        RandomInventoryGenerator.selectedItems = new Vector<InventoryItemGroup>();

        for (int i = 0; i < numItems; i++)
        {
            int randomIndex = RandomNumberGenerator.randomInteger(0, possibleItems.size() - 1);
            RandomInventoryGenerator.selectedItems.add(possibleItems.get(randomIndex));
            possibleItems.remove(randomIndex);
        }
    }

    public static Vector<EquipmentGroup> getRandomEquipments()
    {
        return RandomInventoryGenerator.selectedEquipments;
    }

    private static void regenerateSelectedEquipments(int numEquipments)
    {
        EquipmentGroup[] possibleEquipmentsArray = new EquipmentGroup[] {
            new EquipmentGroup(new DeathScythe(), 1),
            new EquipmentGroup(new RiddleWand(), 1),
            new EquipmentGroup(new GravityBlade(), 1),
            new EquipmentGroup(new QuicksandHammer(), 1),
            new EquipmentGroup(new IceRod(), 1),
            new EquipmentGroup(new ThiefGlove(), 1),
            new EquipmentGroup(new WandOfSummoning(), 1),
            new EquipmentGroup(new InvisibilityWand(), 1),
            new EquipmentGroup(new ViperDagger(), 1),
            new EquipmentGroup(new GauntletOfDarkness(), 1),
            new EquipmentGroup(new MagicJavelin(), 1),
            new EquipmentGroup(new BloodSword(), 1),
            new EquipmentGroup(new CrusherHammer(), 1),
            new EquipmentGroup(new WoodenSword(), 1),
            new EquipmentGroup(new LeechMail(), 1),
            new EquipmentGroup(new PoisonCloth(), 1),
            new EquipmentGroup(new AssassinsCloak(), 1),
            new EquipmentGroup(new DragonPlate(), 1),
            new EquipmentGroup(new ProtectivePlate(), 1),
            new EquipmentGroup(new RockMail(), 1),
            new EquipmentGroup(new ShieldOfSight(), 1),
            new EquipmentGroup(new HealShield(), 1),
            new EquipmentGroup(new MagicShield(), 1),
            new EquipmentGroup(new WoodenShield(), 1),
            new EquipmentGroup(new EtherealShield(), 1)
        };
        Vector<EquipmentGroup> possibleEquipments = new Vector<EquipmentGroup>(Arrays.asList(possibleEquipmentsArray));
        RandomInventoryGenerator.selectedEquipments = new Vector<EquipmentGroup>();

        for (int i = 0; i < numEquipments; i++)
        {
            int randomIndex = RandomNumberGenerator.randomInteger(0, possibleEquipments.size() - 1);
            RandomInventoryGenerator.selectedEquipments.add(possibleEquipments.get(randomIndex));
            possibleEquipments.remove(randomIndex);
        }
    }
}