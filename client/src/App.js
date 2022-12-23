import { Route, Routes } from "react-router-dom";
import Questions from "./pages/Questions";
import AskQuestions from "./pages/AskQuestions";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Questions />} />
        <Route path="/questions" element={<AskQuestions />} />
      </Routes>
    </>
  );
}

export default App;
