
<%@ page import="edu.uoregon.sticklebackdb.Individual" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'individual.label', default: 'Individual')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-individual" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-individual" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="fertilization" title="${message(code: 'individual.fertilization.label', default: 'Fertilization')}" />
					
						<g:sortableColumn property="fishLocation" title="${message(code: 'individual.fishLocation.label', default: 'Fish Location')}" />
					
						<g:sortableColumn property="index" title="${message(code: 'individual.index.label', default: 'Index')}" />
					
						<th><g:message code="individual.maternal.label" default="Maternal" /></th>
					
						<th><g:message code="individual.paternal.label" default="Paternal" /></th>
					
						<th><g:message code="individual.stock.label" default="Stock" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${individualInstanceList}" status="i" var="individualInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${individualInstance.id}">${fieldValue(bean: individualInstance, field: "fertilization")}</g:link></td>
					
						<td>${fieldValue(bean: individualInstance, field: "fishLocation")}</td>
					
						<td>${fieldValue(bean: individualInstance, field: "index")}</td>
					
						<td>${fieldValue(bean: individualInstance, field: "maternal")}</td>
					
						<td>${fieldValue(bean: individualInstance, field: "paternal")}</td>
					
						<td>${fieldValue(bean: individualInstance, field: "stock")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${individualInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
