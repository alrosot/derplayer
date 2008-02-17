/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package derplayer;

import java.io.File;
import java.io.Serializable;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.id3.ID3v1;

/**
 *
 * @author Andoreh
 */
public class Musica implements Comparable, Serializable {

    static final long serialVersionUID = 302778233463749L;
    private String arquivo;
    private String artista;
    private String titulo;
    private boolean tagMP3Lida;
    private String toString;
    private String info;
    private boolean tagsCorretas;

    public Musica(String caminhoArquivo) {
        if (caminhoArquivo == null) {
            throw new NullPointerException();
        }
        this.arquivo = caminhoArquivo;
    }

    public Musica(File arquivoMusica) {
        if (arquivoMusica == null) {
            throw new NullPointerException();
        }
        arquivo = arquivoMusica.getAbsolutePath();
    }
    
    public boolean getTagsCorretas(){
        return tagsCorretas;
    }

    public String getCaminhoArquivo() {
        return arquivo;
    }

    public File getArquivo() {
        File file = new File(arquivo);
        if (file.exists()) {
            return file;
        }

        file.delete();
        return null;
    }

    public String getInfo() {
        if (info == null) {
            consultaTags();
            // remove a extensao do arquivo
            String nomeArquivo = getNomeArquivo(arquivo);
            info = nomeArquivo.substring(0, nomeArquivo.length() - 4);
            if (artista != null) {
                info = info + " " + artista;
            }
            if (titulo != null) {
                info = info + " " + titulo;
            }
        }
        return info;
    }

    public String getArtista() {
        return artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void resetarTags() {
        toString = null;
        info = null;
        tagMP3Lida = false;
    }

    private void consultaTags() {
        if (!tagMP3Lida) {
            tagMP3Lida = true;
            try {
                MP3File mP3File = new MP3File(getArquivo());
                ID3v1 tags = mP3File.getID3v1Tag();
                if (tags != null) {
                    artista = tags.getArtist();
                    titulo = tags.getSongTitle();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        if (toString == null) {
            tagsCorretas = false;
            consultaTags();
            StringBuffer buffer = new StringBuffer();
            if (artista != null) {
                buffer.append(artista.trim());
            }
            if (titulo != null && titulo.trim().length() > 0) {
                if (buffer.length() > 0) {
                    buffer.append(" - ");
                    tagsCorretas = true;
                }
                buffer.append(titulo.trim());
            }
            if (buffer.length() == 0) {
                buffer.append(getNomeArquivo(arquivo).trim());
            }

            toString = buffer.toString();
        }
        return toString;
    }

    @Override
    public boolean equals(Object outro) {
        if (outro instanceof Musica) {
            Musica outraMusica = (Musica) outro;
            if (arquivo == null || outraMusica.arquivo == null) {
                return false;
            }
            return this.arquivo.equals(((Musica) outro).arquivo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.arquivo != null ? this.arquivo.hashCode() : 0);
        return hash;
    }

    public int compareTo(Object outro) {
        if (outro instanceof Musica) {
            Musica outraMusica = (Musica) outro;
            if (arquivo == null || outraMusica.arquivo == null) {
                return -1;
            }
            return this.arquivo.compareTo(((Musica) outro).arquivo);
        }
        return -1;
    }

    private String getNomeArquivo(String caminhoArquivo) {
        int posicaoBarra = caminhoArquivo.lastIndexOf('/');
        int posicaoContraBarra = caminhoArquivo.lastIndexOf('\\');
        int maior = (posicaoBarra > posicaoContraBarra ? posicaoBarra : posicaoContraBarra);
        return caminhoArquivo.substring(maior + 1);
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void atualizarTags() {
        try {
            TagOptionSingleton.getInstance().setDefaultSaveMode(TagConstant.MP3_FILE_SAVE_OVERWRITE);
            MP3File mP3File = new MP3File(getArquivo());
            ID3v1 tags = mP3File.getID3v1Tag();
            if(tags == null){
                tags = new ID3v1();
                mP3File.setID3v1Tag(tags);
            }            
            tags.setArtist(artista);
            tags.setSongTitle(titulo);
            mP3File.save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
