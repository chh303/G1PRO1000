function loadHeader() {
    // Henter header.html filen
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            // Legger til headeren på toppen av body
            document.body.insertAdjacentHTML('afterbegin', data);

            // Legger til headerens CSS-fil dynamisk
            const headerStyles = document.createElement('link');
            headerStyles.rel = 'stylesheet';
            headerStyles.href = 'css/header.css';
            document.head.appendChild(headerStyles);

            // Legger til headerens JavaScript-fil dynamisk (hvis nødvendig)
            const headerScript = document.createElement('script');
            headerScript.src = 'header.js';
            headerScript.defer = true;
            document.body.appendChild(headerScript);
        })
        .catch(error => {
            console.error("Feil ved innlasting av header:", error);
        });
}

// Kall funksjonen når DOM er klar
document.addEventListener("DOMContentLoaded", loadHeader);
