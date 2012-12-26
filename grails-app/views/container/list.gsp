
<%@ page import="edu.uoregon.sticklebackdb.Container" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'container.label', default: 'Container')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-container" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-container" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="barcode" title="${message(code: 'container.barcode.label', default: 'Barcode')}" />
					
						<g:sortableColumn property="location" title="${message(code: 'container.location.label', default: 'Location')}" />
					
						<g:sortableColumn property="size" title="${message(code: 'container.size.label', default: 'Size')}" />
					
						<g:sortableColumn property="total" title="${message(code: 'container.total.label', default: 'Total')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${containerInstanceList}" status="i" var="containerInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${containerInstance.id}">${fieldValue(bean: containerInstance, field: "barcode")}</g:link></td>
					
						<td>${fieldValue(bean: containerInstance, field: "location")}</td>
					
						<td>${fieldValue(bean: containerInstance, field: "size")}</td>
					
						<td>${fieldValue(bean: containerInstance, field: "total")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${containerInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
