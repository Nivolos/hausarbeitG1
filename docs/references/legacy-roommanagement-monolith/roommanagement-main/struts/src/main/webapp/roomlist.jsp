<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="header.listOfRooms"/></h2>
<s:form>
    <s:actionerror/>
    <table>
        <tr>
            <th></th>
            <th><s:text name="room.building"/></th>
            <th><s:text name="room.roomNumber"/></th>
            <th><s:text name="room.seats"/></th>
            <th><s:text name="room.presenterAvailable"/></th>
        </tr>
        <s:iterator value="rooms">
            <tr>
                <td><s:radio name="selectedKey" list="#{building + '|' + roomNumber:''}" theme="simple"/></td>
                <td><s:property value="building"/></td>
                <td><s:property value="roomNumber"/></td>
                <td><s:property value="seats"/></td>
                <td><s:property value="presenterAvailable"/></td>
            </tr>
        </s:iterator>
    </table>
    <s:submit key="button.addRoom" action="addRoom"/>
    <s:submit key="button.editRoom" action="loadRoom"/>
    <s:submit key="button.deleteRoom" action="deleteRoom"/>
</s:form>
