package billBoard;

import java.sql.Date;

public class BillgenBean {
    private String mobile;
    private String bill;
    private String billStatus;
    private Date datefrom;
    private Date dateend;
    private int missingdays;

    // Constructor
    public BillgenBean(String mobile, String bill, String billStatus, Date datefrom, Date dateend, int missingdays) {
        this.mobile = mobile;
        this.bill = bill;
        this.billStatus = billStatus;
        this.datefrom = datefrom;
        this.dateend = dateend;
        this.missingdays = missingdays;
    }

    // Getter and Setter methods for mobile, bill, and billStatus (already present)

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    // Getter and Setter methods for datefrom, dateend, and missingdays

    public Date getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Date datefrom) {
        this.datefrom = datefrom;
    }

    public Date getDateend() {
        return dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

    public int getMissingdays() {
        return missingdays;
    }

    public void setMissingdays(int missingdays) {
        this.missingdays = missingdays;
    }
}
