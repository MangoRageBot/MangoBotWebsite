<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Commands - MangoBot</title>
    <link rel="icon" type="image/x-icon" href="images/favicon.ico">
    <link rel="stylesheet" href="css/main.css?v=${.now?long}">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>
<nav class="navbar">
    <div class="nav-container">
        <a href="/home" class="nav-logo">
                <span class="logo-icon">
                    <img src="images/favicon.ico" alt="Logo" width="48">
                </span>
            <span class="logo-text">MangoBot</span>
        </a>
        <ul class="nav-menu">
            <#list headers as header>
                <li><a href="${header.page()}" class="nav-link<#if header.active()!false> active</#if>">${header.text()}</a></li>
            </#list>
        </ul>
        <a href="https://discord.mangorage.org/" target="_blank" class="nav-cta">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                <path d="M20.317 4.37a19.791 19.791 0 0 0-4.885-1.515.074.074 0 0 0-.079.037c-.21.375-.444.864-.608 1.25a18.27 18.27 0 0 0-5.487 0 12.64 12.64 0 0 0-.617-1.25.077.077 0 0 0-.079-.037A19.736 19.736 0 0 0 3.677 4.37a.07.07 0 0 0-.032.027C.533 9.046-.32 13.58.099 18.057a.082.082 0 0 0 .031.057 19.9 19.9 0 0 0 5.993 3.03.078.078 0 0 0 .084-.028 14.09 14.09 0 0 0 1.226-1.994.076.076 0 0 0-.041-.106 13.107 13.107 0 0 1-1.872-.892.077.077 0 0 1-.008-.128 10.2 10.2 0 0 0 .372-.292.074.074 0 0 1 .077-.01c3.928 1.793 8.18 1.793 12.062 0a.074.074 0 0 1 .078.01c.12.098.246.198.373.292a.077.077 0 0 1-.006.127 12.299 12.299 0 0 1-1.873.892.077.077 0 0 0-.041.107c.36.698.772 1.362 1.225 1.993a.076.076 0 0 0 .084.028 19.839 19.839 0 0 0 6.002-3.03.077.077 0 0 0 .032-.054c.5-5.177-.838-9.674-3.549-13.66a.061.061 0 0 0-.031-.03zM8.02 15.33c-1.183 0-2.157-1.085-2.157-2.419 0-1.333.956-2.419 2.157-2.419 1.21 0 2.176 1.096 2.157 2.42 0 1.333-.956 2.418-2.157 2.418zm7.975 0c-1.183 0-2.157-1.085-2.157-2.419 0-1.333.955-2.419 2.157-2.419 1.21 0 2.176 1.096 2.157 2.42 0 1.333-.946 2.418-2.157 2.418z"/>
            </svg>
            Join Discord
        </a>
        <button class="nav-toggle" aria-label="Toggle menu">
            <span></span>
            <span></span>
            <span></span>
        </button>
    </div>
</nav>
<div class="page-header">
    <div class="container">
        <div class="header-content">
            <span class="section-badge">Documentation</span>
            <h1>Command List</h1>
            <p class="header-subtitle">
                Complete reference of all available commands and their parameters
            </p>
        </div>
    </div>
</div>
<div class="commands-content">
    <div class="container">
        <#list commandDataList as data>
            <div class="command-block">
                <h2>${data.cmd()}</h2>

                <#list data.commandParts() as cmd>
                    <div class="command">
                        <#if cmd.getCommandNotes()?has_content>
                            <span class="cmd">
                                    ${cmd.getName()}
                                    <span class="tooltip">
                                        <#list cmd.getCommandNotes() as note>
                                            <div>${note}</div>
                                        </#list>
                                    </span>
                                </span>
                        <#else>
                            <span class="cmd">${cmd.getName()}</span>
                        </#if>

                        <#list cmd.getParameters() as p>
                            <span class="param <#if p.isRequired()>required</#if>">
                                    <#if p.isRequired()>
                                        &lt;${p.getName()}&gt;
                                    <#else>
                                        [${p.getName()}]
                                    </#if>

                                    <span class="tooltip">
                                        <div><b>Name:</b> ${p.getName()}</div>
                                        <div><b>Type:</b> ${p.getType()}</div>
                                        <div><b>Argument Type:</b> ${p.getArgumentType()}</div>
                                        <div><b>Description:</b> ${p.getDescription()}</div>
                                        <div><b>Suggestions:</b> ${p.getSuggestions()}</div>
                                        <div><b>Required:</b> ${p.isRequired()?string("yes","no")}</div>
                                    </span>
                                </span>
                        </#list>
                    </div>
                </#list>
            </div>
        </#list>
    </div>
</div>
<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <div class="footer-brand">
                    <span class="logo-icon">
                        <img src="images/favicon.ico" alt="Logo" width="48">
                    </span>
                <span class="logo-text">MangoBot</span>
            </div>
            <p class="footer-text">Developed with passion by MangoRage</p>
        </div>
    </div>
</footer>
<script src="js/main.js" defer></script>
</body>
</html>
</html>