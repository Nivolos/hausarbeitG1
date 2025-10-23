<%--
  Created by IntelliJ IDEA.
  User: U728347
  Date: 21.11.2022
  Time: 00:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h2 class="m-2"><s:text name="title.initialize.form"/></h2>
<s:form>
    <s:textfield name="libraryUser.userName" key="label.libraryUser.userName" placeholder="Maxi123" requiredLabel="true"/>
    <s:textfield name="libraryUser.firstName" key="label.libraryUser.firstName"  placeholder="Max" requiredLabel="true"/>
    <s:textfield name="libraryUser.lastName" key="label.libraryUser.lastName" placeholder="Mustermann" requiredLabel="true"/>
    <s:textfield name="libraryUser.email" key="label.libraryUser.email" placeholder="max@mustermann.com" requiredLabel="true"/>
    <s:password name="libraryUser.password" key="label.libraryUser.password" requiredLabel="true"/>
    <s:textfield name="libraryUser.studentNumber" key="label.libraryUser.studentNumber" placeholder = "123" requiredLabel="false"/>

    <s:hidden name="libraryUser.id"/>


    <div class="float-right">
        <s:submit key="button.save" action="createNewUserInLogin" class="btn btn-block btn-primary m-1"/>
        <s:submit key="button.cancel" action="logout" class="btn btn-block btn-primary m-1"/>
    </div>

</s:form>
