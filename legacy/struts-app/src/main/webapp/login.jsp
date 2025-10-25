<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<h2 class="m-2"><s:text name="title.login"/></h2>


<s:form>
  <s:textfield key="label.username" name="libraryUser.userName" class="form-control m-1"/>
  <s:password key="label.password" name="libraryUser.password" class="form-control m-1"/>
  <s:submit key="button.login" action="authuser" class="btn btn-primary m-1"/>
  <br>
  <br>
  <br>
  <s:submit key="button.create.user" action="newUserFromLogin" class="btn btn-primary m-1"/>
</s:form>