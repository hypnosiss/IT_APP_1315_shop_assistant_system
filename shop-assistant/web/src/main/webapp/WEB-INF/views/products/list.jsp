<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="table-responsive">
    <table id="productsTable" class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <th></th>
                <th>EAN</th>
                <th>Name</th>
                <th>Brand</th>
                <th>Status</th>
                <th>Last order date</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${userProducts}" var="userProduct">
            <tr>
                <td style="width: 20px;"><input type="checkbox" /></td>
                <td>${userProduct.product.ean}</td>
                <td>${userProduct.product.name}</td>
                <td>${userProduct.product.brand}</td>
                <td style="font-size: 16px; text-align: center;">
                    <c:choose>
                        <c:when test="${userProduct.status == 'in'}">
                            <label class="label label-success">IN (quantity: ${userProduct.quantity})</label>
                        </c:when>
                        <c:when test="${userProduct.status == 'out'}">
                            <label class="label label-danger">OUT</label>
                        </c:when>
                        <c:when test="${userProduct.status == 'unknown'}">
                            <label class="label label-info">UNKNOWN</label>
                        </c:when>
                    </c:choose>
                </td>
                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${userProduct.lastOrderTimestamp}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div>
    <button id="neworder" class="btn btn-xs btn-danger">Place Order</button>
</div>
