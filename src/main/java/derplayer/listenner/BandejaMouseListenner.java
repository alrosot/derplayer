/*
 * BandejaMouseListenner.java
 *
 * Created on 25/08/2007, 23:03:31
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derplayer.listenner;

import derplayer.Main;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Andoreh
 */
public class BandejaMouseListenner implements MouseListener {

    public BandejaMouseListenner() {
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Main.getPlayList().proxima();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
