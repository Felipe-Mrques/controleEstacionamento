import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public void inserirCarro(Carro carro) {
        String sql = "INSERT INTO Carros (Marca, Placa, Cor, HoraEntrada, HoraSaida) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getPlaca());
            stmt.setString(3, carro.getCor());
            stmt.setInt(4, carro.getHoraEntrada());
            stmt.setInt(5, carro.getHoraSaida());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carro inserido com sucesso.");
            } else {
                System.out.println("Falha ao inserir o carro.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarCarro(Carro carro) {
        String sql = "UPDATE Carros SET Marca = ?, Cor = ?, HoraEntrada = ?, HoraSaida = ? WHERE Placa = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getCor());
            stmt.setInt(3, carro.getHoraEntrada());
            stmt.setInt(4, carro.getHoraSaida());
            stmt.setString(5, carro.getPlaca());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carro atualizado com sucesso.");
            } else {
                System.out.println("Nenhum carro encontrado com essa placa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirCarro(String placa) {
        String sql = "DELETE FROM Carros WHERE Placa = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carro excluído com sucesso.");
            } else {
                System.out.println("Nenhum carro encontrado com essa placa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Carro> listarCarros() {
        List<Carro> listaCarros = new ArrayList<>();
        String sql = "SELECT * FROM Carros";
        try (Connection conn = ConexaoBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("Marca"),
                        rs.getString("Placa"),
                        rs.getString("Cor"),
                        rs.getInt("HoraEntrada"),
                        rs.getInt("HoraSaida")
                );
                listaCarros.add(carro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCarros;
    }
}

