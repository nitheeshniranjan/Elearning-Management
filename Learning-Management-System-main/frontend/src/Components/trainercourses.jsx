import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import SideBar from "./DashBoard/SideBar";
import Navbar from "./DashBoard/Navbar";

function TrainerCourses() {
  const navigate = useNavigate();
  const email = localStorage.getItem("email");

  // Optional: You could add role-check logic here if needed
  const isTrainer = !!email; // Simple check assuming logged-in users are trainers

  return (
    <>
      <body>
        <SideBar current={"courses"} />
        <section id="content">
          <Navbar />
          <main className="t">
            <div className="table-data" style={{ marginTop: "-10px" }}>
              <div className="order">
                <div id="course" className="todo">
                  <div className="head" style={{ marginTop: "-100px" }}>
                    <h3 style={{ color: "Black" }}>Trainer Dashboard</h3>
                    {isTrainer && (
                      <button
                        onClick={() => navigate("/addcourse")}
                        style={{
                          backgroundColor: "darkblue",
                          borderRadius: "10px",
                          color: "white",
                          border: "none",
                          padding: "8px",
                          fontWeight: "500",
                        }}
                      >
                        Add Course <FontAwesomeIcon icon={faPlus} />
                      </button>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </main>
        </section>
      </body>
    </>
  );
}

export default TrainerCourses;
