/*
 * EscolherArquivos.java
 *
 * Created on February 9, 2008, 7:23 PM
 */

package derplayer.gui;

import derplayer.Main;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 
 @author  Andoreh
 */
public class EscolherArquivos extends javax.swing.JFrame {
    
    private DefaultTableModel modelo;
    private static EscolherArquivos instanciaAtual;
    
    /** Creates new form EscolherArquivos */
    public EscolherArquivos() {
        initComponents();
        modelo = (DefaultTableModel) tabelaArquivos.getModel();
    }
    
    public static void exibir() {
        if(instanciaAtual == null){
            instanciaAtual = new EscolherArquivos();
        }
        instanciaAtual.montarLista();
        instanciaAtual.setVisible(true);
    }
    
    private Rectangle getPosicao(){
        int largura = 340;
        int altura = 300;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();          
        Rectangle retorno = new Rectangle((int)screenSize.getWidth()-largura-10,(int)screenSize.getHeight()-altura-62,largura,altura);
        return retorno;        
    }
    
    /** This method is called from within the constructor to
     initialize the form.
     WARNING: Do NOT modify this code. The content of this method is
     always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaArquivos = new javax.swing.JTable();
        btRemover = new javax.swing.JButton();
        btAdicionar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle("Escolher arquivos");
        setBounds(getPosicao());
        setIconImage(Main.getImage());

        tabelaArquivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Arquivos a tocar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaArquivos.setShowHorizontalLines(false);
        tabelaArquivos.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tabelaArquivos);

        btRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit_remove.png"))); // NOI18N
        btRemover.setText("Remover");
        btRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverActionPerformed(evt);
            }
        });

        btAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit_add.png"))); // NOI18N
        btAdicionar.setText("Adicionar");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/help.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(btAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRemover)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btRemover)
                        .addComponent(btAdicionar))
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverActionPerformed
        int[] linhasSelecionadas = tabelaArquivos.getSelectedRows();
        Set<String> arquivosSelecionados = new TreeSet<String>();
        for(int linha : linhasSelecionadas){
            String arquivo = (String) tabelaArquivos.getValueAt(linha, 0);
            arquivosSelecionados.add(arquivo);
        }
        Main.getDiretorios().remover(arquivosSelecionados);
        Main.getPlayList().remover(Diretorios.extraiMusicas(arquivosSelecionados));
        montarLista();
}//GEN-LAST:event_btRemoverActionPerformed

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        Set<String> novoArquivo = Main.getDiretorios().obterNovoDiretorio();
        Main.getDiretorios().adicionar(novoArquivo);  
        Main.getPlayList().adicionar(Diretorios.extraiMusicas(novoArquivo));
        montarLista();        
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String mensagem =
                "Aqui você pode escolher ou remover os arquivos a serem" + Main.quebraLinha +
                "tocados no DerPlayer. Observe que é possível selcionar" + Main.quebraLinha +
                "múltiplos arquivou ou diretórios ao mesmo tempo." + Main.quebraLinha +
                "Se um diretório for escolhido, serão inseridos na lista" + Main.quebraLinha +
                "todos os arquivos .mp3 existentes na raiz do diretório" + Main.quebraLinha +
                "e também em todas as suas subpastas.";
        JOptionPane.showMessageDialog(this, mensagem, "Ajuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void montarLista(){
        Set<String> arquivos = Main.getDiretorios().consultarDiretorios();
        while(modelo.getRowCount()>0){
            modelo.removeRow(0);
        }
        for(String arquivo : arquivos){
            modelo.addRow(new Object[]{arquivo});
        }
    }
    
    /**
     @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EscolherArquivos().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btRemover;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaArquivos;
    // End of variables declaration//GEN-END:variables
    
}