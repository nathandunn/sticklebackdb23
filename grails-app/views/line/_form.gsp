<%@ page import="edu.uoregon.sticklebackdb.Line" %>

%{-- Name --}%
<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'name', 'error')} ">
  <label for="name">
    <g:message code="population.name.label" default="Name" />
  </label>
  <g:textField name="name" value="${lineInstance?.name}"/>
</div>

%{-- Species --}%
<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'species', 'error')} ">
  <label for="species">
    <g:message code="population.species.label" default="Species" />
  </label>
  <g:textField name="species" value="${lineInstance?.species}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'population', 'error')} required">
    <label for="population">
        <g:message code="line.capture.label" default="Capture" />
        <span class="required-indicator">*</span>
    </label>
    <g:each in="${edu.uoregon.sticklebackdb.Capture.listOrderByCaptureDate()}" var="capture">
        <g:link action="show" id="${capture.id}">${capture.display}</g:link>
    </g:each>
    %{--<g:select id="capture" name="capture.id" from="${edu.uoregon.sticklebackdb.Capture.listOrderByCaptureDate()}"--}%
              %{--optionKey="id" required="" multiple="true"--}%
              %{--optionValue="captureDate"--}%
              %{--value="${lineInstance?.capture?.id}" class="many-to-one" noSelection="['null':'None']"/>--}%
</div>



%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'comment', 'error')} ">
  <label for="comment">
    <g:message code="population.comment.label" default="Comment" />
  </label>
  <g:textArea name="comment" value="${lineInstance?.comment}"/>
</div>

%{-- Genetic Comments --}%
<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'geneticNote', 'error')} ">
    <label for="geneticNote">
        <g:message code="population.geneticComments.label" default="Genetic Note" />
    </label>
    <g:textArea name="geneticNote" value="${lineInstance?.geneticNote}"/>
</div>
