/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derplayer.listenner;

import derplayer.gui.Localizador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andoreh
 */
public class LocalizadorListenner implements ActionListener{

    public void actionPerformed(ActionEvent e) {
       Localizador.exibir();
    }

}
