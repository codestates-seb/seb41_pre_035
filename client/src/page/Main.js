import Nav from "../component/Nav";
import Sidebar from "../component/Sidebar";
import Questions from "../page/Questions";

const Main = () => {
  return (
    <main>
      <Nav />
      <Questions />
      <Sidebar />
    </main>
  );
};

export default Main;
