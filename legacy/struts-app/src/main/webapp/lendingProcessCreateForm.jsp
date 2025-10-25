<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h2 class="m-2"><s:text name="title.lendingprocess.form"/></h2>

<s:form>

    <s:select
        key="label.lendingprocess.libraryUser.select" name="selectedLibraryUserId"
        list="libraryUserSelectionMap" listKey="key" listValue="value"
        headerKey="-1" headerValue="%{getText('label.lendingprocess.libraryUser.defaultselection')}"
        multiple="false"
        class="select selectpicker mb-3" data-live-search="true"
    />

    <s:select
        key="label.lendingprocess.publications.select" name="selectedPublicationIds"
        list="publicationSelectionMap" listKey="key" listValue="value"
        headerKey="-1" headerValue="%{getText('label.lendingprocess.publications.defaultselection')}"
        multiple="true"
        class="select selectpicker mb-3" data-live-search="true"
    />

    <div class="float-right">
        <s:submit key="button.save" action="lendPublications" class="btn btn-block btn-primary m-1"/>
        <s:submit key="button.cancel" action="lendingProcessList" class="btn btn-block btn-primary m-1"/>
    </div>
</s:form>