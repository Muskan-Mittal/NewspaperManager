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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


	public class TCustomer 
	{
		static Connection con;
		
	GridPane grid;
	Stage stage1;
	Text title, tarea, tgoogle, thawker;
	TextField google;
	ComboBox<String> chawker, carea;
	Button fetch, all, adv, print, exp,getlist;
	HBox hbox1, hbox2, hbox3, hbox4, hbox5, hg, hp;
	RadioButton bname,  bnews;
	TableView<tableCustomer> tbl;
	PreparedStatement pst1,pst2;
	int x;
	ArrayList<String> hawk, area;
	ObservableList<tableCustomer>ary;
	@SuppressWarnings("unchecked")
	public
	TCustomer() {
		Stage stage = new Stage();
		stage.setTitle("Customer details");
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
		title = new Text("Customer Details");
		tarea = new Text("Areas");
		tgoogle = new Text("Google");
		thawker = new Text("Hawkers");
		title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 0, 0, 4, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(20, 40, 20, 0) );
		tarea.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		tgoogle.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		thawker.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
     	grid.add(title, 0, 0, 2, 1);
		tgoogle.setVisible(false);  thawker.setVisible(false);
		
		//-------textField-------------
		google = new TextField();
		google.setPromptText("Write something");
		google.setPrefWidth(500);
		google.setVisible(false); 
		hg = new HBox();
		hg.getChildren().addAll(tgoogle,google); hg.setSpacing(10);
		grid.add(hg, 1, 2, 1, 1);
		hg.setPadding(new Insets(10,0,0,20)); hg.setAlignment(Pos.BOTTOM_LEFT);
		
		//radio buttons------------------------
		hbox4 = new HBox();
		bname = new RadioButton("By Customer's name");
		bnews = new RadioButton("By Newspaper name");
		hbox4.getChildren().addAll(bname, bnews);
		hbox4.setSpacing(50);
		grid.add(hbox4,1, 3, 1, 1);
		hbox4.setVisible(false); hbox4.setPadding(new Insets(0,0,0,95));
		ToggleGroup grp = new ToggleGroup();
		bname.setToggleGroup(grp);
		bnews.setToggleGroup(grp);
		
		//buttons------------------------------------
		fetch = new Button("FETCH");
		all = new Button("SHOW ALL");
		adv = new Button("Advanced options...");
		getlist = new Button("GET LIST");
		fetch.setStyle("-fx-background-color:#F4CA70");
		getlist.setStyle("-fx-background-color:#F4CA70");
		fetch.setPrefSize(65, 22);
		all.setPrefSize(90, 25);
		adv.setPrefSize(150, 25);
		all.setStyle("-fx-background-color:#96D6FB");
		adv.setStyle("-fx-background-color:#96D6FB");
		print = new Button("PRINT");
		exp = new Button("Export");
		print.setStyle("-fx-background-color:#F4CA70");
		exp.setStyle("-fx-background-color:#F4CA70");
		hp = new HBox();
		hp.getChildren().addAll(print, exp); hp.setSpacing(20);
		grid.add(hp, 2, 4, 1, 1); //hp.setAlignment(Pos.BASELINE_RIGHT);
		//hp.setVisible(false);

		//ArrayList---------------------
		hawk = new ArrayList<String>();
		hawk.add("-Select-");
		area = new ArrayList<String>();
		area.add("-Select-");
		
		//Combo--------------------------
		chawker = new ComboBox<String>();
		chawker.getItems().addAll(hawk);
		chawker.setEditable(true);
		chawker.getSelectionModel().select(0);
		chawker.setPrefWidth(160);
		chawker.setVisibleRowCount(4);
		
		carea = new ComboBox<String>();
		carea.getItems().addAll(area);
		carea.setEditable(false);
		carea.getSelectionModel().select(0);
		carea.setPrefWidth(160);
		carea.setVisibleRowCount(4);
		
		doFillUids();
		
		hbox1 = new HBox();   hbox2 = new HBox();   hbox3 = new HBox(); hbox5 = new HBox();
		hbox1.getChildren().addAll(tarea, carea, fetch);
		hbox2.getChildren().addAll(adv);
		hbox3.getChildren().addAll(thawker, chawker);
		hbox5.getChildren().add(all);
		grid.add(hbox5, 2, 0, 1, 1);
		grid.add(hbox1, 0, 1, 5, 1);
		hbox1.setAlignment(Pos.BASELINE_LEFT); 	hbox5.setPadding(new Insets(30, 0,0,0));;
		hbox2.setPadding(new Insets(15, 0,0,0));
		grid.add(hbox2, 0, 2, 1, 1);
		grid.add(hbox3, 1, 4, 2, 1);
		hbox1.setSpacing(25); hbox3.setSpacing(15); hbox3.setPadding(new Insets(0,0,0,20));
		hbox3.setVisible(false);
		
		//table---------------------------------------
		tbl=new TableView<>();
		TableColumn<tableCustomer, Integer> col_id=new TableColumn<>("Customer Id");
		col_id.setMinWidth(100);
		col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
		TableColumn<tableCustomer, String> col_name=new TableColumn<>("Name");
		col_name.setMinWidth(100);
		col_name.setCellValueFactory(new PropertyValueFactory<>("CName"));
		TableColumn<tableCustomer, String> col_mob=new TableColumn<>("Mobile No");
		col_mob.setMinWidth(110);
		col_mob.setCellValueFactory(new PropertyValueFactory<>("Mob"));
		TableColumn<tableCustomer, String> col_area=new TableColumn<>("Area Alotted");
		col_area.setMinWidth(110);
		col_area.setCellValueFactory(new PropertyValueFactory<>("Ar"));
		TableColumn<tableCustomer, String> col_addr=new TableColumn<>("Address");
		col_addr.setMinWidth(170);
		col_addr.setCellValueFactory(new PropertyValueFactory<>("Adrs"));
		TableColumn<tableCustomer, String> col_hawker=new TableColumn<>("Hawker");
		col_hawker.setMinWidth(110);
		col_hawker.setCellValueFactory(new PropertyValueFactory<>("Hawker"));
		TableColumn<tableCustomer, String> col_np=new TableColumn<>("Newspapers");
		col_np.setMinWidth(200);
		col_np.setCellValueFactory(new PropertyValueFactory<>("Npaper"));
		tbl.getColumns().addAll(col_id,col_name,col_addr, col_mob,col_area,col_hawker, col_np );
		grid.add(tbl, 0, 6,4,1); 
		
		//events------------------------
		all.setOnAction(e->{tbl.setItems(getRows());});
		adv.setOnAction(e->getOptions());
		fetch.setOnAction(e->{tbl.setItems(byArea());});
		bname.setOnAction(e->{tbl.setItems(byName());});
		bnews.setOnAction(e->{tbl.setItems(byNpaper());});
		exp.setOnAction(e->{
			try {
				writeExcel();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		print.setOnAction(e->doprint(stage1));
		chawker.getSelectionModel().selectedItemProperty().addListener((property,oldvalue,newvalue)->{
			String item = chawker.getSelectionModel().getSelectedItem();
			tbl.setItems(byHawker(item));
		});
		
		//------scene------------------
		Scene scene = new Scene(grid, 1100, 700);
		stage.setScene(scene);
		stage.show();
	}
	
	
	private void showMsg(String string) {
		Alert al = new Alert(AlertType.CONFIRMATION);
		al.setContentText(string);
		al.show();
		
	}
	private void writeExcel() throws Exception {
		System.out.println("St");
	    Writer writer = null;
	    System.out.println("Stwdd");
	    try {
	    	System.out.println("Started");
	        File file = new File("E:\\Word documents\\customer.csv");
	        writer = new BufferedWriter(new FileWriter(file));
	        String text="Customer ID,Name,Mobile,Address,Area,Hawker,Newspapers\n";
	        writer.write(text);
	        for (tableCustomer tc : ary) {

	            text = tc.getID() + "," + tc.getCName() + "," + tc.getMob() + "," +tc.getAdrs()+","+ tc.getAr()+","+ tc.getHawker()+","+tc.getNpaper() +"\n";
	            writer.write(text);
	        }
	        System.out.println("Exported");
	        showMsg("Table exported to excel");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    finally {

	         writer.flush();
	         writer.close();
	    } 
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
		if (proceed) 
		{}
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
	
	private ObservableList<tableCustomer> byHawker(String str) {
		ary=FXCollections.observableArrayList();
		try {
			pst1=con.prepareStatement("select * from CustomerInfo where Hawker=? ");
			pst1.setString(1, str);
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.add(new tableCustomer(rs1.getInt("ID"),rs1.getString("CName"), rs1.getString("Adrs"),rs1.getString("Mob") , rs1.getString("Ar"), rs1.getString("Hawker"), rs1.getString("Npaper")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
			
	}

	private ObservableList<tableCustomer> byNpaper() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from CustomerInfo where Npaper like '%"+google.getText()+"%' ");
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.add(new tableCustomer(rs1.getInt("ID"),rs1.getString("CName"), rs1.getString("Adrs"),rs1.getString("Mob") , rs1.getString("Ar"), rs1.getString("Hawker"), rs1.getString("Npaper")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}

	private ObservableList<tableCustomer> byName() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from CustomerInfo where CName like '%"+google.getText()+"%' ");
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.add(new tableCustomer(rs1.getInt("ID"),rs1.getString("CName"), rs1.getString("Adrs"),rs1.getString("Mob") , rs1.getString("Ar"), rs1.getString("Hawker"), rs1.getString("Npaper")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}

	private ObservableList<tableCustomer> byArea() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from CustomerInfo where Ar=? ");
		pst1.setString(1, carea.getSelectionModel().getSelectedItem());
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.add(new tableCustomer(rs1.getInt("ID"),rs1.getString("CName"), rs1.getString("Adrs"),rs1.getString("Mob") , rs1.getString("Ar"), rs1.getString("Hawker"), rs1.getString("Npaper")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}

	private Object getOptions() {
		
		tgoogle.setVisible(true);  thawker.setVisible(true); //hp.setVisible(true);
		hbox3.setVisible(true); hbox4.setVisible(true); google.setVisible(true);
		exp.setOnAction(e->{
			try {
				writeExcel();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		print.setOnAction(e->doprint(stage1));
		return null;
	}
	private ObservableList<tableCustomer> getRows() {
		ary=FXCollections.observableArrayList();
		try {
		pst1=con.prepareStatement("select * from CustomerInfo ");
		ResultSet rs1=pst1.executeQuery();
		while(rs1.next())
		{
		ary.add(new tableCustomer(rs1.getInt("ID"),rs1.getString("CName"), rs1.getString("Adrs"),rs1.getString("Mob") , rs1.getString("Ar"), rs1.getString("Hawker"), rs1.getString("Npaper")));
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return ary;
	}
	private void doFillUids() {

		try {
			carea.getSelectionModel().select(0);
		pst1=con.prepareStatement("select Area from Areas" );
		ResultSet rst= pst1.executeQuery();
		ArrayList<String> lst1 = new ArrayList<String>();
		
		
		while(rst.next())
		{
		String p=rst.getString("Area");
		lst1.add(p);
		}
		carea.getItems().addAll(lst1);
		rst.close();
		
		chawker.getSelectionModel().select(0);
		pst2=con.prepareStatement("select PName from Addpedlar" );
		ResultSet rst2= pst2.executeQuery();
		ArrayList<String> lst2 = new ArrayList<String>();
		
		
		while(rst2.next())
		{
		String q=rst2.getString("PName");
		lst2.add(q);
		}
		chawker.getItems().addAll(lst2);
		rst2.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		
		
	}

}
