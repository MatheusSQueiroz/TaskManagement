/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Task;
import Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathe
 */
public class TaskController {

    public void save(Task task) throws SQLException {

        String sql = "INSERT INTO tasks ("
                + "idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar tarefa "
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Task task) {

        String sql = "UPDATE  tasks SET idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "notes = ?,"
                + "completed = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?,"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o DB
            connection = ConnectionFactory.getConnection();
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            //Setando os valores statement   
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            //Executando a Query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualzar a tarefa  "
                    + ex.getMessage(), ex);

        }

    }

    public void removeById(int taskId){

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }
   
    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //Lista de tarefas que será devolvida quando a chamada 
        // do metodo acontecer
        List<Task> tasks = new ArrayList<Task>();

        try {
            //Criando a conexão com o DB
            connection = ConnectionFactory.getConnection();
           //Setando o valor que corresponde ao filtro de busca
            statement = connection.prepareStatement(sql);
          //Valor retornado pela execução da query
            resultSet = statement.executeQuery();
          //Enquanto houver valor a ser percorrido
            while(resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updated"));
                
                tasks.add(task);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar as tarefa"
                    + ex.getMessage(), ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        //Lista de tarefas que foi criada e carregada do DB
        return tasks;
    }
}
