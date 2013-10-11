
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

%{-- Individual ID --}%
      <li class="fieldcontain">
        <span id="index-label" class="property-label"><g:message code="individual.individualID.label" default="ID" /></span>
      <g:if test="${individualInstance?.individualID}">
        <span class="property-value" aria-labelledby="individualID-label"><g:formatNumber number="${individualInstance?.individualID}" minFractionDigits="4"/></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- Fertilization Date --}%
      <li class="fieldcontain">
        <span id="fertilizationDate-label" class="property-label"><g:message code="individual.fertilizationDate.label" default="Fertilization Date" /></span>
      <g:if test="${individualInstance?.fertilizationDate}">
        <span class="property-value" aria-labelledby="fertilizationDate-label"><g:formatDate date="${individualInstance?.fertilizationDate}" /></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- Fish Location --}%
      <li class="fieldcontain">
        <span id="fishLocation-label" class="property-label"><g:message code="individual.fishLocation.label" default="Fish Location" /></span>
      <g:if test="${individualInstance?.fishLocation}">
        <span class="property-value" aria-labelledby="fishLocation-label"><g:fieldValue bean="${individualInstance}" field="fishLocation"/></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- Maternal ID --}%
      <li class="fieldcontain">
        <span id="maternalID-label" class="property-label"><g:message code="individual.maternalID.label" default="Maternal ID" /></span>
      <g:if test="${individualInstance?.maternalID}">
 <span class="property-value" aria-labelledby="maternalID-label"><g:formatNumber number="${individualInstance?.maternalID}" minFractionDigits="4" /></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- Maternal Stock --}%
      <li class="fieldcontain">  
        <span id="maternalStock-label" class="property-label"><g:message code="individual.maternalStock.label" default="Maternal Stock" /></span>
      <g:if test="${individualInstance?.maternalStock}">
        <span class="property-value" aria-labelledby="maternalStock-label"><g:link controller="stock" action="show" id="${individualInstance?.maternalStock?.id}">${individualInstance?.maternalStock?.stockName}</g:link></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>
      
  %{-- Maternal Individual --}%
      <li class="fieldcontain">
        <span id="maternalIndividual-label" class="property-label"><g:message code="individual.maternalIndividual.label" default="Maternal Individual" /></span>
      <g:if test="${individualInstance?.maternalIndividual?.id}">
        <span class="property-value" aria-labelledby="maternalIndividual-label"><g:link controller="individual" action="show" id="${individualInstance?.maternalIndividual?.id}"><g:formatNumber number="${individualInstance?.maternalIndividual?.individualID}" minFractionDigits="4" /></g:link></span>
      </g:if>
        <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>
      
%{-- Paternal ID --}%
      <li class="fieldcontain">
        <span id="paternalID-label" class="property-label"><g:message code="individual.paternalID.label" default="Paternal ID" /></span>
      <g:if test="${individualInstance?.paternalID}">
 <span class="property-value" aria-labelledby="paternalID-label"><g:formatNumber number="${individualInstance?.paternalID}" minFractionDigits="4" /></span>
      </g:if>
        <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- Paternal Stock --}%
      <li class="fieldcontain">  
        <span id="paternalStock-label" class="property-label"><g:message code="individual.paternalStock.label" default="Paternal Stock" /></span>
      <g:if test="${individualInstance?.paternalStock}">
        <span class="property-value" aria-labelledby="paternalStock-label"><g:link controller="stock" action="show" id="${individualInstance?.paternalStock?.id}">${individualInstance?.paternalStock?.stockName}</g:link></span>
      </g:if>
      <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>
      
  %{-- Paternal Individual --}%
      <li class="fieldcontain">
        <span id="paternalIndividual-label" class="property-label"><g:message code="individual.paternalIndividual.label" default="Paternal Individual" /></span>
      <g:if test="${individualInstance?.paternalIndividual?.id}">
        <span class="property-value" aria-labelledby="paternalIndividual-label"><g:link controller="individual" action="show" id="${individualInstance?.paternalIndividual?.id}"><g:formatNumber number="${individualInstance?.paternalIndividual?.individualID}" minFractionDigits="4" /></g:link></span>
      </g:if>
        <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- Stock -- }%
      <li class="fieldcontain">
        <span id="stock-label" class="property-label"><g:message code="individual.stock.label" default="Stock" /></span>
      <g:if test="${individualInstance?.stock}">
        <span class="property-value" aria-labelledby="stock-label"><g:link controller="stock" action="show" id="${individualInstance?.stock?.id}">${individualInstance?.stock?.stockName}</g:link></span>
      </g:if>
     <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>  
    </li>  

%{-- Stock ID --}%
%{-- <li class="fieldcontain">
<span id="stockID-label" class="property-label"><g:message code="individual.stockID.label" default="Stock ID" /></span>
<g:if test="${individualInstance?.stockID}">
<span class="property-value" aria-labelledby="stockID-label"><g:link controller="stock" action="show" id="${individualInstance?.stockID}">${individualInstance?.stockID}</g:link></span>
</g:if>
</li>
--}%
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