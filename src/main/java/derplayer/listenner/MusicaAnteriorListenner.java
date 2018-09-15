/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derplayer.listenner;

import derplayer.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andoreh
 */
public class MusicaAnteriorListenner implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        Main.getPlayList().anterior();
    }
}
