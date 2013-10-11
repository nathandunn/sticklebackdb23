<%@ page import="edu.uoregon.sticklebackdb.Population" %>

%{-- Name --}%
<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'name', 'error')} ">
  <label for="name">
    <g:message code="population.name.label" default="Name" />
  </label>
  <g:textField name="name" value="${populationInstance?.name}"/>
</div>

%{-- Species --}%
<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'species', 'error')} ">
  <label for="species">
    <g:message code="population.species.label" default="Species" />
  </label>
  <g:textField name="species" value="${populationInstance?.species}"/>
</div>

%{-- GPS --}%
<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'gps', 'error')} ">
  <label for="species">
    <g:message code="population.gps.label" default="GPS" />
  </label>
  <g:textField name="gps" value="${populationInstance?.gps}"/>
</div>

%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'comment', 'error')} ">
  <label for="comment">
    <g:message code="population.comment.label" default="Comment" />
  </label>
  <g:textField name="comment" value="${populationInstance?.comment}"/>
</div>
