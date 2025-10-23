<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="d" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<h2 class="m-2"><s:text name="title.publication.detail"/></h2>

<s:form>
<table class="table">
    <thead>
    <tr>
        <td><s:text name="label.publication.title"></s:text></td>
        <td><s:property value="publication.title"></s:property></td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><s:text name="label.publication.author"></s:text></td>
        <td>
            <s:iterator value="publication.author">
                <s:property value="firstName"/> <s:property value="lastName"/><br/>
            </s:iterator>
        </td>
    </tr>
    <tr>
        <td><s:text name="label.publication.type"></s:text></td>
        <td>
            <s:iterator value="publication.publicationType">
                <s:if test="currentLocale.equals('de')">
                    <s:property value="publicationTypeNameDe"/> <br/>
                </s:if>
                <s:elseif test="currentLocale.equals('en')">
                    <s:property value="publicationTypeNameEn"/> <br/>
                </s:elseif>
                <s:else>
                    <s:property value="publicationTypeNameEn"/> <br/>
                </s:else>
            </s:iterator>
        </td>
    </tr>
    <tr>
        <td><s:text name="label.publication.date"></s:text></td>
        <td><s:property value="publication.publicationDate"></s:property></td>
    </tr>
    <tr>
        <td><s:text name="label.publication.publisher"></s:text></td>
        <td><s:property value="publication.publisher"></s:property></td>
    </tr>
    <tr>
        <td><s:text name="label.publication.isbn"></s:text></td>
        <td><s:property value="publication.isbn"></s:property></td>
    </tr>
    <tr>
        <td><s:text name="label.publication.keywords"></s:text></td>
        <td>
            <s:iterator value="publication.keywords">
                <s:if test="currentLocale.equals('de')">
                    <s:property value="valueDe"/> <br/>
                </s:if>
                <s:elseif test="currentLocale.equals('en')">
                    <s:property value="valueEn"/> <br/>
                    </s:elseif>
                <s:else>
                    <s:property value="valueEn"/> <br/>
                </s:else>
             </s:iterator>
        </td>
    </tr>
    <tr>
        <td><s:text name="label.publication.internalid"></s:text></td>
        <td><s:property value="publication.internalid"></s:property></td>
    </tr>
    <tr>
        <td><s:text name="label.publication.totalStock"></s:text></td>
        <td><s:property value="publication.totalStock"></s:property></td>
    </tr>
    <tr>
        <td><s:text name="label.publication.availablestock"></s:text></td>
        <td><s:property value="publication.availablestock"></s:property></td>
    </tr>
    <tr>
        <td><s:text name="label.publication.lostAmount"></s:text></td>
        <td><s:property value="publication.lostAmount"></s:property></td>
    </tr>
    </tbody>
</table>
    <shiro:hasRole name="admin">
<h2><s:text name="label.publication.details.lendingprocesses"/></h2>
    <d:table class="table" name="lendingProcessList" id="id" requestURI="" defaultsort="1">
        <d:column property="libraryUser.firstName" sortable="true" titleKey="label.lendingprocess.libraryUser.firstname" class="cell"/>
        <d:column property="libraryUser.lastName" sortable="true" titleKey="label.lendingprocess.libraryUser.lastname" class="cell"/>
        <d:column property="plannedReturnDate" sortable="true" titleKey="label.lendingprocess.plannedreturndate" class="cell"/>
        <d:column property="actualReturnDate" sortable="true" titleKey="label.lendingprocess.actualreturndate" class="cell"/>
        <d:column property="libraryUser.email" sortable="true" titleKey="label.lendingprocess.libraryUser.email" class="cell"/>
        <d:column property="publication.title" sortable="true" titleKey="label.lendingprocess.publication.title" class="cell"/>
        <d:column property="publication.internalId" sortable="true" titleKey="label.lendingprocess.publication.internalid" class="cell"/>
    </d:table>
    <div class="float-right">
        <s:if test="publication.availableStock >= 1">
            <s:hidden name="publicationKey" theme="simple" value="%{publication.id}"/>
            <s:submit key="button.publication.lend" action="newLendingProcessForSelectedPublicationId" class="btn btn-block btn-primary m-1"/>
        </s:if>
    </shiro:hasRole>
        <s:submit key="button.cancel" action="showSearch" class="btn btn-block btn-primary m-1"/>

    </div>


</s:form>