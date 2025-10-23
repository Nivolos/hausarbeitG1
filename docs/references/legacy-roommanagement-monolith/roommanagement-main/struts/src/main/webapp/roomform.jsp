<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="header.editRoom"/></h2>
<s:form>
    <s:hidden key="room.id"/>
    <s:textfield key="room.building" requiredLabel="true"/>
    <s:textfield key="room.roomNumber" requiredLabel="true"/>
    <s:textfield key="room.seats" requiredLabel="true"/>
    <s:checkbox key="room.presenterAvailable"/>
    <s:submit key="button.save" action="saveRoom"/>
    <s:submit key="button.cancel" action="showRoomList"/>
</s:form>
