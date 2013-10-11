<%@ page import="edu.uoregon.sticklebackdb.Population" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'population.label', default: 'Population')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <a href="#show-population" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
      <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
  </div>
  <div id="show-population" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list population">

%{--<!--      <g:if test="${populationInstance?.abbreviation}">
<li class="fieldcontain">
<span id="abbreviation-label" class="property-label"><g:message code="population.abbreviation.label" default="Abbreviation" /></span>
<span class="property-value" aria-labelledby="abbreviation-label"><g:fieldValue bean="${populationInstance}" field="abbreviation"/></span>
</li>
</g:if>-->--}%

%{--<!--      <g:if test="${populationInstance?.captureKey}">
<li class="fieldcontain">
<span id="captureKey-label" class="property-label"><g:message code="population.captureKey.label" default="Capture Key" /></span>

<span class="property-value" aria-labelledby="captureKey-label"><g:fieldValue bean="${populationInstance}" field="captureKey"/></span>

</li>
</g:if>-->--}%

%{--<!--      <g:if test="${populationInstance?.captured}">
<li class="fieldcontain">
<span id="captured-label" class="property-label"><g:message code="population.captured.label" default="Captured" /></span>

<span class="property-value" aria-labelledby="captured-label"><g:formatBoolean boolean="${populationInstance?.captured}" /></span>

</li>
</g:if>-->--}%



      <g:if test="${populationInstance?.name}">
        <li class="fieldcontain">
          <span id="name-label" class="property-label"><g:message code="population.name.label" default="Name" /></span>
          <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${populationInstance}" field="name"/></span>
        </li>
      </g:if>
 <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      
      <g:if test="${populationInstance?.species}">
        <li class="fieldcontain">
          <span id="species-label" class="property-label"><g:message code="population.species.label" default="Species" /></span>
          <span class="property-value" aria-labelledby="species-label"><g:fieldValue bean="${populationInstance}" field="species"/></span>
        </li>
      </g:if>
 <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>

      <li class="fieldcontain">
        <span id="comment-label" class="property-label"><g:message code="population.comment.label" default="Comment" /></span>
      <g:if test="${populationInstance?.comment}">
        <span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${populationInstance}" field="comment"/></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{--<!--      <g:if test="${populationInstance?.status}">
<li class="fieldcontain">
<span id="status-label" class="property-label"><g:message code="population.status.label" default="Status" /></span>

<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${populationInstance}" field="status"/></span>

</li>
</g:if>-->--}%

%{--<!--      <g:if test="${populationInstance?.storedPopulationName}">
<li class="fieldcontain">
<span id="storedPopulationName-label" class="property-label"><g:message code="population.storedPopulationName.label" default="Stored Population Name" /></span>

<span class="property-value" aria-labelledby="storedPopulationName-label"><g:fieldValue bean="${populationInstance}" field="storedPopulationName"/></span>

</li>
</g:if>-->--}%

%{--<!--      <g:if test="${populationInstance?.storedPopulationNameAbbreviation}">
<li class="fieldcontain">
<span id="storedPopulationNameAbbreviation-label" class="property-label"><g:message code="population.storedPopulationNameAbbreviation.label" default="Stored Population Name Abbreviation" /></span>

<span class="property-value" aria-labelledby="storedPopulationNameAbbreviation-label"><g:fieldValue bean="${populationInstance}" field="storedPopulationNameAbbreviation"/></span>

</li>
</g:if>-->--}%

    </ol>
    <g:form>
      <fieldset class="buttons">
        <g:hiddenField name="id" value="${populationInstance?.id}" />
        <g:link class="edit" action="edit" id="${populationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
      </fieldset>
    </g:form>
  </div>
</body>
</html>
