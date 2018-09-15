/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derplayer;

import java.io.Serializable;

/**
 *
 * @author Andoreh
 */
public class Configuracao implements Serializable {

    private boolean exibirMuisca = true;
    private boolean exibirErroTags = true;

    public boolean isExibirMuisca() {
        return exibirMuisca;
    }

    public void setExibirMuisca(boolean exibirMuisca) {
        this.exibirMuisca = exibirMuisca;
    }

    public boolean isExibirErroTags() {
        return exibirErroTags;
    }

    public void setExibirErroTags(boolean exibirErroTags) {
        this.exibirErroTags = exibirErroTags;
    }
}
