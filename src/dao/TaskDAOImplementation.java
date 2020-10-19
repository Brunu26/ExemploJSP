package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import util.DBUtil;

public class TaskDAOImplementation implements TaskDAO {
	
	private Connection conn;
	
	public TaskDAOImplementation() {
		conn = DBUtil.getConnection();  
	}

	@Override
	public void createTask(Task task) {
		try {
            String query = "insert into tasks (name, description, data_conclusao,atribuido, status_tarefa,dateCreated, dateUpdated) values (?,?,?,?,?,?,?)";
            
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            preparedStatement.setString( 1, task.getName() );
            preparedStatement.setString( 2, task.getDescription() );             
            preparedStatement.setDate(3, Date.valueOf(task.getData_conclusao()));
            preparedStatement.setString( 4, task.getAtribuido() );
            preparedStatement.setString( 5, task.getStatus_tarefa() );
            
            
            task.setDateCreated(sqlDate);
            preparedStatement.setDate( 6, task.getDateCreated() );
            
            task.setDateUpdated(sqlDate);
            preparedStatement.setDate( 7, task.getDateUpdated() );
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void editTask(Task task) {
		try {
			String query = "update tasks set name=?, description=?, data_conclusao=?, atribuido=?,status_tarefa=?, dateCreated=?, dateUpdated=? where id=?";
	        
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			java.util.Date utilDate = new java.util.Date();
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        
			preparedStatement.setString( 1, task.getName() );
	        preparedStatement.setString( 2, task.getDescription() );
	        preparedStatement.setDate(3, Date.valueOf(task.getData_conclusao()));
	        preparedStatement.setString( 4, task.getAtribuido() );
	        preparedStatement.setString( 5, task.getStatus_tarefa() );
	        preparedStatement.setDate( 6, task.getDateCreated() );
	        
	        
	        
	        task.setDateUpdated(sqlDate);
	        preparedStatement.setDate( 7, task.getDateUpdated() );
	        
	        preparedStatement.setInt(8, task.getId() );
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTask(int id) {
		try {
            String query = "delete from tasks where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public List<Task> listAllTasks() {
		List<Task> tasks = new ArrayList<Task>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery( "select * from tasks" );
            while(resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setData_conclusao(resultSet.getString("data_conclusao"));
                task.setAtribuido(resultSet.getString("atribuido"));
                task.setStatus_tarefa(resultSet.getString("status_tarefa"));
                task.setDateCreated(resultSet.getDate("dateCreated"));
                task.setDateUpdated(resultSet.getDate("dateUpdated"));
                tasks.add(task);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
	}

	@Override
	public Task getTaskById(int id) {
		Task task = new Task();
        try {
            String query = "select * from tasks where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
            	task.setId(resultSet.getInt("id"));
            	task.setName(resultSet.getString("name"));
            	task.setDescription(resultSet.getString("description"));
            	task.setData_conclusao(resultSet.getString("data_conclusao"));
            	task.setAtribuido(resultSet.getString("atribuido"));
            	task.setStatus_tarefa(resultSet.getString("status_tarefa"));
            	task.setDateCreated(resultSet.getDate("dateCreated"));
            	task.setDateUpdated(resultSet.getDate("dateUpdated"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
	}
	
	/**
	 * Retornar as tarefas que não foram concluidas ate a data de hoje
	 * (estão com datas de conclusão maior que a data atual e o status n está concluido)
	 * 
	 * foi a primeira alternativa que encontrei pra mostrar as tarefas pendentes
	 */
	@Override
	public List<Task> listAllAtrasada() {
		List<Task> tasks = new ArrayList<Task>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery( "select * from tasks where ((data_conclusao > now( )) and (status_tarefa != 'Concluida'))" );
            while(resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setData_conclusao(resultSet.getString("data_conclusao"));
                task.setAtribuido(resultSet.getString("atribuido"));
                task.setStatus_tarefa(resultSet.getString("status_tarefa"));
                task.setDateCreated(resultSet.getDate("dateCreated"));
                task.setDateUpdated(resultSet.getDate("dateUpdated"));
                tasks.add(task);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
	}
}
