<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="d" uri="http://displaytag.sf.net" %>

<div class="alert alert-primary" role="alert">
    <s:text name="warning.edit.multiple"/>
</div>

<h2 class="m-2"><s:text name="title.keyword.list"/></h2>


<s:form>
    <d:table class="table" name="keywordList" id="id" requestURI="" defaultsort="2">
        <d:column titleKey="label.selection" media="html">
            <input type="radio" name="keywordKey" value="${attr.id.id}"/>
        </d:column>
        <s:if test="currentLocale.equals('de')">
            <d:column property="valueDe" sortable="true" titleKey="label.keyword.valueDe" class="cell"/>
        </s:if>
        <s:elseif test="currentLocale.equals('en')">
            <d:column property="valueEn" sortable="true" titleKey="label.keyword.valueEn" class="cell"/>
        </s:elseif>
        <s:else>
            <d:column property="valueEn" sortable="true" titleKey="label.keyword.valueEn" class="cell"/>
        </s:else>
    </d:table>

    <div class="float-right">
        <s:submit key="button.newKeyword" action="newKeyword" class="btn btn-block btn-primary m-1"/>
        <s:if test="%{keywordList.size() > 0}">
            <s:submit key="button.editKeyword" action="editKeyword" class="btn btn-block btn-primary m-1"/>
            <s:submit key="button.deleteKeyword" action="deleteKeyword" class="btn btn-block btn-primary m-1"/>
        </s:if>
    </div>

</s:form>