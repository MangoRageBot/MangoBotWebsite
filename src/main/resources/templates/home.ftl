<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MangoBot Main Page</title>
    <link rel="stylesheet" href="css/root.css">
    <link rel="stylesheet" href="css/header.css">
    <script src="js/header.js"></script>

    <meta name="og:title" content="MangoBot">
    <meta name="og:description" content="The Official MangoBot Discord Bot.">
    <meta name="og:image" content="https://mangobot.mangorage.org/pink-sheep.png" http-equiv="content-type">
    <meta name="og:url" content="https://mangobot.mangorage.org/file?id=568d44d8-b6bc-4394-a860-915fac5c085d&amp;target=0" http-equiv="content-type">
    <meta name="og:type" content="website">
</head>
<body>
    <header>
        <h1>MangoBot</h1>
        <nav>
            <ul>
                <#list headers as header>
                    <li><a href=${header.page()}>${header.text()}</a></li>
                </#list>
            </ul>
        </nav>
    </header>
<main>
    <section id="home">
        <h2>Welcome to Our Website</h2>
        <p>Explore our content and learn more about us.</p>
    </section>
    <section id="about">
        <h2>About Us</h2>
        <p><strong>MangoBot</strong> is a versatile Discord bot providing useful utilities, owned and developed by <strong>MangoRage</strong>.</p>
    </section>
    <section id="contact">
        <h2>Contact Us</h2>
        <p>Contact MangoRage at
            <a href="https://discord.mangorage.org/" target="_blank" rel="noopener noreferrer">
                discord
            </a>
        </p>
    </section>
</main>
</body>
</html>