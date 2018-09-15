/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derplayer;

import derplayer.gui.Diretorios;
import derplayer.listenner.BandejaMouseListenner;
import derplayer.listenner.CliqueListenner;
import derplayer.listenner.ConfiguracoesListenner;
import derplayer.listenner.EditarTagsListenner;
import derplayer.listenner.EscolherArquivoListenner;
import derplayer.listenner.ExcluirListenner;
import derplayer.listenner.ExitListenner;
import derplayer.listenner.LocalizadorListenner;
import derplayer.listenner.MusicaAnteriorListenner;
import derplayer.listenner.PararMusicaListenner;
import derplayer.listenner.SobreListenner;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Andoreh
 */
public class Main {

    private static Musica musicaCorregir;
    private static Image image;
    private static TrayIcon trayIcon;
    public static final String quebraLinha = System.getProperty("line.separator");
    private static Diretorios diretorios;
    private static PlayList playList;
    private static Configuracao config;
    private static final String ARQUIVO_CONFIG = "config.obj";

    public static Configuracao getConfiguracao() {
        return config;
    }

    public static Diretorios getDiretorios() {
        return diretorios;
    }

    public static PlayList getPlayList() {
        return playList;
    }

    public static void mostrarMusica(String nomeMusica) {
        if (Main.getConfiguracao().isExibirMuisca()) {
            trayIcon.displayMessage("Tocando...", nomeMusica, TrayIcon.MessageType.INFO);
        }
        trayIcon.setToolTip("DerPlayer" + quebraLinha + nomeMusica);
    }

    public static void avisarTagsIncorretas(String nomeMusica) {
        if (Main.getConfiguracao().isExibirErroTags()) {
            trayIcon.displayMessage("Opa! Problema com as tags!",
                    "Música: [" + nomeMusica + "]" + quebraLinha +
                    "Esse nome não parece estar muito correto." + quebraLinha +
                    "Cliquei aqui para corrigir isso.", TrayIcon.MessageType.ERROR);
        }
        trayIcon.setToolTip("DerPlayer" + quebraLinha + nomeMusica);
    }

    public static void ocultarMusica() {
        trayIcon.setToolTip("DerPlayer");
    }

    public static Image getImage() {
        return image;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        carregarConfiguracoes();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        // verificacao se o sistema aceita icones no systemTray
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "Seu sistema não suporta aplicativos de bandeja de sistema!", "Erro na inicialização", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Instanciacao do menu
        PopupMenu popup = new PopupMenu();

        MenuItem itemSobre = new MenuItem("Sobre...");
        itemSobre.addActionListener(new SobreListenner());
        popup.add(itemSobre);

        MenuItem itemConfiguracoes = new MenuItem("Configurações");
        itemConfiguracoes.addActionListener(new ConfiguracoesListenner());
        popup.add(itemConfiguracoes);

        MenuItem itemEscolherArquivos = new MenuItem("Escolher arquivos");
        itemEscolherArquivos.addActionListener(new EscolherArquivoListenner());
        popup.add(itemEscolherArquivos);

        popup.addSeparator();

        MenuItem itemPararMusica = new MenuItem("Parar música");
        itemPararMusica.addActionListener(new PararMusicaListenner());
        popup.add(itemPararMusica);

        MenuItem itemExcluir = new MenuItem("Excluir música");
        itemExcluir.addActionListener(new ExcluirListenner());
        popup.add(itemExcluir);

        MenuItem itemEditarTags = new MenuItem("Editar tags");
        itemEditarTags.addActionListener(new EditarTagsListenner());
        popup.add(itemEditarTags);

        MenuItem itemLocalizador = new MenuItem("Localizar");
        itemLocalizador.addActionListener(new LocalizadorListenner());
        popup.add(itemLocalizador);

        MenuItem itemMusicaAnterior = new MenuItem("Música anterior");
        itemMusicaAnterior.addActionListener(new MusicaAnteriorListenner());
        popup.add(itemMusicaAnterior);

        popup.addSeparator();

        MenuItem itemSair = new MenuItem("Sair");
        itemSair.addActionListener(new ExitListenner());
        popup.add(itemSair);

        // Instanciacao do tray
        image = Toolkit.getDefaultToolkit().getImage(new Object().getClass().getResource("/img/icon.png"));
        trayIcon = new TrayIcon(image, "DerPlayer", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(new CliqueListenner());
        trayIcon.addMouseListener(new BandejaMouseListenner());
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(null, "Seu sistema não suporta aplicativos de bandeja de sistema!", "Erro na inicialização", JOptionPane.ERROR_MESSAGE);
            return;
        }

        diretorios = new Diretorios("playlist.conf");

        playList = new PlayList();
        playList.adicionar(diretorios.extraiMusicas());
        playList.continuar();
    }

    public static void permitirCorrecaoMusica(Musica musica) {
        musicaCorregir = musica;
    }

    public static Musica getMusicaCorregir() {
        return musicaCorregir;
    }

    public static void salvarConfiguracoes() {
       try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(ARQUIVO_CONFIG)));
             outputStream.writeObject(config);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void carregarConfiguracoes() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(ARQUIVO_CONFIG)));
            config = (Configuracao) inputStream.readObject();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (config == null) {
            config = new Configuracao();
        }
    }
}
