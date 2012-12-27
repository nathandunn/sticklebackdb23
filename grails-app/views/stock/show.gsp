
<%@ page import="edu.uoregon.sticklebackdb.Stock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'stock.label', default: 'Stock')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-stock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-stock" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list stock">
			
				<g:if test="${stockInstance?.abbreviation}">
				<li class="fieldcontain">
					<span id="abbreviation-label" class="property-label"><g:message code="stock.abbreviation.label" default="Abbreviation" /></span>
					
						<span class="property-value" aria-labelledby="abbreviation-label"><g:fieldValue bean="${stockInstance}" field="abbreviation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.barcode}">
				<li class="fieldcontain">
					<span id="barcode-label" class="property-label"><g:message code="stock.barcode.label" default="Barcode" /></span>
					
						<span class="property-value" aria-labelledby="barcode-label"><g:link controller="container" action="show" id="${stockInstance?.barcode?.id}">${stockInstance?.barcode?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.breedingType}">
				<li class="fieldcontain">
					<span id="breedingType-label" class="property-label"><g:message code="stock.breedingType.label" default="Breeding Type" /></span>
					
						<span class="property-value" aria-labelledby="breedingType-label"><g:fieldValue bean="${stockInstance}" field="breedingType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="stock.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${stockInstance}" field="comment"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.containerStatus}">
				<li class="fieldcontain">
					<span id="containerStatus-label" class="property-label"><g:message code="stock.containerStatus.label" default="Container Status" /></span>
					
						<span class="property-value" aria-labelledby="containerStatus-label"><g:fieldValue bean="${stockInstance}" field="containerStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.crossDate}">
				<li class="fieldcontain">
					<span id="crossDate-label" class="property-label"><g:message code="stock.crossDate.label" default="Cross Date" /></span>
					
						<span class="property-value" aria-labelledby="crossDate-label"><g:formatDate date="${stockInstance?.crossDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.crossType}">
				<li class="fieldcontain">
					<span id="crossType-label" class="property-label"><g:message code="stock.crossType.label" default="Cross Type" /></span>
					
						<span class="property-value" aria-labelledby="crossType-label"><g:fieldValue bean="${stockInstance}" field="crossType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.experimentStatus}">
				<li class="fieldcontain">
					<span id="experimentStatus-label" class="property-label"><g:message code="stock.experimentStatus.label" default="Experiment Status" /></span>
					
						<span class="property-value" aria-labelledby="experimentStatus-label"><g:fieldValue bean="${stockInstance}" field="experimentStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.fertilizationDateTime}">
				<li class="fieldcontain">
					<span id="fertilizationDateTime-label" class="property-label"><g:message code="stock.fertilizationDateTime.label" default="Fertilization Date Time" /></span>
					
						<span class="property-value" aria-labelledby="fertilizationDateTime-label"><g:formatDate date="${stockInstance?.fertilizationDateTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.fishAgeDays}">
				<li class="fieldcontain">
					<span id="fishAgeDays-label" class="property-label"><g:message code="stock.fishAgeDays.label" default="Fish Age Days" /></span>
					
						<span class="property-value" aria-labelledby="fishAgeDays-label"><g:fieldValue bean="${stockInstance}" field="fishAgeDays"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.fishStock}">
				<li class="fieldcontain">
					<span id="fishStock-label" class="property-label"><g:message code="stock.fishStock.label" default="Fish Stock" /></span>
					
						<span class="property-value" aria-labelledby="fishStock-label"><g:fieldValue bean="${stockInstance}" field="fishStock"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.groupStatus}">
				<li class="fieldcontain">
					<span id="groupStatus-label" class="property-label"><g:message code="stock.groupStatus.label" default="Group Status" /></span>
					
						<span class="property-value" aria-labelledby="groupStatus-label"><g:fieldValue bean="${stockInstance}" field="groupStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.individualStatus}">
				<li class="fieldcontain">
					<span id="individualStatus-label" class="property-label"><g:message code="stock.individualStatus.label" default="Individual Status" /></span>
					
						<span class="property-value" aria-labelledby="individualStatus-label"><g:fieldValue bean="${stockInstance}" field="individualStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.line}">
				<li class="fieldcontain">
					<span id="line-label" class="property-label"><g:message code="stock.line.label" default="Line" /></span>
					
						<span class="property-value" aria-labelledby="line-label"><g:link controller="line" action="show" id="${stockInstance?.line?.id}">${stockInstance?.line?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.maternalStock}">
				<li class="fieldcontain">
					<span id="maternalStock-label" class="property-label"><g:message code="stock.maternalStock.label" default="Maternal Stock" /></span>
					
						<span class="property-value" aria-labelledby="maternalStock-label"><g:link controller="stock" action="show" id="${stockInstance?.maternalStock?.id}">${stockInstance?.maternalStock?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.maternalStockLabel}">
				<li class="fieldcontain">
					<span id="maternalStockLabel-label" class="property-label"><g:message code="stock.maternalStockLabel.label" default="Maternal Stock Label" /></span>
					
						<span class="property-value" aria-labelledby="maternalStockLabel-label"><g:fieldValue bean="${stockInstance}" field="maternalStockLabel"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="stock.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${stockInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.nursery1InitialNumber}">
				<li class="fieldcontain">
					<span id="nursery1InitialNumber-label" class="property-label"><g:message code="stock.nursery1InitialNumber.label" default="Nursery1 Initial Number" /></span>
					
						<span class="property-value" aria-labelledby="nursery1InitialNumber-label"><g:fieldValue bean="${stockInstance}" field="nursery1InitialNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.nursery2Fertiles}">
				<li class="fieldcontain">
					<span id="nursery2Fertiles-label" class="property-label"><g:message code="stock.nursery2Fertiles.label" default="Nursery2 Fertiles" /></span>
					
						<span class="property-value" aria-labelledby="nursery2Fertiles-label"><g:fieldValue bean="${stockInstance}" field="nursery2Fertiles"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.nursery3Hatched}">
				<li class="fieldcontain">
					<span id="nursery3Hatched-label" class="property-label"><g:message code="stock.nursery3Hatched.label" default="Nursery3 Hatched" /></span>
					
						<span class="property-value" aria-labelledby="nursery3Hatched-label"><g:fieldValue bean="${stockInstance}" field="nursery3Hatched"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.nursery5SecondStageSurvivors}">
				<li class="fieldcontain">
					<span id="nursery5SecondStageSurvivors-label" class="property-label"><g:message code="stock.nursery5SecondStageSurvivors.label" default="Nursery5 Second Stage Survivors" /></span>
					
						<span class="property-value" aria-labelledby="nursery5SecondStageSurvivors-label"><g:fieldValue bean="${stockInstance}" field="nursery5SecondStageSurvivors"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.nursery6Graduates}">
				<li class="fieldcontain">
					<span id="nursery6Graduates-label" class="property-label"><g:message code="stock.nursery6Graduates.label" default="Nursery6 Graduates" /></span>
					
						<span class="property-value" aria-labelledby="nursery6Graduates-label"><g:fieldValue bean="${stockInstance}" field="nursery6Graduates"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.paternalStock}">
				<li class="fieldcontain">
					<span id="paternalStock-label" class="property-label"><g:message code="stock.paternalStock.label" default="Paternal Stock" /></span>
					
						<span class="property-value" aria-labelledby="paternalStock-label"><g:link controller="stock" action="show" id="${stockInstance?.paternalStock?.id}">${stockInstance?.paternalStock?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.paternalStockLabel}">
				<li class="fieldcontain">
					<span id="paternalStockLabel-label" class="property-label"><g:message code="stock.paternalStockLabel.label" default="Paternal Stock Label" /></span>
					
						<span class="property-value" aria-labelledby="paternalStockLabel-label"><g:fieldValue bean="${stockInstance}" field="paternalStockLabel"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${stockInstance?.stockType}">
				<li class="fieldcontain">
					<span id="stockType-label" class="property-label"><g:message code="stock.stockType.label" default="Stock Type" /></span>
					
						<span class="property-value" aria-labelledby="stockType-label"><g:link controller="genetics" action="show" id="${stockInstance?.stockType?.id}">${stockInstance?.stockType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${stockInstance?.id}" />
					<g:link class="edit" action="edit" id="${stockInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
