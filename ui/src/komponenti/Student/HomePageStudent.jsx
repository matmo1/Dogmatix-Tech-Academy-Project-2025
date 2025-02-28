import { useParams, Link } from "react-router-dom";
import { useState } from "react";
import "./HomePageStudent.css";

// Function to darken a color
const darkenColor = (color, amount = 0.2) => {
    // Convert the hex color to RGB
    let r = parseInt(color.slice(1, 3), 16);
    let g = parseInt(color.slice(3, 5), 16);
    let b = parseInt(color.slice(5, 7), 16);

    // Apply the darkening effect
    r = Math.max(0, r - r * amount);
    g = Math.max(0, g - g * amount);
    b = Math.max(0, b - b * amount);

    // Convert back to hex and return
    return `#${Math.round(r).toString(16).padStart(2, "0")}${Math.round(g).toString(16).padStart(2, "0")}${Math.round(b).toString(16).padStart(2, "0")}`;
};

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

    const [streamColor, setStreamColor] = useState("#ffffff"); 
    const [sidebarColor, setSidebarColor] = useState("#ffffff");

    const colors = ["#ffadad", "#ffd6a5", "#fdffb6", "#caffbf", "#a0c4ff", "#bdb2ff", "#ffffff"];

    const toggleSidebar = () => {
        setSidebarOpen(!sidebarOpen);
    };

    const changeColors = () => {
        const randomIndex = Math.floor(Math.random() * colors.length);
        const newColor = colors[randomIndex];
        setStreamColor(newColor); 
        setSidebarColor(darkenColor(newColor, 0.2)); 
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

            <div className={`sidebar ${sidebarOpen ? "open" : ""}`} style={{ backgroundColor: sidebarColor }}>
                <button className="close-button" onClick={toggleSidebar}>✖</button>

                <div className="sidebar-buttons">
                    <Link to="/stream" className="sidebar-button stream-button">Stream</Link>
                    <button className="sidebar-button stream-button" onClick={changeColors}>
                        Change Colors
                    </button>
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

            <div className="stream-content" style={{ backgroundColor: streamColor }}>
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
