import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EstacionamentoGUI extends JFrame {
    private JTextField txtMarca, txtPlaca, txtCor, txtHoraEntrada, txtHoraSaida;
    private JButton btnSalvar, btnAlterar, btnExcluir;
    private JTable tableCarros;
    private DefaultTableModel tableModel;
    private CarroDAO carroDAO;

    public EstacionamentoGUI() {
        carroDAO = new CarroDAO();
        initComponents();
        listarCarros();
    }

    private void initComponents() {
        setTitle("Controle de Estacionamento");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Marca:"), gbc);
        txtMarca = new JTextField(15);
        gbc.gridx = 1;
        add(txtMarca, gbc);

        gbc.gridx = 2;
        add(new JLabel("Placa:"), gbc);
        txtPlaca = new JTextField(15);
        gbc.gridx = 3;
        add(txtPlaca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Cor:"), gbc);
        txtCor = new JTextField(15);
        gbc.gridx = 1;
        add(txtCor, gbc);

        gbc.gridx = 2;
        add(new JLabel("Hora Entrada:"), gbc);
        txtHoraEntrada = new JTextField(10);
        gbc.gridx = 3;
        add(txtHoraEntrada, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Hora Saída:"), gbc);
        txtHoraSaida = new JTextField(10);
        gbc.gridx = 1;
        add(txtHoraSaida, gbc);


        btnSalvar = new JButton("Salvar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btnSalvar, gbc);

        btnAlterar = new JButton("Alterar");
        gbc.gridx = 1;
        add(btnAlterar, gbc);

        btnExcluir = new JButton("Excluir");
        gbc.gridx = 2;
        add(btnExcluir, gbc);

        String[] colunas = {"Marca", "Placa", "Cor", "Hora Entrada", "Hora Saída"};
        tableModel = new DefaultTableModel(colunas, 0);
        tableCarros = new JTable(tableModel);
        tableCarros.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableCarros.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableCarros.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableCarros.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableCarros.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableCarros.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tableCarros);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scrollPane, gbc);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCarro();
                listarCarros();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarCarro();
                listarCarros();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCarro();
                listarCarros();
            }
        });
    }

    private void salvarCarro() {
        try {
            String marca = txtMarca.getText();
            String placa = txtPlaca.getText();
            String cor = txtCor.getText();
            int horaEntrada = Integer.parseInt(txtHoraEntrada.getText());
            int horaSaida = Integer.parseInt(txtHoraSaida.getText());

            Carro carro = new Carro(marca, placa, cor, horaEntrada, horaSaida);
            carroDAO.inserirCarro(carro);
            JOptionPane.showMessageDialog(this, "Carro salvo com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos para Hora Entrada e Hora Saída.");
        }
    }

    private void alterarCarro() {
        if (txtPlaca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira a placa do carro a ser alterado.");
            return;
        }

        try {
            String marca = txtMarca.getText();
            String placa = txtPlaca.getText();
            String cor = txtCor.getText();
            int horaEntrada = Integer.parseInt(txtHoraEntrada.getText());
            int horaSaida = Integer.parseInt(txtHoraSaida.getText());

            Carro carro = new Carro(marca, placa, cor, horaEntrada, horaSaida);
            carroDAO.alterarCarro(carro);
            JOptionPane.showMessageDialog(this, "Carro alterado com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos para Hora Entrada e Hora Saída.");
        }
    }

    private void excluirCarro() {
        if (txtPlaca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira a placa do carro a ser excluído.");
            return;
        }

        String placa = txtPlaca.getText();
        int confirmacao = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja excluir o carro com a placa: " + placa + "?", "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            carroDAO.excluirCarro(placa);
            JOptionPane.showMessageDialog(this, "Carro excluído com sucesso.");
            limparCampos();
        }
    }

    private void limparCampos() {
        txtMarca.setText("");
        txtPlaca.setText("");
        txtCor.setText("");
        txtHoraEntrada.setText("");
        txtHoraSaida.setText("");
    }


    private void listarCarros() {
        tableModel.setRowCount(0);
        List<Carro> listaCarros = carroDAO.listarCarros();
        for (Carro carro : listaCarros) {
            tableModel.addRow(new Object[]{
                    carro.getMarca(),
                    carro.getPlaca(),
                    carro.getCor(),
                    carro.getHoraEntrada(),
                    carro.getHoraSaida()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstacionamentoGUI().setVisible(true));
    }
}
