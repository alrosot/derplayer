/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derplayer.listenner;

import derplayer.Main;
import derplayer.gui.EditarTags;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andoreh
 */
public class EditarTagsListenner implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        new EditarTags(Main.getPlayList().getMusicaAtual()).setVisible(true);
    }

}
