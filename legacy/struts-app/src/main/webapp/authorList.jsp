<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="d" uri="http://displaytag.sf.net" %>

<div class="alert alert-primary" role="alert">
    <s:text name="warning.edit.multiple"/>
</div>

<h2 class="m-2"><s:text name="title.author.list"/></h2>

<s:form>
    <d:table class="table" name="authorList" id="id" requestURI="" defaultsort="2">
        <d:column titleKey="label.selection" media="html">
            <input type="radio" name="authorKey" value="${attr.id.id}"/>
        </d:column>
        <d:column property="firstName" sortable="true" titleKey="label.author.firstName" class="cell"/>
        <d:column property="lastName" sortable="true" titleKey="label.author.lastName" class="cell"/>
    </d:table>

    <div class="float-right">
        <s:submit key="button.newAuthor" action="newAuthor" class="btn btn-block btn-primary m-1"/>
        <s:if test="%{authorList.size() > 0}">
            <s:submit key="button.editAuthor" action="editAuthor" class="btn btn-block btn-primary m-1"/>
            <s:submit key="button.deleteAuthor" action="deleteAuthor" class="btn btn-block btn-primary m-1"/>
        </s:if>
    </div>
</s:form>
