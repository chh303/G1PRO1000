async function loginUser() {
    const userData = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    const response = await fetch("/api/users/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData),
        credentials: "include" // 🚀 Sørger for at session-cookie blir lagret
    });

    const responseData = await response.json();

    if (response.ok) {
        sessionStorage.setItem("userId", responseData.userId);
        sessionStorage.setItem("username", responseData.username);

        // 🔹 Oppdater header
        updateHeaderForUser();

        // 🔹 Omdiriger til poengsiden
        window.location.href = "index.html";
    } else {
        document.getElementById("message").innerText = responseData.error;
    }
}
