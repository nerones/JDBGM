package com.crossdb.sql.optimization;

/**
 * Will use the FIRST_ROWS optimizations for supported dbs
 *
 * Oracle: first_rows(n)
 * db2: OPTIMIZE FOR n ROWS
 *
 *
 * User: treeder
 * Date: Aug 8, 2005
 * Time: 1:42:05 PM
 */
public class FirstRowsOptimization implements OptimizationHint {
    int n;
    public FirstRowsOptimization(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
