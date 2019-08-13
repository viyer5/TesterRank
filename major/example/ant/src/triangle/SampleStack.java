package triangle;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class SampleStack {

    private ArrayList<Integer> intStack;
    private int size;

    public SampleStack() {
        intStack = new ArrayList<Integer>();
        size = 0;
    }

    public void push (int x) {
        intStack.add(x);
        size++;
    }

    public int peek() {
        if (this.getSize() == 0) {
            return -200;
        }
        return intStack.get(size-1);
    }

    public int pop() {
        if (size > 0) {
            int popped = intStack.remove(size - 1);
            size--;
            return popped;
        } else {
            System.out.println("Stack is empty");
            return -200;
        }
    }

    public boolean empty() {
        return size == 0;
    }

    public boolean search(int element) {
        SampleStack temp = new SampleStack();
        temp = this;
        while(!temp.empty()) {
            int ele = temp.pop();
            if (ele == element) {
                return true;
            }
        }
        return false;
    }

    public boolean equals (SampleStack other) {
        boolean equal = false;
        if (other.getSize() != this.size) {
            return false;
        }
        for (int i = 0; i < other.getSize(); i++) {
            int element1 = this.pop();
            int element2 = other.pop();
            if (element1 == element2) {
                equal = true;
            }
        }
        return equal;
    }

    public int getSize() {
        return size;
    }



    public void printStack() {
        for (int i  = size-1; i >= 0; i--) {
            System.out.println(intStack.get(i));
        }
    }

    public static void main (String args[]) {
        SampleStack s = new SampleStack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        //System.out.println(s.peek());
        s.printStack();
    }

}
