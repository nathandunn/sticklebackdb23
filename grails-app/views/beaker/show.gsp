
<%@ page import="edu.uoregon.sticklebackdb.Beaker" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'beaker.label', default: 'Beaker')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-beaker" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-beaker" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list beaker">
			
				<g:if test="${beakerInstance?.beakerId}">
				<li class="fieldcontain">
					<span id="beakerId-label" class="property-label"><g:message code="beaker.beakerId.label" default="Beaker Id" /></span>
					
						<span class="property-value" aria-labelledby="beakerId-label"><g:fieldValue bean="${beakerInstance}" field="beakerId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${beakerInstance?.eggsDead}">
				<li class="fieldcontain">
					<span id="eggsDead-label" class="property-label"><g:message code="beaker.eggsDead.label" default="Eggs Dead" /></span>
					
						<span class="property-value" aria-labelledby="eggsDead-label"><g:fieldValue bean="${beakerInstance}" field="eggsDead"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${beakerInstance?.eggsDeadTotal}">
				<li class="fieldcontain">
					<span id="eggsDeadTotal-label" class="property-label"><g:message code="beaker.eggsDeadTotal.label" default="Eggs Dead Total" /></span>
					
						<span class="property-value" aria-labelledby="eggsDeadTotal-label"><g:fieldValue bean="${beakerInstance}" field="eggsDeadTotal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${beakerInstance?.eggsInitialNumber}">
				<li class="fieldcontain">
					<span id="eggsInitialNumber-label" class="property-label"><g:message code="beaker.eggsInitialNumber.label" default="Eggs Initial Number" /></span>
					
						<span class="property-value" aria-labelledby="eggsInitialNumber-label"><g:fieldValue bean="${beakerInstance}" field="eggsInitialNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${beakerInstance?.eggsInitialTotal}">
				<li class="fieldcontain">
					<span id="eggsInitialTotal-label" class="property-label"><g:message code="beaker.eggsInitialTotal.label" default="Eggs Initial Total" /></span>
					
						<span class="property-value" aria-labelledby="eggsInitialTotal-label"><g:fieldValue bean="${beakerInstance}" field="eggsInitialTotal"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${beakerInstance?.id}" />
					<g:link class="edit" action="edit" id="${beakerInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
