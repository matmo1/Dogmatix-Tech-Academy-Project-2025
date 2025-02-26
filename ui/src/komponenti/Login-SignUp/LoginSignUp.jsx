import React, { useState } from 'react';
import "./LoginSignUp.css";

import user_icon from "./user.png";
import mail_icon from "./mail.png";
import password_icon from "./password.png";

import { useNavigate } from 'react-router-dom'

import axios from 'axios';

export const LoginSignUp = () => {
    const navigate = useNavigate();
    const [action, setAction] = useState("Sign Up");
    const [role, setRole] = useState("Student");
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Form Data:", formData, "Role:", role);
        postData(formData.email, formData.password);
    };

    const postData = async (username, password) => {
        axios.post('http://localhost:8080/api/login', {username, password}) // Replace with your API URL
        .then((response) => {
            if(response?.data?.role === 'STUDENT') {
                navigate('/stream/Math?type=student');
            } else if(response?.data?.role === 'TEACHER') {
                //navigate('/stream/Math?type=student');
            } else if(response?.data?.role === 'ADMIN') {
                //navigate('/stream/Math?type=student');
            }
        })
        .catch((error) => {
            console.log(error.message); // Handle any errors
        });
    };

    return (
        <div className="container">
            <div className="header">
                <div className="text">{action}</div>
                <div className="underline"></div>
            </div>

            <form className="inputs" onSubmit={handleSubmit}>
                {action === "Sign Up" && (
                    <div className="input">
                        <img src={user_icon} alt="User Icon" />
                        <input
                            type="text"
                            name="name"
                            placeholder="Име"
                            value={formData.name}
                            onChange={handleChange}
                            aria-label="Име"
                            required
                        />
                    </div>
                )}
                <div className="input">
                    <img src={mail_icon} alt="Mail Icon" />
                    <input
                        type="text"
                        name="email"
                        placeholder="Имейл"
                        value={formData.email}
                        onChange={handleChange}
                        aria-label="Имейл"
                        required
                    />
                </div>
                <div className="input">
                    <img src={password_icon} alt="Password Icon" />
                    <input
                        type="password"
                        name="password"
                        placeholder="Парола"
                        value={formData.password}
                        onChange={handleChange}
                        aria-label="Парола"
                        required
                    />
                </div>

                {action === "Sign Up" && (
                    <div className="role-dropdown">
                        <select
                            className="role-select"
                            value={role}
                            onChange={(e) => setRole(e.target.value)}
                            aria-label="Роля"
                        >
                            <option value="Student">Student</option>
                            <option value="Admin">Admin</option>
                            <option value="Teacher">Teacher</option>
                        </select>
                    </div>
                )}

                <div className="submit-container">
                    <button
                        type="button"
                        className={`submit ${action === "Login" ? "gray" : ""}`}
                        onClick={() => setAction("Sign Up")}
                    >
                        Sign Up
                    </button>
                    <button
                        type="button"
                        className={`submit ${action === "Sign Up" ? "gray" : ""}`}
                        onClick={() => setAction("Login")}
                    >
                        Login
                    </button>
                    <button type='submit'>
                        Submit Form
                    </button>
                </div>
            </form>
        </div>
    );
};

export default LoginSignUp;
