package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	
	private double xOffset = 0;
	private double yOffset = 0;
	
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));

			Scene scene = new Scene(root);
			// Copy scene drag and others from a project
			stage.setScene(scene);

			// grab your root here
			root.setOnMousePressed((MouseEvent event) -> {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			});

			root.setOnMouseDragged((MouseEvent event) -> {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			});

			stage.setScene(scene);
			new animatefx.animation.FadeIn(root).play();
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop(){
	    System.out.println("GOODBYE!");
	    System.exit(0);
	    
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
