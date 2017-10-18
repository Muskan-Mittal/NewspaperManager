package Forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewsprMaster 
{
	static Connection con;
	GridPane grid;
	Text title, tnpname, tprice;
	TextField price;
	ComboBox<String> npname;
	ArrayList<String> types;
	Button search, nw, save, del, update, close;
	HBox hbox1, hbox2, hbox3;
	PreparedStatement pst;
	int x;

	NewsprMaster() {
		Stage stage = new Stage();
		stage.setTitle("Add Newspaper");
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
		title = new Text("Mr. Newspaper");
		tnpname = new Text("Newspaper Name");
		tprice = new Text("Price");
		title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 1, 0, 2, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(50, 0, 0, 0) );
		tnpname.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		tprice.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
     	grid.add(title, 0, 0, 2, 1);
		grid.add(tnpname, 0, 2);
		grid.add(tprice, 0, 5);
		
		//-------textField-------------
		price = new TextField();
		price.setPrefWidth(200);
		price.setPromptText("Enter price of newspaper");
		grid.add(price, 0, 6);
		
		//ArrayList---------------------
		types = new ArrayList<String>();
		types.add("-Select-");
		
		//Combo--------------------------
		npname = new ComboBox<String>();
		npname.getItems().addAll(types);
		npname.setEditable(true);
		npname.getSelectionModel().select(0);
		npname.setPrefWidth(200);
		npname.setVisibleRowCount(4);
		grid.add(npname, 0, 3);
		
		doFillUids();
		
		search = new Button("SEARCH");
		nw = new Button("NEW");
		update = new Button("UPDATE");
		del = new Button("REMOVE");
		save = new Button("SAVE");
		close = new Button("CLOSE");
		search.setStyle("-fx-background-color:#F4CA70");
		search.setPrefSize(85, 25);
		nw.setPrefSize(75, 25);
		save.setPrefSize(75, 25);
		del.setPrefSize(75, 25);
		close.setPrefSize(75, 25);
		update.setPrefSize(75, 25);
		nw.setStyle("-fx-background-color:#96D6FB");
		close.setStyle("-fx-background-color:#96D6FB");
		del.setStyle("-fx-background-color:#96D6FB");
		update.setStyle("-fx-background-color:#96D6FB");
		save.setStyle("-fx-background-color:#96D6FB");
		hbox3 = new HBox();  hbox1 = new HBox();   hbox2 = new HBox();
		hbox1.getChildren().add(search);
		hbox2.getChildren().addAll(nw, save, del );
		grid.add(hbox1, 1, 3);
		grid.add(hbox2, 0, 8, 2, 1);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setSpacing(20);
	    hbox3.setAlignment(Pos.CENTER);
	    hbox3.setSpacing(20);
	    hbox3.getChildren().addAll(update, close);
	    grid.add(hbox3, 0, 9, 2, 1);
	    
	  //-----events-----------------
		save.setOnAction(e->add());
		nw.setOnAction(e->newData());
		del.setOnAction(e->delete());
		update.setOnAction(e->update());
		search.setOnAction(e->doSearch());
		close.setOnAction(e->{
			stage.close();
		});
		
		//------scene------------------
		Scene scene = new Scene(grid,800, 500);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private Object newData() {
		npname.getSelectionModel().select(0);
		price.clear();
		doFillUids();
		return null;
	}

	private Object delete() {
		
			try {
				pst = con.prepareStatement("DELETE FROM Papertypes WHERE NName=?");
				pst.setString(1, npname.getSelectionModel().getSelectedItem());
				x = pst.executeUpdate();
				Alert al = new Alert(null);
				if(x==0){
					al = new Alert(AlertType.ERROR);
					al.setContentText("INVALID ID");
				}
				else{
					al = new Alert(AlertType.CONFIRMATION);
					al.setContentText("You have successfully deleted the user!!!");
				}
				
				al.setTitle("Remove Newspaper Prompt");
				al.show();
			}
				
			 catch (SQLException e) {
				e.printStackTrace();
			}			
			
		return null;
		
	}

	void update(){
		
		try {
			pst = con.prepareStatement("UPDATE Papertypes SET Price=? WHERE NName=?");
			pst.setString(2,((TextField)npname.getEditor()).getText());
			pst.setString(1, price.getText());
			x = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Alert al = new Alert(AlertType.CONFIRMATION);
		if(x==0){
			al.setContentText("INVALID ID");
		}
		else{
			al.setContentText("You have successfully updated the price!!!");
		}
		
		al.setTitle("Update Newspaper Prompt");
		al.show();
	}

	private Object add() {
		
		Alert al = new Alert(null);
		if(((TextField)npname.getEditor()).getText().isEmpty() || price.getText().isEmpty())
		{
			al = new Alert(AlertType.ERROR);
			al.setContentText("You haven't filled the required fields :(");
			al.show();
		}
		else
		{
			try {
				pst = con.prepareStatement("INSERT INTO Papertypes VALUES(?,?)");
				pst.setString(1,((TextField)npname.getEditor()).getText());
				pst.setString(2, price.getText());
				x = pst.executeUpdate();
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
				
			al = new Alert(AlertType.CONFIRMATION);
			if(x==0){
				al.setContentText("INVALID ID");
			}
			else{
				al.setContentText("You have successfully added the newspaper!!!");
			}
			
			al.setTitle("Add Newspaper Prompt");
			al.show();
		}
		doFillUids();
		return null;
	}
	
	void doSearch()
	{
		try {
			pst=con.prepareStatement("SELECT * FROM Papertypes WHERE NName=?");
			pst.setString(1, npname.getSelectionModel().getSelectedItem());
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				price.setText(rs.getString("Price"));
			}
			else
				System.out.println("Done search");
			rs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//=========================
	void doFillUids()
	{
	
	try {
		npname.getItems().clear();
		ArrayList<String> lst = new ArrayList<String>();
	pst=con.prepareStatement("select NName from Papertypes" );
	ResultSet rst= pst.executeQuery();
	while(rst.next())
	{
	String p=rst.getString("NName");
	lst.add( p);
	}
	npname.getItems().addAll(lst);
	rst.close();
	
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}

}
