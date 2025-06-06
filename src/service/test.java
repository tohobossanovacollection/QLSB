package service;

import model.Invoice;

public class test {
    public static void main(String[] args) {
        InvoiceService invoiceService = new InvoiceService();

        // Tạo một invoice mẫu
        Invoice invoice = new Invoice(
            0,                  // id (auto-increment nếu dùng MySQL)
            1,                  // customerId (giả sử đã có customerId = 1)
            1,                  // pitchId (giả sử đã có pitchId = 1)
            500_000,            // total
            "Test Invoice3"   // note// createdAt
        );

        boolean result = invoiceService.addInvoice(invoice);
        if (result) {
            System.out.println("Thêm hóa đơn thành công!");
        } else {
            System.out.println("Thêm hóa đơn thất bại!");
        }
    }
}
