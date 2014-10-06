package ru.vsu;

import java.util.Random;

public class BarleyBreak {
	// т.к. у нас классические пятнашки, то яцеек должно быть 16
	private final static int GEN_LIMIT = 16;
	// генератор рандомных чисел, нужен при генерации нового поля игры
	private final static Random gen = new Random();	
	// само поле игры
	private int[][] matrix = new int[4][4];
	
	// геттер, позволяет получить всю матрицу
	public int[][] getMatrix() {
		return matrix;
	}
	
	// сеттер для одной ячейки матрицы
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	// сеттер, позволяющий установить сразу всю матрицу
	public int getMatixElem(int i, int j){
		return matrix[i][j];
	}
	
	// процедура генерации новой игры
	public void generateMatrix() {
		//
		int[] invariants = new int[GEN_LIMIT];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = 0;
                invariants[i*4 + j] = 0;
            }
        }

        for (int i = 1; i < 16; i++) {
            int k;
            int l;
            do {
                k = gen.nextInt(100) % 4;
                l = gen.nextInt(100) % 4;
            }
            while (matrix[k][l] != 0);
            matrix[k][l] = i;
            invariants[k*4+l] = i;
        }
        // ...перемешивание...
        boolean change = true;
        int counter = 1;
        while (change) {
            change = false;
            for (int i = 0; i < GEN_LIMIT; i++) {
                if (invariants[i] != i) {
                    for (int j = 0; j < GEN_LIMIT; j++) {
                        if (invariants[j] == i) {
                            int temp = invariants[i];
                            invariants[i] = invariants[j];
                            invariants[j] = temp;
                            change = true;
                            counter++;
                            break;
                        }
                    }
                    break;
                }
            }
        }

        if (counter % 2 != 0) {
            int temp = matrix[0][0];
            matrix[0][0] = matrix[3][3];
            matrix[3][3] = temp;
        }		
	}
	
	// функция проверки выигрыша(обычный перебор матрицы)
	public boolean checkWin() {
        boolean status = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j > 2)
                    break;
                if (matrix[i][j] != i * 4 + j + 1) {
                    status = false;
                }
            }
        }
        return status;
    }
	
	// процедура перестановки
    public void change(int num) {
        int i = 0, j = 0;
        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                if (matrix[k][l] == num) {
                    i = k;
                    j = l;
                }
            }
        }
        if (i > 0) {
            if (matrix[i - 1][j] == 0) {
                matrix[i - 1][j] = num;
                matrix[i][j] = 0;
            }
        }
        if (i < 3) {
            if (matrix[i + 1][j] == 0) {
                matrix[i + 1][j] = num;
                matrix[i][j] = 0;
            }
        }
        if (j > 0) {
            if (matrix[i][j - 1] == 0) {
                matrix[i][j - 1] = num;
                matrix[i][j] = 0;
            }
        }
        if (j < 3) {
            if (matrix[i][j + 1] == 0) {
                matrix[i][j + 1] = num;
                matrix[i][j] = 0;
            }
        }
    }
}
