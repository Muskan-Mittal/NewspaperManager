package Forms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import mysqlCon.AddUserSQL;

public class pedlarInfo 
{
	static Connection con;

	GridPane grid;
	Stage stage1;
	ImageView icon;
	Text title, tname, tarea, tadr, tid, tmob, tdate;
	TextField mob;
	ComboBox<String> cname, carea;
	TextArea adr;
	Button search, nw, save, del, update, close, browse, sms;
	HBox hbox1, hbox2;
	VBox vbox;
	PreparedStatement pst,pst2;
	int x;
	Calendar cal;
	Label lbl;
	Image img;
	ImageView imgview;
	String Path, dt;
	DatePicker dp;
	ArrayList<String> pedlars, area;
	
	
	pedlarInfo()  {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewspaperProject", "root", "muskan");
			System.out.println("ok");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Stage stage=new Stage();
		stage.setTitle("Hawker Console");
		
		    //------grid----------
		    grid = new GridPane();
			grid.setGridLinesVisible(false);
			//grid.setPadding(new Insets(100,0,0,160));
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(25);
			grid.setVgap(25);
			
			//-----text--------------
			title = new Text("Hawker Console");
			tname = new Text("Name");
			tmob = new Text("Mobile No.");
			tarea = new Text("Area alloted");
			tid = new Text("Id Proof");
			tadr = new Text("Address");
			tdate = new Text("D.O.Joining");
			title.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 40));
			title.setFill(Color.BLACK);
			GridPane.setConstraints(title, 1, 0, 2, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(30, 0, 0, 0) );
			tid.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tname.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tadr.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tarea.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tmob.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			tdate.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
			GridPane.setValignment(tadr, VPos.TOP);
	     	grid.add(title, 0, 0, 3, 1);
			grid.add(tname, 0, 1);
			grid.add(tmob, 0, 2);
			grid.add(tarea, 0, 3);
			grid.add(tadr, 0, 4);
			grid.add(tdate, 0, 5);
			grid.add(tid, 0, 6);
			
			//-------textField-------------
			mob = new TextField();
			mob.setPromptText("Enter mobile no.");
			grid.add(mob, 1, 2);
			mob.setPrefWidth(50);
			
			//ArrayList---------------------
			pedlars = new ArrayList<String>();
			pedlars.add("-Select-");
			area = new ArrayList<String>();
			area.add("-Select-");
			
			//Combo--------------------------
			cname = new ComboBox<String>();
			cname.getItems().addAll(pedlars);
			cname.setEditable(true);
			cname.getSelectionModel().select(0);
			cname.setPrefWidth(200);
			cname.setVisibleRowCount(4);
			grid.add(cname, 1, 1);
			
			carea = new ComboBox<String>();
			carea.getItems().addAll(area);
			carea.setEditable(false);
			carea.getSelectionModel().select(0);
			carea.setPrefWidth(200);
			carea.setVisibleRowCount(4);
			grid.add(carea, 1, 3);
			
			doFillUids();
			
			//text area------------------------------
			adr = new TextArea();
	        adr.setPrefRowCount(4);
	        adr.setPrefColumnCount(20);
	        adr.setWrapText(true);
	        adr.setPrefWidth(100);
	        grid.add(adr, 1, 4);
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
			ImageView msg = new ImageView(new Image(getClass().getResourceAsStream("sms.png")));
			msg.setFitHeight(18);
			msg.setFitWidth(18);
			
			search = new Button("SEARCH",sr);
			browse = new Button("Browse...");
			nw = new Button("NEW",ad);
			update = new Button("UPDATE",up);
			del = new Button("REMOVE",rm);
			save = new Button("SAVE",sv);
			close = new Button("CLOSE",cl);
			sms = new Button("SMS",msg);
			search.setStyle("-fx-background-color:#F4CA70");
			search.setPrefSize(95, 25);
			nw.setPrefSize(99, 25);
			sms.setPrefSize(99, 25);
			save.setPrefSize(99, 25);
			del.setPrefSize(99, 25);
			close.setPrefSize(99, 25);
			update.setPrefSize(99, 25);
			nw.setStyle("-fx-background-color:#96D6FB");
			close.setStyle("-fx-background-color:#96D6FB");
			del.setStyle("-fx-background-color:#96D6FB");
			update.setStyle("-fx-background-color:#96D6FB");
			save.setStyle("-fx-background-color:#96D6FB");
			sms.setStyle("-fx-background-color:#96D6FB");
			vbox = new VBox();  hbox1 = new HBox();   hbox2 = new HBox();
			hbox1.getChildren().add(search);
			hbox2.getChildren().add(browse);
			grid.add(hbox1, 2, 1);
			grid.add(hbox2, 1, 6);
		    vbox.setAlignment(Pos.TOP_RIGHT);
		    vbox.setPadding(new Insets(20, 0, 0, 0));
		    vbox.setSpacing(30);
		    vbox.getChildren().addAll(nw, save, update, del,sms, close);
		    grid.add(vbox, 5, 1, 1, 6);    
		    
			//-----events-----------------
			save.setOnAction(e->add());
			sms.setOnAction(e->sendMsg());
			nw.setOnAction(e->newData());
			del.setOnAction(e->delete());
			update.setOnAction(e->update());
			search.setOnAction(e->doSearch());
			browse.setOnAction(e->fChooser());
			close.setOnAction(e->{
				stage.close();
			});
			
			lbl=new Label();
			imgview=new ImageView();
			imgview.setFitHeight(120);
			imgview.setFitWidth(250);
			grid.add(imgview, 1, 7, 2, 1);
			grid.add(lbl, 1, 8, 3, 1);
			
			
			//calendar------------------------
			cal=Calendar.getInstance();
			dp=new DatePicker(LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DATE)));
			dp.setStyle("-fx-font-size:15");
			dp.setPromptText("Select Date");
			dp.requestFocus();
			grid.add(dp, 1, 5);
			dp.setPrefWidth(150);
			
			//------scene------------------
			Scene scene = new Scene(grid, 1100, 700);
			stage.setScene(scene);
			stage.show();
					
		
	}
	
	private Object sendMsg() {
		String m="Msg sent";
		String resp=SmsFile.bceSunSoftSend("8437145781", m);
		//System.out.println(resp);
		if(resp.indexOf("Exception")!=-1)
		System.out.println("Check Internet Connection");
		else
		if(resp.indexOf("successfully")!=-1)
		System.out.println("Sent");
		else
		System.out.println( "Invalid Number");
		
		return null;
	}

	FileChooser fileChooser;
	
	void fChooser()
	{
	fileChooser = new FileChooser();
	fileChooser.setTitle("Open Resource File");
	FileChooser.ExtensionFilter ext=new FileChooser.ExtensionFilter("PNG files (*.png)","*.PNG");
	FileChooser.ExtensionFilter ext1=new FileChooser.ExtensionFilter("JPEG files (*.jpg)","*.JPG");
	fileChooser.getExtensionFilters().addAll(ext,ext1);
	File file=fileChooser.showOpenDialog(stage1);
	if(file==null)
	{
	imgview.setImage(null);
	return;
	}
	Path=file.getPath();
	lbl.setText(Path);
	try {
	img=new Image(new FileInputStream(file));
	imgview.setImage(img);
	System.out.println("\nImg is Ok");
	} catch (FileNotFoundException e) {
	e.printStackTrace();
	}
	}
	
	private Object newData() {
		doFillUids();
		cname.getSelectionModel().select(0);;
		carea.getSelectionModel().select(0);;
		adr.clear();
		mob.clear();
		lbl.setText("");
		imgview.setImage(null);
		return null;
	}

	private Object delete() {
		
			try {
				pst = con.prepareStatement("DELETE FROM Addpedlar WHERE PName=?");
				pst.setString(1, cname.getSelectionModel().getSelectedItem());
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
				
				al.setTitle("Remove Pedlar Prompt");
				al.show();
			}
				
			 catch (SQLException e) {
				e.printStackTrace();
			}			
			
		return null;
		
	}
	Date dosObj;
	DateFormat format;
	java.sql.Date sqlDate;
	String datesql;
	
	void sqldate() throws SQLException{
		
		format=new SimpleDateFormat("dd-MM-yyyy");
		datesql = format.format(cal.getTime());
		System.out.println(datesql);
		try {
			dosObj = format.parse(datesql);
			sqlDate=new java.sql.Date(dosObj.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	void update(){
		
		try {
			sqldate();
			pst = con.prepareStatement("UPDATE Addpedlar SET Mobile=?, ArAlotted=?, Address=?, IdPfPath=?, DOJoining=? WHERE PName=?");
			pst.setString(1, mob.getText());
			pst.setString(2,carea.getSelectionModel().getSelectedItem());
			pst.setString(3, adr.getText());
			pst.setString(4, Path);
			pst.setDate(5, sqlDate);
			pst.setString(6, ((TextField)cname.getEditor()).getText());
			x = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Alert al = new Alert(AlertType.CONFIRMATION);
		if(x==0){
			al.setContentText("INVALID ID");
		}
		else{
			al.setContentText("You have successfully updated the user!!!");
		}
		
		al.setTitle("Update Pedlar Prompt");
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
		if(((TextField)cname.getEditor()).getText().isEmpty() || mob.getText().isEmpty())
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
			else
		        {
			try {
				sqldate();
				pst = con.prepareStatement("INSERT INTO Addpedlar VALUES(?,?,?,?,?,?)");
				pst.setString(1,((TextField)cname.getEditor()).getText());
				pst.setString(2, mob.getText());
				pst.setString(3,carea.getSelectionModel().getSelectedItem());
				pst.setString(4, adr.getText());
				pst.setString(5, Path);
				pst.setDate(6, sqlDate);
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
			pst=con.prepareStatement("SELECT * FROM Addpedlar WHERE PName=?");
			pst.setString(1, cname.getSelectionModel().getSelectedItem());
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				String str=rs.getString("DOJoining");
				String a[]=str.split(",");
			    java.sql.Date SqlDate1 = rs.getDate(6);
				mob.setText(rs.getString("Mobile"));
				carea.getSelectionModel().select(rs.getString("ArAlotted"));
				adr.setText(rs.getString("address"));
				lbl.setText(rs.getString("IdPfPath"));
			    dp.setValue(SqlDate1.toLocalDate());
			    File file = new File(rs.getString("IdPfPath"));
			    img=new Image(new FileInputStream(file));
				imgview.setImage(img);

			}
			else
				System.out.println("Done search");
			rs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//=========================
	void doFillUids()
	{
	
	try {
		cname.getSelectionModel().select(0);
	pst=con.prepareStatement("select PName from Addpedlar" );
	ResultSet rst= pst.executeQuery();
	ArrayList<String> lst1 = new ArrayList<String>();
	ArrayList<String> lst2 = new ArrayList<String>();
	
	while(rst.next())
	{
	String p=rst.getString("PName");
	lst1.add(p);
	}
	cname.getItems().addAll(lst1);
	rst.close();

	pst2=con.prepareStatement("select Area from Areas" );
	ResultSet rs2= pst2.executeQuery();
	
	while(rs2.next())
	{
	String p=rs2.getString("Area");
	lst2.add(p);
	}
	carea.getItems().addAll(lst2);
	rs2.close();
	
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}
}
