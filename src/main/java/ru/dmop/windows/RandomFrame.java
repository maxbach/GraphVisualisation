package ru.dmop.windows;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

/**
 * Экран, на котором будут вводится параметры для рандомного создания графа
 */

public class RandomFrame extends JFrame {
    JTextField field;
    JSlider slider;

    public RandomFrame() throws HeadlessException {
        super("Построить рандомное дерево");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Box mainBox = Box.createVerticalBox();

        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getBoxOfChosingNumber());
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getBoxOfChosingDensity());
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getButton());
        mainBox.add(Box.createVerticalStrut(10));
        setContentPane(mainBox);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private Box getBoxOfChosingNumber() {
        Box box = Box.createHorizontalBox();
        box.add(new JLabel("Количество вершин: "));
        box.add(Box.createHorizontalStrut(10));


        // настраиваем текстовую панель, чтобы ввод был только цифрами
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        field = new JFormattedTextField(formatter);


        box.add(field);
        return box;
    }

    private Box getBoxOfChosingDensity() {
        Box box = Box.createHorizontalBox();
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

        return box;

    }

    private JButton getButton() {
        JButton button = new JButton("Построить рандомное дерево");
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // открываем новое окно
                String textOfField = field.getText();
                if (textOfField != null) {
                    setVisible(false);
                    new GraphAndAlgoFrame(Integer.parseInt(textOfField), slider.getValue());
                }

            }
        });
        return button;
    }

}
