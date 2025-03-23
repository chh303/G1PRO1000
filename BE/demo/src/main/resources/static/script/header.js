document.addEventListener("DOMContentLoaded", async function () {
    try {
        const response = await fetch("/header.html"); // 🔹 Henter header fra server
        if (!response.ok) {
            throw new Error("Kunne ikke laste header.html");
        }
        const headerHTML = await response.text();

        // 🔹 Legger til headeren i toppen av <body>
        const headerContainer = document.createElement("div");
        headerContainer.innerHTML = headerHTML;
        document.body.insertAdjacentElement("afterbegin", headerContainer);

        // 🔹 Sørger for at header.css lastes dynamisk
        const headerStyles = document.createElement("link");
        headerStyles.rel = "stylesheet";
        headerStyles.href = "/css/header.css";
        document.head.appendChild(headerStyles);

    } catch (error) {
        console.error("Feil ved innlasting av header:", error);
    }
});
