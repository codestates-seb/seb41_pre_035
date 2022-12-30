import Nav from "../component/Nav";
import Footer from "../component/Footer";
import "../css/tagsBoard.css";
import Tags from "../data/Tags";

const TagsBoard = () => {
  const taglist = Tags;

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

        {/* ! 나중에 서버로 데이터 받아서 할 것 */}
        <ul className="tagsBoardContainers">
          {Tags.map((tag, idx) => (
            <li key={idx} className="tagsBoardContainer">
              <div className="tagsContainer">
                <div className="tagsName">{tag.title}</div>
                <p className="tagsText">{tag.content}</p>

                <div className="tagsInfo">
                  <div className="tagsInfoNum">2462356 questions</div>
                  <div className="tagsInfoDate">563 asked today, 3129 this week</div>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>

      <Footer />
    </div>
  );
};

export default TagsBoard;
