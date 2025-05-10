package DAO.impl;

import model.*;

public class dropcolumn {
    public static void main(String[] args) {
        DatabaseConnector db = new DatabaseConnector();
        DatabaseConnector.dropColumn("users", "last_login");
    }
}
