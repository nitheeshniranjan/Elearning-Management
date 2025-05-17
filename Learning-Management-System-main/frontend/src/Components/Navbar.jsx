import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import logo from "./images/logo.jpg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faChalkboardUser } from "@fortawesome/free-solid-svg-icons";

function Navbar(props) {
  const value = props.page;
  const navigate = useNavigate();
  const authToken = localStorage.getItem("token");
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const handleLogOut = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("name");
    localStorage.removeItem("id");
    localStorage.removeItem("profileImage");
    navigate("/");
  };

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  const closeMobileMenu = () => {
    setIsMobileMenuOpen(false);
  };

  const handleLoginSelect = (event) => {
    const selectedRole = event.target.value;

    if (selectedRole === "student") {
      navigate("/login");  // or navigate to register: "/register/student"
    } else if (selectedRole === "trainer") {
      navigate("/TrainerLogin");  // or navigate to register: "/register/trainer"
    }
  };

  return (
    <div>
      <nav>
        <div className="logo1">
          <img src={logo} alt="" />
        </div>
        <div className="navigation">
          <div id="menu-btn">
            <div className="menu-dash" onClick={toggleMobileMenu}>
              &#9776;
            </div>
          </div>
          <i
            id="menu-close"
            className="fas fa-times"
            onClick={closeMobileMenu}
          ></i>
          <ul className={isMobileMenuOpen ? "active" : ""}>
            {isMobileMenuOpen && (
              <li className="close-button">
                <button onClick={closeMobileMenu}>X</button>
              </li>
            )}
            {value === "home" ? (
              <li style={{ backgroundColor: "#D4AF37", borderRadius: "5px" }}>
                <Link to={"/"} style={{ color: "white", padding: "12px" }}>
                  Home
                </Link>
              </li>
            ) : (
              <li>
                <Link to={"/"}>Home</Link>
              </li>
            )}
            {value === "courses" ? (
              <li style={{ backgroundColor: "#D4AF37", borderRadius: "5px" }}>
                <Link
                  to={"/courses"}
                  style={{ color: "white", padding: "12px" }}
                >
                  Courses
                </Link>
              </li>
            ) : (
              <li>
                <Link to={"/courses"}>Courses</Link>
              </li>
            )}
            {authToken ? (
              value === "profile" ? (
                <li style={{ backgroundColor: "#D4AF37", borderRadius: "5px" }}>
                  <Link
                    to={"/profile"}
                    style={{ color: "white", padding: "10px" }}
                  >
                    Profile
                    <FontAwesomeIcon icon={faUser} />
                  </Link>
                </li>
              ) : (
                <li>
                  <Link to={"/profile"}>
                    Profile
                    <FontAwesomeIcon icon={faUser} />
                  </Link>
                </li>
              )
            ) : (
              <></>
            )}
            {authToken ? (
              value === "learnings" ? (
                <li style={{ backgroundColor: "#D4AF37", borderRadius: "5px" }}>
                  <Link
                    to={"/learnings"}
                    style={{ color: "white", padding: "10px" }}
                  >
                    Learnings
                    <FontAwesomeIcon icon={faChalkboardUser} />
                  </Link>
                </li>
              ) : (
                <li>
                  <Link to={"/learnings"}>
                    Learnings
                    <FontAwesomeIcon icon={faChalkboardUser} />
                  </Link>
                </li>
              )
            ) : (
              <></>
            )}
            {authToken !== null ? (
              <li>
                <button onClick={handleLogOut} className="sign-out-button">
                  Sign Out
                </button>
              </li>
            ) : (
              <li>
              <select onChange={handleLoginSelect} defaultValue="">
                  <option value="" disabled>
                    Login/SignUp
                  </option>
                  <option value="student">Student</option>
                  <option value="trainer">Trainer</option>
                </select>
                {/* <button onClick={() => navigate("/login")}>Login/SignUp</button> */}
                  
              </li>
            )}
          </ul>
        </div>
      </nav>
    </div>
  );
}

export default Navbar;
