
<%@ page import="edu.uoregon.sticklebackdb.Aquaria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'aquaria.label', default: 'Aquaria')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-aquaria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-aquaria" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="aquaria.container.label" default="Container" /></th>
					
						<g:sortableColumn property="fishTotal" title="${message(code: 'aquaria.fishTotal.label', default: 'Fish Total')}" />
					
						<g:sortableColumn property="fishUnsexed" title="${message(code: 'aquaria.fishUnsexed.label', default: 'Fish Unsexed')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'aquaria.status.label', default: 'Status')}" />
					
						<g:sortableColumn property="statusContainer" title="${message(code: 'aquaria.statusContainer.label', default: 'Status Container')}" />
					
						<g:sortableColumn property="statusFishQuantity" title="${message(code: 'aquaria.statusFishQuantity.label', default: 'Status Fish Quantity')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${aquariaInstanceList}" status="i" var="aquariaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${aquariaInstance.id}">${fieldValue(bean: aquariaInstance, field: "container")}</g:link></td>
					
						<td>${fieldValue(bean: aquariaInstance, field: "fishTotal")}</td>
					
						<td>${fieldValue(bean: aquariaInstance, field: "fishUnsexed")}</td>
					
						<td>${fieldValue(bean: aquariaInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: aquariaInstance, field: "statusContainer")}</td>
					
						<td>${fieldValue(bean: aquariaInstance, field: "statusFishQuantity")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${aquariaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
