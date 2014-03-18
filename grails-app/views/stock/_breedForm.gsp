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

    function setLineView(data){
        var paternalView = $("#newViewDiv");
        var linkView = '${createLink(action: "show",controller: "line")}/' + data.id;
        if(data.captures.length>0){
            paternalView.html('<a href=' + linkView + '>' + data.name+ '</a><br/> Captures: '+data.captures.length + ' Stocks: '+data.stocks.length);
        }
        else{
            paternalView.html('<a href=' + linkView + '>' + data.name+ '</a><br/> Stocks: '+data.stocks.length);
        }
    }

    function clearInputs() {
        $('#newMaternalFinclipLocation').val("");
        $('#newMaternalDnaLocation').val("");
        $('#newMaternalSomaLocation').val("");
        $('#newMaternalIdStatus').val("");
        $('#newMaternalIndividualComment').val("");

        $('#newPaternalFinclipLocation').val("");
        $('#newPaternalDnaLocation').val("");
        $('#newPaternalSomaLocation').val("");
        $('#newPaternalIdStatus').val("");
        $('#newPaternalIndividualComment').val("");
    }

    function clearNewLineInputs() {
        $('#newLineName').val("");
        $('#newLineComment').val("");
        $('#fertilizationDate_day').val("");
        $('#fertilizationDate_month').val("");
        $('#fertilizationDate_year').val("");
    }

    function addAndSelectLine(data) {
        clearNewLineInputs();
        alert('Added new line: ' + data.name);

        var select = $("#line");
        select.append('<option value=' + data.id + '>' + data.name + '</option>');
        select.val(data.id);
        select.change();

        $('#addNewLineDiv').toggle(800);
    }

    function selectLastMaternal() {
        var selected = $('#maternalIndividual option:last').attr('selected', 'selected');
    }

    function selectLastPaternal() {
        var selected = $('#paternalIndividual option:last').attr('selected', 'selected');
    }

    function setPaternalIds(data) {
        var select = $("#paternalIndividual");
        select.empty();

        var count = 0
        for (var key in data) {
            var value = data[key];
            select.append('<option value=' + key + '>' + value + '</option>');
            count += 1;
        }
        if (count == 0) {
            select.append('<option value="">None</option>');
        }


        var paternalId = $("#paternalStock-Id").val();
        $.ajax({
            type: 'POST', data: 'id=' + paternalId, url: '/sticklebackdb/stock/findStock',
            success: function (data, textStatus) {
                if (data) {
                    var paternalView = $("#paternalView");
                    var linkView = '${createLink(action: "show",controller: "stock")}/' + data.id;
                    paternalView.html('<a href=' + linkView + '>' + data.stockName + '</a>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert('error');
            }
        });

    }

    function setMaternalIds(data) {
        var select = $("#maternalIndividual");
        select.empty();

        var count = 0
        for (var key in data) {
            var value = data[key];
            select.append('<option value=' + key + '>' + value + '</option>');
            count += 1;
        }
        if (count == 0) {
            select.append('<option value="">None</option>');
        }

        var maternalId = $("#maternalStock-Id").val();
        $.ajax({
            type: 'POST', data: 'id=' + maternalId, url: '/sticklebackdb/stock/findStock',
            success: function (data, textStatus) {
                if (data) {
                    var maternalView = $("#maternalView");
                    var linkView = '${createLink(action: "show",controller: "stock")}/' + data.id;
                    maternalView.html('<a href=' + linkView + '>' + data.stockName + '</a>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert('error');
            }
        });

    }

    function syncLines(data) {
        var paternalStockId = $('#paternalStock-Id').val();
        var maternalStockId = $('#maternalStock-Id').val();

        console.log('paternal: ' + paternalStockId);
        console.log('maternal: ' + maternalStockId);
        if(maternalStockId==null || paternalStockId==null ) return ;
        if(maternalStockId=='null' || paternalStockId=='null' ) return ;

        $.ajax({
            type: 'POST', data: 'paternalStockId=' + paternalStockId + '&maternalStockId=' + maternalStockId, url: '/sticklebackdb/stock/findCommonLineForStocks', success: function (data, textStatus) {
                if (data) {
                    $('#line').val(data)
                    $('#stockName').val($('#line option:selected').text());
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert('error');
            }
        });
    }

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

        $('#addMaternalIndividualDiv').hide();
        $("#addNewMaternalIndividualButton").click(function(){
            $('#addMaternalIndividualDiv').toggle(800);
        });

        $('#addPaternalIndividualDiv').hide();
        $("#addNewPaternalIndividualButton").click(function(){
            $('#addPaternalIndividualDiv').toggle(800);
        });

        $("#addNewLineDiv").hide();
        $("#addNewLineButton").click(function(){
            $('#addNewLineDiv').toggle(800);
        });

        $("#addMaternalIndividualButton").click(function () {
            var somaLocation= $('#newMaternalSomaLocation').val();
            var finclipLocation= $('#newMaternalFinclipLocation').val();
            var dnaLocation= $('#newMaternalDnaLocation').val();
            var idStatus = $('#newMaternalIdStatus').val();
            var comment = $('#newMaternalIndividualComment').val();
            if (somaLocation.length == 0) {
                alert('You must provide a soma location');
                return;
            }
            var stock = $('#maternalStock-Id').val();

            ${remoteFunction(action: 'addIndividualToStock'
                      , controller: 'individual'
                      , params: '\'stockId=\' + stock+\'&comment=\'+comment+\'&idStatus=\'+idStatus+\'&finclipLocation=\'+finclipLocation+\'&dnaLocation=\'+dnaLocation+\'&somaLocation=\'+somaLocation+\'&fishSex=female\''
                      , method: 'POST'
                      , onSuccess: 'alert(\'Added maternal individual to location:\'+somaLocation);setMaternalIds(data);clearInputs();selectLastMaternal();$(\'#addMaternalIndividualDiv\').toggle(800);'
                      , onError: 'alert(\'error\');'
              )};
        });


        $("#addPaternalIndividualButton").click(function () {
            var somaLocation= $('#newPaternalSomaLocation').val();
            var finclipLocation= $('#newPaternalFinclipLocation').val();
            var dnaLocation= $('#newPaternalDnaLocation').val();
            var idStatus = $('#newPaternalIdStatus').val();
            var comment = $('#newPaternalIndividualComment').val();
            if (somaLocation.length == 0) {
                alert('You must provide a soma location');
                return;
            }
            var stock = $('#paternalStock-Id').val();

            ${remoteFunction(action: 'addIndividualToStock'
                      , controller: 'individual'
                      , params: '\'stockId=\' + stock+\'&comment=\'+comment+\'&idStatus=\'+idStatus+\'&finclipLocation=\'+finclipLocation+\'&dnaLocation=\'+dnaLocation+\'&somaLocation=\'+somaLocation+\'&fishSex=male\''
                      , method: 'POST'
                      , onSuccess: 'alert(\'Added paternal individual in location: \'+somaLocation);setPaternalIds(data);clearInputs();selectLastPaternal(); $(\'#addPaternalIndividualDiv\').toggle(800);'
                      , onError: 'alert(\'error\');'
              )};
        });

        $("#addNewLine").click(function () {
            var name = $('#newLineName').val();
            var comment = $('#newLineComment').val();
            var stockId = '${stockInstance.id}';
            if (name.length == 0) {
                alert('You must provide a name');
                return;
            }
            ${remoteFunction(action: 'addLineToStock'
                      , controller: 'line'
                      , params: '\'stockId=\' + stockId+\'&comment=\'+comment+\'&name=\'+name'
                      , method: 'POST'
                      , onSuccess: 'addAndSelectLine(data);'
                      , onError: 'alert(\'error\');'
              )};
        });

        %{--var maternalStockId = ${stockInstance.maternalStock.id};--}%
        %{--jQuery.ajax({--}%
        %{--type: 'POST'--}%
        %{--, data: 'stockId=' + maternalStockId+ '&excludeGender=male'--}%
        %{--, url: '/sticklebackdb/individual/findIndividualsForStock'--}%
        %{--, success: function (data, textStatus) {--}%
        %{--setMaternalIds(data);--}%
        %{--},--}%
        %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
        %{--alert('error');--}%
        %{--}--}%
        %{--});--}%

        %{--var paternalStockId = ${stockInstance.paternalStock.id};--}%
        %{--jQuery.ajax({--}%
        %{--type: 'POST'--}%
        %{--, data: 'stockId=' + paternalStockId+ '&excludeGender=female'--}%
        %{--, url: '/sticklebackdb/individual/findIndividualsForStock'--}%
        %{--, success: function (data, textStatus) {--}%
        %{--setPaternalIds(data);--}%
        %{--},--}%
        %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
        %{--alert('error');--}%
        %{--}--}%
        %{--});--}%

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

<br/>
<hr/>

%{-- Fertilization Date --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fertilizationDate', 'error')} ">
    <label for="fertilizationDate">
        <g:message code="stock.fertilizationDate.label" default="Fertilization Date"/>
    </label>
    <g:datePicker name="fertilizationDate" precision="day" relativeYears="[0..-20]"
                  value="${stockInstance?.fertilizationDate}" default="${new Date()}" noSelection="['': '']"/>
</div>

<br/>
<hr/>

%{-- Line --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
    <label for="line">
        <g:message code="stock.line.label" default="Select Existing Line"/>
    </label>
    <g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.listOrderByName()}" optionKey="id"
              value="${stockInstance.line?.id}" style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Line -']"
              optionValue="name"
        onchange="
    ${remoteFunction(action: 'findLine'
              , controller: 'line'
              , params: '\'id=\' + this.value'
              , method: 'POST'
              , onSuccess: 'setLineView(data);'
              , onError: 'alert(\'error\');')}
    "
    />

    <input type="button" id="addNewLineButton" value="Add New Line"/>
    <div id="newViewDiv" class="lineSmallView"></div>
</div>


<div id="addNewLineDiv">

    <div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
        <label for="line">
            <g:message code="stock.line.label" default="Add New Line"/>
        </label>
        <input id="addNewLine" type="button" value="Add"/>
        Name: <g:textField id="newLineName" name="newLineName"/>
        Comment: <g:textField id="newLineComment" name="newLineComment"/>
    </div>

</div>

<br/>
<hr/>

%{-- Maternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalStockID', 'error')} ">
    <label for="maternalStockID">
        <g:message code="stock.maternalStockID.label" default="Maternal Stock ID"/>
    </label>


    <g:select id="maternalStock-Id" name="maternalStock.id"
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

    <div id="maternalView" class="stockSmallView"></div>
</div>

%{-- Maternal Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalIndividualID', 'error')} ">
    <label for="maternalIndividual">
        <g:message code="stock.maternalIndividualID.label" default="Maternal Individual ID"/>
    </label>
    %{--<g:select id="maternalIndividual" name="maternalIndividual.id" from="${stockInstance ? edu.uoregon.sticklebackdb.Individual.findAllByStock(stockInstance,[sort:"individualID",order:"desc"]):[]}"--}%
    <g:select id="maternalIndividual" name="maternalIndividual.id"
              from="${stockInstance.maternalStock ? stockInstance.maternalStock.femaleIndividuals: []}"
              value="${stockInstance?.maternalIndividual?.id}" style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Individual -']"
              optionValue="individualIDLabel" optionKey="id"/>
    <input type="button" id="addNewMaternalIndividualButton" value="Add New Maternal Individual"/>
</div>

%{-- Add Maternal Individual ID Button --}%
<div id='addMaternalIndividualDiv' class="fieldcontain ${hasErrors(bean: stockInstance, field: 'addMaternalIndividualID', 'error')} ">
    <label for="addMaternalIndividual">
        <g:message code="stock.addMaternalIndividualID.label" default="Add Maternal Individual"/>
    </label>
    <input id="addMaternalIndividualButton" type="button" value="Add"/>
    Comment: <g:textField id="newMaternalIndividualComment" name="newMaternalIndividualComment" size="50"/>
    <br/>
    <br/>
    Locations
    Soma: <g:textField id="newMaternalSomaLocation" name="newMaternalSomaLocation"/>
    Finclip: <g:textField id="newMaternalFinclipLocation" name="newMaternalFinclipLocation"/>
    Dna: <g:textField id="newMaternalDnaLocation" name="newMaternalDnaLocation"/>

    <br/>
    <br/>
    Id Status: <g:select id="newMaternalIdStatus" name="newMaternalIdStatus" from="${edu.uoregon.IndividualIdStatus.values()}"/>
</div>

<br/>
<hr/>

%{-- Paternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalStockID', 'error')} ">
    <label for="paternalStockID">
        <g:message code="stock.paternalStockID.label" default="Paternal Stock ID"/>
    </label>

    <g:select id="paternalStock-Id" name="paternalStock.id"
              from="${edu.uoregon.sticklebackdb.Stock.listOrderByStockID(order: "desc")}"
              value="${stockInstance?.paternalStock?.id}"
              class="many-to-one" noSelection="['null': '- Choose Stock -']"
              optionValue="stockIDLabel" optionKey="id"
              onchange="
              ${remoteFunction(action: 'findIndividualsForStock'
                      , controller: 'individual'
                      , params: '\'stockId=\' + this.value+\'&excludeGender=female\''
                      , method: 'POST'
                      , onSuccess: 'setPaternalIds(data);syncLines();'
                      , onError: 'alert(\'error\');'
              )}"/>

    <div id="paternalView" class="stockSmallView"></div>
</div>

%{-- Paternal Individual --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalIndividual', 'error')} ">
    <label for="paternalIndividual">
        <g:message code="stock.paternalIndividual.label" default="Paternal Individual"/>
    </label>
    %{--<g:select id="paternalIndividual" name="paternalIndividual.id" from="${stockInstance ? edu.uoregon.sticklebackdb.Individual.findAllByStock(stockInstance,[sort:"individualID",order:"desc"]):[]}"--}%
    %{--<g:select id="paternalIndividual" name="paternalIndividual.id" from="${[]}"--}%
    <g:select id="paternalIndividual" name="paternalIndividual.id"
              from="${stockInstance.paternalStock ? stockInstance.paternalStock.maleIndividuals : []}"
              optionKey="id"
              value="${stockInstance?.paternalIndividual?.id}" style="width:200px;font-size: 12px"
              class="many-to-one" noSelection="['null': '- Choose Individual -']"
              optionValue="individualIDLabel"/>

    <input type="button" id="addNewPaternalIndividualButton" value="Add New Paternal Individual"/>
</div>

%{-- Add Paternal Individual ID Button --}%
<div id='addPaternalIndividualDiv' class="fieldcontain ${hasErrors(bean: stockInstance, field: 'addPaternalIndividualID', 'error')} ">
    <label for="addPaternalIndividual">
        <g:message code="stock.addPaternalIndividualID.label" default="Add Paternal Individual"/>
    </label>
    %{--<input id="addPaternalIndividualButton" type="button" value="Add"/>--}%
    %{--Location: <g:textField id="newPaternalIndividualLocation" name="newPaternalIndividualLocation"/>--}%
    %{--Comment: <g:textField id="newPaternalIndividualComment" name="newPaternalIndividualComment"/>--}%

    <input id="addPaternalIndividualButton" type="button" value="Add"/>
    Comment: <g:textField id="newPaternalIndividualComment" name="newPaternalIndividualComment" size="50"/>
    <br/>
    <br/>
    Locations
    Soma: <g:textField id="newPaternalSomaLocation" name="newPaternalSomaLocation"/>
    Finclip: <g:textField id="newPaternalFinclipLocation" name="newPaternalFinclipLocation"/>
    Dna: <g:textField id="newPaternalDnaLocation" name="newPaternalDnaLocation"/>
    <br/>
    <br/>
    Id Status: <g:select id="newPaternalIdStatus" name="newPaternalIdStatus" from="${edu.uoregon.IndividualIdStatus.values()}"/>

    %{--<g:select id="maternalIndividual" name="maternalIndividual.id" from="${stockInstance ? edu.uoregon.sticklebackdb.Individual.findAllByStock(stockInstance,[sort:"individualID",order:"desc"]):[]}"--}%
    %{--<g:select id="maternalIndividual" name="maternalIndividual.id" from="${[]}"--}%
    %{--value="${stockInstance?.maternalIndividual?.id}" style="width:200px;font-size: 12px"--}%
    %{--class="many-to-one" noSelection="['null': '- Choose Individual -']"--}%
    %{--optionValue="individualIDLabel" optionKey="id"/>--}%
</div>

<br/>
<hr/>

%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'comments', 'error')} ">
    <label for="comments">
        <g:message code="stock.comments.label" default="Comments"/>
    </label>
    <g:textArea name="comments" value="${stockInstance?.comments}"/>
</div>