package controller;

import model.User;
import service.UserService;
import service.BranchService;
import java.util.List;
import view.UserListView;
public class UserController {
    private UserListView userListView;
    private UserService userService = new UserService();
    private BranchService branchService = new BranchService();
    public UserController(UserListView userListView) {
        // Constructor logic if needed
        this.userListView = userListView;
    }
    public void loadData(){
        List<User> users = userService.getUsersByBranch(Integer.parseInt(userListView.getSelectedBranch()));
        userListView.loadDataToTable(users);

    }

    public void loadcbdata(){
        userListView.loadcbdata(branchService.findAll());
    }

    public void processDeleteCustomer(){
        User currenuser = userService.getUserById(userListView.getSelectedUserId());
        try{
            if(userService.deleteUser(currenuser.getId())){
                userListView.showMessage("Xóa thành công !");
            }
            else{
                userListView.showMessage("Xóa thất bại !");
            }
        }
        catch (Exception e){
            userListView.showMessage("hãy chọn 1 người !");
            System.out.println(e.getMessage());
            return;
        }
    }

    public void processEditUser(){
        try{
            User currenuser = userService.getUserById(userListView.getSelectedUserId());
             
            userListView.initdialog(currenuser.getFullName(), currenuser.getEmail(), currenuser.getPhone(), currenuser.getRole());
            userListView.showDialog(true);
        }
        catch (Exception e){
            userListView.showMessage("hãy chọn 1 người !");
            return;
        }
    }

    public void processUpdateUser(){
        User user = userListView.getUpdatedUser();
        if(userService.updateUser(user)){
            userListView.showMessage("Cập nhật thành công !");
        }
        else{
            userListView.showMessage("Cập nhật thất bại !");
        }
    }

    public void processAddUser(){
        // User user = userListView.getNewUser();
        // if(userService.addUser(user)){
        //     userListView.showMessage("Thêm thành công !");
        // }
        // else{
        //     userListView.showMessage("Thêm thất bại !");
        // }
        
        User newUser = userListView.getNewUser();
        if(userService.addUser(newUser)){
            userListView.showMessage("Thêm thành công !");
        }
        else{
            userListView.showMessage("Thêm thất bại !");
        }
    }
}
