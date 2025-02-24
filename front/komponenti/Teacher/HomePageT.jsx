import { useParams, Link } from "react-router-dom";
import { useState } from "react";
import "./HomePageT.css";

export default function HomePageT() {
  const { subject } = useParams();
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const subjects = ["Math", "English", "Science", "History", "Art"];

  const [tasks, setTasks] = useState({
    Math: [],
    English: [],
    Science: [],
    History: [],
    Art: [],
  });

  const [expandedTask, setExpandedTask] = useState(null);
  const [comments, setComments] = useState({});
  const [newComment, setNewComment] = useState("");
  const [newTask, setNewTask] = useState("");
  const [editingTask, setEditingTask] = useState(null);
  const [taskDetails, setTaskDetails] = useState("");

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  const handlePostComment = (task) => {
    if (newComment.trim() !== "") {
      setComments((prev) => ({
        ...prev,
        [task]: [...(prev[task] || []), newComment],
      }));
      setNewComment("");
    }
  };

  const handleAddTask = () => {
    if (newTask.trim() !== "") {
      setTasks((prevTasks) => ({
        ...prevTasks,
        [subject]: [
          ...prevTasks[subject],
          { name: newTask, details: "No details yet", grade: 0 },
        ],
      }));
      setNewTask("");
    }
  };

  const handleRemoveTask = (taskToRemove) => {
    setTasks((prevTasks) => ({
      ...prevTasks,
      [subject]: prevTasks[subject].filter((task) => task.name !== taskToRemove),
    }));
  };

  const handleEditTask = (task) => {
    setEditingTask(task.name);
    setTaskDetails(task.details);
  };

  const handleSaveTaskDetails = (taskName) => {
    setTasks((prevTasks) => ({
      ...prevTasks,
      [subject]: prevTasks[subject].map((task) =>
        task.name === taskName ? { ...task, details: taskDetails } : task
      ),
    }));
    setEditingTask(null);
  };

  const handleEditTaskName = (task, newName) => {
    setTasks((prevTasks) => ({
      ...prevTasks,
      [subject]: prevTasks[subject].map((t) =>
        t.name === task.name ? { ...t, name: newName } : t
      ),
    }));
  };

  const handleGradeChange = (taskName, newGrade) => {
    const cappedGrade = Math.min(Math.max(newGrade, 0), 100);

    setTasks((prevTasks) => ({
      ...prevTasks,
      [subject]: prevTasks[subject].map((task) =>
        task.name === taskName ? { ...task, grade: cappedGrade } : task
      ),
    }));
  };

  return (
    <div className="homepage-container">
      <button className="menu-button" onClick={toggleSidebar}>
        ☰ Menu
      </button>

      <div className={`sidebar ${sidebarOpen ? "open" : ""}`}>
        <button className="close-button" onClick={toggleSidebar}>
          ✖
        </button>

        <div className="sidebar-buttons">
          <Link to="/stream" className="sidebar-button stream-button">
            Stream
          </Link>
        </div>
        <h2>Subjects</h2>
        {subjects.map((subj) => (
          <Link
            key={subj}
            to={`/stream/${subj}`}
            className={`sidebar-item ${subject === subj ? "active" : ""}`}
          >
            {subj}
          </Link>
        ))}
      </div>

      <div className="stream-content">
        {subject && (
          <div className="stream-header">
            <h1>{subject} Stream</h1>
          </div>
        )}

        <div className="tasks-container">
          <div className="add-task-container">
            <input
              type="text"
              className="new-task-input"
              placeholder="New task name"
              value={newTask}
              onChange={(e) => setNewTask(e.target.value)}
            />
            <button onClick={handleAddTask} className="add-task-button">
              Add Task
            </button>
          </div>

          {tasks[subject]?.map((task) => (
            <div key={task.name} className="task-wrapper">
              <div
                className={`task-header ${expandedTask === task.name ? "active-task" : ""
                  }`}
                onClick={() =>
                  setExpandedTask(expandedTask === task.name ? null : task.name)
                }
              >
                <span className="task-title">
                  {editingTask === task.name ? (
                    <input
                      type="text"
                      value={task.name}
                      onChange={(e) => handleEditTaskName(task, e.target.value)}
                      className="task-title-edit"
                    />
                  ) : (
                    task.name
                  )}
                </span>
                <span className="task-grade">
                  {editingTask === task.name ? (
                    <input
                      type="number"
                      value={task.grade}
                      onChange={(e) => handleGradeChange(task.name, e.target.value)}
                      className="grade-input"
                      max="100"
                      min="0"
                    />
                  ) : (
                    `${task.grade}/100`
                  )}
                </span>
                <button
                  onClick={() => handleRemoveTask(task.name)}
                  className="remove-task-button"
                >
                  X
                </button>
                {editingTask !== task.name && (
                  <button
                    onClick={() => handleEditTask(task)}
                    className="edit-task-button"
                  >
                    Edit
                  </button>
                )}
              </div>

              {expandedTask === task.name && (
                <div className="task-content">
                  <p>
                    <strong>Task Details:</strong>
                  </p>
                  {editingTask === task.name ? (
                    <div>
                      <textarea
                        value={taskDetails}
                        onChange={(e) => setTaskDetails(e.target.value)}
                        rows="4"
                        className="edit-task-details"
                      />
                      <button
                        onClick={() => handleSaveTaskDetails(task.name)}
                        className="save-task-button"
                      >
                        Save
                      </button>
                    </div>
                  ) : (
                    <p>{task.details}</p>
                  )}

                  <button className="upload-button">Upload</button>

                  <div className="comments-section">
                    <h4>Comments</h4>
                    <div className="comments-box">
                      {comments[task.name]?.length > 0 ? (
                        comments[task.name].map((comment, index) => (
                          <p key={index} className="comment">
                            {comment}
                          </p>
                        ))
                      ) : (
                        <p className="no-comments">No comments yet.</p>
                      )}
                    </div>

                    <div className="comment-input-container">
                      <input
                        type="text"
                        className="comment-input"
                        placeholder="Write a comment..."
                        value={newComment}
                        onChange={(e) => setNewComment(e.target.value)}
                        onKeyDown={(e) => {
                          if (e.key === "Enter") {
                            handlePostComment(task.name);
                          }
                        }}
                      />
                      <button
                        onClick={() => handlePostComment(task.name)}
                        className="post-comment-button"
                      >
                        Post
                      </button>
                    </div>
                  </div>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}