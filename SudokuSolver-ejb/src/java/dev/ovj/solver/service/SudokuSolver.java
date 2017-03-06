
package dev.ovj.solver.service;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * The local interface for a sudokuSolver class.
 * @author ovj
 */
@Local
public interface SudokuSolver {
    public String solve(String[] dataArray) throws Exception;
}
