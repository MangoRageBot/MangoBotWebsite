<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Guilds - MangoBot</title>
    <link rel="icon" type="image/png" href="images/favicon.png">
    <link rel="stylesheet" href="css/main.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <script src="js/main.js" defer></script>
    <meta name="og:title" content="MangoBot - Guilds">
    <meta name="og:description" content="Explore tricks and commands from MangoBot community servers.">
    <meta name="og:image" content="https://mangobot.mangorage.org/pink-sheep.png">
    <meta name="og:url" content="https://mangobot.mangorage.org/guilds">
    <meta name="og:type" content="website">
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <a href="/home" class="nav-logo">
                <span class="logo-icon">🥭</span>
                <span class="logo-text">MangoBot</span>
            </a>
            <ul class="nav-menu">
                <#list headers as header>
                    <li><a href="${header.page()}" class="nav-link<#if header.active!false> active</#if>">${header.text()}</a></li>
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
        <section class="guilds-hero">
            <div class="container">
                <div class="section-header">
                    <span class="section-badge">Community</span>
                    <h1 class="section-title">Server Guilds</h1>
                    <p class="section-subtitle">Explore tricks and commands from our community servers</p>
                </div>
            </div>
        </section>

        <section class="guilds-content">
            <div class="container">
                <div class="guild-selector">
                    <label for="guild-select" class="guild-label">Choose a Guild</label>
                    <div class="select-wrapper">
                        <select id="guild-select" class="guild-select" onchange="selectGuild(this.value)">
                            <option value="">Select a server...</option>
                            <#list guilds as guild>
                                <option value="${guild.getId()}" <#if selectedGuild?? && selectedGuild.getId() == guild.getId()>selected</#if>>${guild.getName()!"Unknown Guild"}</option>
                            </#list>
                        </select>
                        <svg class="select-arrow" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="6 9 12 15 18 9"/>
                        </svg>
                    </div>
                </div>

                <#if selectedGuild??>
                <div id="guild-info" class="guild-info">
                    <div class="guild-header">
                        <div class="guild-avatar" id="guild-avatar">
                            <#if selectedGuild.getIconUrl()??>
                                <img src="${selectedGuild.getIconUrl()}" alt="${selectedGuild.getName()}" />
                            <#else>
                                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                    <circle cx="9" cy="7" r="4"/>
                                    <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"/>
                                </svg>
                            </#if>
                        </div>
                        <div class="guild-details">
                            <h2 id="guild-name">${selectedGuild.getName()!"Unknown Guild"}</h2>
                            <p id="guild-description">${selectedGuild.getMemberCount()!0} members</p>
                        </div>
                    </div>

                    <div class="tricks-section">
                        <h3 class="tricks-title">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/>
                            </svg>
                            Available Tricks (${tricks?size})
                        </h3>
                        <div id="tricks-list" class="tricks-list">
                            <#if tricks?? && (tricks?size > 0)>
                                <#list tricks as trick>
                                <a href="/trick?guildId=${selectedGuild.getId()}&trickId=${trick.getId()!trick.getName()}" class="trick-item trick-link">
                                    <span class="trick-name">${trick.getName()!"unknown"}</span>
                                    <span class="trick-meta">
                                        <span class="trick-type">${trick.getType()!"NORMAL"}</span>
                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                            <path d="M9 18l6-6-6-6"/>
                                        </svg>
                                    </span>
                                </a>
                                </#list>
                            <#else>
                                <div class="empty-tricks">
                                    <p>No tricks available for this guild</p>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
                <#else>
                <div id="empty-state" class="empty-state">
                    <div class="empty-icon">
                        <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                            <circle cx="12" cy="12" r="10"/>
                            <path d="M8 14s1.5 2 4 2 4-2 4-2"/>
                            <line x1="9" y1="9" x2="9.01" y2="9"/>
                            <line x1="15" y1="9" x2="15.01" y2="9"/>
                        </svg>
                    </div>
                    <h3>Select a Guild</h3>
                    <p>Choose a server from the dropdown above to view available tricks and commands</p>
                </div>
                </#if>
            </div>
        </section>

        <section class="guilds-cta">
            <div class="container">
                <div class="cta-card">
                    <h2>Want Your Server Featured?</h2>
                    <p>Join our Discord community and add MangoBot to your server to get started</p>
                    <a href="https://discord.mangorage.org/" target="_blank" class="btn btn-primary">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M20.317 4.37a19.791 19.791 0 0 0-4.885-1.515.074.074 0 0 0-.079.037c-.21.375-.444.864-.608 1.25a18.27 18.27 0 0 0-5.487 0 12.64 12.64 0 0 0-.617-1.25.077.077 0 0 0-.079-.037A19.736 19.736 0 0 0 3.677 4.37a.07.07 0 0 0-.032.027C.533 9.046-.32 13.58.099 18.057a.082.082 0 0 0 .031.057 19.9 19.9 0 0 0 5.993 3.03.078.078 0 0 0 .084-.028 14.09 14.09 0 0 0 1.226-1.994.076.076 0 0 0-.041-.106 13.107 13.107 0 0 1-1.872-.892.077.077 0 0 1-.008-.128 10.2 10.2 0 0 0 .372-.292.074.074 0 0 1 .077-.01c3.928 1.793 8.18 1.793 12.062 0a.074.074 0 0 1 .078.01c.12.098.246.198.373.292a.077.077 0 0 1-.006.127 12.299 12.299 0 0 1-1.873.892.077.077 0 0 0-.041.107c.36.698.772 1.362 1.225 1.993a.076.076 0 0 0 .084.028 19.839 19.839 0 0 0 6.002-3.03.077.077 0 0 0 .032-.054c.5-5.177-.838-9.674-3.549-13.66a.061.061 0 0 0-.031-.03z"/>
                        </svg>
                        Join Discord
                    </a>
                </div>
            </div>
        </section>
    </main>

    <footer class="footer">
        <div class="container">
            <div class="footer-content">
                <div class="footer-brand">
                    <span class="logo-icon">🥭</span>
                    <span class="logo-text">MangoBot</span>
                </div>
                <p class="footer-text">Developed with passion by MangoRage</p>
                <div class="footer-links">
                    <#list headers as header>
                        <a href="${header.page()}">${header.text()}</a>
                    </#list>
                </div>
            </div>
        </div>
    </footer>

    <script>
        function selectGuild(guildId) {
            if (guildId) {
                window.location.href = '/guilds?guild=' + encodeURIComponent(guildId);
            } else {
                window.location.href = '/guilds';
            }
        }
    </script>
</body>
</html>
