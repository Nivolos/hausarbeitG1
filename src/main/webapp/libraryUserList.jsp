<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="d" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="alert alert-primary" role="alert">
    <s:text name="warning.edit.multiple"/>
</div>

<h2 class="m-2"><s:text name="title.libraryUser.list"/></h2>

<s:form>
    <d:table class="table" name="libraryUserList" id="id" requestURI="" defaultsort="2">
        <d:column titleKey="label.selection" media="html">
            <input type="radio" name="libraryUserKey" value="${attr.id.id}"/>
        </d:column>
        <d:column property="userName" sortable="true" titleKey="label.libraryUser.userName" class="cell"/>
        <d:column property="firstName" sortable="true" titleKey="label.libraryUser.firstName" class="cell"/>
        <d:column property="lastName" sortable="true" titleKey="label.libraryUser.lastName" class="cell"/>
        <d:column property="email" sortable="true" titleKey="label.libraryUser.email" class="cell"/>
        <d:column property="studentNumber" sortable="true" titleKey="label.libraryUser.studentNumber" class="cell"/>
    </d:table>
    <div class="float-right">
        <s:submit key="button.newUser" action="newUser" class="btn btn-block btn-primary m-1"/>
        <s:if test="%{libraryUserList.size() > 0}">
            <s:submit key="button.editUser" action="editUser" class="btn btn-block btn-primary m-1"/>
            <s:submit key="button.deleteUser" action="deleteUser" class="btn btn-block btn-primary m-1"/>
        </s:if>
    </div>

</s:form>