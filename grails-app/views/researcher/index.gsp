
<%@ page import="edu.uoregon.sticklebackdb.Researcher" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'researcher.label', default: 'Researcher')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-researcher" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-researcher" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="username" title="${message(code: 'researcher.username.label', default: 'Username')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'researcher.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="passwordHash" title="${message(code: 'researcher.passwordHash.label', default: 'Password Hash')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${researcherInstanceList}" status="i" var="researcherInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${researcherInstance.id}">${fieldValue(bean: researcherInstance, field: "username")}</g:link></td>
					
						<td>${fieldValue(bean: researcherInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: researcherInstance, field: "passwordHash")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${researcherInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
