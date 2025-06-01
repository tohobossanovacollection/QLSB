package DAO.impl;


public class dropcolumn {
    public static void main(String[] args) {
        DatabaseConnector.dropColumn("users", "last_login");
    }
}
