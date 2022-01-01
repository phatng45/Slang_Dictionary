/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.hcmus.student.sv19127505.SlangDictionary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Admin
 */
public class Game extends Thread implements ActionListener {

    int GameMode;
    int chosenAnswer = -1;
    int correctAnswer;

    int heart = 2;
    int score = 0;
    JButton a, b, c, d;
    JLabel question, statement, scorelabel, cooldown, heartlabel;
    Dictionary dict;
    
    int t = 100;
    boolean bonus;

    public Game(int GameMode,
            JButton a, JButton b, JButton c, JButton d,
            JLabel question, JLabel statement, JLabel cooldown, JLabel scorelabel, JLabel heartlabel,
            Dictionary dict) {
        this.GameMode = GameMode;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.question = question;
        this.statement = statement;
        this.cooldown = cooldown;
        this.heartlabel = heartlabel;

        this.scorelabel = scorelabel;
        this.dict = dict;
        start();
    }

    public void run() {
        if (GameMode == 0)
            statement.setText("has the meaning of...?");
        else
            statement.setText("is the definition of...?");

        ArrayList<String> query;
        a.addActionListener(this);
        b.addActionListener(this);
        c.addActionListener(this);
        d.addActionListener(this);

        while (true) {
            t = 100;
            chosenAnswer = -1;
            query = dict.randomForQuiz(GameMode);
            a.setText(query.get(0));
            b.setText(query.get(1));
            c.setText(query.get(2));
            d.setText(query.get(3));

            question.setText(query.get(4));
            correctAnswer = Integer.parseInt(query.get(5));
            try {
                cooldown.setText("5");
                for (int i = 0; i < 10; ++i)
                    Thread.sleep(t);
                cooldown.setText("4");
                for (int i = 0; i < 10; ++i)
                    Thread.sleep(t);
                cooldown.setText("3");
                for (int i = 0; i < 10; ++i)
                    Thread.sleep(t);
                cooldown.setText("2");
                for (int i = 0; i < 10; ++i)
                    Thread.sleep(t);
                cooldown.setText("1");
                for (int i = 0; i < 10; ++i)
                    Thread.sleep(t);
                cooldown.setText("0");
            } catch (InterruptedException e) {
            }

            if (chosenAnswer == correctAnswer) {
                if (bonus)
                    score += 30;
                else
                    score += 10;
                scorelabel.setText("" + score);
            } else {
                --heart;
                if (heart == 1)
                    heartlabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hearte.png")));
                if (heart == 0){
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == a)
            chosenAnswer = 0;
        else if (e.getSource() == b)
            chosenAnswer = 1;
        else if (e.getSource() == c)
            chosenAnswer = 2;
        else if (e.getSource() == d)
            chosenAnswer = 3;
        
        bonus = Integer.parseInt(cooldown.getText()) > 3;
        
        t = 0;
    }
}
