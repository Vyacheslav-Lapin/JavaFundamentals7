<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Ваши имя и фамилия</title>
</head>
<body>

<jsp:useBean id="person" class="com.epam.web.controllers.SimplePerson" />
<jsp:setProperty property="*" name="person"/>
${person.name}<br/>
${person.surname}<br/>

<%--<jsp:useBean id="pageDate" class="java.util.Date" />--%>
<%--<jsp:setProperty name="person" property="date" value="${pageDate.toInstant()}"/>--%>
<%--${person.date}<br/>--%>

</body>
</html>
