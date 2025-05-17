package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "trainer_id")
	private trainer trainer;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	// Getters and setters...

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(trainer trainer) {
		this.trainer = trainer;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
