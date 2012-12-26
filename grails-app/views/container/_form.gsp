<%@ page import="edu.uoregon.sticklebackdb.Container" %>



<div class="fieldcontain ${hasErrors(bean: containerInstance, field: 'barcode', 'error')} ">
	<label for="barcode">
		<g:message code="container.barcode.label" default="Barcode" />
		
	</label>
	<g:textField name="barcode" value="${containerInstance?.barcode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: containerInstance, field: 'location', 'error')} ">
	<label for="location">
		<g:message code="container.location.label" default="Location" />
		
	</label>
	<g:textField name="location" value="${containerInstance?.location}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: containerInstance, field: 'size', 'error')} required">
	<label for="size">
		<g:message code="container.size.label" default="Size" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="size" type="number" value="${containerInstance.size}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: containerInstance, field: 'total', 'error')} required">
	<label for="total">
		<g:message code="container.total.label" default="Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="total" type="number" value="${containerInstance.total}" required=""/>
</div>

