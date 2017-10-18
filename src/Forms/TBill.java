package Forms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TBill
{
	static Connection con;
	GridPane grid;
	Text title;
	Stage stage1;
	ComboBox<String> mm, yy;
	HBox hbox1, hbox2, hbox3;
	RadioButton paid,  due, all;
	Button print, exp;
	TableView<tableBill> tbl;
	PreparedStatement pst1,pst2;
	int x;
	ArrayList<String> mnth,yr;
	@SuppressWarnings("unchecked")
	TBill() {
		Stage stage = new Stage();
		stage.setTitle("Bill details");
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
		grid.setHgap(15);
		grid.setVgap(15);
		
		//-----text--------------
		title = new Text("Bill Watcher");
		title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 0, 0, 7, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(20, 0, 20, 0) );
     	grid.add(title, 0, 0, 7, 1);
     	
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
		grid.add(hbox1, 0, 1, 7, 1);
		hbox1.setSpacing(20);
		hbox1.setAlignment(Pos.CENTER);		
		
		//radio buttons------------------------
		hbox2 = new HBox();
		paid = new RadioButton("Fully paid");
		due = new RadioButton("Balance due");
		all = new RadioButton("All");
		hbox2.getChildren().addAll(paid, due, all);
		hbox2.setSpacing(30);
		grid.add(hbox2, 0, 2, 7, 1);
		hbox2.setPadding(new Insets(0,0,30,0));
		hbox2.setAlignment(Pos.CENTER);
		ToggleGroup grp = new ToggleGroup();
		paid.setToggleGroup(grp);
		due.setToggleGroup(grp);
		all.setToggleGroup(grp);
		
		//table---------------------------------------
		tbl=new TableView<>();
		TableColumn<tableBill, Integer> col_id=new TableColumn<>("Customer Id");
		col_id.setMinWidth(100);
		col_id.setCellValueFactory(new PropertyValueFactory<>("CId"));
		TableColumn<tableBill, Double> col_bill=new TableColumn<>("Bill");
		col_bill.setMinWidth(100);
		col_bill.setCellValueFactory(new PropertyValueFactory<>("Bill"));
		TableColumn<tableBill, Integer> col_status=new TableColumn<>("Status");
		col_status.setMinWidth(80);
		col_status.setCellValueFactory(new PropertyValueFactory<>("state"));
		TableColumn<tableBill, Integer> col_mm=new TableColumn<>("Month");
		col_mm.setMinWidth(80);
		col_mm.setCellValueFactory(new PropertyValueFactory<>("MM"));
		TableColumn<tableBill, Integer> col_yy=new TableColumn<>("Year");
		col_yy.setMinWidth(80);
		col_yy.setCellValueFactory(new PropertyValueFactory<>("YY"));
		tbl.getColumns().addAll(col_id,col_bill,col_status, col_mm ,col_yy );
		grid.add(tbl, 0, 3, 7, 1);
		
		//button-----------------------
		print = new Button("PRINT");
		exp = new Button("Export");
		print.setStyle("-fx-background-color:#F4CA70");
		exp.setStyle("-fx-background-color:#F4CA70");
		hbox3 = new HBox();
		hbox3.getChildren().addAll(print, exp); hbox3.setSpacing(20);
		grid.add(hbox3, 0, 4, 7, 1); hbox3.setAlignment(Pos.CENTER);
		
		//events------------------------
		all.setOnAction(e->{tbl.setItems(getRows());});
		paid.setOnAction(e->{tbl.setItems(getPaid());});
		due.setOnAction(e->{tbl.setItems(getDue());});
		print.setOnAction(e->doprint(stage1));
		exp.setOnAction(e->{
			try {
				writeExcel();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		//------scene------------------
		Scene scene = new Scene(grid, 850, 700);
		stage.setScene(scene);
		stage.show();			
		
	}
	ObservableList<tableBill>ary;
	
	public void writeExcel() throws Exception {
	    Writer writer = null;
	    try {
	        File file = new File("E:\\Word documents\\bill.csv");
	        writer = new BufferedWriter(new FileWriter(file));
	        String text="Customer ID,Amount,Status\n";
	        writer.write(text);
	        for (tableBill tbill : ary) {
	        	
	            text = tbill.getCId() + "," + tbill.getBill() + "," + tbill.getState() + "\n";
	            writer.write(text);
	            System.out.println(text);
	        }
	        System.out.println("Exported");
	        showMsg("Table has been exported to excel");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    finally {

	         writer.flush();
	         writer.close();
	    } 
	}
	
	private void showMsg(String string) {
		Alert al = new Alert(AlertType.CONFIRMATION);
		al.setContentText(string);
		al.show();
		
	}
	final Label jobStatus = new Label();

	void pageSetup(Node node, Stage owner) 
	{
		// Create the PrinterJob
		PrinterJob job = PrinterJob.createPrinterJob();
		
		if (job == null) 
		{
			return;
		}
		
		// Show the page setup dialog
		boolean proceed = job.showPageSetupDialog(owner);
		Optional<String> result = new TextInputDialog().showAndWait();
		if (result.isPresent()) {
			print(job, node);
			System.out.println("Printing");
		} else {
		    System.out.println("Cancelled");
		}
		//if (proceed) 
		{
			
		}
	}
	
	void print(PrinterJob job, Node node) 
	{
		// Set the Job Status Message
		jobStatus.textProperty().bind(job.jobStatusProperty().asString());
		
		// Print the node
		boolean printed = job.printPage(node);
	
		if (printed) 
		{
			job.endJob();
		}
	}	

	public void doprint(Stage stage1)
	{
		  /* PrinterJob PJ = PrinterJob.createPrinterJob();

		    if(PJ != null && PJ.showPageSetupDialog(stage1.getScene().getWindow())){
		        boolean Success = PJ.printPage(grid);
		        if(Success){
		            PJ.endJob();
		        }
		    }
		Printcode p = new Printcode();*/
		pageSetup(tbl, stage1);
	}
	private ObservableList<tableBill> getPaid() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from Bills where MM=? and YY=? and state=?");
		pst1.setInt(1, mm.getSelectionModel().getSelectedIndex());
		pst1.setInt(2, Integer.parseInt(yy.getSelectionModel().getSelectedItem()));
		pst1.setInt(3, 1);
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.addAll(new tableBill(rs1.getInt("CId"),rs1.getDouble("Bill"), rs1.getInt("state"),rs1.getInt("MM") , rs1.getInt("YY")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}
	private ObservableList<tableBill> getDue() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from Bills where MM=? and YY=? and state=?");
		pst1.setInt(1, mm.getSelectionModel().getSelectedIndex());
		pst1.setInt(2, Integer.parseInt(yy.getSelectionModel().getSelectedItem()));
		pst1.setInt(3, 0);
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.addAll(new tableBill(rs1.getInt("CId"),rs1.getDouble("Bill"), rs1.getInt("state"),rs1.getInt("MM") , rs1.getInt("YY")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}
	
	private ObservableList<tableBill> getRows() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from Bills where MM=? and YY=?");
		pst1.setInt(1, mm.getSelectionModel().getSelectedIndex());
		pst1.setInt(2, Integer.parseInt(yy.getSelectionModel().getSelectedItem()));
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.addAll(new tableBill(rs1.getInt("CId"),rs1.getDouble("Bill"), rs1.getInt("state"),rs1.getInt("MM") , rs1.getInt("YY")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}

}
