document.addEventListener("DOMContentLoaded", async function () {
    try {
        const response = await fetch("/footer.html"); // 🔹 Henter footeren dynamisk
        if (!response.ok) {
            throw new Error("Kunne ikke laste footer.html");
        }
        const footerHTML = await response.text();

        // 🔹 Finner `footer-wrapper` eller lager en hvis den ikke finnes
        let footerWrapper = document.querySelector(".footer-wrapper");
        if (!footerWrapper) {
            footerWrapper = document.createElement("div");
            footerWrapper.classList.add("footer-wrapper");
            document.body.appendChild(footerWrapper);
        }

        // 🔹 Legger footeren inn i wrapperen
        footerWrapper.innerHTML += footerHTML;

    } catch (error) {
        console.error("Feil ved innlasting av footer:", error);
    }
});
