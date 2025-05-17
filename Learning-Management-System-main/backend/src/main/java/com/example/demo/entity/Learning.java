package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Learning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "trainer_id")
	private trainer trainer;

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(trainer trainer) {
		this.trainer = trainer;
	}
}
