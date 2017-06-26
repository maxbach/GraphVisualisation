package ru.dmop.windows;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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

        JButton exit = new JButton("GraphFromFile");
        exit.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    setVisible(false);
                    new GraphAndAlgoFrame();
                }
            }
        });
        box.add(exit);
        box.add(Box.createVerticalStrut(20));

        setContentPane(box);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
