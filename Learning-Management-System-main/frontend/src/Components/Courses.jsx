import React, { useState, useEffect } from "react";
import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function Courses() {
  const [courses, setCourses] = useState([]);
  const [enrolled, setEnrolled] = useState([]);
  const userId = localStorage.getItem("id");
  const authToken = localStorage.getItem("token");
  const email = localStorage.getItem("email") || "test@example.com";
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/api/courses")
      .then((response) => response.json())
      .then((data) => setCourses(data))
      .catch((error) => console.error("Error fetching courses:", error));

    if (userId) {
      fetch(`http://localhost:8080/api/learning/${userId}`)
        .then((response) => response.json())
        .then((data) => {
          const enrolledCourses = data.map((item) => item.course_id);
          setEnrolled(enrolledCourses);
        })
        .catch((error) =>
          console.error("Error fetching enrolled courses:", error)
        );
    }
  }, [userId]);

  const loadRazorpayScript = () => {
    return new Promise((resolve) => {
      const script = document.createElement("script");
      script.src = "https://checkout.razorpay.com/v1/checkout.js";
      script.onload = () => resolve(true);
      script.onerror = () => resolve(false);
      document.body.appendChild(script);
    });
  };

  const enrollCourse = async (courseId, courseName, price) => {
    if (!authToken) {
      toast.error("You need to login to continue", { autoClose: 1000 });
      return setTimeout(() => navigate("/login"), 2000);
    }

    const res = await loadRazorpayScript();
    if (!res) {
      alert("Razorpay SDK failed to load. Are you online?");
      return;
    }

    try {
      const orderResponse = await axios.post(
        "http://localhost:8080/api/payment/create",
        {
          amount: price,
          currency: "INR",
          email: email,
          receipt: "receipt_" + new Date().getTime(),
        }
      );

      const { razorpayOrderId } = orderResponse.data;

      const options = {
        key: "", // Replace with your actual Razorpay key
        amount: price,
        currency: "INR",
        name: "Course Purchase",
        description: `Enroll in ${courseName}`,
        order_id: razorpayOrderId,
        handler: async function (response) {
          const paymentData = {
            razorpayOrderId: response.razorpay_order_id,
            razorpayPaymentId: response.razorpay_payment_id,
            razorpaySignature: response.razorpay_signature,
          };

          try {
            const verificationRes = await axios.post(
              "http://localhost:8080/api/payment/verify",
              paymentData
            );

            if (verificationRes.status === 200) {
              const enrollRequest = {
                userId: userId,
                courseId: courseId,
              };

              await axios.post(
                "http://localhost:8080/api/learning",
                enrollRequest
              );

              toast.success("Course Enrolled successfully", { autoClose: 1000 });
              setTimeout(() => navigate(`/course/${courseId}`), 2000);
            } else {
              toast.error("Payment verification failed", { autoClose: 1000 });
            }
          } catch (error) {
            console.error("Verification error:", error);
            toast.error("Payment verification failed", { autoClose: 1000 });
          }
        },
        prefill: {
          email: email,
        },
        theme: {
          color: "#3399cc",
        },
      };

      const rzp = new window.Razorpay(options);
      rzp.open();
    } catch (error) {
      console.error("Payment initiation failed", error);
      toast.error("Payment initiation failed", { autoClose: 1000 });
    }
  };

  return (
    <div>
      <Navbar page="courses" />
      <div className="courses-container" style={{ marginTop: "20px" }}>
        {courses.map((course) => (
          <div key={course.course_id} className="course-card">
            <img
              src={course.p_link}
              alt={course.course_name}
              className="course-image"
            />
            <div className="course-details">
              <h3 className="course-heading">
                {course.course_name.length < 8
                  ? `${course.course_name} Tutorial`
                  : course.course_name}
              </h3>
              <p className="course-description" style={{ color: "grey" }}>
                Price: Rs.{(course.price / 100).toFixed(2)}
              </p>
              <p className="course-description">
                Tutorial by {course.instructor}
              </p>
            </div>
            {enrolled.includes(course.course_id) ? (
              <button
                className="enroll-button"
                style={{
                  color: "#F4D03F",
                  backgroundColor: "darkblue",
                  fontWeight: "bold",
                }}
                onClick={() => navigate("/learnings")}
              >
                Enrolled
              </button>
            ) : (
              <button
                className="enroll-button"
                onClick={() =>
                  enrollCourse(
                    course.course_id,
                    course.course_name,
                    course.price
                  )
                }
              >
                Enroll
              </button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Courses;
