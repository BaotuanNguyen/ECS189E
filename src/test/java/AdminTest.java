import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    @Test
    public void testMakeClass() { //taken from TestExample. Testing normal usage.
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() { //testing normal usage for class in the future.
        this.admin.createClass("Test", 2018, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2018));
    }


    @Test
    public void testMakeClass3() { //taken from TestExample. Testing that course in the past can't be created
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testMakeClass4() { //testing course w/ capacity = 0 can't be created
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass5() { //testing course w/ capacity < 0 can't be created
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test //"no instructor maybe assigned to more than 2 classes per year" Testing with 3 assignments.
    public void testMakeClass6() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017)); //3rd class should not be created
    }

    @Test //class must have unique class name and year. testing same name/year but different instructor.
    public void testMakeClass7() {
        this.admin.createClass("Test", 2017, "Instructor1", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        assertTrue(this.admin.getClassInstructor("Test", 2017).equals("Instructor1"));
    }

    @Test //testing normal operation of changeCapacity()
    public void testChangeCapacity() {
        this.admin.createClass("Test", 2017, "Instructor1", 15);
        this.admin.changeCapacity("Test", 2017, 16);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 16);
    }

    @Test //testing normal operation of changeCapacity(), changing to same value
    public void testChangeCapacity2() {
        this.admin.createClass("Test", 2017, "Instructor1", 15);
        this.admin.changeCapacity("Test", 2017, 15);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 15);
    }

    @Test
    public void testChangeCapacity3() { //changing capacity to a valid lower value
        this.admin.createClass("Test", 2017, "Instructor1", 15);
        this.admin.changeCapacity("Test", 2017, 14);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 14);
    }

    @Test //testing changing capacity below enrollment
    public void testChangeCapacity4() {
        this.admin.createClass("Test", 2017, "Instructor1", 15);
        this.student.registerForClass("Test", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 0);
        assertFalse(this.admin.getClassCapacity("Test", 2017) == 0);
    }

}
