// script.js
function sendMessage() {
    const userInput = document.getElementById('user-input').value;
    if (userInput.trim() !== "") {
        const chatMessages = document.getElementById('chat-messages');
        
        // Legg til brukerens melding
        const userMessage = document.createElement('div');
        userMessage.classList.add('user-message');
        userMessage.textContent = `Du: ${userInput}`;
        chatMessages.appendChild(userMessage);

        // Simuler chatbotens svar
        setTimeout(() => {
            const botMessage = document.createElement('div');
            botMessage.classList.add('bot-message');
            botMessage.textContent = `Chatbot: Jeg har mottatt meldingen din!`;
            chatMessages.appendChild(botMessage);
            chatMessages.scrollTop = chatMessages.scrollHeight; // Rull ned til siste melding
        }, 1000);

        // Tøm input-feltet
        document.getElementById('user-input').value = "";
    }
}
