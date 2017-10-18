package Forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class costumerEnroll 
{
	static Connection con;
	GridPane grid;
	ImageView icon;
	Text title, tname, tarea, tadr, tped, tmob, tnews;
	TextField cname, mob;
	ComboBox<String> carea, cped;
	TextArea adr;
	Button search, nw, enrol, del, update, close;
	HBox hbox1, hbox2;
	PreparedStatement pst1,pst2;
	int x;
	ListView<String> nps;
	ArrayList<String> area, npaper, ped;

	costumerEnroll() {
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
			//grid.setPadding(new Insets(100,0,0,160));
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(25);
			grid.setVgap(25);
			
			//-----text--------------
			title = new Text("Costumer Enrollment");
			tname = new Text("Name");
			tmob = new Text("Mobile No.");
			tarea = new Text("Area alloted");
			tped = new Text("Hawkers");
			tadr = new Text("Address");
			tnews = new Text("Newspaper");
			title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
			title.setFill(Color.BLACK);
			GridPane.setConstraints(title, 0, 0, 3, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(30, 0, 0, 0) );
			tped.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tname.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tadr.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tarea.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tmob.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tnews.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			GridPane.setValignment(tadr, VPos.TOP);
			GridPane.setValignment(tnews, VPos.TOP);
	     	grid.add(title, 0, 0, 3, 1);
			grid.add(tname, 0, 2);
			grid.add(tmob, 0, 4);
			grid.add(tarea, 0, 5);
			grid.add(tadr, 0, 3);
			grid.add(tped, 0, 6);
			grid.add(tnews, 0, 7);
			
			//-------textField-------------
			cname = new TextField();
			cname.setPromptText("Enter name");
			grid.add(cname, 1, 2);
			mob = new TextField();
			mob.setPromptText("Enter mobile no.");
			grid.add(mob, 1, 4);
			
			//ArrayList---------------------
			area = new ArrayList<String>();
			area.add("-Select-");
			ped = new ArrayList<String>();
			ped.add("-Select-");
			
			//Combo--------------------------
			carea = new ComboBox<String>();
			carea.getItems().addAll(area);
			carea.setEditable(false);
			carea.getSelectionModel().select(0);
			carea.setPrefWidth(230);
			carea.setVisibleRowCount(4);
			grid.add(carea, 1, 5);
			
			cped = new ComboBox<String>();
			cped.getItems().addAll(area);
			cped.setEditable(false);
			cped.getSelectionModel().select(0);
			cped.setPrefWidth(230);
			cped.setVisibleRowCount(4);
			grid.add(cped, 1, 6);
			
			doFillUids();
			carea.getSelectionModel().selectedItemProperty().addListener((property,oldValue,newValue)->{
				//String item=combo.getSelectionModel().getSelectedItem();
				ArrayList<String> lst2 = new ArrayList<String>();
				try {
					pst2=con.prepareStatement("select PName from Addpedlar where ArAlotted=?" );
					pst2.setString(1, carea.getSelectionModel().getSelectedItem());
					ResultSet rs2= pst2.executeQuery();
					
					while(rs2.next())
					{
					String p=rs2.getString("PName");
					lst2.add(p);
					}
					cped.getItems().addAll(lst2);
					rs2.close();
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				});
			
			nps = new ListView<String>();
			
			fillNPList();
			nps.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			nps.setPrefSize(50,130);
			grid.add(nps, 1, 7);
			
			//text area------------------------------
			adr = new TextArea();
	        adr.setPrefRowCount(3);
	        adr.setPrefColumnCount(20);
	        adr.setWrapText(true);
	        adr.setPrefWidth(120);
	        grid.add(adr, 1, 3);
	        String adrDefault = "";
	        adr.setText(adrDefault);
	        
	        //-----buttons----------------
			
			ImageView ad = new ImageView(new Image(getClass().getResourceAsStream("add1.png")));
			ad.setFitHeight(18);
			ad.setFitWidth(18);
			ImageView sv = new ImageView(new Image(getClass().getResourceAsStream("save.png")));
			sv.setFitHeight(18);
			sv.setFitWidth(18);
			ImageView up = new ImageView(new Image(getClass().getResourceAsStream("update.png")));
			up.setFitHeight(18);
			up.setFitWidth(18);
			ImageView rm = new ImageView(new Image(getClass().getResourceAsStream("remove.png")));
			rm.setFitHeight(18);
			rm.setFitWidth(18);
			ImageView cl = new ImageView(new Image(getClass().getResourceAsStream("close.png")));
			cl.setFitHeight(18);
			cl.setFitWidth(18);
			ImageView sr = new ImageView(new Image(getClass().getResourceAsStream("find.png")));
			sr.setFitHeight(18);
			sr.setFitWidth(18);
			
			search = new Button("SEARCH",sr);
			nw = new Button("New",ad);
			update = new Button("Update",up);
			del = new Button("Remove",rm);
			enrol = new Button("Enroll",sv);
			close = new Button("Exit",cl);
			search.setStyle("-fx-background-color:#F4CA70");
			search.setPrefSize(95, 25);
			nw.setPrefSize(73, 25);
			enrol.setPrefSize(82, 25);
			del.setPrefSize(96, 25);
			close.setPrefSize(75, 25);
			update.setPrefSize(88, 25);
			nw.setStyle("-fx-background-color:#96D6FB");
			close.setStyle("-fx-background-color:#96D6FB");
			del.setStyle("-fx-background-color:#96D6FB");
			update.setStyle("-fx-background-color:#96D6FB");
			enrol.setStyle("-fx-background-color:#96D6FB");
			hbox1 = new HBox();   hbox2 = new HBox();
			hbox1.getChildren().add(search);
			hbox2.getChildren().addAll(nw, enrol, update, del, close);
			grid.add(hbox1, 2, 2);
			grid.add(hbox2, 0, 9, 4, 1);
		    hbox2.setSpacing(30); 
		    
			//-----events-----------------
			enrol.setOnAction(e->add());
			nw.setOnAction(e->newData());
			del.setOnAction(e->delete());
			update.setOnAction(e->update());
			search.setOnAction(e->doSearch());
			close.setOnAction(e->{
				stage.close();
			});
			
			//------scene------------------
			
			stage.setTitle("Customer Enroll");
			Scene scene = new Scene(grid,1000, 700);
			stage.setScene(scene);
			stage.show();
					
		
	}
	
	private void fillNPList() {
		
		npaper =new ArrayList<String>();
		try {
			pst1=con.prepareStatement("select NName from Papertypes" );
			ResultSet rst= pst1.executeQuery();
			while(rst.next())
			{
			String p=rst.getString("NName");
			npaper.add(p);
			}
			for(String item:npaper)
			{
			nps.getItems().add(item);
			}
			
			rst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private Object newData() {
		ped.clear();
		adr.clear();
		carea.getSelectionModel().select(0);
		cname.clear();
		mob.clear();
		cped.getSelectionModel().select(0);
		nps.getSelectionModel().clearSelection();
		return null;
	}

	private Object delete() {
		
			try {
				pst2 = con.prepareStatement("DELETE FROM CustomerInfo WHERE CName=?");
				pst2.setString(1, cname.getText());
				x = pst2.executeUpdate();
				Alert al = new Alert(null);
				if(x==0){
					al = new Alert(AlertType.ERROR);
					al.setContentText("INVALID ID");
				}
				else{
					al = new Alert(AlertType.CONFIRMATION);
					al.setContentText("You have successfully removed customer!!!");
				}
				
				al.setTitle("Remove customer Prompt");
				al.show();
			}
				
			 catch (SQLException e) {
				e.printStackTrace();
			}			
			
		return null;
		
	}

	void update(){
		
		try {
			pst1 = con.prepareStatement("UPDATE CustomerInfo SET Adrs=?, Mob=?, Ar=?, Hawker=?, Npaper=? WHERE CName=?");
			pst1.setString(1, adr.getText());
			pst1.setString(3,carea.getSelectionModel().getSelectedItem());
			pst1.setString(2, mob.getText());
			pst1.setString(4, cped.getSelectionModel().getSelectedItem());
			
			String all = "";
			ObservableList<String>selected;
			selected = nps.getSelectionModel().getSelectedItems();
			for(String one:selected)
				all = all + one + ",";
			pst1.setString(5, all);
			pst1.setString(6, cname.getText());
			x = pst1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Alert al = new Alert(AlertType.CONFIRMATION);
		if(x==0){
			al.setContentText("INVALID ID");
		}
		else{
			al.setContentText("You have successfully updated the customer!!!");
		}
		
		al.setTitle("Update Customer Prompt");
		al.show();
	}
	
	boolean MobValidate(){
		Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(mob.getText());
		if(m.find()&& m.group().equals(mob.getText())){
			return true;
		}
		else
		{
			System.out.println("Wrong");
			return false;
		}
	}

	private Object add() {
		
		Alert al = new Alert(null);
		if(cname.getText().isEmpty() || mob.getText().isEmpty())
		{
			al = new Alert(AlertType.ERROR);
			al.setContentText("You haven't filled the required fields :(");
			al.show();
		}
		else
				if(!MobValidate()){
					al = new Alert(AlertType.ERROR);
					al.setContentText("Mobile no. is not possible :(");
					al.show();
				}
				else{
			try {
				pst1 = con.prepareStatement("INSERT INTO CustomerInfo (CName, Adrs, Mob, Ar, Hawker, Npaper) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				pst1.setString(1,cname.getText());
				pst1.setString(3, mob.getText());
				pst1.setString(4, carea.getSelectionModel().getSelectedItem());
				pst1.setString(2, adr.getText());
				pst1.setString(5, cped.getSelectionModel().getSelectedItem());
				String all = "";
				ObservableList<String>selected;
				selected = nps.getSelectionModel().getSelectedItems();
				for(String one:selected)
					all = all + one + ",";
				pst1.setString(6, all);
				x = pst1.executeUpdate();
				ResultSet rs = pst1.getGeneratedKeys();
				rs.next();
				pst1.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			
				
			al = new Alert(AlertType.CONFIRMATION);
			if(x==0){
				al.setContentText("INVALID ID");
			}
			else{
				al.setContentText("You have successfully added the user!!!");
			}
			
			al.setTitle("Add pedlar Prompt");
			al.show();
		}
		return null;
	}
	
	void doSearch()
	{
		try {
			pst1=con.prepareStatement("SELECT * FROM CustomerInfo WHERE CName=?");
			pst1.setString(1, cname.getText());
			ResultSet rs=pst1.executeQuery();
			if(rs.next())
			{
				adr.setText(rs.getString("Adrs"));
				mob.setText(rs.getString("Mob"));
				carea.getSelectionModel().select(rs.getString("Ar"));
				cped.getSelectionModel().select(rs.getString("Hawker"));
				String all = rs.getString("Npaper");
				String []sa = all.split(",");
				for(String v:sa){
					nps.getSelectionModel().select(v);
				}
				
				Alert al = new Alert(AlertType.INFORMATION);
				al.setContentText("Customer Id: "+rs.getString("Id"));
				al.setTitle("Id Info Prompt");
				al.show();
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
		
		cped.getSelectionModel().select(0);
		
		
		} catch (SQLException e) {
		e.printStackTrace();
		}
		}
		
}
