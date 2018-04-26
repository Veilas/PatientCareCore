package src;


public class ServiceData {
	
	private int bed_id;
	private String request_time;
	private String service_time;
	private String serviced;
	
	public ServiceData() {
	    this.bed_id = -1;
		this.request_time = "";
		this.service_time = "";
		this.serviced = "";
    }
	
	public ServiceData(int bed_id, String request_time, String service_time, String serviced) {
		this.bed_id = bed_id;
		this.request_time = request_time;
		this.service_time = service_time;
		this.serviced = serviced;
	}
	
	public void print() {
	    System.out.println("bed_id = " + bed_id + " request_time = " + request_time + " serivce_time = " +
	    service_time + " serviced = " + serviced); 
	}
	
	public int getBed_id() {
		return bed_id;
	}

	public void setBed_id(int bed_id) {
		this.bed_id = bed_id;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getService_time() {
		return service_time;
	}

	public void setService_time(String service_time) {
		this.service_time = service_time;
	}

	public String getServiced() {
		return serviced;
	}

	public void setServiced(String serviced) {
		this.serviced = serviced;
	}

}
