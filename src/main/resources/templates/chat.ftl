<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat</title>
    <link rel="stylesheet" href="${self.getStyles()}">
</head>
<body>
    <div class="chat-container">
        <h2>Chat Room</h2>
        <div class="messages">
            <#list messages as message>
                <p>${message}</p>
            </#list>
        </div>
        <form action="/chat" method="post">
            <#if !hasUsername>
                <input type="text" name="username" placeholder="Enter your name" required>
            <#else>
                <input type="hidden" name="username" value="${username}">
            </#if>
            <input type="text" name="message" placeholder="Type your message..." required>
            <button type="submit">Send</button>
        </form>
    </div>
</body>
</html>
