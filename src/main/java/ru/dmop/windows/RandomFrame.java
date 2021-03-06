package ru.dmop.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Экран, на котором будут вводится параметры для рандомного создания графа
 */

public class RandomFrame extends JFrame {
    JTextField field;
    JSlider slider;

    public RandomFrame(boolean isFirst) throws HeadlessException {
        super("Построить случайное дерево");
        Box mainBox = Box.createVerticalBox();

        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getBoxOfChosingNumber());
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getBoxOfChosingDensity());
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getButton(isFirst));
        mainBox.add(Box.createVerticalStrut(10));
        setContentPane(mainBox);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        if (isFirst) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } else {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        }
    }

    private Box getBoxOfChosingNumber() {
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(10));
        box.add(new JLabel("Количество вершин: "));
        box.add(Box.createHorizontalStrut(10));


        field = new NumberTextField();



        box.add(field);
        box.add(Box.createHorizontalStrut(10));
        return box;
    }

    private Box getBoxOfChosingDensity() {
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(10));
        box.add(new JLabel("Плотность графа (в %): "));
        box.add(Box.createHorizontalStrut(10));

        // настраиваем слайды
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(false);

        box.add(slider);
        box.add(Box.createHorizontalStrut(10));

        return box;

    }

    private JButton getButton(boolean isFirst) {
        JButton button = new JButton("Построить рандомное дерево");
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // открываем новое окно
                String textOfField = field.getText();
                if (textOfField != null && !textOfField.isEmpty() && !textOfField.equals("0")) {
                    setVisible(false);
                    new GraphAndAlgoFrame(Integer.parseInt(textOfField), slider.getValue(), isFirst);
                }

            }
        });
        return button;
    }

}
