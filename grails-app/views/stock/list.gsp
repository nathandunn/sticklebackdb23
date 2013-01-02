
<%@ page import="edu.uoregon.sticklebackdb.Stock" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'stock.label', default: 'Stock')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-stock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-stock" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="fishStock" title="${message(code: 'stock.abbreviation.label', default: 'Stock')}" />
                        <g:sortableColumn property="abbreviation" title="${message(code: 'stock.abbreviation.label', default: 'Name')}" />


						<g:sortableColumn property="fertilizationDateTime" title="${message(code: 'stock.breedingType.label', default: 'Fertilized')}" />

                        <th>Expired</th>

						<g:sortableColumn property="crossDate" title="${message(code: 'stock.crossDate.label', default: 'Stock Date')}" />

                        <th>Status</th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${stockInstanceList}" status="i" var="stockInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					

						<td><g:formatNumber number="${stockInstance.fishStock}"/></td>
                        <td><g:link action="show" id="${stockInstance.id}">${fieldValue(bean: stockInstance, field: "abbreviation")}</g:link></td>

						<td>
                            <g:formatDate date="${stockInstance.fertilizationDateTime}" type="date" dateStyle="MEDIUM"/>
                            %{--${fieldValue(bean: stockInstance, field: "breedingType")}--}%
                        </td>
					
						%{--<td>${fieldValue(bean: stockInstance, field: "comment")}</td>--}%
                        <td>&nbsp;</td>
					
						<td>
                            %{--${fieldValue(bean: stockInstance, field: "containerStatus")}--}%
                            <g:formatDate date="${stockInstance.crossDate}" type="date" format="MEDIUM"/>
                        </td>
					
						<td>${stockInstance.fullStatus}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${stockInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
