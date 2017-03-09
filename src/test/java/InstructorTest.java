import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstructorTest {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test //normal use case
    public void testMakeHw() { //Testing normal usage.
        this.admin.createClass("Course1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1","Course1",  2017, "Homework 1", "First HW Assignment" );
        assertTrue(this.instructor.homeworkExists("Course1", 2017, "Homework 1"));
    }

    @Test //test for unassigned instructor
    public void testMakeHW2() {
        this.admin.createClass("Course1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor2","Course1",  2017, "Homework 1", "First HW Assignment" );
        assertFalse(this.instructor.homeworkExists("Course1", 2017, "Homework 1"));
    }

    @Test //normal use case
    public void testAssignGrade() {
        this.admin.createClass("Course1", 2017, "Instructor1", 15);
        this.student.registerForClass("Student1", "Course1", 2017);
        this.instructor.addHomework("Instructor1","Course1",  2017, "Homework 1", "First HW Assignment" );
        this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2017);
        this.instructor.assignGrade("Instructor1", "Course1", 2017, "Homework 1", "Student1", 100);
        assertTrue(this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") != null && this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") == 100 );
    }

    @Test //normal use case, score >100
    public void testAssignGrade2() {
        this.admin.createClass("Course1", 2017, "Instructor1", 15);
        this.student.registerForClass("Student1", "Course1", 2017);
        this.instructor.addHomework("Instructor1","Course1",  2017, "Homework 1", "First HW Assignment" );
        this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2017);
        this.instructor.assignGrade("Instructor1", "Course1", 2017, "Homework 1", "Student1", 120);
        assertTrue(this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") != null && this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") == 120 );
    }

    @Test //unassigned instructor
    public void testAssignGrade3() {
        this.admin.createClass("Course1", 2017, "Instructor1", 15);
        this.student.registerForClass("Student1", "Course1", 2017);
        this.instructor.addHomework("Instructor1","Course1",  2017, "Homework 1", "First HW Assignment" );
        this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2017);
        this.instructor.assignGrade("Instructor2", "Course1", 2017, "Homework 1", "Student1", 100);
        assertFalse(this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") != null && this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") == 100 );
    }

    @Test //unsubmitted homework
    public void testAssignGrade4() {
        this.admin.createClass("Course1", 2017, "Instructor1", 15);
        this.student.registerForClass("Student1", "Course1", 2017);
        this.instructor.addHomework("Instructor1","Course1",  2017, "Homework 1", "First HW Assignment" );
        //this.student.submitHomework("Student1", "Homework 1", "myAnswer", "Course1", 2017);
        this.instructor.assignGrade("Instructor1", "Course1", 2017, "Homework 1", "Student1", 100);
        assertFalse(this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") != null && this.instructor.getGrade("Course1", 2017, "Homework 1", "Student1") == 100 );
    }
}
