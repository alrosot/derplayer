/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derplayer.listenner;

import derplayer.gui.EscolherArquivos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andoreh
 */
public class EscolherArquivoListenner implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        EscolherArquivos.exibir();
    }

}
