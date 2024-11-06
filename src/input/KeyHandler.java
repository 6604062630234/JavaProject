package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

public class KeyHandler implements KeyListener {

    public boolean Up, Down, Left, Right, Jump, Pause = false;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {

        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_A:
                Left = true;
                break;

            case KeyEvent.VK_D:
                Right = true;
                break;

            case KeyEvent.VK_SPACE:
                Jump = true;
                break;

            case KeyEvent.VK_P:
                if (gp.currentState == gp.playState) {
                    gp.currentState = gp.pauseState;
                } else if (gp.currentState == gp.pauseState) {
                    gp.currentState = gp.playState;
                }
                break;
            case KeyEvent.VK_R:
                if (gp.currentState == gp.diedState) {
                    gp.currentState = gp.playState;
                    gp.player.setDefault();
                    gp.setupMonsters();
                    gp.resetCoins();
                }
                break;
            default:
                return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
                Left = false;
                break;

            case KeyEvent.VK_D:
                Right = false;
                break;

            case KeyEvent.VK_SPACE:
                Jump = false;
                break;

            default:
                return;
        }
    }

}
