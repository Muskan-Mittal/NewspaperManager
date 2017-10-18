package Forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class billCollect 
{
	static Connection con;
	
	GridPane grid;
	Text title, tcid;
	Button update, due;
	HBox hbox1;
	VBox vbox;
	PreparedStatement pst1, pst2, pst3;
	ResultSet rs1, rs2;
	int x;
	ComboBox<String>mm, yy;
	ListView<Integer> id;
	ArrayList<String> mnth, yr;
	ArrayList<Integer>idk;

	billCollect() {
		Stage stage = new Stage();
		stage.setTitle("Update bill collection");
		try{
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
		title = new Text("Bill Collector");
		tcid = new Text("CustomerId");
		title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(30, 0, 0, 0) );
		GridPane.setConstraints(tcid, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(0, 0, 0, 0) );
		tcid.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
     	grid.add(title, 0, 0, 3, 1);
		grid.add(tcid, 0, 1);
	
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
		grid.add(hbox1, 1, 2, 3, 1);
		hbox1.setSpacing(20);
		hbox1.setAlignment(Pos.BASELINE_LEFT);	
		hbox1.setPadding(new Insets(10,0,0,30));
		
		id = new ListView<Integer>();
		id.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		id.setPrefSize(150,140);
		fillList();
		grid.add(id, 0, 2, 1, 4); 
		
		//buttons------------------------------------
		update = new Button("UpDate");
		update.setStyle("-fx-background-color:#F4CA70");
		update.setPrefSize(70, 25);
		due = new Button("Balance Due IDs");
		due.setStyle("-fx-background-color:#96D6FB");
		due.setPrefSize(125, 25);

		vbox = new VBox();  
		vbox.getChildren().addAll(due, update);
		grid.add(vbox, 1, 3, 1, 5);
		vbox.setAlignment(Pos.BASELINE_LEFT);
		vbox.setPadding(new Insets(5,0,0,30));
		vbox.setSpacing(50);
		
		//events-----------------------------
        update.setOnAction(e->doUpdate());
        due.setOnAction(e->doFindIds());
		
		//------scene------------------
		Scene scene = new Scene(grid, 750, 450);
		stage.setScene(scene);
		stage.show();		
	}

	private Object doFindIds() {
		id.getItems().clear();
		idk = new ArrayList<Integer>();
		
		try {
			pst1=con.prepareStatement("SELECT * FROM Bills WHERE MM=? AND YY=? ORDER BY CId ");
			pst1.setInt(1, mm.getSelectionModel().getSelectedIndex());
			pst1.setInt(2, Integer.parseInt(yy.getSelectionModel().getSelectedItem()));
			rs1=pst1.executeQuery();
			while(rs1.next()){
				if(rs1.getInt("state")==0){
					idk.add(rs1.getInt("CId"));
				}
			}
			id.getItems().addAll(idk);
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object doUpdate() {
		ObservableList<Integer> duesid;
		try {
			duesid = id.getSelectionModel().getSelectedItems();
			for(Integer i : duesid){
				pst1=con.prepareStatement("UPDATE Bills SET state=? WHERE MM=? AND YY=? AND CId=?");
				pst1.setInt(2, mm.getSelectionModel().getSelectedIndex());
				pst1.setInt(3, Integer.parseInt(yy.getSelectionModel().getSelectedItem()));
				pst1.setInt(1, 1);
				pst1.setInt(4, i);
				x = pst1.executeUpdate();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void fillList() {
		idk = new ArrayList<Integer>();
		try {
			pst1=con.prepareStatement("SELECT * FROM CustomerInfo ORDER BY Id");
			rs1=pst1.executeQuery();
			
			while (rs1.next()){
    			idk.add(rs1.getInt("Id"));
			}
			id.getItems().addAll(idk);
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
