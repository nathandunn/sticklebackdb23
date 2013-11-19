<%@ page import="edu.uoregon.sticklebackdb.Stock" %>


%{-- stockID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockID', 'error')} ">
  <label for="stockID">
    <g:message code="stock.stockID.label" default="Stock ID" />
  </label>
  <g:if test="${stockInstance?.stockID}">    
    <g:textField name="stockID"  value="${stockInstance?.stockID}" readonly="true"/>
  </g:if>
  <g:else>
    <g:textField name="stockID" value="${remoteFunction(action:'getNextStockID', controller:'stock')}" readonly="true"/>
  </g:else>
</div>


%{-- Name --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockName', 'error')} ">
  <label for="stockName">
    <g:message code="stock.stockName.label" default="Stock Name" />
  </label>
  <g:textField name="stockName" value="${stockInstance?.stockName}"/>
</div>

%{-- Line --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
  <label for="line">
    <g:message code="stock.line.label" default="Line" />
  </label>
  <g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.list()}" optionKey="id"
            value="${stockInstance.line?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one"  noSelection="['null': '- Choose Line -']"
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
    <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
              value="${Stock.findByStockID(stockInstance?.maternalStockID)?.stockID}"
              class="many-to-one" noSelection="['null': '- Choose Stock -']"
              optionValue="stockID" optionKey="stockID"    
              />
</div>
    
%{-- Maternal Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalIndividualID', 'error')} ">
  <label for="maternalIndividual">
    <g:message code="stock.maternalIndividualID.label" default="Maternal Individual ID" />
  </label>
<g:if test="${stockInstance?.maternalIndividualID}"> 
  <g:select id="individual" name="individual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
            value="${Individual.findByIndividualID(stockInstance?.maternalIndividualID)?.individualID}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Individual -']"
            optionValue="individualID"
            />  
  
   </g:if>
  <g:else>
  <g:select id="individual" name="individual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
            value="${stockInstance?.maternalIndividualID}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Individual -']"
            optionValue="individualID"
            />  
  </g:else>
</div>

%{-- Paternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalStockID', 'error')} ">
  <label for="paternalStockID">
    <g:message code="stock.paternalStockID.label" default="Paternal Stock ID" />
  </label>

  <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
            value="${Stock.findByStockID(stockInstance?.paternalStockID)?.stockID}"
            class="many-to-one" noSelection="['null': '- Choose Stock -']"
            optionValue="stockID" optionKey="stockID"
            />
</div>

%{-- Paternal Individual --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalIndividual', 'error')} ">
  <label for="paternalIndividual">
    <g:message code="stock.paternalIndividual.label" default="Paternal Individual" />
  </label>
  <g:select id="individual" name="individual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
            value="${Individual.findByIndividualID(stockInstance?.paternalIndividualID)?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Individual -']"
            optionValue="individualID"
            />  
</div>


%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'comments', 'error')} ">
  <label for="comments">
    <g:message code="stock.comments.label" default="Comments" />
  </label>
  <g:textArea name="comments" value="${stockInstance?.comments}" />
</div>