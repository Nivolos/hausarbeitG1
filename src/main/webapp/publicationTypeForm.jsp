<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<h2 class="m-2"><s:text name="title.publicationtype.form"/></h2>

<s:form>
        <s:textfield name="publicationType.publicationTypeNameDe" key="label.publicationtype.name.de" placeholder="Buch" class="form-control"/>
        <s:textfield name="publicationType.publicationTypeNameEn" key="label.publicationtype.name.en" placeholder="book" class="form-control"/>
        <div class="form-check form-check-inline">
            <s:checkbox name="publicationType.authorRequired" key="label.publicationtype.isauthorrequired" class="form-check-input" align="center"/>
            <s:checkbox name="publicationType.publicationDateRequired" key="label.publicationtype.ispublicationdaterequired" class="form-check-input"/>
            <s:checkbox name="publicationType.publisherRequired" key="label.publicationtype.ispublisherrequired" class="form-check-input"/>
            <s:checkbox name="publicationType.isbnRequired" key="label.publicationtype.isisbnrequired" class="form-check-input"/>
        </div>
        <s:hidden name="publicationType.id" />
    <div class="float-right">
        <s:submit key="button.save" action="savePublicationType" class="btn btn-block btn-primary m-1"/>
        <s:submit key="button.cancel" action="adminPublicationTypeList" class="btn btn-block btn-primary m-1" />
    </div>
</s:form>