/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derplayer;

import java.io.FileInputStream;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;

/**
 *
 * @author Andoreh
 */
public class BasicPlayer extends Thread {

    private Musica musica;
    private Player player;

    public BasicPlayer(Musica musica) {
        this.musica = musica;
    }

    public void run() {
        try {
            // Aproveito que o arquivo serah aberto e atualizo as tags.
            // Vai que houve uma mudanca externa            
            musica.resetarTags();

            FileInputStream inputStream = new FileInputStream(musica.getArquivo());
            player = new Player(inputStream);
            String descricaoMusica = musica.toString();
            if (musica.getTagsCorretas()) {
                if (Main.getConfiguracao().isExibirErroTags()) {
                    Main.permitirCorrecaoMusica(null);
                    Main.mostrarMusica(descricaoMusica);
                } else {
                    Main.permitirCorrecaoMusica(musica);
                    Main.avisarTagsIncorretas(descricaoMusica);
                }
            } else {
                Main.permitirCorrecaoMusica(musica);
                Main.avisarTagsIncorretas(descricaoMusica);
            }

            player.play();
            player.close();
            Main.getPlayList().continuar();
        } catch (Exception exception) {
            if (player != null) {
                player.close();
            }
            Main.getPlayList().remover(musica);
            Main.getPlayList().tocar();
            exception.printStackTrace();
        }
    }

    void close() {
        if (player != null) {
            player.close();
        }
    }

    public Musica getMusica() {
        return musica;
    }
}
