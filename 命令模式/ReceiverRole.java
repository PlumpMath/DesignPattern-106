/**
 * Created by Zephyr on 2017/1/5.
 */
public class ReceiverRole {
    private PeopleBean people;
    private PeopleBean peopleCache=new PeopleBean();
    public ReceiverRole(){
        this.people=new PeopleBean(-1,"");
    }
    public ReceiverRole(PeopleBean people){
        this.people=people;
    }

    public void opActionUpdateAge(int age){
        System.out.println("before command: "+people.toString());
        this.people.update(age);
        System.out.println("after command: "+people.toString()+"\n");
    }

    public void opActionUpdateName(String name){
        System.out.println("before command: "+people.toString());
        this.people.update(name);
        System.out.println("after command: "+people.toString()+"\n");
    }

    public void rollBackAge(){
        people.setAge(peopleCache.getAge());
        System.out.println("after rollback: "+people.toString());
    }

    public void rollBackName(){
        people.setName(peopleCache.getName());
        System.out.println("after rollback: "+people.toString());
    }
}
