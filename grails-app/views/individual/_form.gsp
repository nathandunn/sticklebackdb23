<%@ page import="edu.uoregon.sticklebackdb.Individual" %>

%{-- Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'individualID', 'error')} ">
  <label for="individualID">
    <g:message code="individual.individualID.label" default="Individual ID"/>
  </label>
  <g:textField name="individualID" type="number" value="${individualInstance?.individualID}"  readonly="true"/>
</div>

%{-- Stock --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'stock', 'error')} ">
  <label for="stock">
    <g:message code="individual.stock.label" default="Stock"/>
  </label>  
  <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
            value="${individualInstance?.stock?.id}"
            class="many-to-one" noSelection="['null': '- Choose Stock -']"
            optionValue="stockID" optionKey="id"
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

%{-- Maternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'maternalStockID', 'error')} ">
  <label for="maternalStockID">
    <g:message code="individual.maternalStockID.label" default="Maternal Stock ID" />
  </label>
  <g:if test="${individualInstance?.maternalStockID}">     
    <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
              value="${Stock.findByStockID(individualInstance?.maternalStockID)?.id}"  style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Maternal Stock -']"
              optionValue="stockID" optionKey="id"/>  
  </g:if>
  <g:else>
    <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
              value="${individualInstance?.maternalStockID}"  style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Maternal Stock -']"
              optionValue="stockID" optionKey="id"/>  
  </g:else>
</div>

%{-- Maternal Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'maternalIndividualID', 'error')} ">
  <label for="maternalIndividual">
    <g:message code="individual.maternalIndividualID.label" default="Maternal Individual ID" />
  </label>
  <g:if test="${individualInstance?.maternalIndividualID}">    
    <g:select id="individual" name="individual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
              value="${Individual.findByIndividualID(individualInstance?.maternalIndividualID)?.id}"  style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Maternal Individual -']"
              optionValue="individualID" optionKey="id"
              />  
  </g:if>
  <g:else>
    <g:select id="individual" name="individual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
              value="${individualInstance?.maternalIndividualID}"  style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Maternal Individual -']"
              optionValue="individualID" optionKey="id"
              />  
  </g:else>
</div>


%{-- Paternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'paternalStockID', 'error')} ">
  <label for="paternalStockID">
    <g:message code="individual.paternalStockID.label" default="Paternal Stock ID" />
  </label>
  <g:if test="${individualInstance?.paternalStockID}">     
  <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
            value="${Stock.findByStockID(individualInstance?.paternalStockID)?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Maternal Stock -']"
            optionValue="stockID" optionKey="id"/>  
  </g:if>
  <g:else>
     <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
            value="${individualInstance?.paternalStockID}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Maternal Stock -']"
            optionValue="stockID" optionKey="id"/>  
       </g:else>
</div>

%{-- Paternal Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'paternalIndividualID', 'error')} ">
  <label for="paternalIndividual">
    <g:message code="individual.paternalIndividualID.label" default="Paternal Individual ID" />
  </label>
<g:if test="${individualInstance?.maternalIndividualID}">  
  <g:select id="individual" name="individual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
            value="${Individual.findByIndividualID(individualInstance?.paternalIndividualID)?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Paternal Individual -']"
            optionValue="individualID" optionKey="id"
            />  
   </g:if>
  <g:else>
     <g:select id="individual" name="individual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
            value="${individualInstance?.paternalIndividualID}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Paternal Individual -']"
            optionValue="individualID" optionKey="id"
            />  
       </g:else>
</div>


%{-- Fish Location --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'fishLocation', 'error')} ">
  <label for="fishLocation">
    <g:message code="individual.fishLocation.label" default="Fish Location"/>
  </label>
  <g:textField name="fishLocation" value="${individualInstance?.fishLocation}"/>
</div>

%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'comments', 'error')} ">
  <label for="comments">
    <g:message code="individual.comments.label" default="Comments" />
  </label>
  <g:textArea name="comments" value="${individualInstance?.comments}" />
</div>