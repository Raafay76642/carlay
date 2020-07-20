package com.example.smartparkingsystem;

public class profileModelClass {
    private String Name;
    private String age;
    private String Lno;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLno() {
        return Lno;
    }

    public void setLno(String lno) {
        Lno = lno;
    }


public profileModelClass()
{

}

    public profileModelClass(String name, String age, String lno) {
        Name = name;
        this.age = age;
        Lno = lno;
    }
}
