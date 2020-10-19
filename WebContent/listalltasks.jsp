<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of all tasks</title>
</head>
<body >


	<table border="1" width="100%">
		<thead>
			<tr>
				<th>ID da Tarefa</th>
				<th>Nome</th>
				<th>Descrição</th>
				<th>Data de Conclusão</th>
				<th>Atribuido a</th>
				<th>Status</th>
				<th>Data da criação</th>
				<th>Data da atualização</th>
				<th colspan="2">Ações</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tasks}" var="task">
				<tr>
					<td><c:out value="${task.id}" /></td>
					<td><c:out value="${task.name}" /></td>
					<td><c:out value="${task.description}" /></td>
					<td><c:out value="${task.data_conclusao}" /></td>
					<td><c:out value="${task.atribuido}" /></td>
					<td><c:out value="${task.status_tarefa}" /></td>
					<td><c:out value="${task.dateCreated}" /></td>
					<td><c:out value="${task.dateUpdated}" /></td>
					<td><a
						href="TaskController.do?action=edit&id=<c:out value="${task.id}"/>">Editar</a></td>
					<td><a
						href="TaskController.do?action=delete&id=<c:out value="${task.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<jsp:useBean id="now" class="java.util.Date" />


	<h3 style="text-align: center"><c:out value="Tarefas Atrasadas: " /></h3>
	<c:forEach items="${tasks}" var="task" >

		<c:if
			test="${task.data_conclusao le now and task.status_tarefa ne 'Concluida'}">
			<p style="text-align: center">
				${task.id} : ${task.description}<br>
				 Responsável pela tarefa: ${task.atribuido}
		</p>
			</c:if>
	</c:forEach >
	
	
	<p style="text-align: center">
		<a href="TaskController.do?action=create">Criar tarefa</a><br> <a
			href="TaskController?action=listalltasks">Listar todas as tarefas</a><br>
		<a href="TaskController.do?action=atraso">Relação das tarefas
			Pendentes!</a>

	</p>

</body>
</html>