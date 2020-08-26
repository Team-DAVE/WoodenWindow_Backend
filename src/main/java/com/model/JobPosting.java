package com.model;

import javax.persistence.*;

@Entity
@Table(name = "JOB_POSTING")
public class JobPosting {
    @Id
    @Column(name="JOB_POSTING_ID", columnDefinition="serial primary key")
    @GeneratedValue(strategy=GenerationType.IDENTITY) // the table creates this value
    private int jobPostingId;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "JOB_SUMMARY")
    private String jobSummary;

    @Column(name = "SALARY")
    private String salary;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="BUSINESS", referencedColumnName="BUSINESS_ID", columnDefinition="INT", nullable = false)
    private Business business;

    public JobPosting() {
    }

    public int getJobPostingId() {
        return jobPostingId;
    }

    public void setJobPostingId(int jobPostingId) {
        this.jobPostingId = jobPostingId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobSummary() {
        return jobSummary;
    }

    public void setJobSummary(String jobSummary) {
        this.jobSummary = jobSummary;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "JobPosting{" +
                "jobPostingId=" + jobPostingId +
                ", position='" + position + '\'' +
                ", jobSummary='" + jobSummary + '\'' +
                ", salary='" + salary + '\'' +
                ", business=" + business +
                '}';
    }
}
