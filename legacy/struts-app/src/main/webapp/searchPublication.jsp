<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="d" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sj" uri="/struts-dojo-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<h2 class="md-2"><s:text name="title.publications.list"/></h2>

<s:form>
  <div class="row">
    <div class="form-group m-2">
      <s:label for="publication.title" key="label.publication.title" theme="simple" />
      <s:textfield name="publication.title"
                   key="label.publication.title"
                   class="form-control"
                   theme="simple"/>
    </div>
    <div class="form-group m-2">
      <s:label for="publication.publisher" key="label.publication.publisher" theme="simple" />
      <s:textfield
            name="publication.publisher"
            key="label.publication.publisher"
            class="form-control"
            theme="simple"/>
    </div>
    <div class="form-group m-2">
      <s:label for="publication.isbn" key="label.publication.isbn" theme="simple" />
      <s:textfield name="publication.isbn" key="label.publication.isbn" class="form-control" theme="simple"/>
    </div>
    <div class="form-group m-2">
      <s:label for="selectedAuthorIds" key="label.publication.author.search.defaultselection" theme="simple" />
      <br>
      <s:select name="selectedAuthorIds"
                key="selectedAuthorIds"
                list="authorSelectionMap" listKey="key" listValue="value"
                headerKey="-1" headerValue="%{getText('label.publication.author.search.defaultselection')}"
                multiple="true" theme="simple"
                class="select selectpicker mb-3 md-2 mr-3" data-live-search="true" />
    </div>
    <div class="form-group m-2">
      <s:label for="selectedPublicationTypeIds" key="label.publication.publicationtype.search.defaultselection"
               theme="simple" />
      <br>
      <s:select
              name="selectedPublicationTypeIds"
              key="label.publication.type"
              list="publicationTypeSelectionMap"
              listKey="key" listValue="value"
              headerKey="-1" headerValue="%{getText('label.publication.publicationtype.search.defaultselection')}"
              multiple="true" theme="simple"
              class="select selectpicker mb-3 md-2 mr-3" data-live-search="true" />
    </div>
    <div class="form-group m-2">
      <s:label for="selectedKeywordIds" key="label.publication.keywords.search.defaultselection"
               theme="simple" />
      <br>
      <s:select name="selectedKeywordIds"
                key="selectedKeywordIds"
                list="keywordSelectionMap" listKey="key" listValue="value"
                headerKey="-1" headerValue="%{getText('label.publication.keywords.search.defaultselection')}"
                multiple="true" theme="simple"
                class="select selectpicker mb-3 md-2 mr-3" data-live-search="true" />
    </div>
    <div class="row">
    <div class="form-group m-2">
      <s:label for="publication.publicationDate" key="label.publication.searchDate" theme="simple" />
      <br>
      <sj:head/>
      <sj:datetimepicker name="publication.publicationDate"
                         displayFormat="%{getText(format.date)}"
                         value=""/>
    </div>
      <div class="float-right">
        <s:submit key="button.search" action="showSearchResult" class="btn btn-block btn-primary m-1" theme="simple"/>
        <s:submit key="button.resetSearch" action="showSearch" class="btn btn-block btn-light m-1" theme="simple"/>
      </div>
    </div>

  </div>
</s:form>
<h2><s:text name="title.publications.searchresult"/></h2>

<s:form>
  <d:table class="table table-responsive" name="publicationList" id="id" requestURI="" defaultsort="2">
    <d:column titleKey="label.selection" media="html">
      <input type="radio" name="publicationKey" value="${attr.id.id}"/>
    </d:column>
    <d:column property="title" sortable="true" titleKey="label.publication.title" class="cell"/>
    <d:column titleKey="label.publication.author" sortable="true" class="cell">
      <s:iterator value="#attr.id.author">
        <s:property value="firstName"/> <s:property value="lastName"/><br/>
      </s:iterator>
    </d:column>
    <d:column property="publicationDate" titleKey="label.publication.date"
              format="{0,date,MM/dd/yyyy}" sortable="true" class="cell"/>
    <d:column sortable="true" titleKey="label.publication.type" class="cell">
              <s:iterator value="#attr.id.publicationType">
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
    </d:column>
    <d:column titleKey="label.publication.keywords" sortable="true" class="cell">
      <s:iterator value="#attr.id.keywords">
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
    </d:column>
    <d:column property="availableStock" sortable="true" titleKey="label.publication.availablestock" class="cell"/>
    <d:column property="internalId" sortable="true" titleKey="label.publication.internalid" class="cell"/>
  </d:table>

  <shiro:hasRole name="admin">
      <div class="float-right">
    <s:if test="%{!publicationList.isEmpty()}">
      <s:submit key="button.publication.lend" action="newLendingProcessForSelectedPublicationId" class="btn btn-block btn-primary m-1"/>
      <s:submit key="button.publication.show" action="showPublicationDetail" class="btn btn-block btn-primary m-1"/>
      <s:submit key="button.publication.edit" action="editPublication" class="btn btn-block btn-light m-1"/>
      <s:submit key="button.publication.delete" action="deletePublication" class="btn btn-block btn-light m-1"/>
    </s:if>
    <s:submit key="button.publication.new" action="newPublication" class="btn btn-block btn-light m-1"/>
      </div>
  </shiro:hasRole>

  <shiro:hasRole name="user">
    <div class="float-right">
      <s:if test="%{!publicationList.isEmpty()}">
        <s:submit key="button.publication.show" action="showPublicationDetail" class="btn btn-block btn-primary m-1"/>
      </s:if>
    </div>
  </shiro:hasRole>

</s:form>