package Forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.sun.javafx.geom.Rectangle;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("restriction")
public class MainForm extends Application
{
	static Connection con;
	public static void main(String[] args) throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewspaperProject", "root", "muskan");
			System.out.println("ok");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		launch(args);
	}
	
	GridPane grid, gr1, gr2, gr3;
	Stage stage1;
	ImageView iv1, iv2, iv3, iv4, ig1, ig2, id1, id2, id3, id4,ift;
	Text title, thead, t1, t2, p1, p2, p3, p4, tbill, q1, q2, tdet, d1, d2, d3, d4, f1,f2,f3;
	TextField mob;
	ComboBox<String> cname, carea;
	TextArea adr;
	Button search, nw, save, del, update, close, browse, sms;
	HBox hbox1, hbox2, hb1, hb2, hb3, h1, h2, h3, h4, h5, h6, hf;
	VBox vb1, vb2, vb3, vb4;
	PreparedStatement pst,pst2;
	int x;
	Calendar cal;
	Label lbl;
	Image img;
	ImageView imgview;
	String Path, dt;
	DatePicker dp;
	ArrayList<String> pedlars, area;
	Text id,pwd;
	TextField txtId; PasswordField txtpwd; 
	Button btnLog, btnExit; 
	@Override
	public void start(Stage stage) throws Exception {
		 //------grid----------
	    grid = new GridPane();
		grid.setGridLinesVisible(false);
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(25); grid.setVgap(25);
		grid.setStyle("-fx-background-color:#FF4C3B");
		
		//login=====================================
		gr1 = new GridPane();
	    gr1.setStyle("-fx-background-color:white");
		gr1.setPrefSize(280, 210); gr1.setHgap(18); gr1.setVgap(18);
		gr1.setAlignment(Pos.CENTER); 
		//--------------Text-------------------------------
		title = new Text("Login Panel");
		title.setFont(Font.font("Lucida Sans", FontWeight.BLACK, 30));
		GridPane.setMargin(title, new Insets(0,0,0,25));
		id = new Text("User ID");
		id.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		txtId = new TextField();
		txtId.setPromptText("Enter ur email id");
		pwd = new Text("Password");
		pwd.setFont(Font.font("Lucida Sans", FontWeight.NORMAL, 15));
		txtpwd = new PasswordField();
		txtpwd.setPromptText("Enter password");
		gr1.add(id, 0, 1);
		gr1.add(txtId, 1, 1);
		gr1.add(txtpwd, 1, 2);
		gr1.add(pwd, 0, 2);
		gr1.add(title, 0, 0, 2, 1);
		//--------------AddButtons-----------------------------
		btnLog = new Button("LOGIN");
		btnExit = new Button("SMS Pwd");
		btnLog.setStyle("-fx-background-color:#96D6FB");
		btnExit.setStyle("-fx-background-color:#96D6FB");
		HBox hbox = new HBox();
		HBox.setMargin(btnExit, new Insets(0,0,0,10));
		hbox.getChildren().addAll(btnLog, btnExit);
		gr1.add(hbox, 1, 3);  
		btnLog.setPrefSize(75, 25);
		btnExit.setPrefSize(80, 25);
		//=========================================================
		
		thead = new Text("Smart technology for\nNewspaper Agency");
		thead.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 48));
		thead.setFill(Color.BISQUE);
		GridPane.setConstraints(thead, 0, 0, 1, 3, HPos.LEFT, VPos.CENTER, null, null, new Insets(30, 90, 0, 170) );
		t1 = new Text("Manage your job with Innovation...\nUse the new Job Manager and feel the difference. ");
		t1.setFont(Font.font("Lucida Sans", FontWeight.SEMI_BOLD, 20));
		t1.setFill(Color.BISQUE);
		GridPane.setConstraints(t1, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER, null, null, new Insets(0, 40, 0, 170));
		
		//about====================================================
		gr2 = new GridPane();
	    gr2.setStyle("-fx-background-color:#F7CE3E");
		gr2.setPrefSize(1510, 380);
		gr2.setHgap(20); gr1.setVgap(20);
		gr2.setAlignment(Pos.CENTER); 

		vb1 = new VBox();  vb2 = new VBox();  vb3 = new VBox();  vb4 = new VBox();
		
		iv1 = new ImageView(new Image(getClass().getResourceAsStream("hawker.jpg")));
		iv1.setFitWidth(90); iv1.setFitHeight(105);
		p1 = new Text("Add Hawker");
		p1.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); vb1.setSpacing(3); 
		p1.setFill(Color.BROWN);	vb1.getChildren().addAll(iv1, p1);
		
		iv2 = new ImageView(new Image(getClass().getResourceAsStream("cusomer.jpg")));
		iv2.setFitWidth(95); iv2.setFitHeight(105);
		p2 = new Text("Add Customer");
		p2.setFont(Font.font("Verdana", FontWeight.NORMAL, 14)); vb2.setSpacing(3);
		p2.setFill(Color.BROWN);	vb2.getChildren().addAll(iv2, p2);
		
		iv3 = new ImageView(new Image(getClass().getResourceAsStream("newspaper.jpg")));
		iv3.setFitWidth(90); iv3.setFitHeight(105);
		p3 = new Text(" Add Paper");
		p3.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); vb3.setSpacing(3);
		p3.setFill(Color.BROWN);	vb3.getChildren().addAll(iv3, p3);
		
		iv4 = new ImageView(new Image(getClass().getResourceAsStream("area.jpg")));
		iv4.setFitWidth(90); iv4.setFitHeight(105);
		p4 = new Text(" Add Area");
		p4.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); vb4.setSpacing(3);
		p4.setFill(Color.BROWN);	vb4.getChildren().addAll(iv4, p4);
		hbox1 = new HBox();
		hbox1.getChildren().addAll(vb1, vb2, vb3, vb4); hbox1.setSpacing(30);
		gr2.add(hbox1, 0, 0, 1, 2);  hbox1.setPadding(new Insets(0, 0 , 30, 0));
		//=======================================================
		
		tbill = new Text("Manage Bills");
		tbill.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 35));
		tbill.setFill(Color.SADDLEBROWN);
		GridPane.setConstraints(tbill, 0, 1, 1, 1, HPos.LEFT, VPos.TOP, null, null, new Insets(0, 0, 20, 0) );
		tdet = new Text("Details");
		tdet.setFont(Font.font("Lucida Sans", FontWeight.BOLD, 35));
		tdet.setFill(Color.SADDLEBROWN);
		GridPane.setConstraints(tdet, 1, 1, 1, 1, HPos.LEFT, VPos.TOP, null, null, new Insets(0, 30, 0, 80) );
		
		
		//bill icons================================================
		hb1 = new HBox();  hb2 = new HBox();
		ig1 = new ImageView(new Image(getClass().getResourceAsStream("cal.png")));
		ig1.setFitWidth(70); ig1.setFitHeight(70);
		q1 = new Text("Generate bill just\non single click.");
		q1.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); hb1.setSpacing(7);
		q1.setFill(Color.BROWN);	hb1.getChildren().addAll(ig1, q1); hb1.setPadding(new Insets(10, 0 , 20, 0)); 
		
		ig2 = new ImageView(new Image(getClass().getResourceAsStream("money.png")));
		ig2.setFitWidth(70); ig2.setFitHeight(70);
		q2 = new Text("Make the entries of the \ncollected bill by updating \nfor all.");
		q2.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); hb2.setSpacing(7);
		q2.setFill(Color.BROWN);	hb2.getChildren().addAll(ig2, q2); hb2.setPadding(new Insets(10, 0 , 20, 0));
		 
		hb3 = new HBox(); hb3.getChildren().addAll(hb1,hb2); gr2.add(hb3, 0, 3); hb3.setSpacing(20);
		
		//details=======================================
		h1 = new HBox(); h2 = new HBox(); h3 = new HBox(); h4 = new HBox(); h5 = new HBox(); h6 = new HBox();
		id1 = new ImageView(new Image(getClass().getResourceAsStream("bills.png")));
		id1.setFitWidth(80); id1.setFitHeight(80);
		d1 = new Text("Get all the details of \nany Bill of any customer \nof any desired month. ");
		d1.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); h1.setSpacing(8);
		d1.setFill(Color.BROWN);	h1.getChildren().addAll(id1, d1);
		
		id2 = new ImageView(new Image(getClass().getResourceAsStream("man.png")));
		id2.setFitWidth(80); id2.setFitHeight(80);
		d2 = new Text("Get the details of all \nthe customers of any area \non any basis.");
		d2.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); h2.setSpacing(8);
		d2.setFill(Color.BROWN);	h2.getChildren().addAll(id2, d2);
		
		id3 = new ImageView(new Image(getClass().getResourceAsStream("hawk.png")));
		id3.setFitWidth(80); id3.setFitHeight(80);
		d3 = new Text("Get the details of your \nemployed hawkers and \nthe areas they serve.");
		d3.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); h3.setSpacing(8);
		d3.setFill(Color.BROWN);	h3.getChildren().addAll(id3, d3);
		
		id4 = new ImageView(new Image(getClass().getResourceAsStream("statistics.png")));
		id4.setFitWidth(80); id4.setFitHeight(80);
		d4 = new Text("Get statistics on the \nbasis of the no. of the \ntypes of newspapers prefered.");
		d4.setFont(Font.font("Verdana", FontWeight.NORMAL, 15)); h4.setSpacing(8);
		d4.setFill(Color.BROWN);	h4.getChildren().addAll(id4, d4);
		
		h5.getChildren().addAll(h1, h2); h5.setSpacing(30);
		gr2.add(h5, 1, 1, 1, 1);  h5.setPadding(new Insets(30, 0 , 20, 80));
		h6.getChildren().addAll(h3, h4); h6.setSpacing(32);
		gr2.add(h6, 1, 2, 1, 2);  h6.setPadding(new Insets(10, 0 , 10, 80));
		
		//footer
		f1 = new Text("Made with ");
		f1.setFont(Font.font("Lucida Sans", FontWeight.MEDIUM, 17));
		f1.setFill(Color.BISQUE);
		f2 = new Text(" by Muskan Mittal under guidence of Sir Rajesh Bansal");
		f2.setFont(Font.font("Lucida Sans", FontWeight.MEDIUM, 17));
		f2.setFill(Color.BISQUE);
		ift = new ImageView(new Image(getClass().getResourceAsStream("heart.png")));
		ift.setFitWidth(20); ift.setFitHeight(20);
		hf = new HBox();
		hf.getChildren().addAll(f1, ift, f2); hf.setAlignment(Pos.TOP_CENTER);
		
		//GridPane.setConstraints(f3, 1, 9, 4, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(0, 0, 0, 20));
		
		gr2.add(tbill, 0, 2);
		gr2.add(tdet, 1, 0);
		grid.add(thead, 1, 0 ,1, 3);
		grid.add(t1, 1, 3 ,1, 1);
		grid.add(gr2, 0, 4, 7, 3);
		grid.add(gr1, 3, 1, 1, 3);
		grid.add(hf, 0, 7, 6, 1);
		
		
		//events==========================
		btnLog.setOnAction(e->verifyLogin());
		btnExit.setOnAction(e->sendMsg());
        
		
      //------scene------------------
		Scene scene = new Scene(grid, 1510, 700);
		stage.setScene(scene);
		stage.show();
		
	}
	private Object sendMsg() {
		String m="Your password is 'muskanmtl' for userid 'Muskan Mittal'. ";
		String resp=SmsFile.bceSunSoftSend("8437145781", m);
		if(resp.indexOf("Exception")!=-1)
		System.out.println("Check Internet Connection");
		else
		if(resp.indexOf("successfully")!=-1)
		System.out.println("Sent");
		else
		System.out.println( "Invalid Number");
		return null;
	}

	String name, psd;
	private Object verifyLogin() {
		try {
			pst = con.prepareStatement("select * from login where username=?");
			pst.setString(1, "Muskan Mittal");
			
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
					name = rs.getString("username");
				    psd = rs.getString("pwd");	
			}
			rs.close();
			if(name.equals(txtId.getText()) && psd.equals(txtpwd.getText()))
			{
				showMsg("You have successfully logged in ");
				iv1.setOnMouseClicked((MouseEvent e) -> {
		            new pedlarInfo();
		        });
				iv2.setOnMouseClicked((MouseEvent e) -> {
		            new costumerEnroll();
		        });
				iv3.setOnMouseClicked((MouseEvent e) -> {
		            new NewsprMaster();
		        });
				iv4.setOnMouseClicked((MouseEvent e) -> {
		            new areaManager();
		        });
				ig1.setOnMouseClicked((MouseEvent e) -> {
		            new billgenerate();
		        });
				ig2.setOnMouseClicked((MouseEvent e) -> {
		            new billCollect();
		        });
				id1.setOnMouseClicked((MouseEvent e) -> {
		            new TBill();
		        });
				id2.setOnMouseClicked((MouseEvent e) -> {
		            new TCustomer();
		        });
				id3.setOnMouseClicked((MouseEvent e) -> {
		            new THawkers();
		        });
				id4.setOnMouseClicked((MouseEvent e) -> {
		            new Barchart();
		        });
		
			}
			else
				showMsg("Either Username or password is incorrect ");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	private void showMsg(String string) {
		Alert al = new Alert(AlertType.INFORMATION);
		al.setTitle("Login Prompt");
		al.setContentText(string);
		al.show();
		
	}
	
	

}
