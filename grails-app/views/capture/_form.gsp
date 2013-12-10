<%@ page import="edu.uoregon.sticklebackdb.Capture" %>



<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'line', 'error')} required">
	<label for="line">
		<g:message code="capture.line.label" default="Line" />
		<span class="required-indicator">*</span>
	</label>
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
	<g:datePicker name="captureDate" precision="day"  value="${captureInstance?.captureDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: captureInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="capture.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${captureInstance?.comment}"/>
</div>

