package customerDashtv;

public class CustomerBean {
    private String name;
    private String mobile;
    private String area;
    private String email;
    private String spapers;

    public CustomerBean() {}

    public CustomerBean(String name, String mobile, String area, String email, String spapers) {
        super();
        this.name = name;
        this.mobile = mobile;
        this.area = area;
        this.email = email;
        this.spapers = spapers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpapers() {
        return spapers;
    }

    public void setSpapers(String spapers) {
        this.spapers = spapers;
    }
}
