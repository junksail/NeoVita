<!DOCTYPE html>
<html>
<head>
    <title>NeoVito</title>
</head>
<body>
<h1>Neovito</h1>
<hr>
<h4>Информация о товаре</h4>
<b>Название товара: </b>${product.title}<br>
<b>Описание: </b>${product.description}<br>
<b>Цена: </b>${product.price}<br>
<b>Город: </b>${product.city}<br>
<b>Автор: </b>${product.author}<br>
<hr>
<form action="/product/delete/${product.id}" method="post">
    <input type = "submit" value = "Удалить"/>
</form>
</body>
</html>