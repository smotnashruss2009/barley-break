package ru.vsu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {
	// Константа, отвечающая за возможность ресайза окна
	private static final boolean RESIZABLE = true;
	// само окно
	static JFrame application = new JFrame();
	// таблица для пятнашек
	private final static JPanel panel = new JPanel(new GridLayout(4, 4, 2, 2));
	static BarleyBreak barleyBreak;
	
	// главная функция, здесь происходит создание окна, установка его атрибутов, а так же, инициализация игры
	public static void main(String[] args) {
		// позиция и размеры окна
		application.setBounds(100, 200, 250, 250);
		// возможность ресайзить окно
		application.setResizable(RESIZABLE);
		// двойная буферизация для панели чтобы ничего не лагало, хотя можно и не включать
		panel.setDoubleBuffered(true);
		// вызов конструктора пятнашек
		barleyBreak = new BarleyBreak();
		// генерируем новое поле
		barleyBreak.generateMatrix();
		// добавляем нашу панель в окно
		application.add(panel);
		// т.к. мы добавили поле в окно, то нам нужно обновить графическую оболочку нашего поля
		repaintField();
		
		application.setVisible(true);
	}
	
	// процедура перерисовки поля
	private static void repaintField() {
		// убираем все с панели
		panel.removeAll();
		//теперь мы заново добавляем ячейки(кнопки) в нашу панель
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JButton button = new JButton(Integer.toString(barleyBreak.getMatixElem(i, j)));
                button.setFocusable(false);
                panel.add(button);
                if (barleyBreak.getMatixElem(i, j) == 0) {
                    button.setVisible(false);
                } else
					// чтобы игра у нас была полноценная, то нам нужен обработчик нажатий для наших ячеек(кнопок)
                    button.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							/* при нажатии на ячейку(кнопку) мы делаем ее невидимой и меняем позицию, 
							затем перерисовываем нашу панель, т.к. у нас ячейка поняла свою позицию.
							Так же мы проверяем выигрыш в нашей игре, если собрали правильно, пользователь видит соответствующее сообщение, 
							по нажатию кнопки ок генерируется новая игра.
							*/
				            JButton button = (JButton) e.getSource();
				            button.setVisible(false);
				            String name = button.getText();
				            barleyBreak.change(Integer.parseInt(name));
				            repaintField();
				            if (barleyBreak.checkWin()){
					            JOptionPane.showMessageDialog(null, "You win :D", "Congratulations", 1);
				            	barleyBreak.generateMatrix();
				            	repaintField();
				            	application.setVisible(false);
				            	application.setVisible(true);
				            }
						}
					});
            }
        }

        panel.validate();
	}

}
