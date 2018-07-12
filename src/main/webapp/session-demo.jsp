<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Session demo</title>
</head>
<body>

${requestScope.get("out")}

<%--suppress JspAbsolutePathInspection --%>
<form action="/SessionController" method="post">
    <input name="paramName" placeholder="paramName"/><br/>
    <input name="paramValue" placeholder="paramValue"/><br/>
    <input type="submit" value="Send next HttpRequest"/>
</form>

</body>
</html>
