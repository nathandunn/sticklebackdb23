<%@ page import="edu.uoregon.sticklebackdb.Stock" %>


%{-- stockID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockID', 'error')} ">
  <label for="stockID">
    <g:message code="stock.stockID.label" default="Stock ID" />
  </label>
  <g:textField name="stockID" value="${stockInstance?.stockID}"  readonly="true"/>
</div>

%{-- Name --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockName', 'error')} ">
  <label for="stockName">
    <g:message code="stock.stockName.label" default="Stock Name" />
  </label>
  <g:textField name="stockName" value="${stockInstance?.stockName}"/>
</div>


%{-- Population --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'population', 'error')} ">
  <label for="population">
    <g:message code="stock.population.label" default="Population" />
  </label>
   <g:select id="population" name="population.id" from="${edu.uoregon.sticklebackdb.Population.list()}" optionKey="id"
            value="${individualInstance?.population?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '']"
            optionValue="name"
            />
</div>

%{-- Fertilization Date --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fertilizationDate', 'error')} ">
  <label for="fertilizationDate">
    <g:message code="stock.fertilizationDate.label" default="Fertilization Date" />
  </label>
  <g:datePicker name="fertilizationDate" precision="day"  value="${stockInstance?.fertilizationDate}" default="none" noSelection="['': '']" />
</div>


%{-- Maternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalStockID', 'error')} ">
  <label for="maternalStockID">
    <g:message code="stock.maternalStockID.label" default="Maternal Stock ID" />
  </label>
  <g:field type="number" name="maternalStockID" value="${stockInstance?.maternalStockID}"/>
</div>

%{-- Maternal Stock --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalStock', 'error')} ">
  <label for="maternalStock">
    <g:message code="stock.maternalStock.label" default="Maternal Stock" />
  </label>
  <g:textField name="maternalStock" value="${stockInstance?.maternalStock}"/>
</div>

%{-- Maternal ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalID', 'error')} ">
  <label for="maternalID">
    <g:message code="stock.maternalID.label" default="Maternal ID" />
  </label>
  <g:textField name="maternalID" value="${stockInstance?.maternalID}"/>
</div>

%{-- Maternal Individual --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalIndividual', 'error')} ">
  <label for="maternalIndividual">
    <g:message code="stock.maternalIndividual.label" default="Maternal Individual" />
  </label>
  <g:textField name="maternalIndividual" value="${stockInstance?.maternalIndividual}"/>
</div>

%{-- Paternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalStockID', 'error')} ">
  <label for="paternalStockID">
    <g:message code="stock.paternalStockID.label" default="Paternal Stock ID" />
  </label>
  <g:textField name="paternalStockID" value="${stockInstance?.paternalStockID}"/>
</div>

%{-- Paternal Stock --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalStock', 'error')} ">
  <label for="paternalStock">
    <g:message code="stock.paternalStock.label" default="Paternal Stock" />
  </label>
  <g:textField name="paternalStock" value="${stockInstance?.paternalStock}"/>
</div>

%{-- Paternal ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalID', 'error')} ">
  <label for="paternalID">
    <g:message code="stock.paternalID.label" default="Paternal ID" />
  </label>
  <g:textField name="paternalID" value="${stockInstance?.paternalID}"/>
</div>

%{-- Paternal Individual --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalIndividual', 'error')} ">
  <label for="paternalIndividual">
    <g:message code="stock.paternalIndividual.label" default="Paternal Individual" />
  </label>
  <g:textField name="paternalIndividual" value="${stockInstance?.paternalIndividual}"/>
</div>


%{-- Comment --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'comment', 'error')} ">
  <label for="comment">
    <g:message code="stock.comment.label" default="Comment" />
  </label>
  <g:textArea name="comment" value="${stockInstance?.comment}" />
</div>