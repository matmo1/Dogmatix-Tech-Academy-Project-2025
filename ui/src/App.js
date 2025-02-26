import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import HomePageT from './komponenti/Teacher/HomePageT';
import HomePageStudent from './komponenti/Student/HomePageStudent';
import LoginSignUp from './komponenti/Login-SignUp/LoginSignUp';
import { useParams } from 'react-router-dom';

// Wrapper to decide between Student and Teacher Homepage
function HomePageWrapper() {
  const { subject } = useParams();
  const location = useLocation();

  // Get the user type from query params
  const type = new URLSearchParams(location.search).get('type');

  if (type === 'student') {
    return <HomePageStudent subject={subject} />;
  } else if (type === 'teacher') {
    return <HomePageT subject={subject} />;
  } else {
    return <Navigate to="/loginSignUp" />;
  }
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/loginSignUp" />} />

        <Route path="/loginSignUp" element={<LoginSignUp />} />

        <Route path="/stream" element={<Navigate to="/stream/Math?type=teacher" />} />

        <Route path="/stream/:subject" element={<HomePageWrapper />} />
      </Routes>
    </Router>
  );
}

export default App;