package dev.ovj.solver.service.implementation;

import dev.ovj.solver.service.SudokuSolver;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.ejb.Stateless;

/**
 * SudokuSolverImpl provides the implementation of a local SudokuSolver.
 * @author ovj
 */
@Stateless
public class SudokuSolverImpl implements SudokuSolver {

    @Override
    public String solve(String[] dataArray) throws Exception {
        final int sideLength = (int) Math.sqrt(dataArray.length);
        //Create a initial board from the received data
        int[][] initialBoard = new int[sideLength][sideLength];
        int k = 0;
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                initialBoard[i][j] = Integer.parseInt(dataArray[k++]);
            }
        }

        //Check whether the initial board is a valid one 
        if (!IsBoardValid(initialBoard)) {
            throw new Exception("board is not valid");
        }
        //if valid solve the sudoku
        this.solveSudoku(initialBoard);

        //Convert the final board to a data array
        //String[] data = Stream.of(initialBoard).flatMapToInt(IntStream::of).mapToObj(String::valueOf).toArray(String[]::new);
        String[] data = new String[initialBoard.length * initialBoard.length];
        k = 0;
        for (int m = 0; m < sideLength; m++) {
            for (int n = 0; n < sideLength; n++) {
                data[k++] = String.valueOf(initialBoard[m][n]);
            }
        }
        return String.join(",", data);
    }

    /**
     * solveSudoku() will try solving the received sudoku board. The below
     * method is based on backTracking. The method will iterate each cell of the
     * sudoku, When a blank cell is found, Method will try putting each number
     * from 1 to 9, until it found a consistent one. Method will call itself
     * recurssively until it finds an inconsistency. Method will back track and
     * revert the change and continue with next possible number.
     *
     */
    private boolean solveSudoku(int[][] board) {
        //Iterate throught each cell
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 0) {
                    //if cell is blank find a number suitable for the cell 
                    for (int k = 1; k <= 9; k++) {
                        if (isBoardConsistent(board, r, c, k)) {
                            //add the number to cell and call the method to solve next cell 
                            board[r][c] = (k);
                            if (solveSudoku(board)) {
                                //once the board solved completely return true 
                                return true;
                            } else {
                                //if unable to solve the current cell, revert the last assignment
                                board[r][c] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * isBoardConsistent checks whether inserting a number to position
     * board[row][col] will keep the board in its consistent state.
     */
    private boolean isBoardConsistent(int[][] board, int row, int col, int num) {
        //Length of one side of Sudoku.
        final int sideLength = board.length;
        //Length of one side of sub square Sudoku.
        final int subSqSide = (int) Math.sqrt(sideLength);
        // check whether the num is present in current row and column 
        for (int i = 0; i < sideLength; i++) {
            if (board[row][i] == num) {
                return false;
            }
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check whether the num is present in subsquare
        int rowStart = row - row % subSqSide;
        int colStart = col - col % subSqSide;

        for (int m = 0; m < subSqSide; m++) {
            for (int n = 0; n < subSqSide; n++) {
                if (board[rowStart + n][colStart + m] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * IsBoardValid will check the whether given board is valid or not.
     */
    private boolean IsBoardValid(int[][] board) {
        //Length of one side of Sudoku. 
        final int sideLength = board.length;
        //Length of one side of Sub Square ofSudoku. 
        final int subSqSide = (int) Math.sqrt(sideLength);
        //Row Array for storing booleans for indicating the presence of a digit. 
        boolean row[][] = new boolean[sideLength][sideLength];
        //Column Array for storing booleans for indicating the presence of a digit.
        boolean col[][] = new boolean[sideLength][sideLength];
        //SubSquare Array for storing booleans for indicating the presence of a digit.
        boolean sub[][][] = new boolean[subSqSide][subSqSide][sideLength];
        int index;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] > 0 && board[i][j] <= 9) {
                    index = board[i][j] - 1;
                    if (row[i][index]) {
                        return false;
                    }
                    row[i][index] = true;

                    if (col[j][index]) {
                        return false;
                    }
                    col[j][index] = true;

                    if (sub[i / subSqSide][j / subSqSide][index]) {
                        return false;
                    }
                    sub[i / subSqSide][j / subSqSide][index] = true;
                }
            }
        }
        return true;
    }

}
