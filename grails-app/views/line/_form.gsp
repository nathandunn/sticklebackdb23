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

%{-- GPS --}%
<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'gps', 'error')} ">
  <label for="species">
    <g:message code="population.gps.label" default="GPS" />
  </label>
  <g:textField name="gps" value="${lineInstance?.gps}"/>
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
