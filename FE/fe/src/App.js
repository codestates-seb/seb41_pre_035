import "./App.css";
import axios from "axios";

function App() {
  axios
    .get("http://34.64.254.252:8080/")
    .then((Response) => {
      console.log(Response.data);
    })
    .catch((Error) => {
      console.log(Error);
    });

  return <div className="App"></div>;
}

export default App;
