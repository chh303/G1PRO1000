// quiz-script.js

// ------------------------- Spørsmålsdata -------------------------
const quizData = [
  { question: "Hva handler grønn kode hovedsakelig om?", options: ["Å bruke mange farger i design", "Å skrive kode med mindre strømforbruk", "Å bruke nyeste teknologi", "Å kode kun for grønne bedrifter"], correct: "Å skrive kode med mindre strømforbruk" },
  { question: "Hvilket tiltak bidrar mest til energieffektiv frontend?", options: ["Bruk av store bakgrunnsbilder", "Animasjoner i høy oppløsning", "Lazy loading av bilder og innhold", "Autoplay av videoer"], correct: "Lazy loading av bilder og innhold" },
  { question: "Hva kan du gjøre i backend for å redusere miljøavtrykket?", options: ["Øke antall serverkall", "Bruke polling i stedet for events", "Cache ofte brukte data", "Bruke større databaser"], correct: "Cache ofte brukte data" },
  { question: "Hvilken teknologi er kjent for å bruke fornybar energi i sine datasentre?", options: ["AWS", "Google Cloud", "DigitalOcean", "GitHub"], correct: "Google Cloud" },
  { question: "Hvilket av disse prinsippene er IKKE en del av grønn koding?", options: ["Redusere dataoverføring", "Optimalisere algoritmer", "Bruke så mye RAM som mulig", "Minimere lastetid"], correct: "Bruke så mye RAM som mulig" },
];

const energibrukData = [
  { question: "Hva bruker mest energi i en nettside?", options: ["Store bilder og videoer", "Enkel tekst", "Fargerike knapper", "Responsiv design"], correct: "Store bilder og videoer" },
  { question: "Hva kan redusere strømforbruket i UI?", options: ["Bruk av mørk modus", "Flere fonter", "GIF-animasjoner", "Video i bakgrunnen"], correct: "Bruk av mørk modus" },
  { question: "Hva er energibesparende?", options: ["Komprimere bilder", "Bruke store JavaScript-bibliotek", "Autoplay av video", "Lysmodus alltid"], correct: "Komprimere bilder" },
  { question: "Hva fører til unødvendig strømforbruk?", options: ["Tung animasjon", "Systemfonter", "Minifisert CSS", "SVG-ikoner"], correct: "Tung animasjon" },
  { question: "Hva reduserer overføring og strømforbruk?", options: ["Lazy loading", "Inline CSS", "Store logoer", "Store skrifttyper"], correct: "Lazy loading" },
];

const effektivitetData = [
  { question: "Hva er en god praksis for effektiv looping?", options: ["Bruke nested loops alltid", "Foretrekke while fremfor for", "Unngå unødvendige gjentakelser", "Iterere med rekursjon uansett"], correct: "Unngå unødvendige gjentakelser" },
  { question: "Hva bidrar til bedre ytelse i datastrukturer?", options: ["Bruke ArrayList for alt", "Velge struktur basert på bruk", "Alltid bruke HashMap", "Unngå datastrukturer"], correct: "Velge struktur basert på bruk" },
  { question: "Hvordan bør man behandle store datamengder?", options: ["Bruke én stor løkke", "Lagre alt i minnet", "Batch-prosessere", "Gjenta spørringer konstant"], correct: "Batch-prosessere" },
  { question: "Hva er best for ytelse?", options: ["Å logge alt", "Korte funksjoner", "Tung logging i produksjon", "Unngå refaktorering"], correct: "Korte funksjoner" },
  { question: "Hva er et tegn på ineffektiv kode?", options: ["Kode med kommentarer", "Gjentatt logikk", "Enkle funksjoner", "Gjenbrukbar kode"], correct: "Gjentatt logikk" },
];

const designvalgData = [
  { question: "Hva er viktig i bærekraftig design?", options: ["Bruk av mange fonter", "Minimalistisk layout", "Store bilder", "Animerte bakgrunner"], correct: "Minimalistisk layout" },
  { question: "Hva reduserer energibruk i UI-design?", options: ["Video i bakgrunnen", "Dynamisk lastet innhold", "Flash-elementer", "Ukomprimert grafikk"], correct: "Dynamisk lastet innhold" },
  { question: "Hvordan påvirker farger strømforbruk?", options: ["Lyse farger bruker mindre", "Mørk modus kan spare strøm", "Farger påvirker ikke", "Flerfarget bakgrunn er best"], correct: "Mørk modus kan spare strøm" },
  { question: "Hva er best praksis for bruk av media?", options: ["HD-videoer automatisk", "Kompakte bilder og SVG", "GIF-er som bakgrunn", "Fullskjerm-animasjoner"], correct: "Kompakte bilder og SVG" },
  { question: "Hva er en ulempe med overkomplisert UI?", options: ["Det bruker mindre båndbredde", "Det er raskere å utvikle", "Det krever mer energi", "Det er enklere å forstå"], correct: "Det krever mer energi" },
];
// ------------------------- Tilstand -------------------------
let currentQuestionIndex = 0;
let energibrukIndex = 0;
let effektivitetIndex = 0;
let designvalgIndex = 0;
let loggedInUserId = null;
let answered = false;

// ------------------------- Autentisering -------------------------
async function checkSession() {
  try {
    const res = await fetch("/api/users/session", {
      method: "GET",
      credentials: "include"
    });
    if (!res.ok) throw new Error("Ikke logget inn");
    const data = await res.json();
    loggedInUserId = data.userId;
  } catch {
    alert("Du må være logget inn for å ta quizen.");
    window.location.href = "login.html";
  }
}

// ------------------------- Poengsystem -------------------------
async function increaseScore() {
  try {
    await fetch("/api/score/update", {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        userId: loggedInUserId,
        score: 50,
        achievements: ["Quiz Bonus"]
      })
    });
  } catch (err) {
    console.error("Kunne ikke oppdatere score", err);
  }
}

function showScorePopup(containerId) {
  const popup = document.querySelector(`#${containerId} .score-popup`);
  if (popup) {
    popup.classList.add("show");
    setTimeout(() => popup.classList.remove("show"), 500);
  }
}

// ------------------------- Grønn Kode -------------------------
function loadGronnKodeQuestion() {
  const q = quizData[currentQuestionIndex];
  const container = document.getElementById("quiz-container");

  container.innerHTML = `
    <div class="score-popup">+50</div>
    <h3>${q.question}</h3>
    <div class="quiz-options">
      ${q.options.map(opt => `<button class="quiz-option-btn" onclick="checkAnswer(this, '${q.correct.replace(/'/g, "\\'")}', 'quiz-container')">${opt}</button>`).join("")}
    </div>
    <p id="quiz-feedback" class="quiz-feedback"></p>
    <button id="quiz-nextBtn" style="display:none;" onclick="nextGronnKodeQuestion()">Neste</button>
  `;
}

async function checkAnswer(button, correctAnswer, containerId) {
  if (answered) return;
  answered = true;

  const buttons = document.querySelectorAll(`#${containerId} .quiz-option-btn`);
  buttons.forEach(b => b.disabled = true);

  const feedback = document.querySelector(`#${containerId} .quiz-feedback`);
  const nextBtn = document.querySelector(`#${containerId} button[id$='nextBtn']`);

  if (button.innerText === correctAnswer) {
    feedback.textContent = "Riktig! 🎉";
    feedback.style.color = "green";
    showScorePopup(containerId);
    await increaseScore();
  } else {
    feedback.textContent = "Feil! ❌";
    feedback.style.color = "red";
  }

  if (nextBtn) nextBtn.style.display = "inline-block";
}

function nextGronnKodeQuestion() {
  currentQuestionIndex++;
  answered = false;
  if (currentQuestionIndex < quizData.length) {
    loadGronnKodeQuestion();
  } else {
    document.getElementById("quiz-container").innerHTML = "<h3>Du har fullført Grønn kode-quizen! 🎉</h3>";
  }
}

// ------------------------- Energiforbruk -------------------------
function loadEnergibrukQuestion() {
  const q = energibrukData[energibrukIndex];
  const container = document.getElementById("energibruk-quiz-container");

  container.innerHTML = `
    <div class="score-popup">+50</div>
    <h3>${q.question}</h3>
    <div class="quiz-options">
      ${q.options.map(opt => `<button class="quiz-option-btn" onclick="checkEnergibrukAnswer(this, '${q.correct.replace(/'/g, "\\'")}')">${opt}</button>`).join("")}
    </div>
    <p class="quiz-feedback"></p>
    <button style="display:none;" onclick="nextEnergibrukQuestion()">Neste</button>
  `;
}

function checkEnergibrukAnswer(button, correctAnswer) {
  handleAnswer(button, correctAnswer, "energibruk-quiz-container", () => nextEnergibrukQuestion());
}

function nextEnergibrukQuestion() {
  energibrukIndex++;
  answered = false;
  if (energibrukIndex < energibrukData.length) {
    loadEnergibrukQuestion();
  } else {
    document.getElementById("energibruk-quiz-container").innerHTML = "<h3>Du har fullført Energibruk-quizen! 🎉</h3>";
  }
}

// ------------------------- Effektivitet -------------------------
function loadEffektivitetQuestion() {
  const q = effektivitetData[effektivitetIndex];
  const container = document.getElementById("effektivitet-quiz-container");

  container.innerHTML = `
    <div class="score-popup">+50</div>
    <h3>${q.question}</h3>
    <div class="quiz-options">
      ${q.options.map(opt => `<button class="quiz-option-btn" onclick="checkEffektivitetAnswer(this, '${q.correct.replace(/'/g, "\\'")}')">${opt}</button>`).join("")}
    </div>
    <p class="quiz-feedback"></p>
    <button style="display:none;" onclick="nextEffektivitetQuestion()">Neste</button>
  `;
}

function checkEffektivitetAnswer(button, correctAnswer) {
  handleAnswer(button, correctAnswer, "effektivitet-quiz-container", () => nextEffektivitetQuestion());
}

function nextEffektivitetQuestion() {
  effektivitetIndex++;
  answered = false;
  if (effektivitetIndex < effektivitetData.length) {
    loadEffektivitetQuestion();
  } else {
    document.getElementById("effektivitet-quiz-container").innerHTML = "<h3>Du har fullført Effektivitet-quizen! 🎉</h3>";
  }
}

// ------------------------- Designvalg -------------------------
function loadDesignvalgQuestion() {
  const q = designvalgData[designvalgIndex];
  const container = document.getElementById("designvalg-quiz-container");

  container.innerHTML = `
    <div class="score-popup">+50</div>
    <h3>${q.question}</h3>
    <div class="quiz-options">
      ${q.options.map(opt => `<button class="quiz-option-btn" onclick="checkDesignvalgAnswer(this, '${q.correct.replace(/'/g, "\\'")}')">${opt}</button>`).join("")}
    </div>
    <p class="quiz-feedback"></p>
    <button style="display:none;" onclick="nextDesignvalgQuestion()">Neste</button>
  `;
}

function checkDesignvalgAnswer(button, correctAnswer) {
  handleAnswer(button, correctAnswer, "designvalg-quiz-container", () => nextDesignvalgQuestion());
}

function nextDesignvalgQuestion() {
  designvalgIndex++;
  answered = false;
  if (designvalgIndex < designvalgData.length) {
    loadDesignvalgQuestion();
  } else {
    document.getElementById("designvalg-quiz-container").innerHTML = "<h3>Du har fullført Designvalg-quizen! 🎉</h3>";
  }
}

// ------------------------- Felles svarhåndtering -------------------------
async function handleAnswer(button, correctAnswer, containerId, nextCallback) {
  if (answered) return;
  answered = true;

  const container = document.getElementById(containerId);
  const buttons = container.querySelectorAll(".quiz-option-btn");
  const feedback = container.querySelector(".quiz-feedback");
  const nextBtn = container.querySelector("button");

  buttons.forEach(btn => btn.disabled = true);

  if (button.innerText === correctAnswer) {
    feedback.textContent = "Riktig! 🎉";
    feedback.style.color = "green";
    showScorePopup(containerId);
    await increaseScore();
  } else {
    feedback.textContent = "Feil! ❌";
    feedback.style.color = "red";
  }

  if (nextBtn) nextBtn.style.display = "inline-block";
  nextBtn.onclick = () => {
    nextCallback();
  };
}

// ------------------------- Init -------------------------
document.addEventListener("DOMContentLoaded", () => {
  checkSession().then(() => {
    handleHashChange(); // Last inn første gang
    window.addEventListener("hashchange", handleHashChange); // Oppdater ved endring
  });

  // Hindrer scrolling ved klikk på sidebar
  const links = document.querySelectorAll(".quiz-sidebar a");
  links.forEach(link => {
    link.addEventListener("click", (e) => {
      e.preventDefault();
      const hash = link.getAttribute("href");
      history.pushState(null, null, hash);
      handleHashChange();
    });
  });
});

function handleHashChange() {
  const hash = window.location.hash || "#gronnkode";
  const allSections = document.querySelectorAll(".quiz-article");

  // Skjul alle quiz-seksjoner
  allSections.forEach(section => section.style.display = "none");

  // Vis aktuell seksjon
  const targetSection = document.querySelector(hash);
  if (targetSection) {
    targetSection.style.display = "block";
  }

  // Last inn spørsmål
  switch (hash) {
    case "#energibruk":
      loadEnergibrukQuestion();
      break;
    case "#effektivitet":
      loadEffektivitetQuestion();
      break;
    case "#designvalg":
      loadDesignvalgQuestion();
      break;
    default:
      loadGronnKodeQuestion();
  }
}

