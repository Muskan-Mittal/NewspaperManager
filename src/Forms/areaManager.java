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

public class areaManager 
{
	static Connection con;
	GridPane grid;
	Text title, tarea;
	ComboBox<String> carea;
	Button save;
	HBox hbox;
	PreparedStatement pst;
	int x;
	ArrayList<String> area;

	
	areaManager()  {
		Stage stage = new Stage(); 
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
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		
		//-----text--------------
		title = new Text("Area Manager");
		tarea = new Text("Area");
		title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 0, 0, 3, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(0, 0, 0, 0) );
		tarea.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 20));
     	grid.add(title, 0, 0, 3, 1);
		grid.add(tarea, 0, 2);
		
		//ArrayList---------------------
		area = new ArrayList<String>();
		area.add("-Add-");
		
		//Combo--------------------------
		carea = new ComboBox<String>();
		carea.getItems().addAll(area);
		carea.setEditable(true);
		carea.getSelectionModel().select(0);
		carea.setPrefWidth(200);
		carea.setVisibleRowCount(4);
		grid.add(carea, 1, 2,2,1);	
		doFillUids();
        
		save = new Button("SAVE");
		save.setPrefSize(100, 25);
		save.setStyle("-fx-background-color:#96D6FB");
		hbox = new HBox();
		hbox.getChildren().add(save);
		hbox.setAlignment(Pos.CENTER);
		grid.add(hbox, 1, 3);
		hbox.setPadding(new Insets(20,0,0,0));
		System.out.println("Fine");
		save.setOnAction(e->add());
		
		//------scene------------------
		Scene scene = new Scene(grid,600, 400);
		stage.setScene(scene);
		stage.show();		
	}
	
private Object add() {
		
		Alert al = new Alert(null);
		if(((TextField)carea.getEditor()).getText().isEmpty())
		{
			al = new Alert(AlertType.ERROR);
			al.setContentText("You haven't filled the required field :(");
			al.show();
		}
		else
		{
			try {
				pst = con.prepareStatement("INSERT INTO Areas VALUES(?)");
				pst.setString(1,((TextField)carea.getEditor()).getText());
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
				al.setContentText("You have successfully added the area!!!");
			}
			
			al.setTitle("Add Area Prompt");
			al.show();
		}
		doFillUids();
		return null;
	}

void doFillUids()
{
	carea.getItems().clear();
	try {
		ArrayList<String> lst = new ArrayList<String>();
	pst=con.prepareStatement("select Area from Areas" );
	ResultSet rst= pst.executeQuery();
	while(rst.next())
	{
	String p=rst.getString("Area");
	lst.add(p);
	}
	carea.getItems().addAll(lst);
	rst.close();
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}
	

}