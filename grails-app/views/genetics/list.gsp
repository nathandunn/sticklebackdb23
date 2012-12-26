
<%@ page import="edu.uoregon.sticklebackdb.Genetics" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'genetics.label', default: 'Genetics')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-genetics" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-genetics" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="captureDate" title="${message(code: 'genetics.captureDate.label', default: 'Capture Date')}" />
					
						<g:sortableColumn property="comments" title="${message(code: 'genetics.comments.label', default: 'Comments')}" />
					
						<g:sortableColumn property="identification" title="${message(code: 'genetics.identification.label', default: 'Identification')}" />
					
						<g:sortableColumn property="source" title="${message(code: 'genetics.source.label', default: 'Source')}" />
					
						<g:sortableColumn property="species" title="${message(code: 'genetics.species.label', default: 'Species')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${geneticsInstanceList}" status="i" var="geneticsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${geneticsInstance.id}">${fieldValue(bean: geneticsInstance, field: "captureDate")}</g:link></td>
					
						<td>${fieldValue(bean: geneticsInstance, field: "comments")}</td>
					
						<td>${fieldValue(bean: geneticsInstance, field: "identification")}</td>
					
						<td>${fieldValue(bean: geneticsInstance, field: "source")}</td>
					
						<td>${fieldValue(bean: geneticsInstance, field: "species")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${geneticsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
