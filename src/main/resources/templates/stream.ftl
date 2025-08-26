<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>${title!"Untitled Video"}</title>
    <style>
        body { background: #000; color: #fff; text-align: center; padding: 2em; font-family: Arial, sans-serif; }
        video { width: 80%; max-width: 1000px; border: 2px solid #444; border-radius: 10px; background: #222; }
        h1 { margin-bottom: 1em; }
    </style>
</head>
<body>
    <h1>${title!"Untitled Video"}</h1>
    <video controls autoplay>
        <source src="/stream?v=${videoName}&mode=stream" type="video/mp4" />
        Your trash browser doesn’t support HTML5 video.
    </video>
</body>
</html>