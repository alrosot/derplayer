/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derplayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

class PreLoader extends Thread {

    private List<Musica> musicas;

    PreLoader(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public void run() {
        for (Musica musica : musicas) {
            musica.toString();
        }
        Main.getPlayList().salvar();
    }
}

/**
 *
 * @author Andoreh
 */
public class PlayList {

    enum Estados {
        PLAY, STOP, PREVIOUS, NEXT, CLOSE
    }
    private Estados acaoAtual;
    private List<Musica> musicas;
    private BasicPlayer basicPlayer;
    private int posicaoAtual;
    private static final String ARQUIVO_SERIALIZAR = "DerPlayer.db";

    public PlayList() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(ARQUIVO_SERIALIZAR)));
            musicas = (List<Musica>) inputStream.readObject();
            Collections.shuffle(musicas);
            inputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            musicas = new ArrayList<Musica>();
        }

        posicaoAtual = 0;
        acaoAtual = Estados.PLAY;
    }

    private void preLoader() {
        PreLoader preLoader = new PreLoader(musicas);
        preLoader.setPriority(Thread.MIN_PRIORITY);
        preLoader.start();
    }
    
    public Musica getMusicaAtual(){
        return basicPlayer.getMusica();
    }

    void salvar() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(ARQUIVO_SERIALIZAR)));
            outputStream.writeObject(musicas);
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void adicionar(Set<Musica> musicasNovas) {
        for (Musica musica : musicasNovas) {
            if (!musicas.contains(musica)) {
                musicas.add(musica);
            }
        }
        preLoader();
    }

    public void remover(Set<Musica> musicasSelecionadas) {
        musicas.removeAll(musicasSelecionadas);        
        salvar();
    }

    void remover(Musica musica) {
        musicas.remove(musica);        
    }

    public void tocar(Musica musica) {
        int posicaoMusica = musicas.indexOf(musica);
        if (posicaoMusica > 0) {
            posicaoAtual = posicaoMusica;
        } else {
            musicas.add(musica);
            posicaoAtual = musicas.indexOf(musica);
        }
        if (acaoAtual == Estados.STOP) {
            acaoAtual = Estados.PLAY;
            continuar();
        } else {
            acaoAtual = Estados.PLAY;
            basicPlayer.close();
        }
    }

    public List<Musica> consultarListaMusicas() {
        return musicas;
    }

    public void continuar() {
        if (acaoAtual == Estados.STOP) {
            return;
        }
        if (acaoAtual == Estados.PREVIOUS) {
            posicaoAtual--;
            if (posicaoAtual < 0) {
                posicaoAtual = musicas.size() - 1;
            }
            tocar();
        }
        if (acaoAtual == Estados.PLAY) {
            tocar();
        }
        if (acaoAtual == Estados.NEXT) {
            posicaoAtual++;
            tocar();
        }

        acaoAtual = Estados.NEXT;
    }

    public void tocar() {
        try {
            posicaoAtual = posicaoAtual % musicas.size();
            basicPlayer = new BasicPlayer(musicas.get(posicaoAtual));
            //basicPlayer.setPriority(Thread.MAX_PRIORITY);
            basicPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();   
            Main.ocultarMusica();
            acaoAtual = Estados.STOP;
        }
    }

    public void proxima() {
        if (acaoAtual == Estados.STOP) {
            acaoAtual = Estados.PLAY;
            continuar();
        } else {
            acaoAtual = Estados.NEXT;
            basicPlayer.close();
        }
    }

    public void anterior() {
        if (acaoAtual == Estados.STOP) {
            acaoAtual = Estados.PREVIOUS;
            continuar();
        } else {
            acaoAtual = Estados.PREVIOUS;
            basicPlayer.close();
        }
    }

    public void parar() {
        acaoAtual = Estados.STOP;
        basicPlayer.close();
    }

    public void excluirMusicaAtual() {
        Musica musicaExcluir = basicPlayer.getMusica();
        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o arquivo\n" + musicaExcluir.getCaminhoArquivo(), "Excluir arquivo", JOptionPane.YES_NO_OPTION);
        if (showConfirmDialog == JOptionPane.YES_OPTION) {
            acaoAtual = Estados.STOP;
            basicPlayer.close();
            remover(musicaExcluir);
            acaoAtual = Estados.PLAY;
            continuar();
            musicaExcluir.getArquivo().delete();
        }
    }

    public void close() {
        acaoAtual = Estados.CLOSE;
        basicPlayer.close();
        salvar();
        System.exit(0);
    }
}
