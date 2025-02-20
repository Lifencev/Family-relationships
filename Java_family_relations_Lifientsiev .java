import java.util.HashSet;
import java.util.Set;

public class Java_family_relations_Lifientsiev {

    public static void main(String[] args) {
        Person john = new Person("john", Person.Gender.Male);
        Person jim = new Person("jim", Person.Gender.Male);
        Person peter = new Person("peter", Person.Gender.Male);
        Person alex = new Person("alex", Person.Gender.Male);
        Person tom = new Person("tom", Person.Gender.Male);
        Person jane = new Person("jane", Person.Gender.Female);
        Person anna = new Person("anna", Person.Gender.Female);
        Person mary = new Person("mary", Person.Gender.Female);
        Person catlyn = new Person("catlyn", Person.Gender.Female);
        Person sarah = new Person("sarah", Person.Gender.Female);

        john.addChild(jim);
        jane.addChild(jim);
        jim.addChild(anna);
        jim.addChild(tom);
        anna.addChild(peter);
        tom.addChild(mary);

        System.out.println("Jim: ");
        System.out.println(jim);
        System.out.print("john is jim's father: ");
        System.out.println(john.isFather(jim));
        System.out.print("jim is john's father: ");
        System.out.println(jim.isFather(john));
        System.out.print("anna is peter's mother: ");
        System.out.println(anna.isMother(peter));
        System.out.print("peter's uncles: ");
        System.out.println(peter.getUncles());
        System.out.print("jim's aunts: ");
        System.out.println(jim.getAunts());
        System.out.print("anna's brothers: ");
        System.out.println(anna.getBrothers());
        System.out.print("tom's sisters: ");
        System.out.println(tom.getSisters());
        System.out.print("jim is john's ancestor: ");
        System.out.println(jim.isAncestor(john));
        System.out.print("john is jim's ancestor: ");
        System.out.println(john.isAncestor(jim));
        System.out.print("jim is john's descendant: ");
        System.out.println(jim.isDescendant(john));
        System.out.print("john is jim's descendant: ");
        System.out.println(john.isDescendant(jim));
        System.out.print("jane's children: ");
        System.out.println(jane.getChildren());
    }
}

class Person {

    enum Gender {
        Male, Female
    }

    private final String name;
    private final Gender gender;
    private final Set<Person> children = new HashSet<>();
    private Person mother;
    private Person father;

    public Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public void setMother(Person mother) {
        if (mother.gender.equals(Gender.Female)) {
            this.mother = mother;
        }
    }

    public void setFather(Person father) {
        if (father.gender.equals(Gender.Male)) {
            this.father = father;
        }
    }

    public void addChild(Person child) {
        this.children.add(child);
        if (this.gender.equals(Gender.Female)) {
            child.setMother(this);
        } else {
            child.setFather(this);
        }
    }

    public boolean isMother(Person person) {
        return person.mother != null && person.mother.equals(this);
    }

    public boolean isFather(Person person) {
        return person.father != null && person.father.equals(this);
    }

    public Set<Person> getChildren() {
        return this.children;
    }

    public Set<Person> getAncestors() {
        Set<Person> ancestors = new HashSet<>();
        if (this.father != null) {
            ancestors.add(this.father);
            ancestors.addAll(this.father.getAncestors());
        }
        if (this.mother != null) {
            ancestors.add(this.mother);
            ancestors.addAll(this.mother.getAncestors());
        }
        return ancestors;
    }

    public Set<Person> getDescendants() {
        Set<Person> descendants = new HashSet<>();
        if (!this.children.isEmpty()) {
            for (Person child: this.children) {
                descendants.add(child);
                descendants.addAll(child.getDescendants());
            }
        }
        return descendants;
    }

    public boolean isAncestor(Person person) {
        return this.getAncestors().contains(person);
    }

    public boolean isDescendant(Person person) {
        return this.getDescendants().contains(person);
    }

    public Set<Person> getSisters() {
        Set<Person> sisters = new HashSet<>();
        addSiblings(this.mother, Gender.Female, sisters);
        addSiblings(this.father, Gender.Female, sisters);
        return sisters;
    }

    public Set<Person> getBrothers() {
        Set<Person> brothers = new HashSet<>();
        addSiblings(this.mother, Gender.Male, brothers);
        addSiblings(this.father, Gender.Male, brothers);
        return brothers;
    }

    private void addSiblings(Person parent, Gender siblingGender, Set<Person> siblings) {
        if (parent != null) {
            for (Person sibling: parent.children) {
                if (sibling.gender.equals(siblingGender) && !sibling.equals(this)) {
                    siblings.add(sibling);
                }
            }
        }
    }

    public Set<Person> getAunts() {
        Set<Person> aunts = new HashSet<>();
        if (this.father != null) {
            aunts.addAll(this.father.getSisters());
        }
        if (this.mother != null) {
            aunts.addAll(this.mother.getSisters());
        }
        return aunts;
    }

    public Set<Person> getUncles() {
        Set<Person> uncles = new HashSet<>();
        if (this.father != null) {
            uncles.addAll(this.father.getBrothers());
        }
        if (this.mother != null) {
            uncles.addAll(this.mother.getBrothers());
        }
        return uncles;
    }

    @Override
    public String toString() {
        return "(" + this.name + ", " + this.gender +
                ", mother: " + (this.mother != null ? name : "Null") +
                ", father: " + (this.father != null ? name : "Null") + ")";
    }
}