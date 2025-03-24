document.addEventListener("DOMContentLoaded", async function () {
    try {
        const response = await fetch("/header.html");
        if (!response.ok) throw new Error("Kunne ikke laste header.html");

        const headerHTML = await response.text();
        const headerContainer = document.createElement("div");
        headerContainer.innerHTML = headerHTML;
        document.body.insertAdjacentElement("afterbegin", headerContainer);
        
        await updateHeaderForUser();
    } catch (error) {
        console.error("❌ Feil ved innlasting av header:", error);
    }
});

async function updateHeaderForUser() {
    try {
        const response = await fetch("/api/users/session", { credentials: "include" });
        if (!response.ok) {
            console.log("❌ Bruker ikke logget inn.");
            return;
        }

        const userData = await response.json();
        if (!userData.loggedIn) return;

        console.log(`✅ Bruker er innlogget: ${userData.username}`);

        const buttonsContainer = document.querySelector(".buttons");
        if (!buttonsContainer) {
            console.error("❌ Fant ikke .buttons i headeren!");
            return;
        }

        buttonsContainer.innerHTML = `
        <button class="nav-btn user-btn">${userData.username}</button>
        <button class="nav-btn logout-btn">Logg ut</button>
    `;
    

        document.querySelector(".logout-btn").addEventListener("click", async () => {
            await fetch("/api/users/logout", { method: "POST", credentials: "include" });
            localStorage.clear();
            location.reload();
        });
    } catch (error) {
        console.log("❌ Bruker ikke logget inn.");
    }
}
