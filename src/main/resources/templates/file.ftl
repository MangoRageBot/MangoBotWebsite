<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Text Viewer</title>
  <style>
    html, body {
      margin: 0;
      padding: 0;
      background: #121212;
      color: #e0e0e0;
      font-family: monospace;
      font-size: 12px;
      line-height: 1.2;
      overflow: auto;
      height: 100%;
    }
    .container {
      width: 100%;
      height: 100%;
      padding: 0;
      margin: 0;
      overflow: auto;
    }
    .line {
      display: flex;
      width: 100%;
      white-space: pre-wrap;
      word-wrap: break-word;
      margin: 0;
    }
    .line-number {
      width: 4em;
      text-align: right;
      margin-right: 1em;
      color: #888888;
      flex-shrink: 0;
    }
    .line-text {
      flex-grow: 1;
    }
  </style>
</head>
<body>
  <div class="container">
    <#list lines as line>
      <div class="line">
        <div class="line-number">${line?index + 1}</div>
        <div class="line-text">${line}</div>
      </div>
    </#list>
  </div>
</body>
</html>

