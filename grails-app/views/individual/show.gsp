
<%@ page import="edu.uoregon.sticklebackdb.Individual" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'individual.label', default: 'Individual')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-individual" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-individual" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list individual">
			
				<g:if test="${individualInstance?.fertilization}">
				<li class="fieldcontain">
					<span id="fertilization-label" class="property-label"><g:message code="individual.fertilization.label" default="Fertilization" /></span>
					
						<span class="property-value" aria-labelledby="fertilization-label"><g:formatDate date="${individualInstance?.fertilization}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${individualInstance?.fishLocation}">
				<li class="fieldcontain">
					<span id="fishLocation-label" class="property-label"><g:message code="individual.fishLocation.label" default="Fish Location" /></span>
					
						<span class="property-value" aria-labelledby="fishLocation-label"><g:fieldValue bean="${individualInstance}" field="fishLocation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${individualInstance?.index}">
				<li class="fieldcontain">
					<span id="index-label" class="property-label"><g:message code="individual.index.label" default="Index" /></span>
					
						<span class="property-value" aria-labelledby="index-label"><g:fieldValue bean="${individualInstance}" field="index"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${individualInstance?.maternal}">
				<li class="fieldcontain">
					<span id="maternal-label" class="property-label"><g:message code="individual.maternal.label" default="Maternal" /></span>
					
						<span class="property-value" aria-labelledby="maternal-label"><g:link controller="individual" action="show" id="${individualInstance?.maternal?.id}">${individualInstance?.maternal?.barcode}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${individualInstance?.paternal}">
				<li class="fieldcontain">
					<span id="paternal-label" class="property-label"><g:message code="individual.paternal.label" default="Paternal" /></span>
					
						<span class="property-value" aria-labelledby="paternal-label"><g:link controller="individual" action="show" id="${individualInstance?.paternal?.id}">${individualInstance?.paternal?.barcode}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${individualInstance?.stock}">
				<li class="fieldcontain">
					<span id="stock-label" class="property-label"><g:message code="individual.stock.label" default="Stock" /></span>
					
						<span class="property-value" aria-labelledby="stock-label"><g:link controller="stock" action="show" id="${individualInstance?.stock?.id}">${individualInstance?.stock?.barcode}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${individualInstance?.stockDate}">
				<li class="fieldcontain">
					<span id="stockDate-label" class="property-label"><g:message code="individual.stockDate.label" default="Stock Date" /></span>
					
						<span class="property-value" aria-labelledby="stockDate-label"><g:formatDate date="${individualInstance?.stockDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${individualInstance?.stockIndividualDate}">
				<li class="fieldcontain">
					<span id="stockIndividualDate-label" class="property-label"><g:message code="individual.stockIndividualDate.label" default="Stock Individual Date" /></span>
					
						<span class="property-value" aria-labelledby="stockIndividualDate-label"><g:formatDate date="${individualInstance?.stockIndividualDate}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${individualInstance?.id}" />
					<g:link class="edit" action="edit" id="${individualInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
