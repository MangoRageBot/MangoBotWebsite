<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Error ${code}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <style>
            body {
            margin: 0;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background: #0f172a;
            color: #e5e7eb;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            }

            .error-card {
            max-width: 500px;
            padding: 2.5rem;
            background: #020617;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
            text-align: center;
            }

            .error-code {
            font-size: 4rem;
            font-weight: 800;
            color: #f97316;
            margin-bottom: 0.5rem;
            }

            .error-message {
            font-size: 1.2rem;
            margin-bottom: 1.5rem;
            color: #cbd5f5;
            }

            .error-hint {
            font-size: 0.95rem;
            color: #94a3b8;
            }

            a {
            display: inline-block;
            margin-top: 2rem;
            padding: 0.6rem 1.2rem;
            border-radius: 6px;
            background: #f97316;
            color: #020617;
            text-decoration: none;
            font-weight: 600;
            }

            a:hover {
            background: #fb923c;
            }
        </style>
    </head>
    <body>

        <div class="error-card">
            <div class="error-code">${code}</div>

            <div class="error-message">
                <#if message?has_content>
                    ${message}
                <#else>
                    Something went wrong.
                </#if>
            </div>

            <div class="error-hint">
                    This error was handled by <code>${handler}</code>
            </div>
                <a href="${backUrl}">Go back</a>
        </div>

    </body>
</html>
