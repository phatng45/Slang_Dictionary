/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.hcmus.student.sv19127505.SlangDictionary;

/**
 *
 * @author Admin
 */
public class Game extends Thread{
    int GameMode;
    int heart = 2;
    int score = 0;
    
    public Game(int GameMode) {
        this.GameMode = GameMode;
        start();
    }
    
    public void run(){
        while(heart != 0){
            
        }
    }
}
