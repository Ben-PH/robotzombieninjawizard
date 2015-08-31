package rznw.game.maincharacter;

import rznw.map.element.MainCharacterMapElement;

public class Robot extends MainCharacter
{
    private static char mapCharacter = 'R';

    public void generateMapElement(int row, int column)
    {
        this.mapElement = new MainCharacterMapElement(row, column, Robot.mapCharacter, this);
    }
}
