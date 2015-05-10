<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="application/javascript">
    $(document).ready(function () {
        var progressbarValue = 0;

        function reloadProgressBar() {
            progressbarValue += 2;

            $('#search-product-progressbar').attr('aria-valuenow', progressbarValue)
                    .css('width', progressbarValue + '%');
            if (progressbarValue == 100) {
                return;
            }

            setTimeout(reloadProgressBar, 100);
        }

        $('#search-product-button').click(function () {
            var ean = $('#search-product-ean').val();
            if (!ean.match("([0-9]{8})") && !ean.match("([0-9]{13})")) {
                alert('Invalid European Article Number');
                return false;
            }

            $('#search-product-modal').modal();
            setTimeout(reloadProgressBar, 100);

            $.ajax({
                method: "POST",
                url: "/products/add",
                cache: false,
                data: JSON.stringify({ean: ean}),
                datatype : "json",
                contentType: "application/json; charset=utf-8",
            })
            .done(function() {
                window.location = '/products';
            });

            return false;
        });
    });
</script>

<div id="search-product" style="float: right">
    <div style="float: left;">
        <label>Add product: </label>&nbsp;&nbsp;
    </div>
    <div style="float: left;">
        <input type="text" id="search-product-ean" placeholder="EAN" class="form-control" style="width: 300px;float: left;" />
    </div>
    <div style="float: left;">
        <button id="search-product-button" class="btn btn-primary" style="float: left;">Add</button>
    </div>
    <div style="clear: both"></div>
</div>
<div style="clear: both"></div>

<br/>

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
                <td>
                    <c:choose>
                        <c:when test="${empty userProduct.name}">
                            ${userProduct.product.name}
                        </c:when>
                        <c:otherwise>
                            ${userProduct.name}
                        </c:otherwise>
                    </c:choose>
                </td>
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
    <button id="neworder" class="btn btn-danger">Place Order</button>
</div>

<div class="modal fade" id="search-product-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-primary" id="search-product-progressbar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>