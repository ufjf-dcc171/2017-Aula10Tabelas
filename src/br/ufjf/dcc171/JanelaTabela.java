package br.ufjf.dcc171;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.layout.Border;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class JanelaTabela extends JFrame {

    private JTextField txtNome = new JTextField(20);
    private JTextField txtIdade = new JTextField(2);
    private JTextField txtEmail = new JTextField(40);
    private JButton btnAdicionar = new JButton("Adicionar");
    private JButton btnRemover = new JButton("Remover");
    private JButton btnSalvar = new JButton("Salvar");

    private JTable tabela;

    public JanelaTabela() throws HeadlessException {
        super("Tabelas 01");
        Object[][] dados = new Object[][]{
            {"Fulano", "18", "fulano@somedomain.com"},
            {"Beltrano", "22", "beltrano@somedomain.com"},
            {"Ciclano", "19", "ciclano@somedomain.com"}
        };
        Object[] titulos = new Object[]{"Nome", "Idade", "E-Mail"};
        tabela = new JTable(new DefaultTableModel(dados, titulos));
        btnSalvar.setEnabled(false);
        btnRemover.setEnabled(false);

        JPanel formulario = new JPanel();
        formulario.setLayout(new GridLayout(2, 3));
        formulario.add(txtNome);
        formulario.add(txtIdade);
        formulario.add(txtEmail);
        formulario.add(btnSalvar);
        formulario.add(btnRemover);
        formulario.add(btnAdicionar);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tabela.getSelectedRowCount() == 0) {
                    btnRemover.setEnabled(false);
                    btnSalvar.setEnabled(false);
                    btnAdicionar.setEnabled(true);
                } else {
                    btnAdicionar.setEnabled(false);
                    btnRemover.setEnabled(true);
                    btnSalvar.setEnabled(true);
                    DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
                    int linha = tabela.getSelectedRow();
                    txtNome.setText((String)modelo.getValueAt(linha, 0));
                    txtIdade.setText((String)modelo.getValueAt(linha, 1));
                    txtEmail.setText((String)modelo.getValueAt(linha, 2));
                }
            }
        });

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
                modelo.addRow(new Object[]{
                    txtNome.getText(),
                    txtIdade.getText(),
                    txtEmail.getText()
                });
                txtNome.setText("");
                txtIdade.setText("");
                txtEmail.setText("");
                txtNome.requestFocus();
            }
        });
        
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabela.getSelectedRowCount()==0) return;
                DefaultTableModel modelo = (DefaultTableModel)tabela.getModel();
                modelo.removeRow(tabela.getSelectedRow());
            }
        });
        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabela.getSelectedRowCount()==0) return;
                DefaultTableModel modelo = (DefaultTableModel)tabela.getModel();
                int linha  = tabela.getSelectedRow();
                modelo.setValueAt(txtNome.getText(), linha, 0);
                modelo.setValueAt(txtIdade.getText(), linha, 1);
                modelo.setValueAt(txtEmail.getText(), linha, 2);
                txtNome.setText("");
                txtIdade.setText("");
                txtEmail.setText("");
                tabela.clearSelection();
                txtNome.requestFocus();
            }
        });
    }

}
