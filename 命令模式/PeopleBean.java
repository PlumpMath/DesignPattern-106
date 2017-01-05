/**
 * Created by Zephyr on 2017/1/5.
 */
public class PeopleBean {
    private int age=-1;
    private String name="";
    public PeopleBean(){
    }
    public PeopleBean(int age,String name){
        this.age=age;
        this.name=name;
    }
    public void update(int age,String name){
        this.age=age;
        this.name=name;
    }
    public void update(int age){
        this.age=age;
    }
    public void update(String name){
        this.name=name;
    }
    protected PeopleBean clone(){
        return new PeopleBean(age,name);
    }
    @Override
    public String toString(){
        return "[年龄："+age+"\t姓名："+name+"]";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
