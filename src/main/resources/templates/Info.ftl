<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/InfoServlet.css">
    <title>MangoBot Plugins</title>
    <meta name="og:title" content="MangoBot">
    <meta name="og:description" content="The Official MangoBot Discord Bot.">
    <meta name="og:image" content="https://mangobot.mangorage.org/pink-sheep.png">
    <meta name="og:url" content="https://mangobot.mangorage.org/file?id=568d44d8-b6bc-4394-a860-915fac5c085d&target=0">
    <meta name="og:type" content="website">
</head>
<body>
<div class="container">
    <h1 class="page-title">Installed Plugins:</h1>
    <#list plugins as plugin>
    <div class="plugin-item">
        <h2>Plugin Details:</h2>
        <div class="plugin-details">
                <p>Id: <span class="plugin-id">${plugin.id()!"Unknown"}</span></p>
                <p>Name: <span class="plugin-name">${plugin.name()!"Unknown"}</span></p>
                <p>Type: <span class="plugin-type">${plugin.type()!"Unknown"}</span></p>
                <p>Version: <span class="plugin-version">${plugin.version()!"Unknown"}</span></p>
        </div>
    </div>
    </#list>
</div>
</body>
</html>
