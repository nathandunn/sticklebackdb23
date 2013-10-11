<%@ page import="edu.uoregon.sticklebackdb.Individual" %>

%{-- Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'individualID', 'error')} ">
  <label for="individualID">
    <g:message code="individual.individualID.label" default="Individual ID"/>
  </label>
  <g:field name="individualID" type="number" value="${individualInstance.individualID}"/>
</div>

%{-- Stock --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'stock', 'error')} ">
  <label for="stock">
    <g:message code="individual.stock.label" default="Stock"/>
  </label>
  <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
            value="${individualInstance?.stock?.id}"
            class="many-to-one" noSelection="['null': '']"
            optionValue="stockID"
            />
</div>

%{-- Fertilization Date --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'fertilizationDate', 'error')} ">
  <label for="fertilizationDate">
    <g:message code="individual.fertilizationDate.label" default="Fertilization Date"/>
  </label>
  <g:datePicker name="fertilizationDate" precision="day" value="${individualInstance?.fertilizationDate}" default="none"
                noSelection="['': '']"/>
</div>

%{-- Maternal ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'maternalID', 'error')} ">
  <label for="maternalID">
    <g:message code="individual.maternalID.label" default="Maternal ID" />
  </label>
  <g:field name="maternalID" type="number" value="${individualInstance.maternalID}" />
</div>

%{-- Paternal ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'paternalID', 'error')} ">
  <label for="paternalID">
    <g:message code="individual.paternalID.label" default="Paternal ID"/>
  </label>
  <g:field name="paternalID" type="number" value="${individualInstance.paternalID}"/>
</div>

%{-- Fish Location --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'fishLocation', 'error')} ">
  <label for="fishLocation">
    <g:message code="individual.fishLocation.label" default="Fish Location"/>
  </label>
  <g:textField name="fishLocation" value="${individualInstance?.fishLocation}"/>
</div>

%{-- Comment --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'comments', 'error')} ">
  <label for="comments">
    <g:message code="individual.comments.label" default="Comments"/>
  </label>
  <g:field name="comments" type="string" value="${individualInstance.comments}"/>
</div>