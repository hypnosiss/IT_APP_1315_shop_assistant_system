<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}">
    <div class="alert alert-error fade in">
        ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
    </div>
</c:if>
<div class="col-md-6">
    <h4>Login</h4>
    <br />
    <form action="<c:url value='j_spring_security_check' />" method='POST'>
        <label>Username:</label>
        <input type="text" name="j_username" class="form-control" />
        <label>Password:</label>
        <input type="password" name="j_password" class="form-control" />
        <hr />
        <button class="btn btn-primary btn-alt" type="submit"><span class="glyphicon glyphicon-user"></span> Log In </button>
    </form>
</div>
<div class="col-md-6">
    <h4>Register</h4>
    <br />
    <form:form action="/register" method="POST" modelAttribute="userRegisterForm">
        <label>Username: <span style="color: red;">*</span></label>
        <form:input path="username" class="form-control" />
        <label>Password (8-16 characters): <span style="color: red;">*</span></label>
        <form:password path="password" class="form-control" />
        <label>Repeat password: <span style="color: red;">*</span></label>
        <form:password path="repeatPassword" class="form-control" />
        <hr />
        <label>Name: <span style="color: red;">*</span></label>
        <form:input path="name" class="form-control" />
        <label>Address: <span style="color: red;">*</span></label>
        <form:textarea path="address" class="form-control" />
        <label>Phone:</label>
        <form:input path="phone" class="form-control" />
        <br/>
        <span style="color: red;">*</span> - required
        <br/>
        <br/>
        <button class="btn btn-primary btn-alt" type="submit"><span class="glyphicon glyphicon-user"></span> Create account </button>
    </form:form>
</div>
