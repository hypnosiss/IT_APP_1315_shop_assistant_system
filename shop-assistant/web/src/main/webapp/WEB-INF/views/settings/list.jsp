<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@	taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
            <tr>
                <td></td>
                <td>01/22/2015 </td>
                <td>
                    <label class="label label-info">300 USD</label>
                </td>
                <td>
                    <label class="label label-success">Delivered</label></td>
                <td>01/25/2015</td>
                <td>
                    <a href="#" class="btn btn-xs btn-danger">View</a>
                </td>
            </tr>
        </tbody>
    </table>
</div>
