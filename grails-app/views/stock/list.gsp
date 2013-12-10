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
      <g:sortableColumn property="stockID" title="${message(code: 'stock.stockID.label', default: 'ID')}" />
      <g:sortableColumn property="stockName" title="${message(code: 'stock.stockName.label', default: 'Stock Name')}" />
      </tr>
      </thead>
      <tbody>
      <g:each in="${stockInstanceList}" status="i" var="stockInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          <td>
              ${stockInstance.id}
              <g:link action="show" id="${stockInstance.id}" controller="stock">${stockInstance?.stockIDLabel}
        </g:link></td>
        <td>${fieldValue(bean: stockInstance, field: "stockName")}</td>
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
