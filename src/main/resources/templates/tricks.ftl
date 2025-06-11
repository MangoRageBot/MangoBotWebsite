<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mangobot Tricks</title>
    <link rel="stylesheet" href="${self.getStyles()}">
    <link rel="stylesheet" href="css/header.css">
    <script src="js/header.js"></script>
    <style>
        .page-body {
            margin: 0;
            font-family: sans-serif;
        }

        .container {
            display: flex;
            flex-direction: row;
            gap: 20px;
            padding: 20px;
        }

        .trick-list {
            width: 250px;
            border-right: 1px solid #ccc;
            padding-right: 15px;
        }

        .trick-list ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .trick-list li {
            margin-bottom: 8px;
        }

        .trick-list a {
            text-decoration: none;
            color: #007bff;
        }

        .trick-details {
            flex-grow: 1;
        }

        textarea {
            width: 100%;
            box-sizing: border-box;
        }

        .meta-item {
            margin: 4px 0;
        }

        .section-title {
            margin-top: 20px;
        }
    </style>
</head>
<body class="page-body">

<header>
    <h1>MangoBot</h1>
    <nav>
        <ul>
            <#list headers as header>
                <li><a href="${header.page()}">${header.text()}</a></li>
            </#list>
        </ul>
    </nav>
</header>

<div class="container">

    <#if guildId??>
        <div class="trick-list">
            <h3>Tricks</h3>
            <#if tricks?? && tricks?size gt 0>
                <ul>
                    <#list tricks as trickItem>
                        <li>
                            <a href="?guildId=${guildId}&trickId=${trickItem.getTrickID()}">
                                ${trickItem.getTrickID()} (${trickItem.getType()})
                            </a>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p>No tricks found. Maybe don't run a bot on a ghost town server?</p>
            </#if>
        </div>

        <div class="trick-details">
            <#if trickId?? && trick??>
                <h2 class="trick-title">Trick Details</h2>
                <div class="trick-info">
                    <p class="trick-id">ID: ${trick.getTrickID()}</p>
                    <p class="trick-type">Type: ${trick.getType()}</p>
                    <p class="trick-guild">Guild: ${trick.getGuildID()?c} ${guildName}</p>
                </div>

                <#switch trick.getType()>
                    <#case "ALIAS">
                        <h3 class="section-title">Alias Target</h3>
                        <div class="trick-alias">${trick.getAliasTarget()}</div>
                        <#break>
                    <#case "NORMAL">
                        <h3 class="section-title">Content</h3>
                        <div class="trick-content">
                            <textarea cols="50" rows="20" wrap="hard" readonly>${trick.getContent()}</textarea>
                        </div>
                        <#break>
                    <#case "SCRIPT">
                        <h3 class="section-title">Script</h3>
                        <div class="trick-script">
                            <textarea cols="50" rows="20" wrap="hard" readonly>${trick.getScript()}</textarea>
                        </div>
                        <#break>
                    <#default>
                        <p>Unknown trick type. What kind of junk are you storing?</p>
                </#switch>

                <div class="trick-meta">
                    <p class="meta-item">Trick Owner: ${trick.getOwnerID()?c} ${ownerName}</p>
                    <p class="meta-item">Last Edited By: ${trick.getLastUserEdited()?c} ${lastUserName}</p>
                    <p class="meta-item">Created: ${created}</p>
                    <p class="meta-item">Last Edited: ${lastEdited}</p>
                    <p class="meta-item">Times Used: ${trick.getTimesUsed()?c}</p>
                    <p class="meta-item">Locked: ${trick.isLocked()?string}</p>
                    <p class="meta-item">Embeds Suppressed: ${trick.isSuppressed()?string}</p>
                </div>
            <#else>
                <p>Select a trick from the list on the left. Don’t just sit there like a stale donut.</p>
            </#if>
        </div>
    <#else>
        <!-- Guild Selection Page -->
        <h2 class="title">Select Guild</h2>
        <form method="GET" action="/trick" class="form-group">
            <label for="guildId">Choose a Guild:</label>
            <select name="guildId" id="guildId" class="select-input">
                <#list guilds as guild>
                    <option value="${guild.id()}">${guild.name()}</option>
                </#list>
            </select>
            <button type="submit" class="btn btn-primary">Enter!</button>
        </form>
    </#if>

</div>
</body>
</html>
