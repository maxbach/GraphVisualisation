package ru.dmop.windows;

import com.mxgraph.swing.mxGraphComponent;
import ru.dmop.finderWays.DejkstraFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VisualisationDejkstraFrame extends JFrame {

    DejkstraFinder finder;
    mxGraphComponent component;
    JLabel label;

    public VisualisationDejkstraFrame(DejkstraFinder finder) throws HeadlessException {
        super("Визуализация алгоритма Дейкстра");
        this.finder = finder;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        finder.initGraph();
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(10));
        component = new mxGraphComponent(finder.getGraph());
        component.setEnabled(false);
        box.add(component);
        box.add(Box.createVerticalStrut(10));
        box.add(getBox());
        box.add(Box.createVerticalStrut(10));
        setContentPane(box);


        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }

    private Box getBox() {
        Box box = Box.createHorizontalBox();
        label = new JLabel("<html>Нажмите кнопку Next, чтобы заработал алгоритм</html>");
        box.add(Box.createHorizontalStrut(10));
        box.add(label);
        box.add(Box.createHorizontalStrut(10));
        box.add(getButton());
        box.add(Box.createHorizontalStrut(10));
        return box;
    }

    private JButton getButton() {
        final JButton button = new JButton("Next");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                finder.doNextStep();
                component.refresh();
                if (finder.isReady()) {
                    button.setVisible(false);
                }
                VisualisationDejkstraFrame.this.label.setText("<html>" + finder.getMessage() + "</html>");

            }
        });
        button.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        return button;
    }
}
