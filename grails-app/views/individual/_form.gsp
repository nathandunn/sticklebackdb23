<%@ page import="edu.uoregon.sticklebackdb.Individual" %>



<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'fertilization', 'error')} ">
    <label for="fertilization">
        <g:message code="individual.fertilization.label" default="Fertilization"/>

    </label>
    <g:datePicker name="fertilization" precision="day" value="${individualInstance?.fertilization}" default="none"
                  noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'fishLocation', 'error')} ">
    <label for="fishLocation">
        <g:message code="individual.fishLocation.label" default="Fish Location"/>

    </label>
    <g:textField name="fishLocation" value="${individualInstance?.fishLocation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'index', 'error')} ">
    <label for="index">
        <g:message code="individual.index.label" default="Index"/>

    </label>
    <g:field name="index" type="number" value="${individualInstance.index}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'maternal', 'error')} required">
    <label for="maternal">
        <g:message code="individual.maternal.label" default="Maternal"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="maternal" name="maternal.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
              required="" value="${individualInstance?.maternal?.id}" class="many-to-one"
        optionValue="barcode"
    />
</div>

<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'paternal', 'error')} required">
    <label for="paternal">
        <g:message code="individual.paternal.label" default="Paternal"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="paternal" name="paternal.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
              required="" value="${individualInstance?.paternal?.id}" class="many-to-one"
              optionValue="barcode"
    />
</div>

<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'stock', 'error')} ">
    <label for="stock">
        <g:message code="individual.stock.label" default="Stock"/>

    </label>
    <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
              value="${individualInstance?.stock?.id}"
              class="many-to-one" noSelection="['null': '']"
        optionValue="barcode"
    />
</div>

<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'stockDate', 'error')} ">
    <label for="stockDate">
        <g:message code="individual.stockDate.label" default="Stock Date"/>

    </label>
    <g:datePicker name="stockDate" precision="day" value="${individualInstance?.stockDate}" default="none"
                  noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: individualInstance, field: 'stockIndividualDate', 'error')} ">
    <label for="stockIndividualDate">
        <g:message code="individual.stockIndividualDate.label" default="Stock Individual Date"/>

    </label>
    <g:datePicker name="stockIndividualDate" precision="day" value="${individualInstance?.stockIndividualDate}"
                  default="none" noSelection="['': '']"/>
</div>

