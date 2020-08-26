package com.model;

import com.model.Profile;
import com.model.Users;

import javax.persistence.*;

@Entity
@Table(name = "PROFILE_SKILL")
public class ProfileSkill {
    @Id
    @Column(name="PROFILE_SKILL_ID", columnDefinition="serial primary key")
    @GeneratedValue(strategy=GenerationType.IDENTITY) // the table creates this value
    private int profileSkillId;

    @Column(name = "PROFILE_SKILL_NAME")
    private String profileSkillName;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="PROFILE", referencedColumnName="PROFILE_ID", columnDefinition="INT", nullable = false)
    private Profile profile;

    public ProfileSkill() {
    }

    public int getProfileSkillId() {
        return profileSkillId;
    }

    public void setProfileSkillId(int profileSkillId) {
        this.profileSkillId = profileSkillId;
    }

    public String getProfileSkillName() {
        return profileSkillName;
    }

    public void setProfileSkillName(String profileSkillName) {
        this.profileSkillName = profileSkillName;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ProfileSkill{" +
                "profileSkillId=" + profileSkillId +
                ", profileSkillName='" + profileSkillName + '\'' +
                ", profile=" + profile +
                '}';
    }
}
