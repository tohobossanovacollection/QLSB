package utils;

public class test2 {
    public static void main(String[] args) {
        //khai bao
        //DayEnum day = DayEnum.MONDAY;
        //in enum
        for(DayEnum day : DayEnum.values())
        {
            System.out.println(day.getDayName());
        }
        //lay day qua index ko dung .day()
        System.out.println(DayEnum.values()[0]);
    }
}
