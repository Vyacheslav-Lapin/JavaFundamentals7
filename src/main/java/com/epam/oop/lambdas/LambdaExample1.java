package com.epam.oop.lambdas;

import lombok.experimental.FieldDefaults;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class LambdaExample1 {

    public static void main(String... __) {

        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button clicked!");
            }
        });
    }
}
