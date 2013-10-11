<%@ page import="edu.uoregon.sticklebackdb.Individual" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'individual.label', default: 'Individual')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
  <a href="#list-individual" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                   default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                            args="[entityName]"/></g:link></li>
    </ul>
  </div>

  <div id="list-individual" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
      <thead>
        <tr>
      <g:sortableColumn property="individualID" title="${message(code: 'individual.individualID.label', default: 'ID')}"/>
      <g:sortableColumn property="stock" title="${message(code: 'individual.stock.label', default: 'Stock')}"/> 
      </tr>
      </thead>
      <tbody>
      <g:each in="${individualInstanceList}" status="i" var="individualInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          <td><g:link action="show" id="${individualInstance.id}">
          <g:formatNumber number="${individualInstance?.individualID}" minFractionDigits="4"/></g:link>
        </td>
        <td>
        <g:link action="show" id="${individualInstance?.stock?.id}" controller="stock">${individualInstance?.stock?.stockName}
        </g:link>
        </td>
        </tr>
      </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${individualInstanceTotal}"/>
    </div>
  </div>
</body>
</html>
