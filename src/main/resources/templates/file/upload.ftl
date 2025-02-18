<!DOCTYPE html>
<html lang="en">
<head>
    <title>File Upload Page</title>
    <link rel="stylesheet" href="${self.getStyles()}"> <!-- Use external CSS if desired -->
</head>
<body>
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
