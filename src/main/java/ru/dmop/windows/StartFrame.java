package ru.dmop.windows;

import com.mxgraph.view.mxGraph;
import ru.dmop.graph.GraphBuilder;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Стартовый экран
 */
public class StartFrame extends JFrame {

    public StartFrame(boolean isFirst) {
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
                    InputStream inputStream = null;
                    try {
                        inputStream = new FileInputStream(fileopen.getSelectedFile());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    setVisible(false);
                    Object graph = GraphBuilder.getGraphFromFile(inputStream);
                    if (graph != null)
                        new GraphAndAlgoFrame((mxGraph) graph);
                    else
                        setVisible(true);
                }
            }
        });
        box.add(GraphFromFile);
        box.add(Box.createVerticalStrut(20));

        setContentPane(box);
        if (isFirst) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } else {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        }
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

}
