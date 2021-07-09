<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>统一认证登录平台</title>
</head>

<body>
<script type="text/javascript">
    window.opener.layer.closeAll('dialog');
    var token = ${token}
    console.debug(token)
    if (token.access_token) {
        window.opener.config.putToken(token);
        window.opener.location.replace('/index.html');
    } else {
        var index = window.opener.layer.getFrameIndex(window.name);
        window.opener.layer.alert(token.msg || "登录失败");
        window.opener.layer.close(index);
    }
    window.close();
</script>
</body>
</html>