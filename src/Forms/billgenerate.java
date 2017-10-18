package Forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class billgenerate 
{
	static Connection con;
	
	GridPane grid;
	Text title, tcid, tpp, tprice, tstdate, tenddate, amt,info;
	TextField cid;
	Button nw, genbill, search, bill;
	HBox hbox1, hbox2, hbox3, hbox4, hbox5;
	PreparedStatement pst1, pst2, pst3;
	ResultSet rs1, rs2;
	int x;
	ComboBox<String>mm, yy;
	ListView<String> nps;
	ListView<Double>prc;
	ArrayList<String> npaper, mnth, yr;
	ArrayList<Double>price, rate;
	Calendar cal;
	DatePicker dps, dpe;
	billgenerate() {
		Stage stage = new Stage();
		stage.setTitle("Calculate bill");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewspaperProject", "root", "muskan");
			System.out.println("ok");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//------grid----------
	    grid = new GridPane();
		grid.setGridLinesVisible(false);
		//grid.setPadding(new Insets(100,0,0,160));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		
		//-----text--------------
		title = new Text("Bill Generator");
		tcid = new Text("CustomerId");
		tpp = new Text("Newspapers");
		tprice = new Text("Rates");
		tstdate = new Text("Start Date");
		tenddate = new Text("End Date");
		info = new Text("Individual Bill");
		amt = new Text("");
		title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 0, 0, 5, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(30, 0, 0, 0) );
		GridPane.setConstraints(tpp, 0, 6, 2, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(0, 0, 0, 0) );
		GridPane.setConstraints(tprice, 1, 6, 2, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(0, 0, 0, 0) );
		GridPane.setConstraints(info, 2, 1, 3, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(30, 0, 0, 0) );
		tcid.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		tstdate.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		tenddate.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		tpp.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		tprice.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		amt.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		info.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 20));
     	grid.add(title, 0, 0, 5, 1);
		grid.add(tcid, 2, 2);
		grid.add(tstdate, 2, 3);
		grid.add(tenddate, 2, 4);
		grid.add(tpp, 2, 5);
		grid.add(tprice, 3, 5);
		grid.add(amt, 2, 8, 2,1);
		grid.add(info, 2, 1, 3, 1);
		
		tpp.setVisible(false);
		tprice.setVisible(false);
		
		//-------textField-------------
		cid = new TextField();
		cid.setPromptText("Enter customer id");
		cid.setPrefWidth(100);
		grid.add(cid, 3, 2);
		
		//ArrayList---------------------
		mnth = new ArrayList<String>();
		mnth.add("-Month-"); mnth.add("January"); mnth.add("February"); mnth.add("March"); mnth.add("April");
		mnth.add("May"); mnth.add("June"); mnth.add("July"); mnth.add("August"); mnth.add("September");
		mnth.add("October"); mnth.add("November"); mnth.add("December");
		yr = new ArrayList<String>();
		yr.add("-Year-"); yr.add("2017"); yr.add("2018"); yr.add("2019"); yr.add("2020"); yr.add("2021");
		yr.add("2022"); yr.add("2023"); yr.add("2024"); yr.add("2025"); yr.add("2026"); yr.add("2027");

		//Combo--------------------------
		mm = new ComboBox<String>();
		mm.getItems().addAll(mnth);
		mm.setEditable(false);
		mm.getSelectionModel().select(0);
		mm.setPrefWidth(120);
		mm.setVisibleRowCount(4);
		
		yy = new ComboBox<String>();
		yy.getItems().addAll(yr);
		yy.setEditable(false);
		yy.getSelectionModel().select(0);
		yy.setPrefWidth(100);
		yy.setVisibleRowCount(4);
		
		hbox1 = new HBox();
		hbox1.getChildren().addAll(mm, yy);
		grid.add(hbox1, 0, 3, 1, 1);
		hbox1.setSpacing(20);
		hbox1.setAlignment(Pos.CENTER);
		
		nps = new ListView<String>();
		prc = new ListView<Double>();
		nps.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		nps.setPrefSize(120,70);
		prc.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		prc.setPrefSize(60,70);
		hbox5 = new HBox();
		hbox5.getChildren().addAll(nps, prc);
		hbox5.setSpacing(20);
		hbox5.setAlignment(Pos.CENTER);
		grid.add(hbox5, 2, 6, 3, 1); 
		nps.setVisible(false); prc.setVisible(false);
		
		//buttons------------------------------------
		search = new Button("FETCH");
		nw = new Button("NEW");
		bill = new Button("BILL");
		genbill = new Button("GENERATE BILL");
		search.setStyle("-fx-background-color:#F4CA70");
		search.setPrefSize(70, 25);
		nw.setPrefSize(80, 25);
		bill.setPrefSize(80, 25);
		genbill.setPrefSize(120, 25);

		nw.setStyle("-fx-background-color:#96D6FB");
		bill.setStyle("-fx-background-color:#96D6FB");
		genbill.setStyle("-fx-background-color:#96D6FB");

		hbox3 = new HBox();   hbox2 = new HBox();   hbox4 = new HBox();
		hbox2.getChildren().add(genbill);
		hbox3.getChildren().addAll(search);
		hbox4.getChildren().addAll(bill, nw);
		grid.add(hbox2, 0, 4, 1, 1);
		hbox2.setAlignment(Pos.CENTER);
		grid.add(hbox3, 4, 2);
		grid.add(hbox4, 2, 7, 3, 1);
		hbox4.setSpacing(25);
		hbox4.setAlignment(Pos.CENTER);
		
		//Separator---------------------------
		Separator sep = new Separator();
        sep.setValignment(VPos.TOP);
        sep.setOrientation(Orientation.VERTICAL);
        GridPane.setConstraints(sep, 1, 2);
        GridPane.setRowSpan(sep, 7);
        grid.getChildren().add(sep );
        sep.setPadding(new Insets(0, 25, 0, 25));
        
        //dates------------------------------
        cal=Calendar.getInstance();
		dps=new DatePicker();
		dps.setStyle("-fx-font-size:12");
		dps.setPromptText("Select Date");
		dps.setPrefSize(140, 25);
		dps.requestFocus();
		grid.add(dps, 3, 3, 2, 1);
		
		dpe=new DatePicker();
		dpe.setStyle("-fx-font-size:12");
		dpe.setPromptText("Select Date");
		dpe.setPrefSize(140, 25);
		dpe.requestFocus();
		grid.add(dpe, 3, 4, 2, 1);
        
        //events-----------------------------
        search.setOnAction(e->doSearch());
        bill.setOnAction(e->dobill());
        genbill.setOnAction(e->doGenbill());
        nw.setOnAction(e->clear());
        
      //------scene------------------
		Scene scene = new Scene(grid, 950, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	private Object clear() {
		cid.clear();
		nps.getItems().clear();
		nps.setVisible(false);
		prc.getItems().clear();
		prc.setVisible(false);
		((TextField)dps.getEditor()).clear();
		((TextField)dpe.getEditor()).clear();
		tpp.setVisible(false);
		tprice.setVisible(false);
		amt.setVisible(false);
		return null;
	}

	int noOfDays(){
		YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(yy.getSelectionModel().getSelectedItem()), mm.getSelectionModel().getSelectedIndex());
		int daysInMonth = yearMonthObject.lengthOfMonth(); //28  
		return daysInMonth;
	}

	private Object doGenbill() {
		
	    	rate = new ArrayList<Double>();
	    	try {
	    		pst1=con.prepareStatement("SELECT * FROM CustomerInfo ORDER BY Id");
	    		rs1=pst1.executeQuery();
	    		String all; String sa[];
	    		while (rs1.next()){
	    			all = rs1.getString("Npaper");
					sa = all.split(",");
					for(String v:sa){
						pst2=con.prepareStatement("SELECT Price FROM Papertypes WHERE NName=?");
						pst2.setString(1, v);
						rs2=pst2.executeQuery();
						if(rs2.next())
						{
							rate.add(rs2.getDouble("Price"));
						}
						else
						rs2.close();
					}
					double net=0;
					for(Double d : rate){
						net += d;
					}
					net*= noOfDays();
					pst3=con.prepareStatement("INSERT INTO Bills VALUES(?,?,?,?,?)");
					pst3.setInt(1,rs1.getInt("Id"));
					pst3.setDouble(2, net);
					pst3.setInt(3, 0);
					pst3.setInt(4, mm.getSelectionModel().getSelectedIndex() );
					pst3.setInt(5, Integer.parseInt(yy.getSelectionModel().getSelectedItem()) );
					x=pst3.executeUpdate();
					pst3.close();
			} 
	    	
	    	}catch (SQLException e) {
				e.printStackTrace();
			}
        
		return null;
	}

	private Object dobill() {
		ObservableList<Integer> idx;
		ObservableList<Double> pc;
		
		idx=nps.getSelectionModel().getSelectedIndices();
		for(Integer index : idx){
		int c=index;
		prc.getSelectionModel().select(c);
		}
		pc=prc.getSelectionModel().getSelectedItems();
		double net=0;
		for(Double d : pc){
			net += d;
		}
		
		String dts = dps.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String dte = dpe.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		System.out.println( dts + "  " + dte);
		String s1 = dts.substring(0, 2);  String s2 = dte.substring(0, 2);
		int a = Integer.parseInt(s2)-Integer.parseInt(s1) + 1;
		net *= a;
		amt.setText("Amount: "+net);
		return null;
	}

	private Object doSearch() {
		
		npaper= new ArrayList<String>(); 
		price= new ArrayList<Double>(); 
		String []sa; String all;
		ResultSet rs, rs2;
		if(cid.getText().isEmpty()){
			Alert al = new Alert(AlertType.ERROR);
			al.setContentText("You haven't filled the required field :( ");
			al.show();
		}
		else
		try {
			pst1=con.prepareStatement("SELECT * FROM CustomerInfo WHERE Id=?");
			pst1.setInt(1,Integer.parseInt(cid.getText()));
			rs=pst1.executeQuery();
			
			if(rs.next())
			{
				all = rs.getString("Npaper");
				sa = all.split(",");
				for(String v:sa){
					nps.getItems().add(v);
					
					pst2=con.prepareStatement("SELECT Price FROM Papertypes WHERE NName=?");
					pst2.setString(1, v);
					rs2=pst2.executeQuery();
					
					if(rs2.next())
					{
						price.add(rs2.getDouble("Price"));
					}
					else
					rs2.close();
				}
				
				for(Double item:price)
				{
				prc.getItems().add(item);
				}
				nps.setVisible(true); 
				prc.setVisible(true);
				tpp.setVisible(true);
				tprice.setVisible(true);
			}
			else
				System.out.println("Done search");
			rs.close(); 
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
