<!DOCTYPE html>
<html>
<head>
    <title>NeoVita</title>
</head>
<body>
<h1>NeoVita</h1><hr>
<h3>${user.name}</h3><br>
<b>Номер телефона: </b> ${user.phoneNumber}<br><br>
<h3>Объявления пользователя:</h3><br>
<#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
    </div>
    <#else>
    <h3>Этот пользователь ничего не продаёт!</h3>
</#list>
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>

</body>
</html>