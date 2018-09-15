/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derplayer.gui;

import derplayer.Main;
import derplayer.Musica;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

class Filtro extends FileFilter {

    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return true;
        }
        if (pathname.getName().endsWith(".mp3")) {
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Arquivos de música (*.mp3)";
    }
}

/**
 *
 * @author Andoreh
 */
public class Diretorios {

    private String arquivoMusicas;
    private Set<String> arquivosTocar;

    public Diretorios(String caminhoArquivoMusicas) {
        arquivoMusicas = caminhoArquivoMusicas;
    }

    public Set<String> consultarDiretorios() {
        if (arquivosTocar == null) {
            arquivosTocar = new TreeSet<String>();
            Scanner scanner = null;
            try {
                scanner = new Scanner(new File(arquivoMusicas));
                while (scanner.hasNext()) {
                    String linha = scanner.nextLine();
                    arquivosTocar.add(linha);
                }
                scanner.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            if (arquivosTocar.size() == 0) {
                Set<String> novoDiretorio = obterNovoDiretorio();
                if (novoDiretorio.isEmpty()) {
                    System.exit(0);
                }

                adicionar(novoDiretorio);
            }
        }
        return arquivosTocar;
    }

    private void salvar() {
        try {
            FileOutputStream arquivoDiretorios = new FileOutputStream(new File(arquivoMusicas));
            Set<String> arquivos = consultarDiretorios();
            Object[] vetorArquivos = arquivos.toArray();
            for (int i = 0; i < vetorArquivos.length; i++) {
                String nomeArquivo = (String) vetorArquivos[i];
                if (i == vetorArquivos.length - 1) {
                    arquivoDiretorios.write((nomeArquivo).getBytes());
                } else {
                    arquivoDiretorios.write((nomeArquivo + Main.quebraLinha).getBytes());
                }
            }
            arquivoDiretorios.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Set<Musica> extraiMusicas() {
        Set<String> arquivos = consultarDiretorios();
        return extraiMusicas(arquivos);     
    }

    public static Set<Musica> extraiMusicas(Set<String> arquivos) {        
        Set<Musica> musicas = new TreeSet<Musica>();
        for (String diretorio : arquivos) {
            extraiMusicas(musicas, diretorio);
        }
        return musicas;
    }

    private static void extraiMusicas(Set<Musica> musicas, String nomeDiretorio) {
        File diretorio = new File(nomeDiretorio);
        if (diretorio.isDirectory()) {
            File[] arquivosContidos = diretorio.listFiles();
            for (File musica : arquivosContidos) {
                extraiMusicas(musicas, musica.getAbsolutePath());
            }
        } else {
            if (diretorio.getName().endsWith(".mp3")) {
                musicas.add(new Musica(diretorio));
            }
        }
    }

    void remover(Set<String> arquivosSelecionados) {
        Set<String> arquivos = consultarDiretorios();
        arquivos.removeAll(arquivosSelecionados);
        salvar();
    }

    void adicionar(Set<String> novoArquivo) {
        Set<String> arquivos = consultarDiretorios();
        arquivos.addAll(novoArquivo);
        salvar();
    }

    public Set<String> obterNovoDiretorio() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setDialogTitle("Escolha o que deseja tocar");
        fileChooser.setFileFilter(new Filtro());
        fileChooser.showOpenDialog(null);

        File[] vetor = fileChooser.getSelectedFiles();
        if (vetor == null) {
            return new TreeSet<String>();
        }

        Set<String> retorno = new TreeSet<String>();
        for (File arquivo : vetor) {
            retorno.add(arquivo.getAbsolutePath());
        }
        return retorno;
    }
}
