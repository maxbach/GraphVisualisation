package ru.dmop.windows;

import javax.swing.*;

/**
 Стартовый экран
 */
public class StartFrame extends JFrame {
    public StartFrame(){
        super("Поиск кратчайших путей в графе");
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(20));
        JButton randomGraph = new JButton("RandomGraph");
        randomGraph.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(randomGraph);
        box.add(Box.createVerticalStrut(10));
        box.add(Box.createVerticalGlue());
        JButton Exit = new JButton("Exit");
        Exit.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(Exit);
        box.add(Box.createVerticalStrut(20));
        setContentPane(box);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

}
