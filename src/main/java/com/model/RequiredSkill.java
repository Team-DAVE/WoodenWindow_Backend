package com.model;

import javax.persistence.*;

@Entity
@Table(name = "REQUIRED_SKILL")
public class RequiredSkill {
    @Id
    @Column(name="REQUIRED_SKILL_ID", columnDefinition="serial primary key")
    @GeneratedValue(strategy=GenerationType.IDENTITY) // the table creates this value
    private int requiredSkillId;

    @Column(name = "REQUIRED_SKILL_NAME")
    private String requiredSkillName;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="JOB_POSTING", referencedColumnName="JOB_POSTING_ID", columnDefinition="INT", nullable = false)
    private JobPosting jobPosting;

    public RequiredSkill() {
    }

    public int getRequiredSkillId() {
        return requiredSkillId;
    }

    public void setRequiredSkillId(int requiredSkillId) {
        this.requiredSkillId = requiredSkillId;
    }

    public String getRequiredSkillName() {
        return requiredSkillName;
    }

    public void setRequiredSkillName(String requiredSkillName) {
        this.requiredSkillName = requiredSkillName;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    @Override
    public String toString() {
        return "RequiredSkill{" +
                "requiredSkillId=" + requiredSkillId +
                ", requiredSkillName='" + requiredSkillName + '\'' +
                ", jobPosting=" + jobPosting +
                '}';
    }
}
