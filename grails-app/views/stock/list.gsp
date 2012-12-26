
<%@ page import="edu.uoregon.sticklebackdb.Stock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'stock.label', default: 'Stock')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-stock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-stock" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="stock.barcode.label" default="Barcode" /></th>
					
						<g:sortableColumn property="crossDate" title="${message(code: 'stock.crossDate.label', default: 'Cross Date')}" />
					
						<g:sortableColumn property="crossType" title="${message(code: 'stock.crossType.label', default: 'Cross Type')}" />
					
						<g:sortableColumn property="fertilizationDateTime" title="${message(code: 'stock.fertilizationDateTime.label', default: 'Fertilization Date Time')}" />
					
						<g:sortableColumn property="fertilizationDisplay" title="${message(code: 'stock.fertilizationDisplay.label', default: 'Fertilization Display')}" />
					
						<g:sortableColumn property="fishAgeDays" title="${message(code: 'stock.fishAgeDays.label', default: 'Fish Age Days')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${stockInstanceList}" status="i" var="stockInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${stockInstance.id}">${fieldValue(bean: stockInstance, field: "barcode")}</g:link></td>
					
						<td><g:formatDate date="${stockInstance.crossDate}" /></td>
					
						<td>${fieldValue(bean: stockInstance, field: "crossType")}</td>
					
						<td><g:formatDate date="${stockInstance.fertilizationDateTime}" /></td>
					
						<td>${fieldValue(bean: stockInstance, field: "fertilizationDisplay")}</td>
					
						<td>${fieldValue(bean: stockInstance, field: "fishAgeDays")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${stockInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
