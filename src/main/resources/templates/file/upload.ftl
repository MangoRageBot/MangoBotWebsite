<!DOCTYPE html>
<html lang="en">
<head>
    <title>File Upload Page</title>
    <link rel="stylesheet" href="${self.getStyles()}">
    <link rel="stylesheet" href="css/header.css">
    <script src="js/header.js"></script>
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

    <h1>Upload a File</h1>

    <form method="post" action="/upload" enctype="multipart/form-data">
        <div id="drop-area">
            <p>Drag and drop a file here or click to select</p>
            <input type="file" id="file-input" name="file" />
        </div>
        <br />
        <input type="submit" id="upload" value="Upload" />
    </form>

    <script src="/js/dragDropUpload.js"></script>
</body>
</html>
