<%@ page import="edu.uoregon.sticklebackdb.Beaker" %>



<div class="fieldcontain ${hasErrors(bean: beakerInstance, field: 'beakerId', 'error')} required">
	<label for="beakerId">
		<g:message code="beaker.beakerId.label" default="Beaker Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="beakerId" type="number" value="${beakerInstance.beakerId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: beakerInstance, field: 'eggsDead', 'error')} required">
	<label for="eggsDead">
		<g:message code="beaker.eggsDead.label" default="Eggs Dead" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="eggsDead" type="number" value="${beakerInstance.eggsDead}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: beakerInstance, field: 'eggsDeadTotal', 'error')} required">
	<label for="eggsDeadTotal">
		<g:message code="beaker.eggsDeadTotal.label" default="Eggs Dead Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="eggsDeadTotal" type="number" value="${beakerInstance.eggsDeadTotal}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: beakerInstance, field: 'eggsInitialNumber', 'error')} required">
	<label for="eggsInitialNumber">
		<g:message code="beaker.eggsInitialNumber.label" default="Eggs Initial Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="eggsInitialNumber" type="number" value="${beakerInstance.eggsInitialNumber}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: beakerInstance, field: 'eggsInitialTotal', 'error')} required">
	<label for="eggsInitialTotal">
		<g:message code="beaker.eggsInitialTotal.label" default="Eggs Initial Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="eggsInitialTotal" type="number" value="${beakerInstance.eggsInitialTotal}" required=""/>
</div>

