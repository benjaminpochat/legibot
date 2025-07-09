import logo from './logo.svg';
import './App.css';

function App() {

  const BACKEND_URL = "http://localhost:8080"

  return (
    <div className="App">
      <header>
      Euro Chat - un chatbot pour savoir ce qui se passe Parlement Européen
      </header>
      <div id="conversation">
      </div>
      <div id="nextQuestion">
        <input id="question" type="text"/>
        <button id="send" type="button" onClick={() => sendQuestion()}>Envoyer ma question</button>
      </div>
    </div>
  );

  async function sendQuestion() {
    const questionInput = document.querySelector("#question")
    const question = questionInput.value
    console.debug(`Sending question "${question}"`)
    document.querySelector("#conversation").insertAdjacentHTML("beforeend", `
        <pre>Question : ${question}</pre>
    `)
    questionInput.value = null

    const response = await fetch(BACKEND_URL + "/chatbot", {
        method: "POST",
        headers: {
            "Content-Type": "application/text"
        },
        body: question
    })
    const responseText = await response.text()
    console.log(`Receiving response "${responseText}"}`)
    document.querySelector("#conversation").insertAdjacentHTML("beforeend", `
        <pre>Réponse : ${responseText}</pre>
    `)
  }
}

export default App;
