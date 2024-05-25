//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Task Queue
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

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * This class contains the code for TaskQueue.
 */
public class TaskQueue {
  private Task[] heapData;
  private int size;
  private CompareCriteria priorityCriteria;

  /**
   * Creates an empty TaskQueue with the given capacity and priority criteria.
   * 
   * @param capacity         the max number of Tasks this priority queue can hold
   * @param priorityCriteria the criteria for the queue to use to determine a Task's priority
   */
  public TaskQueue(int capacity, CompareCriteria priorityCriteria) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacity must be greater than zero.");
    }
    this.heapData = new Task[capacity];
    this.size = 0;
    this.priorityCriteria = priorityCriteria;
  }

  /**
   * Gets the criteria use to prioritize tasks in this a TaskQueue.
   * 
   * @return the prioritization criteria of this TaskQueue
   */
  public CompareCriteria getPriorityCriteria() {
    return priorityCriteria;
  }

  /**
   * Reports if a TaskQueue is empty.
   * 
   * @return true if this TaskQueue is empty, false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Reports the size of a TaskQueue.
   * 
   * @return the number of Tasks in this TaskQueue
   */
  public int size() {
    return size;
  }



  /**
   * Gets the Task in a TaskQueue that has the highest priority WITHOUT removing it. The Task that
   * has the highest priority may differ based on the current priority criteria.
   * 
   * @return the Task in this queue with the highest priority
   */
  public Task peekBest() {
    if (isEmpty()) {
      throw new NoSuchElementException("This Queue is empty");
    }
    return heapData[0];
  }

  /**
   * Adds the newTask to this priority queue.
   * 
   * @param newTask the task to add to the queue
   */
  public void enqueue(Task currentTask) {
    if (currentTask.isCompleted()) {
      throw new IllegalArgumentException("Cant enqueue already completed task.");
    }
    if (size == heapData.length) {
      throw new IllegalStateException("The Queue is completely full");
    }
    heapData[size] = currentTask;
    percolateUp(size);
    size = size + 1;
  }

  /**
   * Fixes one heap violation by moving it up the heap.
   * 
   * @param index the of the element where the violation may be
   */
  protected void percolateUp(int index) {
    Task currentTask = heapData[index];

    while (index > 0) {
      int parentIndex = (index - 1) / 2;
      Task parentTask = heapData[parentIndex];
      if (parentTask == null || currentTask == null) {
        break;
      }

      int comparisonResult = parentTask.compareTo(currentTask, priorityCriteria);

      if (comparisonResult >= 0) {
        break;
      }

      heapData[index] = parentTask;
      index = parentIndex;
    }

    // Place the current task in the final correct position
    heapData[index] = currentTask;
  }



  /**
   * Gets and removes the Task that has the highest priority. The Task that has the highest priority
   * may differ based on the current priority criteria.
   * 
   * @return the Task in this queue with the highest priority
   */
  public Task dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("This Queue is empty");
    }
    Task data = heapData[0];
    heapData[0] = heapData[size - 1];
    heapData[size - 1] = null;
    size = size - 1;
    percolateDown(0);
    return data;
  }


  /**
   * Fixes one heap violation by moving it down the heap.
   * 
   * @param index the of the element where the violation may be
   */
  protected void percolateDown(int index) {
    Task currentTask = heapData[index];

    while (2 * index + 1 < size) {
      int childsIndex = 2 * index + 1;

      if (childsIndex + 1 < size
          && heapData[childsIndex + 1].compareTo(heapData[childsIndex], priorityCriteria) > 0) {
        childsIndex++;
      }


      if (currentTask.compareTo(heapData[childsIndex], priorityCriteria) >= 0) {
        break;
      }


      heapData[index] = heapData[childsIndex];

      index = childsIndex;
    }
    heapData[index] = currentTask;
  }



  /**
   * Changes the priority criteria of this priority queue and fixes it so that is is a proper
   * priority queue based on the new criteria.
   * 
   * @param newPriorityCriteria the (new) criteria that should be used to prioritize the Tasks in
   *                            this queue
   */
  public void reprioritize(CompareCriteria newPriorityCriteria) {
    if (newPriorityCriteria == priorityCriteria) {
      return;
    }
    priorityCriteria = newPriorityCriteria;

    for (int i = size / 2 - 1; i >= 0; i--) {
      percolateDown(i);
    }
  }



  /**
   * Creates and returns a deep copy of the heap's array of data.
   * 
   * @return the deep copy of the array holding the heap's data
   */
  public Task[] getHeapData() {
    return Arrays.copyOf(heapData, size);
  }
}
