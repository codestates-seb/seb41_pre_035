import Nav from "../component/Nav";
import Footer from "../component/Footer";
import "../css/tagsBoard.css";
import Tags from "../data/Tags";

const TagsBoard = () => {
  return (
    <div className="container">
      <Nav className="tagsBoardNav" />

      <div className="tagsBoard">
        <div className="ttagsBoardInfo">
          <h2 className="tagsBoardTitle">Tags</h2>
          <div className="tagsBoardText">
            A tag is a keyword or label that categorizes your question with other, similar questions. <br /> Using the right tags makes it easier for others to find and answer your question.
          </div>
        </div>

        <div className="tagsBoardFilter">
          <form>
            <input type="text" placeholder="Filter by tag name" className="tagsBoardSearch" />
          </form>
          <div className="tagsBoardFilterBtns">
            <button>Popular</button>
            <button>Name</button>
            <button>New</button>
          </div>
        </div>

        {Tags.map()}
        <div className="tagsBoardContainer">
          <div className="tagsContainer">
            <div className="tagsName">javascript</div>
            <p className="tagsText">
              For questions about programming in ECMAScript (JavaScript/JS) and its different dialects/implementations (except for ActionScript). Keep in mind that JavaScript is NOT the same as Java!
              Include all labels that are relevant to your question; e.g., [node.js], [jQuery], [JSON], [ReactJS], [angular], [ember.js], [vue.js], [typescript], [svelte], etc.
            </p>

            <div className="tagsInfo">
              <div className="tagsInfoNum">2462356 questions</div>
              <div className="tagsInfoDate">563 asked today, 3129 this week</div>
            </div>
          </div>
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default TagsBoard;
