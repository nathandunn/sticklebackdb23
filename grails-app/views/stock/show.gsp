
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

%{-- * * * Stock ID formatted to X.0000 * * * --}%
      <li class="fieldcontain">
        <span id="fishStock-label" class="property-label"><g:message code="stock.stockID.label" default="ID" /></span>
      <g:if test="${stockInstance?.stockID}">
        <span class="property-value" aria-labelledby="stockID-label"><g:formatNumber number="${stockInstance?.stockID}" minFractionDigits="4"/></span>          
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- * * * Stock Name * * * --}%
      <li class="fieldcontain">
        <span id="name-label" class="property-label"><g:message code="stock.stockName.label" default="Name" /></span>
      <g:if test="${stockInstance?.stockName}">
        <span class="property-value" aria-labelledby="stockName-label"><g:fieldValue bean="${stockInstance}" field="stockName"/></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- * * * Stock Population (with link) * * * --}%
      <li class="fieldcontain">
        <span id="population-label" class="property-label"><g:message code="stock.population.label" default="Population" /></span>
      <g:if test="${stockInstance?.population}">
        <span class="property-value" aria-labelledby="population-label"><g:link controller="population" action="show" id="${stockInstance?.population?.id}">${stockInstance?.population?.name}</g:link></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- * * * Stock fertilization date * * * --}%
      <li class="fieldcontain">
        <span id="fertilizationDate-label" class="property-label"><g:message code="stock.fertilizationDate.label" default="Fertilization Date" /></span>
      <g:if test="${stockInstance?.fertilizationDate}">
        <span class="property-value" aria-labelledby="fertilizationDate-label"><g:formatDate date="${stockInstance?.fertilizationDate}" /></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- * * * Stock researcher comments * * * --}%
      <li class="fieldcontain">
        <span id="comment-label" class="property-label"><g:message code="stock.comment.label" default="Comment" /></span>
      <g:if test="${stockInstance?.comment}">
        <span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${stockInstance}" field="comment"/></span>  
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

%{-- * * * Maternal Lineage * * * --}%
      <li class="fieldcontain">
        <span id="maternalStockID-label" class="property-label"><g:message code="stock.maternalID.label" default="Maternal Stock ID" /></span>
      <g:if test="${stockInstance?.maternalStockID}">
        <span class="property-value" aria-labelledby="maternalStockID-label"><g:formatNumber number="${stockInstance?.maternalStockID}" minFractionDigits="4"/></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="maternalStock-label"> ---- </span>
        </g:else>
      </li>
      
       <li class="fieldcontain">
        <span id="maternalStock-label" class="property-label"><g:message code="stock.maternalStock.label" default="Maternal Stock" /></span>
      <g:if test="${stockInstance?.maternalStock}">
        <span class="property-value" aria-labelledby="maternalStock-label"><g:link controller="stock" action="show" id="${stockInstance?.maternalStock?.id}"><g:fieldValue bean="${stockInstance.maternalStock}" field="stockName"/></g:link></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="maternalStock-label"> ---- </span>
        </g:else>
      </li>      

      <li class="fieldcontain">
        <span id="maternalID-label" class="property-label"><g:message code="stock.maternalID.label" default="Maternal Individual" /></span>
      <g:if test="${stockInstance?.maternalIndividual}">
  <span class="property-value" aria-labelledby="maternalIndividual-label"><g:link controller="individual" action="show" id="${stockInstance?.maternalIndividual?.id}"><g:formatNumber number="${stockInstance?.maternalID}" minFractionDigits="4"/></g:link></span>
         </g:if>
      <g:elseif  test="${stockInstance?.maternalID}">
        <span class="property-value" aria-labelledby="maternalID-label"><g:formatNumber number="${stockInstance?.maternalID}" minFractionDigits="4"/></span>
      </g:elseif>
       <g:else>
        <span class="property-value" aria-labelledby="maternalStock-label"> ---- </span>
        </g:else>
      </li>


%{-- * * * Paternal Lineage * * * --}%
    <li class="fieldcontain">
        <span id="paternalStockID-label" class="property-label"><g:message code="stock.paternalID.label" default="Paternal Stock ID" /></span>
      <g:if test="${stockInstance?.paternalStockID}">
        <span class="property-value" aria-labelledby="paternalStockID-label"><g:formatNumber number="${stockInstance?.paternalStockID}" minFractionDigits="4"/></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>
      
       <li class="fieldcontain">
        <span id="paternalStock-label" class="property-label"><g:message code="stock.paternalStock.label" default="Paternal Stock" /></span>
      <g:if test="${stockInstance?.paternalStock}">
        <span class="property-value" aria-labelledby="paternalStock-label"><g:link controller="stock" action="show" id="${stockInstance?.paternalStock?.id}"><g:fieldValue bean="${stockInstance.paternalStock}" field="stockName"/></g:link></span>
      </g:if>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>      

      <li class="fieldcontain">
        <span id="paternalID-label" class="property-label"><g:message code="stock.paternalID.label" default="Paternal Individual" /></span>
      <g:if test="${stockInstance?.paternalIndividual}">
  <span class="property-value" aria-labelledby="paternalIndividual-label"><g:link controller="individual" action="show" id="${stockInstance?.paternalIndividual?.id}"><g:formatNumber number="${stockInstance?.paternalID}" minFractionDigits="4"/></g:link></span>
         </g:if>
      <g:elseif  test="${stockInstance?.paternalID}">
        <span class="property-value" aria-labelledby="paternalID-label"><g:formatNumber number="${stockInstance?.paternalID}" minFractionDigits="4"/></span>
      </g:elseif>
       <g:else>
        <span class="property-value" aria-labelledby="paternalStock-label"> ---- </span>
        </g:else>
      </li>

      <li class="fieldcontain">    
         <span class="property-value" aria-labelledby="paternalStock-label"> <g:link controller="individual" action="query" params="[pStockID: stockInstance.stockID]">Show Stock Individuals</g:link></span>
        </li>
      
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
