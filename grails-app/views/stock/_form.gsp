<%@ page import="edu.uoregon.sticklebackdb.Stock" %>



<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'barcode', 'error')} required">
	<label for="barcode">
		<g:message code="stock.barcode.label" default="Barcode" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="barcode" name="barcode.id" from="${edu.uoregon.sticklebackdb.Container.list()}" optionKey="id" required="" value="${stockInstance?.barcode?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'crossDate', 'error')} required">
	<label for="crossDate">
		<g:message code="stock.crossDate.label" default="Cross Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="crossDate" precision="day"  value="${stockInstance?.crossDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'crossType', 'error')} required">
	<label for="crossType">
		<g:message code="stock.crossType.label" default="Cross Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="crossType" type="number" value="${stockInstance.crossType}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fertilizationDateTime', 'error')} required">
	<label for="fertilizationDateTime">
		<g:message code="stock.fertilizationDateTime.label" default="Fertilization Date Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fertilizationDateTime" precision="day"  value="${stockInstance?.fertilizationDateTime}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fertilizationDisplay', 'error')} ">
	<label for="fertilizationDisplay">
		<g:message code="stock.fertilizationDisplay.label" default="Fertilization Display" />
		
	</label>
	<g:textField name="fertilizationDisplay" value="${stockInstance?.fertilizationDisplay}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fishAgeDays', 'error')} required">
	<label for="fishAgeDays">
		<g:message code="stock.fishAgeDays.label" default="Fish Age Days" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="fishAgeDays" type="number" value="${stockInstance.fishAgeDays}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fishStock', 'error')} required">
	<label for="fishStock">
		<g:message code="stock.fishStock.label" default="Fish Stock" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="fishStock" type="number" value="${stockInstance.fishStock}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} required">
	<label for="line">
		<g:message code="stock.line.label" default="Line" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.list()}" optionKey="id" required="" value="${stockInstance?.line?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalStock', 'error')} required">
	<label for="maternalStock">
		<g:message code="stock.maternalStock.label" default="Maternal Stock" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="maternalStock" name="maternalStock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id" required="" value="${stockInstance?.maternalStock?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'nursery1InitialNumber', 'error')} required">
	<label for="nursery1InitialNumber">
		<g:message code="stock.nursery1InitialNumber.label" default="Nursery1 Initial Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nursery1InitialNumber" type="number" value="${stockInstance.nursery1InitialNumber}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'nursery2Fertiles', 'error')} required">
	<label for="nursery2Fertiles">
		<g:message code="stock.nursery2Fertiles.label" default="Nursery2 Fertiles" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nursery2Fertiles" type="number" value="${stockInstance.nursery2Fertiles}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'nursery3Hatched', 'error')} required">
	<label for="nursery3Hatched">
		<g:message code="stock.nursery3Hatched.label" default="Nursery3 Hatched" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nursery3Hatched" type="number" value="${stockInstance.nursery3Hatched}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'nursery5SecondStageSurvivors', 'error')} required">
	<label for="nursery5SecondStageSurvivors">
		<g:message code="stock.nursery5SecondStageSurvivors.label" default="Nursery5 Second Stage Survivors" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nursery5SecondStageSurvivors" type="number" value="${stockInstance.nursery5SecondStageSurvivors}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'nursery6Graduates', 'error')} required">
	<label for="nursery6Graduates">
		<g:message code="stock.nursery6Graduates.label" default="Nursery6 Graduates" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nursery6Graduates" type="number" value="${stockInstance.nursery6Graduates}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockType', 'error')} required">
	<label for="stockType">
		<g:message code="stock.stockType.label" default="Stock Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="stockType" name="stockType.id" from="${edu.uoregon.sticklebackdb.Genetics.list()}" optionKey="id" required="" value="${stockInstance?.stockType?.id}" class="many-to-one"/>
</div>

