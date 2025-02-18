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
                <li><a href=/>Home</a></li>
                <li><a href=/info>Info</a></li>
                <li><a href=/upload>Upload</a></li>
                <li><a href=/trick>Tricks</a></li>
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
