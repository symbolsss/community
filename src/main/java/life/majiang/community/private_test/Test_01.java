package life.majiang.community.private_test;

public class Test_01 {
}

class Person{
    Person()
    {
        System.out.println("person run");
    }
}

class PersonDemo2
{
    public static void main(String[] args)
    {
        System.out.println("---1---");
        Person p = new Person();
        System.out.println("---2---");
        new Person();
        System.out.println("---3---");
    }
}