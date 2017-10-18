package Forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Barchart  {
	static Connection con;
	XYChart.Series values;
	Text abt, title;
	GridPane grid;
	BarChart barChart;
	PreparedStatement pst1,pst2;
	ResultSet rs1,rs2;
	int x;
	Stage primaryStage;
   
   Barchart(){
	   try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewspaperProject", "root", "muskan");
			System.out.println("ok");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	   
    	primaryStage = new Stage();
        primaryStage.setTitle("Statistics");
        grid = new GridPane();
        grid = new GridPane();
		grid.setGridLinesVisible(false);
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Newspapers");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Readers");

		barChart = new BarChart(xAxis, yAxis);

        fillChart();
		grid.add(barChart, 0, 2, 4,1);
		
		//-----text--------------
		title = new Text("Statistics");
		abt = new Text("This bar chart illustrates the number of newspapers of all the categories prefered \nby our customers. Through this it can be easily analysed which all newspapers \nare in great demand and accordingly the economy can interpreted. ");
		title.setFont(Font.font("Lucida Sans", FontWeight.BLACK, 40));
		title.setFill(Color.BLACK);
		GridPane.setConstraints(title, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(30, 0, 0, 0) );
		GridPane.setConstraints(abt, 0, 1, 3, 1, HPos.CENTER, VPos.CENTER, null, null, new Insets(0, 0, 30, 0) );
		abt.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
     	grid.add(title, 0, 0, 1, 1);
		grid.add(abt, 0, 1, 3, 1);
		
        Scene scene = new Scene(grid, 1000, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fillChart() {
    	values = new XYChart.Series();
    	values.setName("No. of Newspapers of each category");
    	try {
			pst1=con.prepareStatement("SELECT * FROM Papertypes");
			rs1=pst1.executeQuery();
			int k =0;
			while(rs1.next()){
    			String p = rs1.getString("NName");
    			System.out.println("fin");
    			pst2=con.prepareStatement("Select COUNT(Npaper) from CustomerInfo where Npaper like '%"+p+"%'");
    			System.out.println("fine");
    			rs2=pst2.executeQuery();
    			if(rs2.next())
    			{
    				k = rs2.getInt(1);
    				values.getData().add(new XYChart.Data(p, k));
    			}
    			 
    			else
    				rs2.close();
    			
			}
			barChart.getData().add(values);
			
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
   
}