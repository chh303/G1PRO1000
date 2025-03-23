// Open chat window
function openChat() {
    document.getElementById('chat-window').style.display = 'flex';
}

// Close chat window
function closeChat() {
    document.getElementById('chat-window').style.display = 'none';
}

// Send message function
function sendMessage() 
    var messageInput = document.getElementById('user-message');
    var messageText = messageInput.value;
    
    if (messageText.trim() !== "") {
        var messagesContainer = document.getElementById('messages');
        var messageElement = document.createElement('div');
        messageElement.classList.add('message');
        messageElement.textContent = messageText;
        messagesContainer.appendChild(messageElement);
        
        messageInput.value = ""; // Clear the input after sending the message
    }
}
