import { useNavigate } from "react-router-dom";
import "../css/QuestionItem.css";
import "../css/Tags.css";

function QuestionItem({ questionItem }) {
  const navigate = useNavigate();

  const handleTitleClick = () => {
    navigate(`/questions/${questionItem.id}`);
  };

  return (
    <div className="questionSummary">
      <div className="postSum qFlexItem">
        <p>0 votes</p>
        <p>0 answers</p>
        <p>0 views</p>
      </div>
      <div className="qFlexItem qPost">
        <p onClick={handleTitleClick} id="qPostTitle">
          {questionItem.title}
        </p>
        <p id="qPostCon">{questionItem.content}</p>
        <div className="tags">
          {questionItem.tags.map((el, idx) => (
            <li key={idx} className="tag">
              <p className="tagTitle sTag">{el}</p>
            </li>
          ))}
        </div>
      </div>
    </div>
  );
}

export default QuestionItem;
