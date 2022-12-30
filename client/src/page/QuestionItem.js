import { useNavigate } from "react-router-dom";
import "../css/QuestionItem.css";
import "../css/Tags.css";

function formatDate(value1, value2) {
  const date1 = new Date(value1); //최근 수정한 날짜
  const date2 = new Date(value2); //생성한 날짜
  if (date1.getFullYear() < date2.getFullYear()) return date2.getFullYear() - date1.getFullYear();
  else if (date1.getMonth() < date2.getMonth()) return date2.getMonth() - date1.getMonth();
  else if (date1.getDate() < date2.getDate()) return date2.getDate() - date1.getDate();
  else if (date1.getTime() < date2.getTime()) return date2.getTime() - date1.getTime();
  else if (date1.getSeconds() < date2.getSeconds()) return date2.getSeconds() - date1.getSeconds();
  else return 0;
}

function QuestionItem({ questionItem }) {
  const navigate = useNavigate();

  const handleTitleClick = () => {
    navigate(`/questions/${questionItem.questionId}`);
  };

  return (
    <div className="questionSummary">
      <div className="postSum qFlexItem">
        <p>{questionItem.voteCount} votes</p>
        <p>{questionItem.answerCount} answers</p>
        <p>{questionItem.viewCount} views</p>
      </div>
      <div className="qbtm">
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
          <p>
            {questionItem.writerName} modified {formatDate(questionItem.lastModifiedAt, questionItem.createdAt)} min ago
          </p>
        </div>
      </div>
    </div>
  );
}

export default QuestionItem;
