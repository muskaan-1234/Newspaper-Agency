package tablesview;

public class HawkerBean {
	public String hname;
	String mobile;
	String alloareas;
	String doj;
		
	public HawkerBean() {}
	public HawkerBean(String hname, String mobile, String alloareas, String doj) {
		super();
		this.hname = hname;
		this.mobile = mobile;
		this.alloareas = alloareas;
		this.doj = doj;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	 public String getAllo_areas() {
	        return alloareas;
	    }
	 public void setAllo_areas(String alloareas) {
	        this.alloareas = alloareas;
	    }
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	
	
	
}
