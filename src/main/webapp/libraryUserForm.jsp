<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h2 class="m-2"><s:text name="title.libraryUser.form"/></h2>


<s:form>
    <s:textfield name="libraryUser.userName" key="label.libraryUser.userName" placeholder="Maxi123" requiredLabel="true"/>
    <s:textfield name="libraryUser.firstName" key="label.libraryUser.firstName"  placeholder="Max" requiredLabel="true"/>
    <s:textfield name="libraryUser.lastName" key="label.libraryUser.lastName" placeholder="Mustermann" requiredLabel="true"/>
    <s:textfield name="libraryUser.email" key="label.libraryUser.email" placeholder="max@mustermann.com" requiredLabel="true"/>
    <s:password name="libraryUser.password" key="label.libraryUser.password" requiredLabel="true"/>
    <s:textfield name="libraryUser.studentNumber" key="label.libraryUser.studentNumber" placeholder = "123" requiredLabel="false"/>
    <s:select name="selectedRolesId"
              key="label.libraryUser.role"
              requiredLabel="true"
              list="roleSelectionMap" listKey="key" listValue="value" headerKey="-1"
              headerValue="%{getText('label.libraryUser.role.defaultselection')}"
              multiple="false" value="existingRolesIds"
              class="select selectpicker mb-3 md-2 mr-3" data-live-search="true" />

    <s:hidden name="libraryUser.id"/>

    <div class="float-right">
        <s:submit key="button.save" action="saveLibraryUser" class="btn btn-block btn-primary m-1"/>
        <s:submit key="button.cancel" action="libraryUserList" class="btn btn-block btn-primary m-1"/>
    </div>

</s:form>