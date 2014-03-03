<%@ page import="edu.uoregon.sticklebackdb.Stock; edu.uoregon.sticklebackdb.Individual" %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'individual.label', default: 'Individual')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
  <g:javascript library="jquery" plugin="jquery"/>
</head>
<body>
  <a href="#show-individual" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
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
        <span id="index-label" class="property-label"><g:message code="individual.individualID.label" default="Individual ID" /></span>
      <g:if test="${individualInstance?.individualID}">
        <span class="property-value" aria-labelledby="individualID-label">
            ${individualInstance?.individualIDLabel}
        </span>
      </g:if>
      <g:else>
        <span class="property-value" aria-labelledby="individualID-label"> ---- </span>
      </g:else>
      </li>

%{-- Stock ID --}%
      <li class="fieldcontain"> 
        <span id="stockID-label" class="property-label"><g:message code="individual.stockID.label" default="Stock ID" /></span>  
      <g:if test="${individualInstance?.stock}">
        <span class="property-value" aria-labelledby="stockID-label"><g:link controller="stock" action="show" id="${individualInstance?.stock?.id}">${individualInstance?.stock?.stockIDLabel}</g:link></span>
      </g:if>
      <g:elseif test="individualInstance?.stockID">
        <span class="property-value" aria-labelledby="stockID-label">${individualInstance?.stockID}</span>
      </g:elseif>     
      <g:else>
        <span class="property-value" aria-labelledby="stockID-label"> ---- </span>
      </g:else>  
      </li>

%{-- Stock Name --}%
      <li class="fieldcontain">
        <span id="stockName-label" class="property-label"><g:message code="individual.stockID.label" default="Stock Name" /></span>
      <g:if test="${individualInstance?.stock}">
        <span class="property-value" aria-labelledby="stockName-label"><g:link controller="stock" action="show" id="${individualInstance.stock?.id}">${individualInstance.stock.stockName}</g:link></span>
      </g:if>
      <g:else>
        <span class="property-value" aria-labelledby="stockName-label"> ---- </span>
      </g:else>  
      </li> 

%{-- Stock Line (with link)--}%
      <li class="fieldcontain">
        <span id="line-label" class="property-label"><g:message code="stock.line.label" default="Stock Line" /></span>
      <g:if test="${individualInstance?.stock?.line}">
        <span class="property-value" aria-labelledby="line-label"><g:link controller="line" action="show" id="${individualInstance.stock.line.id}">${individualInstance.stock.line.name}</g:link></span>
      </g:if>
      <g:else>
        <span class="property-value" aria-labelledby="stockLine-label"> ---- </span>
      </g:else>
      </li>

      %{-- Fish Sex --}%
      <li class="fieldcontain">
        <span id="sex-label" class="property-label"><g:message code="individual.fishSex.label" default="Fish Sex" /></span>
      <g:if test="${individualInstance?.fishSex}">
        <span class="property-value" aria-labelledby="fishSex-label">${individualInstance.fishSex}</span>
      </g:if>
      <g:else>
        <span class="property-value" aria-labelledby="fishSex-label"> ---- </span>
      </g:else>
      </li>

      
%{-- Fertilization Date --}%    
      %{--<li class="fieldcontain">--}%
        %{--<span id="fertilizationDate-label" class="property-label"><g:message code="individual.fertilizationDate.label" default="Fertilization Date" /></span>--}%
      %{--<g:if test="${individualInstance?.stock?.fertilizationDate}">--}%
        %{--<span class="property-value" aria-labelledby="fertilizationDate-label"><g:formatDate type="datetime" style="MEDIUM" date="${individualInstance?.stock?.fertilizationDate}" /></span>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
        %{--<span class="property-value" aria-labelledby="fertilizationDate-label"> ---- </span>--}%
      %{--</g:else>--}%
      %{--</li>--}%

%{-- Fish Location --}%
      <li class="fieldcontain">
        <span id="fishLocation-label" class="property-label"><g:message code="individual.fishLocation.label" default="Fish Location" /></span>
      <g:if test="${individualInstance?.fishLocation}">
        <span class="property-value" aria-labelledby="fishLocation-label"><g:fieldValue bean="${individualInstance}" field="fishLocation"/></span>
      </g:if>
      <g:else>
        <span class="property-value" aria-labelledby="fishLocation-label"> ---- </span>
      </g:else>
      </li>

      <br>
      <hr>

      %{-- Maternal Stock ID --}%
      %{--<li class="fieldcontain"> --}%
        %{--<span id="maternalStockID-label" class="property-label"><g:message code="individual.maternalStockID.label" default="Maternal Stock ID" /></span>--}%
      %{--<g:if test="${individualInstance?.maternalStock}">--}%
        %{--<span class="property-value" aria-labelledby="maternalStockID-label"><g:link controller="stock" action="show" id="${individualInstance?.maternalStock?.id}">${individualInstance?.maternalStock?.stockIDLabel}</g:link></span>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
        %{--<span class="property-value" aria-labelledby="maternalStockID-label"> ---- </span>--}%
      %{--</g:else>--}%
      %{--</li>   --}%

      %{-- * * * Maternal Individual ID * * * --}%
      %{--<li class="fieldcontain">--}%
        %{--<span id="maternalIndividualID-label" class="property-label"><g:message code="individual.maternalIndividualID.label" default="Maternal Individual ID" /></span>--}%
      %{--<g:if test="${individualInstance?.maternalIndividual}">--}%
        %{--<span class="property-value" aria-labelledby="maternalIndividualID-label"><g:link controller="individual" action="show" id="${individualInstance?.maternalIndividual?.id}">${individualInstance?.maternalIndividual?.individualIDLabel}</g:link></span>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
        %{--<span class="property-value" aria-labelledby="maternalSIndividualID-label"> ---- </span>--}%
      %{--</g:else>--}%
      %{--</li>--}%

      %{-- * * * Maternal Line * * * --}%
      %{--<li class="fieldcontain">--}%
        %{--<span id="maternalLine-label" class="property-label"><g:message code="individual.maternalLine" default="Maternal Line" /></span>--}%
      %{--<g:if test="${individualInstance?.maternalStock}">--}%
        %{--<span class="property-value" ><g:link controller="line" action="show" id="${individualInstance?.maternalStock?.line?.id}">${individualInstance?.maternalStock?.line?.name}</g:link></span>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
        %{--<span class="property-value" aria-labelledby="maternalLine-label"> ---- </span>--}%
      %{--</g:else>--}%
      %{--</li>--}%
      %{--<br>--}%
      %{--<hr>--}%

      %{-- Paternal Stock ID --}%
      %{--<li class="fieldcontain"> --}%
        %{--<span id="paternalStockID-label" class="property-label"><g:message code="individual.paternalStockID.label" default="Paternal Stock ID" /></span>--}%
      %{--<g:if test="${individualInstance?.paternalStock}">--}%
        %{--<span class="property-value" aria-labelledby="paternalStockID-label"><g:link controller="stock" action="show" id="${individualInstance?.paternalStock?.id}">${individualInstance?.paternalStock.stockIDLabel}</g:link></span>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
        %{--<span class="property-value" aria-labelledby="paternalStockID-label"> ---- </span>--}%
      %{--</g:else>--}%
      %{--</li>   --}%

      %{-- * * * Paternal Individual ID * * * --}%
      %{--<li class="fieldcontain">--}%
        %{--<span id="paternalIndividualID-label" class="property-label"><g:message code="individual.paternalIndividualID.label" default="Paternal Individual ID" /></span>--}%
      %{--<g:if test="${individualInstance?.paternalIndividual}">--}%
        %{--<span class="property-value" aria-labelledby="paternalIndividualID-label"><g:link controller="individual" action="show" id="${individualInstance?.paternalIndividual?.id}">${individualInstance?.paternalIndividual?.individualIDLabel}</g:link></span>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
        %{--<span class="property-value" aria-labelledby="paternalSIndividualID-label"> ---- </span>--}%
      %{--</g:else>--}%
      %{--</li>--}%

      %{-- * * * Paternal Line * * * --}%
      %{--<li class="fieldcontain">--}%
        %{--<span id="paternalLine-label" class="property-label"><g:message code="individual.paternalLine" default="Paternal Line" /></span>--}%
      %{--<g:if test="${individualInstance?.paternalStockID}">--}%
        %{--<span class="property-value" ><g:link controller="line" action="show" id="${individualInstance?.paternalStock?.line?.id}">${individualInstance?.paternalStock?.line?.name}</g:link></span>--}%
      %{--</g:if>--}%
      %{--<g:else>--}%
        %{--<span class="property-value" aria-labelledby="paternalLine-label"> ---- </span>--}%
      %{--</g:else>--}%
      %{--</li>--}%


    </ol>
    <g:form>
      <fieldset class="buttons">
        <g:hiddenField name="id" value="${individualInstance?.id}" />
        <g:link class="ui-icon-print" action="print" id="${individualInstance?.id}"><g:message code="default.button.print.label" default="Print" /></g:link>
        <g:link class="edit" action="edit" id="${individualInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
      </fieldset>
    </g:form>
  </div>
</body>
</html>