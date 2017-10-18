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
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class THawkers 
{
	static Connection con;
	GridPane grid;
	Stage stage1;
	Text title;
	Button print, exp;
	HBox hbox1, hbox2;
	TableView<tableHawker> tbl;
	PreparedStatement pst1,pst2;
	int x;
	@SuppressWarnings("unchecked")
	THawkers(){
		Stage stage = new Stage();
		stage.setTitle("Hawker details");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewspaperProject", "root", "muskan");
			System.out.println("ok");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 //------grid----------
	    grid = new GridPane();
		grid.setGridLinesVisible(false);
		//grid.setPadding(new Insets(100,0,0,160));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		
		//-----text--------------
		title = new Text("Hawker Details");
		title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 0, 0, 7, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(40, 0, 30, 0) );
     	grid.add(title, 0, 0, 7, 1);
     	
     	//table---------------------------------------
     	tbl=new TableView<>();
		TableColumn<tableHawker, String> col_name=new TableColumn<>("Name");
		col_name.setMinWidth(100);
		col_name.setCellValueFactory(new PropertyValueFactory<>("PName"));
		TableColumn<tableHawker, String> col_mob=new TableColumn<>("Mobile No");
		col_mob.setMinWidth(110);
		col_mob.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
		TableColumn<tableHawker, String> col_area=new TableColumn<>("Area Alotted");
		col_area.setMinWidth(110);
		col_area.setCellValueFactory(new PropertyValueFactory<>("ArAlotted"));
		TableColumn<tableHawker, String> col_addr=new TableColumn<>("Address");
		col_addr.setMinWidth(170);
		col_addr.setCellValueFactory(new PropertyValueFactory<>("Address"));
		TableColumn<tableHawker, String> col_hawker=new TableColumn<>("Id Proof");
		col_hawker.setMinWidth(190);
		col_hawker.setCellValueFactory(new PropertyValueFactory<>("IdPfPath"));
		TableColumn<tableHawker, Date> col_date=new TableColumn<>("D.O.Joining");
		col_date.setMinWidth(100);
		col_date.setCellValueFactory(new PropertyValueFactory<>("DOJoining"));
		tbl.getColumns().addAll(col_name,col_addr, col_mob,col_area,col_hawker, col_date );
		grid.add(tbl, 0, 1,7,1);
		//tbl.setPadding(new Insets(30,0,0,0));
		tbl.setItems(getRows());
		
		//button-----------------------
		print = new Button("PRINT");
		exp = new Button("Export");
		print.setStyle("-fx-background-color:#F4CA70");
		exp.setStyle("-fx-background-color:#F4CA70");
		hbox1 = new HBox();
		hbox1.getChildren().addAll(print, exp); hbox1.setSpacing(20);
		grid.add(hbox1, 0, 2, 7, 1); hbox1.setAlignment(Pos.CENTER);
		
		//events========================
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
		Scene scene = new Scene(grid, 1100, 700);
		stage.setScene(scene);
		stage.show();		
		
	}
ObservableList<tableHawker>ary;
	
	public void writeExcel() throws Exception {
	    Writer writer = null;
	    try {
	        File file = new File("E:\\Word documents\\hawker.csv");
	        writer = new BufferedWriter(new FileWriter(file));
	        String text="Customer ID,Amount,Status\n";
	        writer.write(text);
	        for (tableHawker tbill : ary) {

	            text = tbill.getPName() + "," + tbill.getMobile() + "," + tbill.getArAlotted() + "\n";
	            writer.write(text);
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
		pageSetup(tbl, stage1);
	}
	
	private ObservableList<tableHawker> getRows() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from Addpedlar ");
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.add(new tableHawker(rs1.getString("PName"), rs1.getString("Mobile"), rs1.getString("ArAlotted"), rs1.getString("Address"),rs1.getString("IdPfPath"), rs1.getDate("DOJoining")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}

}
