package todolist.Controller.Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import todolist.Model.Task;

/**
 * ConnectioDAO
 */
public class ListDAO {

    private Connection connection;
    private List<Task> tarefa;

    public ListDAO() {
        this.connection = ConnectionFactory.getConnection();

    }

    // criar Tabela
    public void criarTabela() {
        String query = "CREATE TABLE IF NOT EXISTS tarefas_todolist (TAREFA VARCHAR(255),CONCLUIDA VARCHAR(255),ID SERIAL PRIMARY KEY)";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(query);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Listar todos os valores cadastrados
    public List<Task> listarTodos() {
        PreparedStatement stmt = null;
        // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta
        tarefa = new ArrayList<>();
        // Cria uma lista para armazenar os carros recuperados do banco de dados
        try {
            String query = "SELECT * FROM tarefas_todolist";
            stmt = connection.prepareStatement(query);
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Carros com os valores do
                // registro
                Task tarefas = new Task(
                        rs.getString("tarefa"),
                        rs.getBoolean("concluida"),
                        rs.getInt("id"));
                tarefa.add(tarefas); // Adiciona o objeto Carros à lista de carros
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
            // Fecha a conexão, o PreparedStatement e o ResultSet
        }
        return tarefa; // Retorna a lista de carros recuperados do banco de dados
    }

    // Cadastrar Carro no banco
    public void cadastrar(String tarefa, Boolean concluida) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String query = "INSERT INTO tarefas_todolist (tarefa, concluida) VALUES (?, ?)";

        try {
            stmt = connection.prepareStatement(query);

            stmt.setString(1, tarefa);
            stmt.setBoolean(2, concluida);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);

        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void atualizar(String tarefa, Boolean concluida, int id) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String query = "UPDATE tarefas_todolist SET tarefa = ?, concluida = ? WHERE id = ?"; // Placa é a minha chave
                                                                                             // primária //A ordem não
                                                                                             // importa, o que realmente
                                                                                             // importa é a ordem dos
                                                                                             // pontos de interrogação
                                                                                             // em relação a ordem no
                                                                                             // inferior

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, tarefa);
            stmt.setBoolean(2, concluida);
            // placa é chave primaria não pode ser alterada
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Dados atualizados com sucesso");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);

        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void apagar(String id) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pela id
        String query = "DELETE FROM tarefas_todolist WHERE id = ?";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate(); // Executa a instrução SQL
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        }
        ConnectionFactory.closeConnection(connection, stmt);
    }

}