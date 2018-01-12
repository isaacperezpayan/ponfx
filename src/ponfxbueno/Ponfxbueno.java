/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ponfxbueno;
/*Importamos las distintas clases para su posterior utilizacion en objetos*/
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane; /*Layout para colocacion de elementos en ventana*/
import javafx.scene.paint.Color; /*paint.Color para obtener los distintos colores*/
import javafx.scene.shape.Circle; /*Importamos Clase geometrica (Circulo)*/
import javafx.stage.Stage;

/**
 *
 * @author isaac
 */
public class Ponfxbueno extends Application {
    short bolaCenterX=10;
    short bolaCenterY=30;
    byte velocidadbolay=3;
    byte velocidadbolax=3;
    @Override
    public void start(Stage primaryStage) {
        Pane root= new Pane();
        Scene scene = new Scene (root, 600, 400);
        scene.setFill(Color.YELLOW);
        primaryStage.setTitle("PongFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        Circle bola = new Circle(/*10, 30, 7*/);
        bola.setCenterX(bolaCenterX);
        bola.setCenterY(30);
        bola.setRadius(7);
        AnimationTimer movimiento;
        movimiento = new AnimationTimer() {
            public void handle(long now) {
                bola.setCenterX(bolaCenterX);
                bolaCenterX+= velocidadbolax;
                if (bolaCenterX >= 600){
                    velocidadbolax = -3;
                }
                if (bolaCenterX <= 0){
                    velocidadbolax = 3;
                }
                bola.setCenterY(bolaCenterY);
                bolaCenterY+= velocidadbolay;
                if (bolaCenterY >= 400){
                    velocidadbolay = -3;
                }
                if (bolaCenterY <= 0){
                    velocidadbolay = 3;
                }
            };
        };
        root.getChildren().add(bola);
        bola.setFill(Color.PURPLE);
        movimiento.start();
        /*Codigo anterior simplificado de las disntintas clases y objetos*/
        /*Scene scene = new Scene(root, 600, 400, Color.YELLOW);
        Circle bola = new Circle(10, 30, 7, Color.PURPLE);
        En estas dos lineas hemos declarado obtejos y sus atributos en una sola linea
        */
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
