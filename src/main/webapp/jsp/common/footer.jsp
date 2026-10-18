    </div> <!-- End Main Container from header.jsp -->

    <footer class="mt-5 py-4 border-top text-center w-100" style="background-color: var(--card-bg); border-color: var(--card-border) !important; transition: background-color 0.3s ease;">
        <div class="container">
            <p class="mb-0 text-muted fw-medium">&copy; 2026 Campus Placement &amp; Internship Portal.</p>
            <p class="small text-muted mb-0">Built with Core Java &amp; MVC Architecture.</p>
        </div>
    </footer>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- ═══════════════════════════════════════════════════════════════
         THEME SWITCHER — Premium Pill Switch
         The CSS handles all visual state changes (knob position, icon
         crossfade) automatically via [data-theme] on <html>.
         JS only toggles the attribute, updates the label, and persists.
         ═══════════════════════════════════════════════════════════════ -->
    <script>
    (function () {
        'use strict';

        var STORAGE_KEY = 'cpPortalTheme';
        var html        = document.documentElement;
        var btn         = document.getElementById('theme-toggle-btn');
        var label       = document.getElementById('theme-label');

        /**
         * Apply a theme ('light' | 'dark').
         * CSS does all visual work via [data-theme] selector.
         */
        function applyTheme(theme) {
            html.setAttribute('data-theme',    theme);
            html.setAttribute('data-bs-theme', theme);

            if (btn) btn.setAttribute('aria-pressed', theme === 'light' ? 'false' : 'true');
            if (label) label.textContent = theme === 'light' ? 'Light' : 'Dark';

            try { localStorage.setItem(STORAGE_KEY, theme); } catch (e) {}
        }

        function toggleTheme() {
            var current = html.getAttribute('data-theme') || 'dark';
            applyTheme(current === 'dark' ? 'light' : 'dark');
        }

        // ── Initialise: sync label with the theme already applied by
        //    the anti-flash script in header.jsp ──
        var saved = 'dark';
        try { saved = localStorage.getItem(STORAGE_KEY) || 'dark'; } catch (e) {}
        applyTheme(saved);

        // ── On first ever visit, respect OS preference ──
        try {
            if (!localStorage.getItem(STORAGE_KEY)) {
                if (window.matchMedia && window.matchMedia('(prefers-color-scheme: light)').matches) {
                    applyTheme('light');
                }
            }
        } catch (e) {}

        // ── Wire button ──
        if (btn) btn.addEventListener('click', toggleTheme);

    })();
    </script>


</body>
</html>
