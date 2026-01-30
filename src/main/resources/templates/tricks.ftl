<!DOCTYPE html>
<html lang="en">
<script src="js/main.js" defer></script>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <a href="/home" class="nav-logo">
                <span class="logo-icon">
                    <img src="images/favicon.ico" alt="Logo" width="48">
                </span>
                <span class="logo-text">MangoBot</span>
            </a>
            <ul class="nav-menu">
                <#list headers as header>
                    <li><a href="${header.page()}" class="nav-link<#if header.active()!false> active</#if>">${header.text()}</a></li>
                </#list>
            </ul>
            <a href="https://discord.mangorage.org/" target="_blank" class="nav-cta">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M20.317 4.37a19.791 19.791 0 0 0-4.885-1.515.074.074 0 0 0-.079.037c-.21.375-.444.864-.608 1.25a18.27 18.27 0 0 0-5.487 0 12.64 12.64 0 0 0-.617-1.25.077.077 0 0 0-.079-.037A19.736 19.736 0 0 0 3.677 4.37a.07.07 0 0 0-.032.027C.533 9.046-.32 13.58.099 18.057a.082.082 0 0 0 .031.057 19.9 19.9 0 0 0 5.993 3.03.078.078 0 0 0 .084-.028 14.09 14.09 0 0 0 1.226-1.994.076.076 0 0 0-.041-.106 13.107 13.107 0 0 1-1.872-.892.077.077 0 0 1-.008-.128 10.2 10.2 0 0 0 .372-.292.074.074 0 0 1 .077-.01c3.928 1.793 8.18 1.793 12.062 0a.074.074 0 0 1 .078.01c.12.098.246.198.373.292a.077.077 0 0 1-.006.127 12.299 12.299 0 0 1-1.873.892.077.077 0 0 0-.041.107c.36.698.772 1.362 1.225 1.993a.076.076 0 0 0 .084.028 19.839 19.839 0 0 0 6.002-3.03.077.077 0 0 0 .032-.054c.5-5.177-.838-9.674-3.549-13.66a.061.061 0 0 0-.031-.03zM8.02 15.33c-1.183 0-2.157-1.085-2.157-2.419 0-1.333.956-2.419 2.157-2.419 1.21 0 2.176 1.096 2.157 2.42 0 1.333-.956 2.418-2.157 2.418zm7.975 0c-1.183 0-2.157-1.085-2.157-2.419 0-1.333.955-2.419 2.157-2.419 1.21 0 2.176 1.096 2.157 2.42 0 1.333-.946 2.418-2.157 2.418z"/>
                </svg>
                Join Discord
            </a>
            <button class="nav-toggle" aria-label="Toggle menu">
                <span></span>
                <span></span>
                <span></span>
            </button>
        </div>
    </nav>

    <main>
        <section class="trick-hero">
            <div class="container">
                <div class="breadcrumb">
                    <a href="/home">Home</a>
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="9 18 15 12 9 6"/>
                    </svg>
                    <a href="/tricks">Guilds</a>
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="9 18 15 12 9 6"/>
                    </svg>
                    <a href="/tricks?guildId=${selectedGuildId!""}">${guildName!"Guild"}</a>
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="9 18 15 12 9 6"/>
                    </svg>
                    <span>${trick.getName()!"Trick"}</span>
                </div>
            </div>
        </section>

        <section class="trick-content">
            <div class="container">
                <div class="trick-layout">
                    <div class="trick-main">
                        <div class="trick-header-card">
                            <div class="trick-title-row">
                                <div class="trick-icon">
                                    <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                        <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/>
                                    </svg>
                                </div>
                                <div class="trick-title-info">
                                    <h1>${trick.getName()!"Unknown Trick"}</h1>
                                    <div class="trick-badges">
                                        <span class="badge badge-type">${trick.getType()!"NORMAL"}</span>
                                        <#if trick.isLocked()!false>
                                            <span class="badge badge-locked">
                                                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                                    <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                                                    <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                                                </svg>
                                                Locked
                                            </span>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="trick-message-card">
                            <h2>
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
                                </svg>
                                Message Content
                            </h2>
                            <div class="message-content">
                                <pre><code>${trick.getContent()!"No content"}</code></pre>
                            </div>
                        </div>
                    </div>

                    <aside class="trick-sidebar">

                        <#list categories as category>
                            <div class="sidebar-card">
                                <h3>${category.name()}</h3>
                                <div class="detail-list">
                                    <#list category.info() as item>
                                        <div class="detail-item">
                                            <span class="detail-label">${item.name()}</span>
                                            <span class="detail-value${item.bold()?then(' detail-bold','')}">
                                                ${item.value()}
                                            </span>
                                        </div>
                                    </#list>
                                </div>
                            </div>
                        </#list>

                        <a href="/tricks?guildId=${selectedGuildId!""}" class="btn btn-secondary btn-full">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M19 12H5M12 19l-7-7 7-7"/>
                            </svg>
                            Back to Guild
                        </a>
                    </aside>
                </div>
            </div>
        </section>
    </main>

    <footer class="footer">
        <div class="container">
            <div class="footer-content">
                <div class="footer-brand">
                    <span class="logo-icon">
                        <img src="images/favicon.ico" alt="Logo" width="48">
                    </span>
                    <span class="logo-text">MangoBot</span>
                </div>
                <p class="footer-text">Developed with passion by MangoRage</p>
            </div>
        </div>
    </footer>
</body>
</html>
