package rznw.save;

public class GameLoader extends BaseGameLoader
{
    protected ComponentLoader[] getComponentLoaders()
    {
        return new ComponentLoader[] {
            new MainCharacterLoader(),
            new ShopLoader(),
            new EnemyLoader(),
            new SummonLoader(),
            new MapLoader(),
            new MapVisibilityLoader(),
            new InventoryLoader(),
            new EquipmentLoader()
        };
    }
}
