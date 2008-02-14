/*
 * CliqueListenner.java
 * 
 * Created on 25/08/2007, 20:18:08
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derplayer.listenner;

import derplayer.Main;
import derplayer.Musica;
import derplayer.gui.EditarTags;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andoreh
 */
public class CliqueListenner implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        Musica musica = Main.getMusicaCorregir();
        if(musica != null){
            new EditarTags(musica).setVisible(true);
        }
    }

}
