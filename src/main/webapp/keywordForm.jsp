<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h2 class="m-2"><s:text name="title.keyword.form"/></h2>


<s:form>
    <s:textfield name="keyword.valueDe" key="label.keyword.valueDe" placeholder="Physik" />
    <s:textfield name="keyword.valueEn" key="label.keyword.valueEn" placeholder="physics"/>

    <s:hidden name="keyword.id" />

    <div class="float-right">
        <s:submit key="button.save" action="saveKeyword" class="btn btn-block btn-primary m-1"/>
        <s:submit key="button.cancel" action="adminKeywordList" class="btn btn-block btn-primary m-1"/>
    </div>


</s:form>