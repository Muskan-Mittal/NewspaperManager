package Forms;

import java.util.Date;

public class tableHawker {
	String PName, Mobile, ArAlotted, Address, IdPfPath;
	Date DOJoining;
	
	public tableHawker(String pName, String mobile, String arAlotted, String address, String idPfPath, Date dOJoining) {
		super();
		PName = pName;
		Mobile = mobile;
		ArAlotted = arAlotted;
		Address = address;
		IdPfPath = idPfPath;
		DOJoining = dOJoining;
	}
	public String getPName() {
		return PName;
	}
	public void setPName(String pName) {
		PName = pName;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getArAlotted() {
		return ArAlotted;
	}
	public void setArAlotted(String arAlotted) {
		ArAlotted = arAlotted;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getIdPfPath() {
		return IdPfPath;
	}
	public void setIdPfPath(String idPfPath) {
		IdPfPath = idPfPath;
	}
	public Date getDOJoining() {
		return DOJoining;
	}
	public void setDOJoining(Date dOJoining) {
		DOJoining = dOJoining;
	}
	
	
}
