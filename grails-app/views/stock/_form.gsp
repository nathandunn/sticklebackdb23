<%@ page import="edu.uoregon.sticklebackdb.Stock" %>

<r:require modules="jquery,jquery-ui"/>


%{-- stockID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockID', 'error')} ">
    <label for="stockID">
        <g:message code="stock.stockID.label" default="Stock ID"/>
    </label>
    %{--<g:textField name="stockID" value="${stockInstance?.stockID}" />--}%
    <div class="locked-field">${stockInstance?.stockIDLabel}</div>
    <g:hiddenField name="stockID" value="${stockInstance?.stockID}"/>

</div>


<script>
    $(document).ready(function () {
        var availableTags = [
            <g:each var="stockName" in="${stockNames}" status="iter">
            "${stockName}"
            ,
            </g:each>
        ];
        $("#stockName").autocomplete({
            source: availableTags
        });
    });
</script>

%{-- Name --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockName', 'error')} ">
    <label for="stockName">
        <g:message code="stock.stockName.label" default="Stock Name"/>
    </label>
    <g:textField id="stockName" class="ui-autocomplete-input" name="stockName" value="${stockInstance?.stockName}"
                 autocomplete="off" size="60"/>
</div>

%{-- Line --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
    <label for="line">
        <g:message code="stock.line.label" default="Line"/>
    </label>
    <g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.listOrderByName()}" optionKey="id"
              value="${stockInstance.line?.id}" style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Line -']"
              optionValue="name" />
</div>

%{-- Fertilization Date --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fertilizationDate', 'error')} ">
    <label for="fertilizationDate">
        <g:message code="stock.fertilizationDate.label" default="Fertilization Date"/>
    </label>
    <g:datePicker name="fertilizationDate" precision="day" relativeYears="[-20..0]"
                  value="${stockInstance?.fertilizationDate}" default="none" noSelection="['': '']"/>
</div>

%{-- Capture --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'capture', 'error')} ">
    <label for="capture">
        <g:message code="stock.capture.label" default="Capture "/>
    </label>
    <g:select id="capture" name="capture.id" from="${edu.uoregon.sticklebackdb.Capture.listOrderByCaptureDate()}" optionKey="id"
              value="${stockInstance.capture?.id}" style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- None -']"
              optionValue="display" />
</div>

%{-- Maternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalStockID', 'error')} ">
    <label for="maternalStockID">
        <g:message code="stock.maternalStockID.label" default="Maternal Stock ID"/>
    </label>
    <script>
        function setMaternalIds(data) {
            var select = $("#maternalIndividual");
            select.empty();

            var count = 0
            for (var key in data) {
                var value = data[key];
                select.append('<option value=' + key + '>' + value + '</option>');
                count += 1 ;
            }
            if(count==0){
                select.append('<option value="">None</option>');
            }
        }
    </script>
    <g:select id="maternalStock.id" name="maternalStock.id"
              from="${edu.uoregon.sticklebackdb.Stock.listOrderByStockID(order: "desc")}"
              value="${stockInstance?.maternalStock?.id}"
              class="many-to-one" noSelection="['null': '- Choose Stock -']"
              optionValue="stockIDLabel" optionKey="id"
              onchange="
              ${remoteFunction(action: 'findIndividualsForStock'
                      , controller: 'individual'
                      , params: '\'stockId=\' + this.value+\'&excludeGender=male\''
                      , method: 'POST'
                      , onSuccess: 'setMaternalIds(data);'
                      , onError: 'alert(\'error\');'
              )}"/>
</div>

%{-- Maternal Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalIndividualID', 'error')} ">
    <label for="maternalIndividual">
        <g:message code="stock.maternalIndividualID.label" default="Maternal Individual ID"/>
    </label>
    %{--<g:select id="maternalIndividual" name="maternalIndividual.id" from="${stockInstance ? edu.uoregon.sticklebackdb.Individual.findAllByStock(stockInstance,[sort:"individualID",order:"desc"]):[]}"--}%
    <g:select id="maternalIndividual" name="maternalIndividual.id" from="${[]}"
              value="${stockInstance?.maternalIndividual?.id}" style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Individual -']"
              optionValue="individualIDLabel" optionKey="id"/>
</div>

%{-- Paternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalStockID', 'error')} ">
    <label for="paternalStockID">
        <g:message code="stock.paternalStockID.label" default="Paternal Stock ID"/>
    </label>
    <script>
        function setPaternalIds(data) {
            var select = $("#paternalIndividual");
            select.empty();

            var count = 0
            for (var key in data) {
                var value = data[key];
                select.append('<option value=' + key + '>' + value + '</option>');
                count += 1 ;
            }
            if(count==0){
                select.append('<option value="">None</option>');
            }
        }
    </script>

    <g:select id="paternalStock.id" name="paternalStock.id" from="${edu.uoregon.sticklebackdb.Stock.listOrderByStockID(order: "desc")}"
              value="${stockInstance?.paternalStock?.id}"
              class="many-to-one" noSelection="['null': '- Choose Stock -']"
              optionValue="stockIDLabel" optionKey="id"
              onchange="
              ${remoteFunction(action: 'findIndividualsForStock'
                      , controller: 'individual'
                      , params: '\'stockId=\' + this.value+\'&excludeGender=female\''
                      , method: 'POST'
                      , onSuccess: 'setPaternalIds(data);'
                      , onError: 'alert(\'error\');'
              )}"/>
</div>

%{-- Paternal Individual --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalIndividual', 'error')} ">
    <label for="paternalIndividual">
        <g:message code="stock.paternalIndividual.label" default="Paternal Individual"/>
    </label>
    %{--<g:select id="paternalIndividual" name="paternalIndividual.id" from="${stockInstance ? edu.uoregon.sticklebackdb.Individual.findAllByStock(stockInstance,[sort:"individualID",order:"desc"]):[]}"--}%
    <g:select id="paternalIndividual" name="paternalIndividual.id" from="${[]}"
              optionKey="id"
              value="${stockInstance?.paternalIndividual?.id}" style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Individual -']"
              optionValue="individualIDLabel" />
</div>


%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'comments', 'error')} ">
    <label for="comments">
        <g:message code="stock.comments.label" default="Comments"/>
    </label>
    <g:textArea name="comments" value="${stockInstance?.comments}"/>
</div>