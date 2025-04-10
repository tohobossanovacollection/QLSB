Thiết kế hệ thống quản lý sân bóng đá theo mô hình MVC với Java

## Mô hình MVC tổng quan
MVC là mô hình phân chia ứng dụng thành 3 thành phần chính:

Model: Quản lý dữ liệu và logic nghiệp vụ
View: Hiển thị thông tin và tương tác với người dùng
Controller: Điều phối luồng xử lý, kết nối Model và View

I. Model (Mô hình dữ liệu)
1. Các lớp Entity (Thực thể)

Field.java: Thông tin sân bóng (id, tên, loại sân, giá theo giờ, trạng thái)
Booking.java: Thông tin đặt sân (id, sân, thời gian bắt đầu, kết thúc, khách hàng, trạng thái)
MonthlyBooking.java: Đơn đặt sân theo tháng (id, khách hàng, danh sách ngày/giờ cố định, giá ưu đãi)
Customer.java: Thông tin khách hàng (id, tên, SĐT, email, loại khách hàng, ghi chú)
Product.java: Sản phẩm bán kèm (id, tên, giá, số lượng tồn kho)
Order.java: Đơn hàng (id, khách hàng, danh sách sản phẩm, tổng tiền)
OrderItem.java: Chi tiết đơn hàng (sản phẩm, số lượng, đơn giá)
Transaction.java: Giao dịch thu chi (id, loại, số tiền, ngày, mô tả, người thực hiện)
Branch.java: Chi nhánh (id, tên, địa chỉ, danh sách sân)
User.java: Người dùng hệ thống (id, tên, username, password, vai trò)

2. Các lớp DAO (Data Access Object)

FieldDAO.java: Truy xuất dữ liệu sân bóng
BookingDAO.java: Truy xuất dữ liệu đặt sân
MonthlyBookingDAO.java: Truy xuất dữ liệu đơn tháng
CustomerDAO.java: Truy xuất dữ liệu khách hàng
ProductDAO.java: Truy xuất dữ liệu sản phẩm
OrderDAO.java: Truy xuất dữ liệu đơn hàng
TransactionDAO.java: Truy xuất dữ liệu giao dịch
BranchDAO.java: Truy xuất dữ liệu chi nhánh
UserDAO.java: Truy xuất dữ liệu người dùng
DatabaseConnection.java: Quản lý kết nối CSDL

3. Các lớp Service (Xử lý nghiệp vụ)

FieldService.java: Xử lý nghiệp vụ liên quan đến sân bóng
BookingService.java: Xử lý nghiệp vụ đặt sân, kiểm tra trùng lịch
MonthlyBookingService.java: Xử lý đơn đặt sân theo tháng
CustomerService.java: Xử lý nghiệp vụ khách hàng
InventoryService.java: Quản lý hàng tồn kho
SalesService.java: Xử lý bán hàng
ReportService.java: Tạo báo cáo, thống kê
TransactionService.java: Quản lý thu chi
UserService.java: Quản lý người dùng, phân quyền

II. View (Giao diện người dùng)
1. Các giao diện chính

MainView.java: Giao diện chính, menu điều hướng
LoginView.java: Màn hình đăng nhập

2. Giao diện quản lý sân và đặt sân

FieldStatusView.java: Hiển thị trạng thái sân theo ngày/tuần/tháng
BookingView.java: Giao diện đặt sân
BookingListView.java: Danh sách đặt sân
MonthlyBookingView.java: Quản lý đơn đặt sân theo tháng

3. Giao diện quản lý khách hàng

CustomerView.java: Thêm/sửa khách hàng
CustomerListView.java: Danh sách khách hàng
CustomerDetailView.java: Chi tiết khách hàng

4. Giao diện bán hàng và kho

SalesView.java: Giao diện bán hàng
ProductView.java: Quản lý sản phẩm
InventoryView.java: Quản lý kho

5. Giao diện báo cáo

ReportView.java: Hiển thị các báo cáo
RevenueChartView.java: Biểu đồ doanh thu
TransactionView.java: Quản lý thu chi

6. Giao diện quản lý hệ thống

BranchView.java: Quản lý chi nhánh
UserView.java: Quản lý người dùng
SettingsView.java: Thiết lập hệ thống

7. Các thành phần giao diện chung

Components/: Thư mục chứa các component dùng chung

Calendar.java: Component lịch
TimeSlot.java: Component hiển thị khung giờ
TableComponent.java: Component bảng dữ liệu
DialogComponent.java: Component hộp thoại



III. Controller (Điều khiển)
1. Điều khiển chính

MainController.java: Điều khiển luồng chính
AuthController.java: Xử lý đăng nhập, phân quyền

2. Điều khiển quản lý sân và đặt sân

FieldController.java: Xử lý tương tác quản lý sân
BookingController.java: Xử lý tương tác đặt sân
MonthlyBookingController.java: Xử lý đơn đặt sân theo tháng

3. Điều khiển quản lý khách hàng

CustomerController.java: Xử lý tương tác quản lý khách hàng

4. Điều khiển bán hàng và kho

SalesController.java: Xử lý tương tác bán hàng
ProductController.java: Xử lý tương tác quản lý sản phẩm
InventoryController.java: Xử lý tương tác quản lý kho

5. Điều khiển báo cáo

ReportController.java: Xử lý tương tác báo cáo
TransactionController.java: Xử lý tương tác quản lý thu chi

6. Điều khiển quản lý hệ thống

BranchController.java: Xử lý tương tác quản lý chi nhánh
UserController.java: Xử lý tương tác quản lý người dùng
SettingsController.java: Xử lý tương tác thiết lập hệ thống

IV. Các gói bổ sung
1. Utils (Tiện ích)

DateTimeUtils.java: Xử lý thời gian, ngày tháng
ValidationUtils.java: Kiểm tra hợp lệ dữ liệu
PdfGenerator.java: Tạo file PDF (hóa đơn, báo cáo)
ExcelExporter.java: Xuất dữ liệu ra Excel
Constants.java: Các hằng số sử dụng trong ứng dụng

2. Config (Cấu hình)

AppConfig.java: Cấu hình ứng dụng
DatabaseConfig.java: Cấu hình kết nối CSDL

3. Exception (Xử lý ngoại lệ)

BookingException.java: Xử lý ngoại lệ liên quan đến đặt sân
InventoryException.java: Xử lý ngoại lệ liên quan đến kho
AuthenticationException.java: Xử lý ngoại lệ liên quan đến xác thực

## Luồng xử lý điển hình
Ví dụ về luồng xử lý chức năng đặt sân:

Người dùng tương tác với BookingView để đặt sân
BookingController nhận yêu cầu và gọi BookingService
BookingService kiểm tra tính hợp lệ, gọi BookingDAO để lưu dữ liệu
BookingDAO thực hiện lưu vào CSDL
BookingService trả kết quả cho BookingController
BookingController cập nhật BookingView để hiển thị kết quả