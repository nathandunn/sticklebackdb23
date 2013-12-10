<%@ page import="edu.uoregon.sticklebackdb.Individual" %>

%{-- Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'individualID', 'error')} ">
  <label for="individualID">
    <g:message code="individual.individualID.label" default="Individual ID"/>
  </label>
  %{--<g:textField id="individualID" name="individualID" value="${individualInstance?.individualID}"  readonly="true"/>--}%

    <div class="locked-field">
    <g:if test="${individualInstance.individualID}">
        ${individualInstance?.individualIDLabel}
    </g:if>
    <g:else>
        NOT CREATED
    </g:else>
</div>
</div>

%{-- Stock --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'stock', 'error')} ">
  <label for="stock">
    <g:message code="individual.stock.label" default="Stock"/>
  </label>  
  <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.listOrderByStockID(order:"desc")}" optionKey="id"
            value="${individualInstance?.stock?.id}"
            class="many-to-one" noSelection="['null': '- Choose Stock -']"
            optionValue="stockIDLabel"
            onchange="
${remoteFunction( action: 'getNextIndividualID'
  , controller: 'individual'
  , params: '\'stockID=\' + this.value '
  , method: 'POST'
  , onSuccess: '$(\"#individualID\").val(data);'
  // , onSuccess: 'alert(data);'
  , onError: 'alert(\'error\');'
)}" 
            />
</div>

%{-- Fish Sex --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'fishSex', 'error')} ">
  <label for="fishSex">
    <g:message code="individual.fertilizationDate.label" default="Fish Sex"/>
  </label>
  <g:textField name="fishSex" value="${individualInstance?.fishSex}" />
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
  <script>
    function setMaternalIds(data){
                  alert(data);
                  var select = $("#matIndivIDSelect");
                  select.empty();                
                  select.add(new Option(data[0]));             
    }
  </script>
  <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.listOrderByStockID(order:"desc")}"
            value="${individualInstance?.maternalStock?.id}"
            class="many-to-one" noSelection="['null': '- Choose Stock -']"
            optionValue="stockIDLabel" optionKey="id"
            onchange="
${remoteFunction( action: 'findIndividualsForStock'
  , controller: 'individual'
  , params: '\'stockID=\' + this.value '
  , method: 'POST'
  , onSuccess: 'setMaternalIds(data);'
  , onError: 'alert(\'error\');'
)}"  
            />
</div>


%{-- Maternal Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'maternalIndividualID', 'error')} ">
  <label for="maternalIndividual">
    <g:message code="stock.maternalIndividualID.label" default="Maternal Individual ID" />
  </label>
  <g:select id="matIndivIDSelect" name="matIndivIDSelect.id"  from="${edu.uoregon.sticklebackdb.Individual.listOrderByStockID()}"
            value="${individualInstance?.maternalIndividual?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Individual -']"
            optionValue="individualIDLabel"  optionKey="id"
            />  
</div>



%{-- Paternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'paternalStockID', 'error')} ">
  <label for="paternalStockID">
    <g:message code="stock.paternalStockID.label" default="Paternal Stock ID" />
  </label>
  <script>
   function setPaternalIds(data){
        alert(data);
        var select = $("#patIndivIDSelect");
        select.empty();                
        select.add(new Option(data[0])); 
   }
  </script>

  <g:select id="paternalStockIDSelect" name="paternalStockIDSelect.id" from="${edu.uoregon.sticklebackdb.Stock.listOrderByStockID()}"
            value="${individualInstance?.paternalStock?.id}"
            class="many-to-one" noSelection="['null': '- Choose Stock -']"
            optionValue="stockIDLabel" optionKey="id"
            onchange="
${remoteFunction( action: 'findIndividualsForStock'
  , controller: 'individual'
  , params: '\'stockID=\' + this.value '
  , method: 'POST'
  , onSuccess: 'setPaternalIds(data);'
  , onError: 'alert(\'error\');'
)}" 
            />
</div>

%{-- Paternal Individual --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'paternalIndividual', 'error')} ">
  <label for="paternalIndividual">
    <g:message code="stock.paternalIndividual.label" default="Paternal Individual" />
  </label> 
  <g:select id="patIndivIDSelect" name="patIndivIDSelect.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
            value="${individualInstance?.paternalIndividual?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Individual -']"
            optionValue="individualIDLabel"
            />  
</div>

%{-- Fish Location --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'fishLocation', 'error')} ">
  <label for="fishLocation">
    <g:message code="individual.fishLocation.label" default="Fish Location"/>
  </label>
  <g:textField name="fishLocation" value="${individualInstance?.fishLocation}" size="40"/>
</div>

%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'comments', 'error')} ">
  <label for="comments">
    <g:message code="individual.comments.label" default="Comments" />
  </label>
  <g:textArea name="comments" value="${individualInstance?.comments}" />
</div>