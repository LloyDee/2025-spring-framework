package car.example.bean;

public class MyBean {
    private String message;
    private int age;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void showMessage() {
        System.out.println("Message: "+message);
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "message='" + message + '\'' +
                ", age=" + age +
                '}';
    }
}
