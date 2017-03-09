import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test
    public void testRegisterClass() { //positive test for normal use case
        this.admin.createClass("Course1", 2017, "Instructor1", 10);
        this.student.registerForClass("Student1", "Course1", 2017);
        assertTrue(this.student.isRegisteredFor("Student1", "Course1", 2017));
    }

    @Test
    public void testRegisterClass2() { //testing for full class. should not be able to register
        this.admin.createClass("Course1", 2017, "Instructor1", 1);
        this.student.registerForClass("Student1", "Course1", 2017);
        this.student.registerForClass("Student2", "Course1", 2017);
        assertFalse(this.student.isRegisteredFor("Student2", "Course1", 2017));
    }

    @Test
    public void testRegisterClass3() { //testing for class that doesn't exists. should not be able to register
        this.student.registerForClass("Student2", "Course1", 2017);
        assertFalse(this.student.isRegisteredFor("Student2", "Course1", 2017));
    }

    @Test
    public void testDropClass() { //positive test for normal use case
        this.admin.createClass("Course1", 2017, "Instructor1", 10);
        this.student.registerForClass("Student1", "Course1", 2017);
        this.student.dropClass("Student1", "Course1", 2017);
        assertFalse(this.student.isRegisteredFor("Student1", "Course1", 2017));
    }

    @Test
    public void testSubmitHomework(){ //positive test for normal use case
        this.admin.createClass("Course1", 2017, "Instructor1", 10);
        this.instructor.addHomework("Instructor1","Course1",  2017, "Homework 1", "First HW Assignment" );
        this.student.registerForClass("Student1", "Course1", 2017);
        this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2017);
        assertTrue(this.student.hasSubmitted("Student1", "Homework 1", "Course1", 2017));
    }

    @Test
    public void testSubmitHomework2(){ //testing for homework that does not exist
        this.admin.createClass("Course1", 2017, "Instructor1", 10);
        this.student.registerForClass("Student1", "Course1", 2017);
        this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2017);
        assertFalse(this.student.hasSubmitted("Student1", "Homework 1", "Course1", 2017));
    }

    @Test
    public void testSubmitHomework3(){ //testing for submitting HW for an unregistered course
        this.admin.createClass("Course1", 2017, "Instructor1", 10);
        this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2017);
        assertFalse(this.student.hasSubmitted("Student1", "Homework 1", "Course1", 2017));
    }

    @Test
    public  void testSubmitHomework4(){ //testing for submitting HW in a different year
        this.admin.createClass("Course1", 2018, "Instructor1", 10);
        this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2018);
        assertFalse(this.student.hasSubmitted("Student1", "Homework 1", "Course1", 2018));
    }

}