package tool.rental.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Utility class for configuring and managing Swing JTable components.
 */
public class TableConfigurator {
    private final JTable table;

    /**
     * Constructs a TableConfigurator with the specified JTable.
     *
     * @param table the JTable to be configured
     */
    public TableConfigurator(JTable table) {
        this.table = table;
    }

    /**
     * Retrieves the DefaultTableModel associated with the configured JTable.
     *
     * @return the DefaultTableModel of the JTable
     */
    private DefaultTableModel getTableModel() {
        return (DefaultTableModel) table.getModel();
    }

    /**
     * Retrieves the TableColumn at the specified index of the JTable.
     *
     * @param index the index of the column
     * @return the TableColumn at the specified index
     */
    private TableColumn getColumn(int index) {
        return table.getColumnModel().getColumn(index);
    }

    /**
     * Configures the JTable with the specified columns, hiding the columns specified in hiddenColumns.
     *
     * @param columns        an array of column names
     * @param hiddenColumns  an array of column indices to be hidden
     */
    public void setup(String[] columns, int[] hiddenColumns) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel model = getTableModel();

        // Add columns
        for (String column : columns) {
            model.addColumn(column);
        }

        // Hide specified columns
        if (hiddenColumns != null) {
            for (int idx : hiddenColumns) {
                TableColumn columnModel = getColumn(idx);
                columnModel.setMinWidth(0);
                columnModel.setMaxWidth(0);
            }
        }

        // Set default editor to null to disable cell editing
        table.setDefaultEditor(Object.class, null);
    }

    /**
     * Configures the JTable with the specified columns, making no columns hidden.
     *
     * @param columns an array of column names
     */
    public void setup(String... columns) {
        setup(columns, new int[]{0});
    }

    /**
     * Inserts a single row into the JTable.
     *
     * @param row an array representing the data of a single row
     */
    public void insertRow(String[] row) {
        DefaultTableModel model = getTableModel();
        model.addRow(row);
    }

    /**
     * Inserts multiple rows into the JTable, optionally resetting the table before insertion.
     *
     * @param rows        an Iterable containing arrays of row data
     * @param resetTable  a boolean indicating whether to reset the table before insertion
     */
    public void insertRows(Iterable<String[]> rows, boolean resetTable) {
        DefaultTableModel model = getTableModel();

        if (resetTable) {
            model.setNumRows(0);
        }

        for (String[] row : rows) {
            insertRow(row);
        }
    }
}
