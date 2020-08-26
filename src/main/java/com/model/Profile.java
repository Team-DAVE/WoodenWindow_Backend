package com.model;

import javax.persistence.*;

@Entity
@Table(name = "PROFILE")
public class Profile {
    @Id
    @Column(name="PROFILE_ID", columnDefinition="serial primary key")
    @GeneratedValue(strategy=GenerationType.IDENTITY) // the table creates this value
    private int profileId;

    @Column(name = "PROFILE_NAME")
    private String profileName;

    @Column(name="RESUME", nullable = false)
    private String resume;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="USERS", referencedColumnName="USERS_ID", columnDefinition="INT", nullable = false)
    private Users user;

    public Profile() {
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", profileName='" + profileName + '\'' +
                ", resume='" + resume + '\'' +
                ", user=" + user +
                '}';
    }
}
