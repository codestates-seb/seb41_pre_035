import Nav from "../component/Nav";
import Sidebar from "../component/Sidebar";
import Footer from "../component/Footer";
import Questions from "../page/Questions";

const Main = () => {
  return (
    <main>
      <Nav />
      <Questions />
      <Sidebar />
      <Footer />
    </main>
  );
};

export default Main;
