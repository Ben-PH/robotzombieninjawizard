package rznw.ui;

import java.awt.event.KeyEvent;

public class SaveScreenKeyListener extends StateTransitionKeyListener
{
    private SaveScreenRenderer saveScreenRenderer;

    public SaveScreenKeyListener(SaveScreenRenderer saveScreenRenderer)
    {
        this.saveScreenRenderer = saveScreenRenderer;
    }

    public void keyPressed(KeyEvent event)
    {
    }

    public void enterState()
    {
        this.saveScreenRenderer.render();
    }

    public int getNextState(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            return DispatchKeyListener.STATE_GAME_ESCAPE_MENU;
        }

        return DispatchKeyListener.STATE_SAVE_SCREEN;
    }
}
