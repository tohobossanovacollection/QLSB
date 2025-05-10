package DAO.impl;

import DAO.BookingDAO;
import DAO.impl.BookingDAOImpl;

public class test {
    public static void main(String[] args) {
        BookingDAO bookingDAO = new BookingDAOImpl();
        bookingDAO.findByPitch(1);
        System.out.println(bookingDAO.findByPitch(1));
    }
}
