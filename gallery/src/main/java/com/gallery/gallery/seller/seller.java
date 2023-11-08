package com.gallery.gallery.seller;

public class seller {
    private Long id;
    private String company;
    private String pwd;
    private String tel;
    private String email;

    // 생성자
    public seller(Long id, String company, String pwd, String tel, String email) {
        this.id = id;
        this.company = company;
        this.pwd = pwd;
        this.tel = tel;
        this.email = email;
    }

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}