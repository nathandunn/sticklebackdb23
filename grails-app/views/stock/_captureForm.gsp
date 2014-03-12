<%@ page import="edu.uoregon.sticklebackdb.ResearcherService; edu.uoregon.sticklebackdb.Stock" %>

<r:require modules="jquery,jquery-ui"/>



<script>

    function setLine(){
//        var name = $('#population').val($(this).find("option:selected").attr("title"));
//        var name = $('#population').val($(this).find("option:selected").text());
        var name = $("#population option:selected").text();

        $("#line option:contains(" + name + ")").attr('selected', 'selected');
        $('#line').change();
        $('#stockName').val(name);


//        $("#line").filter(function() {
//            alert('name '+name +' filtering: '+$(this).text().trim());
//            //may want to use $.trim in here
//            return $(this).text().trim() == name ;
//        }).prop('selected', true);
//        $('#line').val($(this).attr("title"));
//        $('#line').val(12)
//        $('#line').title(name);
    }

    function setLineView(data) {
        var paternalView = $("#newViewDiv");

        if (!data) {
            paternalView.html('');
            return;
        }

        var linkView = '${createLink(action: "show",controller: "line")}/' + data.id;
        if (data.captures !== 'undefined' && data.captures.length > 0) {
            paternalView.html('<a href=' + linkView + '>' + data.name + '</a><br/> Captures: ' + data.captures.length + ' Stocks: ' + data.stocks.length);
        }
        else {
            paternalView.html('<a href=' + linkView + '>' + data.name + '</a><br/> Stocks: ' + data.stocks.length);
        }
    }

    function clearInputs() {
        $('#newLineName').val('');
        $('#newLineComment').val('');
        $('#population').val('');
        $('#newLineDate_day').val('');
        $('#newLineDate_month').val('');
        $('#newLineDate_year').val('');
    }

    function addAndSelectLine(data) {
        clearInputs();
        alert('Added new line: ' + data.name);
        var select = $("#line");
        select.append('<option value=' + data.id + '>' + data.name + '</option>');

        $('#addNewLineDiv').toggle(800);

        select.val(data.id);
        select.change();
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

        $("#addNewLineDiv").hide();
//        $("#addNewLineButton").click(function () {
//            $('#addNewLineDiv').toggle(800);
//        });


        $("#addNewLine").click(function () {
            var name = $('#newLineName').val();
//            var captureComment = $('#newLineComment').val();
//            var populationId = $('#population').val();
//            var captureDay = $('#newLineDate_day').val();
//            var captureMonth = $('#newLineDate_month').val();
//            var captureYear = $('#newLineDate_year').val();
            if (name.length == 0) {
                alert('You must provide a name');
                return;
            }


            ${remoteFunction(action: 'addLineToStockWithCapture'
                      , controller: 'line'
                      , params: "\'name=\'+name+\'&stockId=\'+${stockInstance.stockID}+\'&comment=\'"
                      , method: 'POST'
                      , onSuccess: 'addAndSelectLine(data);'
                      , onError: 'alert(\'error\');'
              )};
        });
    });
</script>

<g:if test="${!stockInstance.id}">

    <div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
        <label for="population">
            Capture Population:
        </label>
        <g:select id="population" name="population"
                  from="${edu.uoregon.sticklebackdb.Population.executeQuery("from Population p order by p.common asc,p.identification asc")}"
                  optionKey="id" optionValue="shortIdentification"
        onchange="setLine();"
        />
    </div>

    <div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
        <label for="line">
            <g:message code="stock.line.label" default="Capture Date"/>
        </label>
        <g:datePicker id="newLineDate" name="newLineDate" precision="day" relativeYears="[0..-20]"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
        <label for="line">
            <g:message code="stock.line.label" default="Capture Comment"/>
        </label>
        <g:textField id="newLineComment" name="newLineComment"/>
    </div>
</g:if>


<br/>
<hr/>

%{-- Line --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
    <label for="line">
        <g:message code="stock.line.label" default="Existing Line With Capture"/>
    </label>
%{--<g:select id="line" name="line.id" from="${lines}" optionKey="id"--}%
    <g:if test="${isNewOrAdmin}">
        <g:select id="line" name="line.id" from="${lines}" optionKey="id"
                  value="${stockInstance.line?.id}" style="width:260px;font-size: 12px"
                  class="many-to-one" noSelection="['null': '- Add New Line From Selected Population -']"
                  optionValue="name"
                  onchange="
                  ${remoteFunction(action: 'findLine'
                          , controller: 'line'
                          , params: '\'id=\' + this.value'
                          , method: 'POST'
                          , onSuccess: 'setLineView(data);'
                          , onError: 'alert(\'error\');')}
                  "/>

        %{--<input type="button" id="addNewLineButton" value="Add New Line"/>--}%

        <div id="newViewDiv" class="lineSmallView">
            <g:if test="${stockInstance?.line?.id}">
                <g:link action="show" controller="line"
                        id="${stockInstance?.line?.id}">${stockInstance?.line.name}</g:link>
                <g:if test="${stockInstance.line?.captures}">
                    Captures:  ${stockInstance?.line?.captures.size()}
                    Stocks:  ${stockInstance.line?.stocks.size()}
                </g:if>
            </g:if>
        </div>
    </g:if>
    <g:else>
        <g:link action="show" id="${stockInstance?.line?.id}" controller="line">${stockInstance?.line?.name}</g:link>
    </g:else>
</div>


<div id='addNewLineDiv' class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
    <label for="line">
        <g:message code="stock.line.label" default="Add New Line"/>
    </label>
    <input id="addNewLine" type="button" value="Add"/>
    Name: <g:textField id="newLineName" name="newLineName"/>
</div>

<br/>
<hr/>

%{--if admin or new --}%
%{-- Name --}%
<shiro:hasRole name="${edu.uoregon.sticklebackdb.ResearcherService.ROLE_ADMINISTRATOR}">
    <div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockName', 'error')} ">
        <label for="stockName">
            <g:message code="stock.stockName.label" default="Stock Name"/>
        </label>
        <g:textField id="stockName" class="ui-autocomplete-input" name="stockName" value="${stockInstance?.stockName}"
                     autocomplete="off" size="60"/>
    </div>
</shiro:hasRole>
%{--if user or old--}%
<shiro:hasRole name="${edu.uoregon.sticklebackdb.ResearcherService.ROLE_USER}">
    <div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockName', 'error')} ">
        <label for="stockName">
            <g:message code="stock.stockName.label" default="Stock Name"/>
        </label>
        <g:if test="${stockInstance.id}">
            ${stockInstance.stockName}
        </g:if>
        <g:else>
            <g:textField id="stockName" class="ui-autocomplete-input" name="stockName"
                         value="${stockInstance?.stockName}"
                         autocomplete="off" size="60"/>
        </g:else>
    </div>
</shiro:hasRole>




%{-- stockID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockID', 'error')} ">
    <label for="stockID">
        <g:message code="stock.stockID.label" default="Stock ID"/>
    </label>
    %{--<g:textField name="stockID" value="${stockInstance?.stockID}" />--}%
    <div class="locked-field">${stockInstance?.stockIDLabel}</div>
    <g:hiddenField name="stockID" value="${stockInstance?.stockID}"/>

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