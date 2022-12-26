import { useNavigate } from "react-router-dom";

function QuestionItem() {
  const navigate = useNavigate();
  let questionId = 0;

  const handleTitleClick = () => {
    navigate(`/questions/${questionId}`);
  };
  return (
    <>
      <div className="questionSummary">
        <p>0 votes</p>
        <p>0 answer</p>
        <p>0 views</p>
        <p onClick={handleTitleClick}>title</p>
        <p>content</p>
      </div>
    </>
  );
}

export default QuestionItem;
