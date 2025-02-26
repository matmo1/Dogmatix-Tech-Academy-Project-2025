import { useParams, Link } from "react-router-dom";
import { useState } from "react";
import "./HomePageStudent.css";

export default function HomePageStudent() {
    const { subject } = useParams();
    const [sidebarOpen, setSidebarOpen] = useState(false);

    const subjects = ["Math", "English", "Science", "History", "Art"];

    const tasks = {
        Math: [],
        English: [],
        Science: [],
        History: [],
        Art: [],
    };

    const [expandedTask, setExpandedTask] = useState(null);
    const [comments, setComments] = useState({});
    const [newComment, setNewComment] = useState("");

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

    return (
        <div className="homepage-container">
            <button className="menu-button" onClick={toggleSidebar}>
                ☰ Menu
            </button>

            <div className={`sidebar ${sidebarOpen ? "open" : ""}`}>
                <button className="close-button" onClick={toggleSidebar}>✖</button>

                <div className="sidebar-buttons">
                    <Link to="/stream" className="sidebar-button stream-button">Stream</Link>
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
                    {tasks[subject]?.map((task) => (
                        <div key={task} className="task-wrapper">
                            <div
                                className={`task-header ${expandedTask === task ? "active-task" : ""}`}
                                onClick={() => setExpandedTask(expandedTask === task ? null : task)}
                            >
                                <span className="task-title">{task}</span>
                                <span className="task-grade">0/100</span>
                            </div>

                            {expandedTask === task && (
                                <div className="task-content">
                                    <p><strong>Task Details:</strong> Complete the {task} before the due date.</p>

                                    <button className="upload-button">Upload</button>

                                    <div className="comments-section">
                                        <h4>Comments</h4>
                                        <div className="comments-box">
                                            {comments[task]?.length > 0 ? (
                                                comments[task].map((comment, index) => (
                                                    <p key={index} className="comment">{comment}</p>
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
                                                        handlePostComment(task);
                                                    }
                                                }}
                                            />
                                            <button
                                                onClick={() => handlePostComment(task)}
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
