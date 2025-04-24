package DAO.impl;

import model.*;

public class test2 {
    public static void main(String[] args) {
        DatabaseConnector db = new DatabaseConnector();
        DatabaseConnector.dropColumn("users", "last_login");
    }
}
