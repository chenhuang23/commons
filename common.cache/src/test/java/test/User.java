package test;

import java.io.Serializable;
import java.net.SocketAddress;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            name;
    private int               age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
