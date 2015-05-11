<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <th>Name</th>
                <th>Date</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Delivery On</th>
                <th># #</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.shop.name}</td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${order.orderTimestamp}"/></td>
                    <td><label class="label label-info">${order.totalPrice}</label></td>
                    <td><label class="label label-success">UNKNOWN</label></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${order.deliveryDate}"/></td>
                    <td><a href="#" class="btn btn-xs btn-danger">View</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
