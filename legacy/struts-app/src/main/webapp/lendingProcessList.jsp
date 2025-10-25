<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="d" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<shiro:hasRole name="admin">
<h2 class="m-2"><s:text name="title.lendingprocess.list"/></h2>

<s:form>
    <div class="row">
        <div class="form-group m-2">
            <s:label for="searchPublication.title" key="label.publication.title" theme="simple" />
            <s:textfield name="searchPublication.title"
                         key="label.publication.title"
                         class="form-control"
                         theme="simple"/>
        </div>
        <div class="form-group m-2">
            <s:label for="searchLibraryUser.firstName" key="label.lendingprocess.libraryUser.firstname" theme="simple" />
            <s:textfield
                    name="searchLibraryUser.firstName"
                    key="label.lendingprocess.libraryUser.firstname"
                    class="form-control"
                    theme="simple"/>
        </div>
        <div class="form-group m-2">
            <s:label for="searchLibraryUser.lastName" key="label.lendingprocess.libraryUser.lastname" theme="simple" />
            <s:textfield
                    name="searchLibraryUser.lastName"
                    key="label.lendingprocess.libraryUser.lastname"
                    class="form-control"
                    theme="simple"/>
        </div>

        <!-- Filtering Checkboxes -->
        <div class="form-group m-2">
            <s:label for="searchLendingProcess.lost" key="label.lendingprocess.include.only.lost" theme="simple" />
            <s:checkbox name="searchLendingProcess.lost" key="label.lendingprocess.include.only.lost" theme="simple"/>
        </div>
        <div class="form-group m-2">
            <s:label for="searchLendingProcess.returned" key="label.lendingprocess.include.only.not.returned"
                     theme="simple" />
            <s:checkbox name="searchLendingProcess.returned" key="label.lendingprocess.include.only.not.returned"
                        theme="simple" fieldValue="false" value="searchLendingProcess.returned" />
        </div>
        <div class="form-group m-2">
            <s:label for="includeOutstandingOverdueReminderOnly"
                     key="label.lendingprocess.include.only.overdue.without.reminder" theme="simple" />
            <s:checkbox name="includeOutstandingOverdueReminderOnly"
                        key="label.lendingprocess.include.only.overdue.without.reminder" theme="simple" />
        </div>

        <div class="row m-2">
            <div class="float-right">
                <s:submit key="button.search" action="lendingProcessSearchResult" class="btn btn-block btn-primary m-1" theme="simple"/>
                <s:submit key="button.resetSearch" action="lendingProcessList" class="btn btn-block btn-light m-1" theme="simple"/>
            </div>
        </div>

    </div>
</s:form>

<s:form>
    <d:table class="table table-responsive" name="lendingProcessList" id="id" requestURI="" defaultsort="4">
        <d:column titleKey="label.selection" media="html">
            <s:checkbox name="selectedLendingProcessIds" fieldValue="%{#attr.id.id}" theme="simple" value="false"/>
        </d:column>
        <d:column property="libraryUser.firstName" sortable="true" titleKey="label.lendingprocess.libraryUser.firstname" class="cell"/>
        <d:column property="libraryUser.lastName" sortable="true" titleKey="label.lendingprocess.libraryUser.lastname" class="cell"/>
        <d:column property="libraryUser.studentNumber" sortable="true" titleKey="label.lendingprocess.libraryUser.studentnumber" class="cell"/>
        <d:column property="lendingDate" sortable="true" titleKey="label.lendingprocess.lendingdate" class="cell"/>
        <d:column property="plannedReturnDate" sortable="true" titleKey="label.lendingprocess.plannedreturndate" class="cell"/>
        <d:column property="actualReturnDate" sortable="true" titleKey="label.lendingprocess.actualreturndate" class="cell"/>
        <d:column property="publication.title" sortable="true" titleKey="label.lendingprocess.publication.title" class="cell" style="min-width: 200px"/>
        <d:column property="publication.internalId" sortable="true" titleKey="label.lendingprocess.publication.internalid" class="cell"/>
        <d:column sortable="true" titleKey="label.lendingprocess.lost" class="cell">
            <s:property value="%{getText(#attr.id.lost)}"></s:property>
        </d:column>
    </d:table>


    <div class="float-right">
        <s:submit key="button.lendingprocesses.returnselected" action="returnPublications" class="btn btn-block m-1 btn-primary"/>
        <s:submit key="button.lendingprocesses.extendselected" action="extendPublications" class="btn btn-block m-1 btn-light"/>
        <s:submit key="button.lendingprocesses.remindselected" action="remindPublications" class="btn btn-block m-1 btn-light"/>
        <s:submit key="button.lendingprocesses.lostselected"   action="lostPublications"   class="btn btn-block m-1 btn-light"/>
    </div>
</s:form>
</shiro:hasRole>
<shiro:hasRole name="user">
    <h2 class="m-2"><s:text name="title.user.welcome"/></h2>
    <s:text name="user.welcome.text" />
</shiro:hasRole>