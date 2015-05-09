<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="col-md-12">
    <form:form action="/settings/change" method="POST" modelAttribute="changeSettingsForm">
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
        <button class="btn btn-primary btn-alt" type="submit"><span class="glyphicon glyphicon-user"></span> Save </button>
    </form:form>
</div>