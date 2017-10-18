package Forms;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Printcode 
{
	// Create the JobStatus Label	
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
}


