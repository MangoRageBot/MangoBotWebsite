<!DOCTYPE html>
<html>
    <head>
        <title>Commands</title>
        <style>
            body {
            font-family: monospace;
            background: #0f0f0f;
            color: #eee;
            padding: 20px;
            }

            .command-block {
            margin-bottom: 24px;
            border-bottom: 1px solid #333;
            padding-bottom: 12px;
            }

            .command {
            margin: 4px 0;
            }

            /* Command name tooltip */
            .cmd {
            cursor: help;
            position: relative;
            color: #ffd37f;
            }

            .cmd .tooltip {
            display: none;
            position: absolute;
            background: #222;
            border: 1px solid #555;
            padding: 6px;
            border-radius: 4px;
            top: 1.5em;
            left: 0;
            white-space: nowrap;
            z-index: 100;
            }

            .cmd:hover .tooltip {
            display: block;
            }

            /* Parameter tooltip */
            .param {
            cursor: help;
            position: relative;
            color: #7fd7ff;
            }

            .param.required {
            color: #ff7f7f;
            }

            .param .tooltip {
            display: none;
            position: absolute;
            background: #222;
            border: 1px solid #555;
            padding: 6px;
            border-radius: 4px;
            top: 1.5em;
            left: 0;
            white-space: nowrap;
            z-index: 100;
            }

            .param:hover .tooltip {
            display: block;
            }

        </style>
    </head>
    <body>

        <h1>Command List</h1>

        <#list commandDataList as data>
            <div class="command-block">

                <!-- Show CommandData.cmd -->
                <h2>${data.cmd()}</h2>

                <#list data.commandParts() as cmd>
                    <div class="command">

                        <!-- Command name with tooltip -->
                        <#if cmd.getCommandNotes()?has_content>
                            <span class="cmd">
                                ${cmd.getName()}
                                <span class="tooltip">
                                    <#list cmd.getCommandNotes() as note>
                                        <div>• ${note}</div>
                                    </#list>
                                </span>
                            </span>
                        <#else>
                            <span>${cmd.getName()}</span>
                        </#if>

                        <!-- Parameters -->
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

    </body>
</html>
