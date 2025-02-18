<!DOCTYPE html>
<html>
<head>
    <title>File Management</title>
    <link rel="stylesheet" href="${self.getStyles()}">
</head>
<body>
    <div class="container">
        <h1 class="title">File Management</h1>
        <div class="url-section">
            <input type="text" value="https://mangobot.mangorage.org/file?id=${id}" id="copyInput" readonly />
            <button class="copy-button" onclick="document.getElementById('copyInput').select(); document.execCommand('copy');">
                Click to copy URL to clipboard
            </button>
        </div>

        <#if isOwner>
            <div class="owner-actions">
                <a href="/file?id=${id}&delete=1">Delete File</a>
            </div>
        </#if>

        <#list config.targetList() as targetFile>
            <div class="target-section">
                <h4>
                    <a href="/file?id=${id}&target=${targetFile.index()}">${targetFile.name()}</a>
                </h4>
                <a href="/file?id=${id}&target=${targetFile.index()}&dl=1">Download</a>

                <#if isOwner>
                    <a href="/file?id=${id}&target=${targetFile.index()}&delete=1">Delete</a>
                </#if>
            </div>
        </#list>
    </div>
</body>
</html>