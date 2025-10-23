<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h2 class="m-2"><s:text name="title.publications.list"/></h2>
<%@ taglib prefix="sj" uri="/struts-dojo-tags" %>

<s:form>
    <s:textfield name="publication.title" key="label.publication.title"
                 requiredLabel="true"
                 placeholder="Jakarta Struts for Dummies" class="form-control"/>

    <s:select name="selectedPublicationTypeId"
              key="label.publication.type"
              requiredLabel="true"
              list="publicationTypeSelectionMap" listKey="key" listValue="value" headerKey="-1"
              headerValue="%{getText('label.publication.publicationtype.defaultselection')}"
              multiple="false" value="existingPublicationTypeId"
              class="select selectpicker mb-3 md-2 mr-3" data-live-search="true" />

    <s:select name="selectedAuthorIds"
              key="label.publication.authors.defaultselection"
              list="authorSelectionMap" listKey="key" listValue="value"
              headerKey="-1" headerValue="%{getText('label.publication.authors.defaultselection')}"
              multiple="true" value="existingAuthorIds"
              class="select selectpicker mb-3 md-2 mr-3" data-live-search="true" />

    <sj:head/>
    <sj:datetimepicker name="publication.publicationDate" key="label.publication.publicationdate" value="" displayFormat="%{getText(format.date)}" cssClass=""/>
    <s:textfield name="publication.publisher" key="label.publication.publisher" placeholder="John Wiley and Sons" class="form-control"/>

    <s:textfield name="publication.isbn" key="label.publication.isbn" placeholder="978-076-455-957-0" class="form-control"/>

    <s:textfield name="publication.internalId" key="label.publication.internalid" placeholder="TI 35-34509"
                 class="form-control"/>

    <s:textfield name="publication.availableStock" key="label.publication.availablestock" placeholder="3" requiredLabel="true" class="form-control"/>

    <s:select name="selectedKeywordIds"
              key="label.publication.keywords.defaultselection"
              list="keywordSelectionMap" listKey="key" listValue="value"
              headerKey="-1" headerValue="%{getText('label.publication.keywords.defaultselection')}"
              multiple="true" value="existingKeywordIds"
              class="select selectpicker mb-3 md-2 mr-3" data-live-search="true" />

    <s:hidden name="publication.id"/>

    <s:submit key="button.save" action="savePublication" class="btn btn-primary m-1"/>
    <s:submit key="button.cancel" action="showSearch" class="btn btn-primary m-1"/>
</s:form>