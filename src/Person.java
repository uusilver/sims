public class Person{
 private String name;
 public Person(String name){
 this.name = name; 
 }
 public boolean equals(Object o){
 if(!(o instanceof Person)) return false;
 Person p = (Person)o;
 return p.name.equals(this.name);
 }
 }