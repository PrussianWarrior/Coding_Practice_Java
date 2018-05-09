import java.util.*;

public class Problem_13 {
  private static class BoxComparator implements Comparator<Box> {
    @Override
    public int compare(Box box_1, Box box_2) {
      return box_2.height - box_1.height;
    }
  }

  private static final BoxComparator BOX_COMPARATOR = new BoxComparator();

  private static class Box {
    int height;
    int width;
    int depth;

    public Box(int height, int width, int depth) {
      this.height = height;
      this.width = width;
      this.depth = depth;
    }

    @Override
    public String toString() {
      return String.format("Height = %5d; Width = %5d; Depth = %5d", height, width, depth);
    }

    public boolean can_be_above(Box box) {
      return this.width < box.width && this.depth < box.depth;
    }
  }

  public static void main(String[] args) {
    Box[] boxes_1 = {
      new Box(100, 3, 3),  new Box(11, 4, 4),   new Box(13, 2, 2),   new Box(17, 7, 7),
      new Box(19, 5, 5),   new Box(23, 11, 11), new Box(29, 13, 13), new Box(31, 17, 17),
      new Box(25, 18, 18), new Box(33, 19, 19), new Box(34, 20, 20), new Box(35, 21, 21),
      new Box(36, 22, 22), new Box(37, 23, 23), new Box(38, 24, 24), new Box(39, 25, 25),
      new Box(41, 27, 27), new Box(300, 19, 19), new Box(43, 28, 28), new Box(100, 30, 30),
    };

    int height_of_tallest_stack_1 = find_tallest_stack_1(boxes_1);
    int height_of_tallest_stack_2 = find_tallest_stack_2(boxes_1);
    int height_of_tallest_stack_3 = find_tallest_stack_3(boxes_1);

    System.out.println("The height of the tallest stack is");
    System.out.println("Method 1: " + height_of_tallest_stack_1);
    System.out.println("Method 2: " + height_of_tallest_stack_2);
    System.out.println("Method 3: " + height_of_tallest_stack_3);
  }

  private static int find_tallest_stack_1(Box[] boxes) {
    Arrays.sort(boxes, BOX_COMPARATOR);
    for (Box box : boxes) {
      System.out.println(box);
    }
    int max_height = 0;
    for (int i = 0; i < boxes.length; i++) {
      max_height = Math.max(max_height, find_tallest_stack_1(boxes, i));
    }
    return max_height;
  }

  private static int find_tallest_stack_1(Box[] boxes, int index) {
    int max_height = 0;
    for (int i = index + 1; i < boxes.length; i++) {
      if (boxes[i].can_be_above(boxes[index])) {
        int height = find_tallest_stack_1(boxes, i);
        max_height = Math.max(height, max_height);
      }
    }
    max_height += boxes[index].height;
    return max_height;
  }

  private static int find_tallest_stack_2(Box[] boxes) {
    Arrays.sort(boxes, BOX_COMPARATOR);
    int max_height = 0;
    int[] cache = new int[boxes.length];
    for (int i = 0; i < boxes.length; i++) {
      max_height = Math.max(max_height, find_tallest_stack_2(boxes, i, cache));
    }
    return max_height;
  }

  private static int find_tallest_stack_2(Box[] boxes, int index, int[] cache) {
    if (index < boxes.length && cache[index] > 0) {
      System.out.println("Returning cached result for box with index = " + index);
      return cache[index];
    }
    int max_height = 0;
    for (int i = index + 1; i < boxes.length; i++) {
      if (boxes[i].can_be_above(boxes[index])) {
        int height = find_tallest_stack_2(boxes, i, cache);
        max_height = Math.max(max_height, height);
      }
    }

    max_height += boxes[index].height;
    cache[index] = max_height;
    return max_height;
  }

  private static int find_tallest_stack_3(Box[] boxes) {
    int[] cache = new int[boxes.length];
    return find_tallest_stack_3(boxes, null, 0, cache);
  }

  private static int find_tallest_stack_3(Box[] boxes, Box base, int index, int[] cache) {
    if (index >= boxes.length) {
      return 0;
    }

    Box next_base = boxes[index];
    int height_with_base = 0;
    if (base == null || next_base.can_be_above(base)) {
      if (cache[index] == 0) {
        cache[index] = find_tallest_stack_3(boxes, next_base, index + 1, cache);
        cache[index] += next_base.height;
      }
      height_with_base = cache[index];
    }
    int height_without_base = find_tallest_stack_3(boxes, base, index + 1, cache);
    return Math.max(height_with_base, height_without_base);
  }
}