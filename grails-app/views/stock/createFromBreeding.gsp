<%@ page import="edu.uoregon.sticklebackdb.Stock" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'stock.label', default: 'Stock')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
  <g:javascript library="jquery" plugins="jquery,jquery-ui"/>
</head>
<body>
  <a href="#create-stock" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
    </ul>
  </div>
  <div id="create-stock" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]" />
    from Breeding
    </h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${stockInstance}">
      <ul class="errors" role="alert">
        <g:eachError bean="${stockInstance}" var="error">
          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
    </g:hasErrors>
    <g:form action="saveFromBreeding" >
      <fieldset class="form">
        <g:render template="breedForm"/>
      </fieldset>
      <fieldset class="buttons">
        <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
        <g:link action="list" class="ui-icon-cancel">Cancel</g:link>
      </fieldset>
    </g:form>
  </div>
</body>
</html>
