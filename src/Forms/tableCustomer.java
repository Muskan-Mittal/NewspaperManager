package Forms;

public class tableCustomer {
	int ID;
	String CName, Adrs, Mob, Ar, Hawker, Npaper;
	
	tableCustomer(){
	}
	
	public tableCustomer(int iD, String cName, String adrs, String mob, String ar, String hawker, String npaper) {
		super();
		ID = iD;
		CName = cName;
		Adrs = adrs;
		Mob = mob;
		Ar = ar;
		Hawker = hawker;
		Npaper = npaper;
	}

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCName() {
		return CName;
	}
	public void setCName(String cName) {
		CName = cName;
	}
	public String getAdrs() {
		return Adrs;
	}
	public void setAdrs(String adrs) {
		Adrs = adrs;
	}
	public String getMob() {
		return Mob;
	}
	public void setMob(String mob) {
		Mob = mob;
	}
	public String getAr() {
		return Ar;
	}
	public void setAr(String ar) {
		Ar = ar;
	}
	public String getHawker() {
		return Hawker;
	}
	public void setHawker(String hawker) {
		Hawker = hawker;
	}
	public String getNpaper() {
		return Npaper;
	}
	public void setNpaper(String npaper) {
		Npaper = npaper;
	}
	
}
