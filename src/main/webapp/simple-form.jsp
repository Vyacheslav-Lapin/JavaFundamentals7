<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="/bean-mapping" method="post">
    <input type="hidden" name="command" value="naming"/>
    <input name="name" placeholder="Your name"/><br/>
    <input name="surname" placeholder="Your surname"/><br/>
    <input type="submit" value="Отправить"/>
</form>

</body>
</html>
