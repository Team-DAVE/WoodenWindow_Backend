package com.model;

import javax.persistence.*;

@Entity
@Table(name = "BUSINESS")
public class Business {
    @Id
    @Column(name="BUSINESS_ID", columnDefinition="serial primary key")
    @GeneratedValue(strategy=GenerationType.IDENTITY) // the table creates this value
    private int businessId;

    @Column(name = "BUSINESS_NAME")
    private String businessName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "SUMMARY")
    private String summary;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="USERS", referencedColumnName="USERS_ID", columnDefinition="INT", nullable = false)
    private Users user;

    public Business() {
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Business{" +
                "businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", address='" + address + '\'' +
                ", summary='" + summary + '\'' +
                ", user=" + user +
                '}';
    }
}
