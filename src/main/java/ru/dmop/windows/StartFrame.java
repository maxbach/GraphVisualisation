package ru.dmop.windows;

import ru.dmop.graph.GraphBuilder;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * Стартовый экран
 */
public class StartFrame extends JFrame {
    public StartFrame() {
        super("Поиск кратчайших путей в графе");
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(20));

        JButton randomGraph = new JButton("RandomGraph");
        randomGraph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new RandomFrame();
            }
        });
        randomGraph.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(randomGraph);

        box.add(Box.createVerticalStrut(10));
        box.add(Box.createVerticalGlue());

        JButton GraphFromFile = new JButton("GraphFromFile");
        GraphFromFile.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        GraphFromFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    //InputOutputStream io = new InputOutputStream (fileopen.getSelectedFile());
                    InputStream inputStream = null;
                    try {
                        inputStream = new FileInputStream(fileopen.getSelectedFile());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    setVisible(false);
                        //new GraphAndAlgoFrame(GraphBuilder.getGraphFromFile(inputStream));
                }
            }
        });
        box.add(GraphFromFile);
        box.add(Box.createVerticalStrut(20));

        setContentPane(box);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
