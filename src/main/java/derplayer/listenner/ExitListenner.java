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
public class ExitListenner implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        try{
            Main.getPlayList().close();
        }
        catch(Exception ex){
            System.exit(0);
        }        
    }
}
