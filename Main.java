package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	ArrayList<vertex> v = new ArrayList<>();
	ArrayList<vertex> vButton = new ArrayList<>();
	HashMap<String, vertex> name = new HashMap<>();
	Pane pp = new Pane();
	double imageWidth = 0;
	double imageHeight = 0;
	Circle marker;
	String path = "";
	TextField tt2;
	ComboBox<String> c = new ComboBox<String>();
	ComboBox<String> c2 = new ComboBox<String>();
	graph g=new graph();
	Line line;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(10,10,10,10));
			
			 root.setStyle("-fx-background-color:LIGHTBLUE");

				ImageView a2 = new ImageView("palMAPP.jpg");
				a2.setFitHeight(800);
				a2.setFitWidth(600);
				Button b2 = new Button(null, a2);
				b2.setAlignment(Pos.CENTER);
				pp.getChildren().add(b2);
				root.setCenter(pp);
			Scene scene = new Scene(root,1550, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		
			
		File myObj = new File("C:\\\\Users\\\\user\\\\Desktop\\\\cities.csv");
			if (myObj.length() != 0) {

				try {
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNext()) {
						String data = myReader.nextLine();
						g.addVertex(data);
						 
				}
					myReader.close();
				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				
				
				File myObj2 = new File("C:\\Users\\\\user\\Desktop\\Roads.csv");
				if (myObj2.length() != 0) {

					try {
						Scanner myReaderr = new Scanner(myObj2);
						while (myReaderr.hasNext()) {
							 
							String data = myReaderr.nextLine();
							data=data.trim();
							System.out.println(data);
							String[] tokens = data.split(",");
							System.out.println(tokens[0]);
							g.addEdge(tokens[0],tokens[1], Double.parseDouble(tokens[2]));
							 
					}
						g.print();
						myReaderr.close();
					} catch (FileNotFoundException e) {
						System.out.println("An error occurred.");
						e.printStackTrace();
					}
				
				}
			}
			
			System.out.println("..............................................");
			File myObj3 = new File("C:\\Users\\user\\Desktop\\AirDistance.csv");
			if (myObj3.length() != 0) {

				try {
					Scanner myReader = new Scanner(myObj3);
					while (myReader.hasNext()) {
						String data = myReader.nextLine();
						System.out.println(data);
						String[] tokens = data.split(",");
						//g.AddAirDistance(tokens[0],tokens[1],Double.parseDouble(tokens[2]));
						g.addheur(tokens[0],tokens[1],Double.parseDouble(tokens[2]));
						
						
				}
					 g.print1();
					myReader.close();
				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
			
			
		}
		
			File myObj4 = new File("C:\\Users\\user\\Desktop\\feedback_sentiment\\cities_longlat.txt");
			if (myObj4.length() != 0) {

				try {
					Scanner myReader = new Scanner(myObj4);
					while (myReader.hasNext()) {
						String data = myReader.nextLine();
						
						String[] tokens = data.split(":");
						vertex ver = new vertex(tokens[0], Double.parseDouble(tokens[1]),
								Double.parseDouble(tokens[2]));
						v.add(ver);
						name.put(tokens[0], ver);
						 
				}
					myReader.close();
				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
			
			
		}
			Circle[] button = new Circle[v.size()];
			HashMap<Circle, String> ss = new HashMap<>();
			for (int k = 0; k < v.size(); k++) {
				
				double longitude = v.get(k).getLongitude();
				double latitude = v.get(k).getLatitude();
				imageWidth = a2.getFitWidth();
				imageHeight = a2.getFitHeight();

				//double y = (imageHeight - (latitude + 90) / 180 * imageHeight)+800;
				double y=(latitude/919) * (800+10);
				//double x = (longitude + 180) / 360 * imageWidth -400;

				double x=(longitude/585)* (600+10);
				button[k] = new Circle(x, y, 4, Color.BLACK);

				pp.getChildren().addAll(button[k]);
				ss.put(button[k], v.get(k).getName());

			}
   

			for (Circle buttonn : button) {
				int index = Arrays.asList(button).indexOf(buttonn);

				buttonn.setOnMouseClicked(event -> {
					System.out.println("Button " + index + " clicked");

					String country = ss.get(buttonn);
				
					vertex v = name.get(country);
					vButton.add(v);
					
					 if (vButton.size() == 1) {
						c.setValue(country);
						}
					 else {
						 c2.setValue(country);
							}
						

				});
			}

			c.setMaxSize(200, 100);
			c.setStyle("-fx-font-size: 15pt;");
			
			List<String> items = new ArrayList<>();
			for (int i = 0; i <v.size(); i++) {
			    items.add(v.get(i).getName());
			}
			Collections.sort(items);
			
			for (String item : items) {
			    c.getItems().add(item);
			}
			
			
			
			Label l1 = new Label("Source");
			l1.setFont(Font.font(17));
			l1.setFont(Font.font(null, FontWeight.BOLD, l1.getFont().getSize()));
			Label l2 = new Label("Target");
			l2.setFont(Font.font(17));
			l2.setFont(Font.font(null, FontWeight.BOLD, l2.getFont().getSize()));
			
			c2.setMaxSize(200, 100);
			c2.setStyle("-fx-font-size: 15pt;");
			
			for (String item : items) {
			    c2.getItems().add(item);
			}

			Button b = new Button("Run");
			b.setFont(Font.font(15));
			b.setFont(Font.font(null, FontWeight.BOLD, b.getFont().getSize()));
			b.setMaxSize(100, 100);
			HBox h2 = new HBox();
			Label l3 = new Label("Path:");
			l3.setFont(Font.font(17));
			l3.setFont(Font.font(null, FontWeight.BOLD, l3.getFont().getSize()));
			TextArea tt = new TextArea();
			tt.setMaxSize(250, 250);
			tt.setFont(Font.font(20));
			h2.getChildren().addAll(l3, tt);

			Label l4 = new Label("Distance:");
			l4.setFont(Font.font(17));
			l4.setFont(Font.font(null, FontWeight.BOLD, l4.getFont().getSize()));
			 tt2 = new TextField();
			tt2.setFont(Font.font(20));
			
			Button b3 = new Button("Clear");
			b3.setFont(Font.font(15));
			b3.setFont(Font.font(null, FontWeight.BOLD, b3.getFont().getSize()));
			b3.setMaxSize(100, 100);
			GridPane gg = new GridPane();
		
			gg.setPadding(new Insets(5,5,5,5));
			gg.setVgap(10);
			
			Label ll=new Label("Palestine Map");
			ll.setFont(Font.font(25));
			ll.setAlignment(Pos.CENTER);
			ll.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,25));
			gg.add(ll, 1, 0);
			gg.add(l1, 0, 1);
			gg.add(c, 1, 1);

			gg.add(l2, 0, 2);
			gg.add(c2, 1, 2);

			gg.add(b, 1, 3);

			gg.add(l3, 0, 4);
			gg.add(tt, 1, 4);

			gg.add(l4, 0, 5);
			gg.add(tt2, 1, 5);
			gg.add(b3,1,6);
			gg.setAlignment(Pos.TOP_LEFT);
			root.setLeft(gg);
		
			
			b.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub

					//System.out.println(vButton.size());
					vertex v1 = null;
					vertex v2 = null;
					String s1 = c.getValue();
					String s2 = c2.getValue();
					System.out.println("hi");
					if (s1 != null && s2 != null) {
						v1 = name.get(s1);
						v2 = name.get(s2);
					} else if (vButton.size() == 2) {
						v1 = vButton.get(0);
						c.setValue(v1.getName());
						v2 = vButton.get(1);
						c2.setValue(v2.getName());
					}
					if (v1 != null && v2 != null) {
						//Dijekstra(v1, v2);
						
						tt2.setText(String.valueOf(g.findShortestDistance(v1.getName(), v2.getName())));
						// String pp=path.substring(0,path.lastIndexOf("-->"));
						tt.setText(path);
					}

				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
