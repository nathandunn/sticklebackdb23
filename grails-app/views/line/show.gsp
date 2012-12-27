
<%@ page import="edu.uoregon.sticklebackdb.Line" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'line.label', default: 'Line')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-line" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-line" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list line">
			
				<g:if test="${lineInstance?.abbreviation}">
				<li class="fieldcontain">
					<span id="abbreviation-label" class="property-label"><g:message code="line.abbreviation.label" default="Abbreviation" /></span>
					
						<span class="property-value" aria-labelledby="abbreviation-label"><g:fieldValue bean="${lineInstance}" field="abbreviation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.captureKey}">
				<li class="fieldcontain">
					<span id="captureKey-label" class="property-label"><g:message code="line.captureKey.label" default="Capture Key" /></span>
					
						<span class="property-value" aria-labelledby="captureKey-label"><g:fieldValue bean="${lineInstance}" field="captureKey"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.captured}">
				<li class="fieldcontain">
					<span id="captured-label" class="property-label"><g:message code="line.captured.label" default="Captured" /></span>
					
						<span class="property-value" aria-labelledby="captured-label"><g:formatBoolean boolean="${lineInstance?.captured}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="line.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${lineInstance}" field="comment"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="line.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${lineInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.species}">
				<li class="fieldcontain">
					<span id="species-label" class="property-label"><g:message code="line.species.label" default="Species" /></span>
					
						<span class="property-value" aria-labelledby="species-label"><g:fieldValue bean="${lineInstance}" field="species"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="line.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${lineInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.storedLineName}">
				<li class="fieldcontain">
					<span id="storedLineName-label" class="property-label"><g:message code="line.storedLineName.label" default="Stored Line Name" /></span>
					
						<span class="property-value" aria-labelledby="storedLineName-label"><g:fieldValue bean="${lineInstance}" field="storedLineName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.storedLineNameAbbreviation}">
				<li class="fieldcontain">
					<span id="storedLineNameAbbreviation-label" class="property-label"><g:message code="line.storedLineNameAbbreviation.label" default="Stored Line Name Abbreviation" /></span>
					
						<span class="property-value" aria-labelledby="storedLineNameAbbreviation-label"><g:fieldValue bean="${lineInstance}" field="storedLineNameAbbreviation"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${lineInstance?.id}" />
					<g:link class="edit" action="edit" id="${lineInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
