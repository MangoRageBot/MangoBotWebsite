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
      width: 100vw;
      height: 100vh;
      overflow: hidden;
    }
    .container {
      width: 100vw;
      height: 100vh;
      display: flex;
      flex-direction: column;
      box-sizing: border-box;
    }
    .log-header {
      padding: 5px 10px;
      border-bottom: 1px solid #444;
      background: #1e1e1e;
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-shrink: 0;
    }
    .filename {
      font-weight: bold;
      color: #87ceeb;
    }
    .raw-button {
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
    .log-box {
      flex-grow: 1;
      overflow: auto;
      white-space: nowrap;
      background: #1e1e1e;
      padding: 10px;
      box-sizing: border-box;
    }
    .line {
      display: flex;
      white-space: pre;
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
  </style>
</head>
<body>
  <div class="container">
    <div class="log-header">
      <div class="filename">📄 latest_1.log</div>
      <a href="#" class="raw-button">Raw</a>
    </div>
    <div class="log-box">
      <#list lines as line>
        <div class="line">
          <div class="line-number">${line?index + 1}</div>
          <div class="line-text">${line?html}</div>
        </div>
      </#list>
    </div>
  </div>
</body>
</html>
