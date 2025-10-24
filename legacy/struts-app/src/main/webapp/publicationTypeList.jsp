<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="d" uri="http://displaytag.sf.net" %>

<div class="alert alert-primary" role="alert">
    <s:text name="warning.edit.multiple"/>
</div>

<h2 class="m-2"><s:text name="title.publicationtype.list"/></h2>

<s:form>
<d:table class="table" name="publicationTypeList" id="id" requestURI="" defaultsort="2">
    <d:column titleKey="label.selection" media="html">
        <input type="radio" name="publicationTypeKey" value="${attr.id.id}"/>
    </d:column>
    <s:if test="currentLocale.equals('de')">
        <d:column property="publicationTypeNameDe" sortable="true" titleKey="label.publicationtype.name" class="cell"/>
        <p>(de)</p>
    </s:if>
    <s:elseif test="currentLocale.equals('en')">
        <d:column property="publicationTypeNameEn" sortable="true" titleKey="label.publicationtype.valueEn" class="cell"/>
    </s:elseif>
    <s:else>
        <d:column property="publicationTypeNameEn" sortable="true" titleKey="label.publicationtype.name" class="cell"/>
    </s:else>


</d:table>
    <s:submit key="button.newPublicationType" action="newPublicationType" class="btn btn-primary m-1"/>
    <s:if test="%{!publicationTypeList.isEmpty()}">
        <s:submit key="button.edit.publication.type" action="editPublicationType" class="btn btn-primary m-1"/>
        <s:submit key="button.delete.publication.type" action="deletePublicationType" class="btn btn-primary m-1"/>
    </s:if>
    <s:else>
        <s:text name="label.table.empty"/>
    </s:else>

</s:form>