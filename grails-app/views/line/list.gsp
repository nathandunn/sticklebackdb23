
<%@ page import="edu.uoregon.sticklebackdb.Line" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'line.label', default: 'Line')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-line" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-line" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="abbreviation" title="${message(code: 'line.abbreviation.label', default: 'Abbreviation')}" />
					
						<g:sortableColumn property="captureKey" title="${message(code: 'line.captureKey.label', default: 'Capture Key')}" />
					
						<g:sortableColumn property="captured" title="${message(code: 'line.captured.label', default: 'Captured')}" />
					
						<g:sortableColumn property="comment" title="${message(code: 'line.comment.label', default: 'Comment')}" />
					
						<g:sortableColumn property="lineStatus" title="${message(code: 'line.lineStatus.label', default: 'Line Status')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'line.name.label', default: 'Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${lineInstanceList}" status="i" var="lineInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${lineInstance.id}">${fieldValue(bean: lineInstance, field: "abbreviation")}</g:link></td>
					
						<td>${fieldValue(bean: lineInstance, field: "captureKey")}</td>
					
						<td><g:formatBoolean boolean="${lineInstance.captured}" /></td>
					
						<td>${fieldValue(bean: lineInstance, field: "comment")}</td>
					
						<td>${fieldValue(bean: lineInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: lineInstance, field: "name")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${lineInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
