<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:if test="${not empty notifications}">
	<div class="row">
	<c:forEach items="${notifications}" var="notification">
		<c:if test="${notification.type == 'SUCCESS'}" >
			<div class="col-md-12">
				<div class="alert alert-success">
						${notification.content}
				</div>
			</div>
		</c:if>
		<c:if test="${notification.type == 'INFO'}" >
			<div class="col-md-12">
				<div class="alert alert-info">
						${notification.content}
				</div>
			</div>
		</c:if>
		<c:if test="${notification.type == 'WARNING'}" >
			<div class="col-md-12">
				<div class="alert alert-warning">
						${notification.content}
				</div>
			</div>
		</c:if>
		<c:if test="${notification.type == 'ERROR'}" >
			<div class="col-md-12">
				<div class="alert alert-danger">
						${notification.content}
				</div>
			</div>
		</c:if>
	</c:forEach>
	</div>
</c:if>

