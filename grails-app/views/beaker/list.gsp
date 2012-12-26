
<%@ page import="edu.uoregon.sticklebackdb.Beaker" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'beaker.label', default: 'Beaker')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-beaker" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-beaker" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="beakerId" title="${message(code: 'beaker.beakerId.label', default: 'Beaker Id')}" />
					
						<g:sortableColumn property="eggsDead" title="${message(code: 'beaker.eggsDead.label', default: 'Eggs Dead')}" />
					
						<g:sortableColumn property="eggsDeadTotal" title="${message(code: 'beaker.eggsDeadTotal.label', default: 'Eggs Dead Total')}" />
					
						<g:sortableColumn property="eggsInitialNumber" title="${message(code: 'beaker.eggsInitialNumber.label', default: 'Eggs Initial Number')}" />
					
						<g:sortableColumn property="eggsInitialTotal" title="${message(code: 'beaker.eggsInitialTotal.label', default: 'Eggs Initial Total')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${beakerInstanceList}" status="i" var="beakerInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${beakerInstance.id}">${fieldValue(bean: beakerInstance, field: "beakerId")}</g:link></td>
					
						<td>${fieldValue(bean: beakerInstance, field: "eggsDead")}</td>
					
						<td>${fieldValue(bean: beakerInstance, field: "eggsDeadTotal")}</td>
					
						<td>${fieldValue(bean: beakerInstance, field: "eggsInitialNumber")}</td>
					
						<td>${fieldValue(bean: beakerInstance, field: "eggsInitialTotal")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${beakerInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
