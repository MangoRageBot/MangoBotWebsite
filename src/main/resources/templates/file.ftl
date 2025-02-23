<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Log Viewer</title>
  <style>
    html, body {
      margin: 0;
      padding: 0;
      background: #121212;
      color: #e0e0e0;
      font-family: monospace;
      font-size: 12px;
      line-height: 1.4;
      overflow: auto;
      height: 100%;
    }
    .container {
      width: 100%;
      height: 100%;
      padding: 10px;
      box-sizing: border-box;
      overflow: auto;
    }
    .line {
      display: flex;
      white-space: pre-wrap;
      word-wrap: break-word;
    }
    .line-number {
      width: 3.5em;
      text-align: right;
      margin-right: 1em;
      color: #888888;
      flex-shrink: 0;
    }
    .line-text {
      flex-grow: 1;
      color: #e0e0e0;
    }
    .filename {
      font-weight: bold;
      color: #87ceeb;
      margin-bottom: 10px;
    }
    .log-header {
      padding: 5px;
      border-bottom: 1px solid #444;
      margin-bottom: 5px;
    }
    .raw-button {
      float: right;
      background: #333;
      color: #e0e0e0;
      border: 1px solid #555;
      padding: 2px 6px;
      font-size: 12px;
      cursor: pointer;
      text-decoration: none;
    }
    .raw-button:hover {
      background: #555;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="log-header">
      Automatically made from MangoBot.
    </div>
    <div class="filename">📄 latest_1.log</div>
    <#list lines as line>
      <div class="line">
        <div class="line-number">${line?index + 1}</div>
        <div class="line-text">${line}</div>
      </div>
    </#list>
  </div>
</body>
</html>
