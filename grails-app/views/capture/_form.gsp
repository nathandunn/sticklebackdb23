<%@ page import="edu.uoregon.sticklebackdb.Capture" %>

<r:require modules="jquery,jquery-ui"/>

<script>

    var updateLineName = new function(){
        $('#newLineName').html('cahngeded');
    }   ;

    $(document).ready(function () {
        $( "#population" ).change(function() {
            alert( "Handler for .change() called." );
        });

        $( "#captureDate" ).change(function() {
            alert( "Capture Date Handler for .change() called." );
        });
    });

</script>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'capture', 'error')} required">
	<label for="capture">
		<g:message code="capture.capture.label" default="Line" />
		<span class="required-indicator">*</span>
	</label>

    <g:textField name="newLineName" value="" id="newLineName"/>
	<g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.listOrderByName()}"
        optionValue="name"
              optionKey="id" required="" value="${captureInstance?.line?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'population', 'error')} required">
	<label for="population">
		<g:message code="capture.population.label" default="Population" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="population" name="population.id" from="${edu.uoregon.sticklebackdb.Population.listOrderByIdentification()}"
              optionKey="id" required=""
        optionValue="identification"
              value="${captureInstance?.population?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'captureDate', 'error')} ">
	<label for="captureDate">
		<g:message code="capture.captureDate.label" default="Capture Date" />
		
	</label>
	<g:datePicker id="captureDate" name="captureDate" precision="day"  value="${captureInstance?.captureDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="capture.comment.label" default="Comment" />
		
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
