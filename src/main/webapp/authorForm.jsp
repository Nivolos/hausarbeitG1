<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h2 class="m-2"><s:text name="title.author.form"/></h2>

<s:form>
  <s:textfield name="author.firstName" key="label.author.firstName" placeholder="Michael" />
  <s:textfield name="author.lastName" key="label.author.lastName" placeholder="Paulsen"/>

  <s:hidden name="author.id" />

  <div class="float-right">
    <s:submit key="button.save" action="saveAuthor" class="btn btn-block btn-primary m-1"/>
    <s:submit key="button.cancel" action="adminAuthorList" class="btn btn-block btn-primary m-1"/>
  </div>


</s:form>