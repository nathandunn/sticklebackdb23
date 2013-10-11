
<%@ page import="edu.uoregon.sticklebackdb.Population" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'population.label', default: 'Population')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <a href="#list-population" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
  </div>
  <div id="list-population" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    
    </g:if>
     <span class="property-value" aria-labelledby="paternalStock-label"> Create a new capture. (? Where should this be and what is it doing?)</span>
    
    <table>
      <thead>
        <tr>
      <g:sortableColumn property="name" title="${message(code: 'population.name.label', default: 'Name')}" />
      <g:sortableColumn property="gps" title="${message(code: 'population.gps.label', default: 'GPS')}" />
      </tr>
      </thead>
      <tbody>
      <g:each in="${populationInstanceList}" status="i" var="populationInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          <td><g:link action="show" id="${populationInstance.id}">${fieldValue(bean: populationInstance, field: "name")}</g:link></td>
        <td>${fieldValue(bean: populationInstance, field: "gps")}</td>
        </tr>
      </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${populationInstanceTotal}" />
    </div>
  </div>
</body>
</html>
