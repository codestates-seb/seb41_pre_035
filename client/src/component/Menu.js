import "../css/Btn.css";
import "../css/Questions.css";

export default function FilterMenu() {
  return (
    <div className="filterMenu">
      <div className="menuOptions">
        <div className="menuOption">
          <p>Filter by</p>
          <li>
            <input type="checkbox" />
            No answers
          </li>
          <li>
            <input type="checkbox" />
            No accepted answer
          </li>
          <li>
            <input type="checkbox" />
            Has bounty
          </li>
        </div>
        <div className="menuOption">
          <p>Sorted by</p>
          <li>
            <input type="checkbox" />
            Newest
          </li>
          <li>
            <input type="checkbox" />
            Recent activity
          </li>
          <li>
            <input type="checkbox" />
            Highest score
          </li>
          <li>
            <input type="checkbox" />
            Most frequent
          </li>
          <li>
            <input type="checkbox" />
            Bounty ending soon
          </li>
        </div>
        <div className="menuOption">
          <p>Tagged with</p>
          <li>
            <input type="checkbox" />
            My watched tags
          </li>
          <li>
            <input type="checkbox" />
            The following tags:
          </li>
          <input placeholder="e.g. javascript or python"></input>
        </div>
      </div>
      <div className="applyBtn">
        <button className="btn">Apply filter</button>
        <button className="btn filterBtn">Save suctom filter</button>
      </div>
    </div>
  );
}
