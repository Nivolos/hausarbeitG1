<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html>
<head>
    <title>Room Management</title>
    <s:head/>
</head>
<body>
<tiles:insertAttribute name="header"/>
<hr/>
<s:actionerror/>
<tiles:insertAttribute name="content"/>
</body>
</html>
