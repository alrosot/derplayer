/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derplayer.listenner;

import derplayer.gui.Configuracoes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andoreh
 */
public class ConfiguracoesListenner implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        Configuracoes.exibir();
    }

}
