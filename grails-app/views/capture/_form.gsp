<%@ page import="edu.uoregon.sticklebackdb.Capture" %>

<r:require modules="jquery,jquery-ui"/>

<script>

    function updateLineName() {

        var lineText = '';
//        lineText += $('#population').val();
        lineText += $("#population option:selected").text();
        lineText += '-';
        lineText += $("#captureDate_day option:selected").text();
        lineText += '-';
        lineText += $("#captureDate_month option:selected").val();
        lineText += '-';
        lineText += $("#captureDate_year option:selected").text();


        $('#newLineName').val(lineText);
    }

    $(document).ready(function () {
        $("#population").change(function () {
            updateLineName();
        });

        $("#captureDate_day").change(function () {
            updateLineName();
        });

        $("#captureDate_month").change(function () {
            updateLineName();
        });

        $("#captureDate_year").change(function () {
            updateLineName();
        });
    });

</script>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'capture', 'error')} required">
    <label for="capture">
        <g:message code="capture.capture.label" default="Line"/>
        <span class="required-indicator">*</span>
    </label>

    %{--<g:if test="${captureInstance.id}">--}%
        <g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.listOrderByName()}"
                  optionValue="name"
                  optionKey="id" required="" value="${captureInstance?.line?.id}" class="many-to-one"/>
    %{--</g:if>--}%
    %{--<g:else>--}%
        %{--<g:textField name="newLineName" value="" id="newLineName" size="60"/>--}%
    %{--</g:else>--}%
</div>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'population', 'error')} required">
    <label for="population">
        <g:message code="capture.population.label" default="Population"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="population" name="population.id"
              from="${edu.uoregon.sticklebackdb.Population.listOrderByIdentification()}"
              optionKey="id" required=""
              optionValue="identification"
              value="${captureInstance?.population?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'captureDate', 'error')} ">
    <label for="captureDate">
        <g:message code="capture.captureDate.label" default="Capture Date"/>

    </label>
    <g:datePicker id="captureDate" name="captureDate" precision="day" value="${captureInstance?.captureDate}"
                  default="none" relativeYears="[0..-20]" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'comment', 'error')} ">
    <label for="comment">
        <g:message code="capture.comment.label" default="Comment"/>

    </label>
    <g:textArea name="comment" value="${captureInstance?.comment}"/>
</div>

%{-- GPS --}%
%{--<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'gps', 'error')} ">--}%
%{--<label for="species">--}%
%{--<g:message code="population.gps.label" default="GPS" />--}%
%{--</label>--}%
%{--<g:textField name="gps" value="${captureInstance?.gps?.id}"/>--}%
%{--</div>--}%
