// Last inn chatbot-HTML dynamisk
document.addEventListener("DOMContentLoaded", function () {
    fetch("chatbot.html")
        .then(response => response.text())
        .then(html => {
            document.getElementById("chatbot-placeholder").innerHTML = html;
        })
        .catch(error => console.error("Feil ved lasting av chatbot:", error));
});

// Toggle chat window
function toggleChat() {
    const chatWindow = document.getElementById("chat-window");
    if (!chatWindow) return; // sikkerhetssjekk hvis HTML ikke er lastet enda

    const currentDisplay = window.getComputedStyle(chatWindow).display;
    chatWindow.style.display = currentDisplay === "none" ? "flex" : "none";
}

// Close chat window
function closeChat() {
    const chatWindow = document.getElementById("chat-window");
    if (chatWindow) {
        chatWindow.style.display = "none";
    }
}

// Send message function
function sendMessage() {
    const messageInput = document.getElementById("user-message");
    const messageText = messageInput.value.trim();

    if (messageText !== "") {
        const messagesContainer = document.getElementById("messages");
        const messageElement = document.createElement("div");
        messageElement.classList.add("message");
        messageElement.textContent = messageText;
        messagesContainer.appendChild(messageElement);

        messageInput.value = "";

        // (Valgfritt) Scroll til bunnen
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }
}
