package Forms;

public class tableBill {

	int CId, state, MM, YY;
	double Bill;
	
	tableBill(){}
	
	public tableBill(int cId, double bill, int state, int mM, int yY) {
		super();
		CId = cId;
		this.state = state;
		MM = mM;
		YY = yY;
		Bill = bill;
	}

	public int getCId() {
		return CId;
	}
	public void setCId(int cId) {
		CId = cId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getMM() {
		return MM;
	}
	public void setMM(int mM) {
		MM = mM;
	}
	public int getYY() {
		return YY;
	}
	public void setYY(int yY) {
		YY = yY;
	}
	public double getBill() {
		return Bill;
	}
	public void setBill(double bill) {
		Bill = bill;
	}
	
	
	
}
