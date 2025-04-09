public class Branch {
    private int branchId;
    private String branchName;
    private String location;

    public Branch(int branchId, String branchName, String location) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.location = location;
    }

    // Getters
    public int getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Chi nhánh: " + branchName + " (ID: " + branchId + ") - Vị trí: " + location;
    }
}