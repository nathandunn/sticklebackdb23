
<%@ page import="edu.uoregon.sticklebackdb.Researcher" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'researcher.label', default: 'Researcher')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-researcher" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-researcher" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list researcher">
			
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="researcher.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${researcherInstance}" field="username"/></span>
					
				</li>

				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="researcher.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${researcherInstance}" field="name"/></span>
					
				</li>

				<li class="fieldcontain">
					<span id="roles-label" class="property-label"><g:message code="researcher.roles.label" default="Roles" /></span>
					
						<g:each in="${researcherInstance.roles}" var="r">
						<span class="property-value" aria-labelledby="roles-label">
                            ${r?.name}
                        </span>
						</g:each>
					
				</li>

			</ol>
			<g:form url="[resource:researcherInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${researcherInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
