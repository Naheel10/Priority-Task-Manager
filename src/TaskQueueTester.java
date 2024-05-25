//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Task Queue Tester
// Course: CS 300 Spring 2024
//
// Author: Muhammad Naheel
// Email: naheel@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons: None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * A suite of tester methods to check the correctness of various methods for the Prioritized Task
 * Manager assignment.
 */
public class TaskQueueTester {

  /**
   * Tests the correctness of a Task compareTo() method implementation when the criteria parameter
   * is TITLE.
   * 
   * @return true if all test cases pass, false otherwise
   */
  public static boolean testCompareToTitle() {
    Task task1 = new Task("Gym", "Workout at the gym", 60, PriorityLevel.HIGH);
    Task task2 = new Task("Call parents", "Check in with parents", 10, PriorityLevel.MEDIUM);
    Task task3 = new Task("Study", "Study for exams", 30, PriorityLevel.LOW);
    Task task4 = new Task("Laundry", "Do the laundry", 20, PriorityLevel.LOW);
    Task task5 = new Task("gym", "Workout at the gym", 60, PriorityLevel.HIGH);

    // Comparing tasks based on titles in lexicographical order
    boolean test1 = task1.compareTo(task2, CompareCriteria.TITLE) < 0; // "Gym" < "Call parents"
    boolean test2 = task2.compareTo(task3, CompareCriteria.TITLE) > 0; // "Call parents" > "Study"
    boolean test3 = task3.compareTo(task4, CompareCriteria.TITLE) < 0; // "Study" < "Laundry"
    boolean test4 = task1.compareTo(task1, CompareCriteria.TITLE) == 0; // "Gym" == "Gym"
    boolean test5 = task4.compareTo(task2, CompareCriteria.TITLE) != 0; // "Laundry" != "Call
                                                                        // parents"
    boolean test6 = task5.compareTo(task1, CompareCriteria.TITLE) != 0; // "Gym" != "gym"

    return test1 && test2 && test3 && test4 && test5 && test6;
  }

  /**
   * Tests the correctness of a Task compareTo() method implementation when the criteria parameter
   * is LEVEL.
   * 
   * @return true if all test cases pass, false otherwise
   */
  public static boolean testCompareToLevel() {
    Task task1 = new Task("Gym", "Workout at the gym", 60, PriorityLevel.HIGH);
    Task task2 = new Task("Call parents", "Check in with parents", 10, PriorityLevel.MEDIUM);
    Task task3 = new Task("Study", "Study for exams", 30, PriorityLevel.LOW);
    Task task4 = new Task("Laundry", "Do the laundry", 20, PriorityLevel.LOW);
    Task task5 = new Task("Meet friends", "Hang out with friends", 40, PriorityLevel.MEDIUM);
    Task task6 = new Task("Soccer", "Play soccer", 10, PriorityLevel.LOW);

    // Comparing tasks based on levels (priorities)
    boolean test1 = task1.compareTo(task2, CompareCriteria.LEVEL) > 0; // HIGH > MEDIUM
    boolean test2 = task2.compareTo(task4, CompareCriteria.LEVEL) > 0; // MEDIUM > LOW
    boolean test3 = task1.compareTo(task5, CompareCriteria.LEVEL) > 0; // HIGH > MEDIUM
    boolean test4 = task4.compareTo(task6, CompareCriteria.LEVEL) == 0; // LOW == LOW
    boolean test5 = task2.compareTo(task3, CompareCriteria.LEVEL) > 0; // MEDIUM > LOW

    return test1 && test2 && test3 && test4 && test5;
  }

  /**
   * Tests the correctness of a Task compareTo() method implementation when the criteria parameter
   * is TIME.
   * 
   * @return true if all test cases pass, false otherwise
   */
  public static boolean testCompareToTime() {
    Task task1 = new Task("Gym", "Workout at the gym", 60, PriorityLevel.HIGH);
    Task task2 = new Task("Call parents", "Check in with parents", 10, PriorityLevel.MEDIUM);
    Task task3 = new Task("Study", "Study for exams", 30, PriorityLevel.LOW);
    Task task4 = new Task("Laundry", "Do the laundry", 20, PriorityLevel.LOW);
    Task task5 = new Task("Meet friends", "Hang out with friends", 40, PriorityLevel.MEDIUM);
    Task task6 = new Task("Soccer", "Play soccer", 10, PriorityLevel.LOW);

    // Comparing tasks based on times (durations)
    boolean test1 = task1.compareTo(task2, CompareCriteria.TIME) > 0; // 60 > 10
    boolean test2 = task3.compareTo(task4, CompareCriteria.TIME) > 0; // 30 > 20
    boolean test3 = task4.compareTo(task6, CompareCriteria.TIME) > 0; // 20 > 10
    boolean test4 = task1.compareTo(task3, CompareCriteria.TIME) > 0; // 60 > 30
    boolean test5 = task2.compareTo(task6, CompareCriteria.TIME) == 0; // 10 == 10

    return test1 && test2 && test3 && test4 && test5;
  }

  /**
   * Tests the correctness of a TaskQueue enqueue() method implementation including exceptions and
   * edge cases.
   * 
   * @return true if all test cases pass, false otherwise
   */
  public static boolean testEnqueue() {
    TaskQueue queue = new TaskQueue(5, CompareCriteria.LEVEL);
    Task task1 = new Task("Gym", "Workout at the gym", 60, PriorityLevel.HIGH);
    Task task2 = new Task("Call parents", "Check in with parents", 10, PriorityLevel.MEDIUM);
    Task task3 = new Task("Study", "Study for exams", 30, PriorityLevel.LOW);
    Task task4 = new Task("Laundry", "Do the laundry", 20, PriorityLevel.LOW);
    Task task5 = new Task("Meet friends", "Hang out with friends", 40, PriorityLevel.MEDIUM);

    queue.enqueue(task1);
    queue.enqueue(task2);
    queue.enqueue(task3);
    queue.enqueue(task4);
    queue.enqueue(task5);

    // Verify the order of tasks based on LEVEL priority criteria
    boolean test1 = queue.peekBest().equals(task1);
    queue.dequeue();

    test1 = test1 && queue.peekBest().equals(task2);
    queue.dequeue();

    test1 = test1 && queue.peekBest().equals(task5);
    queue.dequeue();

    test1 = test1 && queue.peekBest().equals(task3);
    queue.dequeue();

    test1 = test1 && queue.peekBest().equals(task4);

    // Testing exception cases
    try {
      queue.enqueue(new Task("Soccer", "Play soccer", 10, PriorityLevel.LOW));
      return false;
    } catch (IllegalStateException e) {

    }

    try {
      queue.enqueue(task1);
      return false;
    } catch (IllegalArgumentException e) {

    }

    return test1;
  }

  /**
   * Tests the correctness of a TaskQueue dequeue() method implementation including exceptions and
   * edge cases.
   * 
   * @return true if all test cases pass, false otherwise
   */
  public static boolean testDequeue() {
    TaskQueue queue = new TaskQueue(6, CompareCriteria.LEVEL);
    Task task1 = new Task("Gym", "Workout at the gym", 60, PriorityLevel.HIGH);
    Task task2 = new Task("Call parents", "Check in with parents", 10, PriorityLevel.MEDIUM);
    Task task3 = new Task("Study", "Study for exams", 30, PriorityLevel.LOW);
    Task task4 = new Task("Laundry", "Do the laundry", 20, PriorityLevel.LOW);
    Task task5 = new Task("Meet friends", "Hang out with friends", 40, PriorityLevel.MEDIUM);
    Task task6 = new Task("Soccer", "Play soccer", 10, PriorityLevel.LOW);

    queue.enqueue(task1);
    queue.enqueue(task2);
    queue.enqueue(task3);
    queue.enqueue(task4);
    queue.enqueue(task5);
    queue.enqueue(task6);

    // Verify tasks are dequeued in the correct order based on LEVEL priority
    boolean test1 = queue.dequeue().equals(task1);
    boolean test2 = queue.dequeue().equals(task5);
    boolean test3 = queue.dequeue().equals(task2);
    boolean test4 = queue.dequeue().equals(task3);
    boolean test5 = queue.dequeue().equals(task4);
    boolean test6 = queue.dequeue().equals(task6);

    // Testing exception handling when the queue is empty
    try {
      queue.dequeue();
      return false;
    } catch (NoSuchElementException e) {

    }

    return test1 && test2 && test3 && test4 && test5 && test6;
  }

  /**
   * Tests the correctness of a TaskQueue peek() method implementation including exceptions and edge
   * cases.
   * 
   * @return true if all test cases pass, false otherwise
   */
  public static boolean testPeek() {
    TaskQueue queue = new TaskQueue(2, CompareCriteria.LEVEL);
    Task task1 = new Task("Gym", "Workout at the gym", 60, PriorityLevel.HIGH);
    Task task2 = new Task("Call parents", "Check in with parents", 10, PriorityLevel.MEDIUM);

    queue.enqueue(task1);
    queue.enqueue(task2);

    // Test peeking the task with the highest priority
    boolean test1 = queue.peekBest().equals(task1);

    // Testing peeking from an empty queue
    queue.dequeue(); // Dequeues task1
    queue.dequeue(); // Dequeues task2

    try {
      queue.peekBest();
      return false;
    } catch (NoSuchElementException e) {

    }

    return test1;
  }

  /**
   * Tests the correctness of a TaskQueue reprioritize() method implementation including exceptions
   * and edge cases.
   * 
   * @return true if all test cases pass, false otherwise
   */
  public static boolean testReprioritize() {
    TaskQueue queue = new TaskQueue(3, CompareCriteria.TIME);
    Task task1 = new Task("Gym", "Workout at the gym", 60, PriorityLevel.LOW);
    Task task2 = new Task("Call parents", "Check in with parents", 10, PriorityLevel.HIGH);
    Task task3 = new Task("Meet friends", "Hang out with friends", 40, PriorityLevel.URGENT);

    queue.enqueue(task1);
    queue.enqueue(task2);
    queue.enqueue(task3);

    queue.reprioritize(CompareCriteria.LEVEL);

    // Checking the order of tasks after reprioritization
    boolean test1 = queue.dequeue().equals(task3);
    boolean test2 = queue.dequeue().equals(task2);
    boolean test3 = queue.dequeue().equals(task1);

    return test1 && test2 && test3;
  }

  /**
   * Runs all tester methods and displays results.
   * 
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    System.out.println("testCompareToTitle: " + testCompareToTitle());
    System.out.println("testCompareToLevel: " + testCompareToLevel());
    System.out.println("testCompareToTime: " + testCompareToTime());
    System.out.println("testEnqueue: " + testEnqueue());
    System.out.println("testDequeue: " + testDequeue());
    System.out.println("testPeek: " + testPeek());
    System.out.println("testReprioritize: " + testReprioritize());
  }
}
