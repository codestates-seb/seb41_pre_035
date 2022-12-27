import { useNavigate } from "react-router-dom";
import "../css/QuestionItem.css";

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
        <p onClick={handleTitleClick}>{questionItem.title}</p>
        <p>{questionItem.content}</p>
      </div>
    </div>
  );
}

export default QuestionItem;
