package triangle.test;
import org.junit.Test;
import static org.junit.Assert.*;
import triangle.SampleStack;

public class TestSuite {

     @Test
    public void push() {
        SampleStack s  = new SampleStack();
        s.push(3);
        int actual_size = 1;
        int expected_size = s.getSize();
        assertTrue(actual_size == expected_size);
    }

    @Test
    public void peek() {
        SampleStack s = new SampleStack();
        s.push(3);
        int expected = 3;
        int actual = s.peek();
        assertTrue(actual == expected);
    }

    @Test
    public void pop() {
        SampleStack s = new SampleStack();
        s.push(3);
        s.pop();
        int expected_size = 0;
        int actual_size = s.getSize();
        assertTrue(expected_size == actual_size);
    }

    @Test
    public void peek2() {
        SampleStack s = new SampleStack();
        int actual = -200;
        int expected = s.peek();
        assertTrue(actual == expected);
    }

    @Test
    public void pop2() {
        SampleStack s = new SampleStack();
        int expected = -200;
        int actual = s.pop();
        assertTrue(actual == expected);
    }

    @Test
    public void equals() {
        SampleStack s = new SampleStack();
        s.push(3);
        s.push(4);
        SampleStack t = new SampleStack();
        t.push(4);
        t.push(5);
        boolean expected = false;
        boolean actual = s.equals(t);
        assertTrue(actual == expected);
    }

    @Test
    public void empty() {
        SampleStack s = new SampleStack();
        boolean expected = true;
        boolean actual = s.empty();
        assertTrue(actual == expected);
    }

    @Test
    public void empty2() {
        SampleStack s = new SampleStack();
        boolean expected = false;
        s.push(1);
        boolean actual = s.empty();
        assertTrue(actual == expected);
    }

    @Test
    public void search() {
        SampleStack s = new SampleStack();
        s.push(1);
        s.push(11);
        s.push(17);
        s.push(12);
        boolean actual = s.search(1);
        boolean expected = true;
        assertTrue(expected == actual);
    }
}