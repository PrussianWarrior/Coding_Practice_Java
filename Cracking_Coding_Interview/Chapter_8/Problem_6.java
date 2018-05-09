import java.util.*;

public class Problem_6 {
  private static class Tower {
    char name;
    LinkedList<Integer> disks;
    public Tower(char name, int num_of_disks) {
      this.name = name;
      disks = new LinkedList<>();
      for (int i = num_of_disks; i >= 1; i--) {
        disks.add(i);
      }
    }
  }

  public static void main(String[] args) {
    int num_of_disks = Integer.parseInt(args[0]);
    int[] count = {0};
    Tower source_tower       = new Tower('A', num_of_disks);
    Tower intermediate_tower = new Tower('B', 0);
    Tower dest_tower         = new Tower('C', 0);
    
    move_disks_1(num_of_disks, source_tower, intermediate_tower, dest_tower, count);

    System.out.println("Number of disks in tower " + source_tower.name + " = " + source_tower.disks.size());
    System.out.println("Number of disks in tower " + intermediate_tower.name + " = " + intermediate_tower.disks.size());
    System.out.println("Number of disks in tower " + dest_tower.name + " = " + dest_tower.disks.size());
    
    System.out.println("Disks in tower " + dest_tower.name + " from bottom to top:");
    for (int n : dest_tower.disks) {
      System.out.printf("%5d", n);
    }
    System.out.println();
  }

  private static void move_disks_1(int num_of_disks, Tower source_tower, Tower intermediate_tower, 
      Tower dest_tower, int[] count) {
    if (num_of_disks == 1) {
      System.out.printf("%5d: ", ++count[0]);
      move_top_disk_from_source_to_dest(source_tower, dest_tower);
      return;
    }
    move_disks_1(num_of_disks - 1, source_tower, dest_tower, intermediate_tower, count);
    System.out.printf("%5d: ", ++count[0]);
    move_top_disk_from_source_to_dest(source_tower, dest_tower);
    move_disks_1(num_of_disks - 1, intermediate_tower, source_tower, dest_tower, count);
  }

  private static void move_top_disk_from_source_to_dest(Tower source, Tower dest) {
    System.out.println("Move disk " + source.disks.getLast() + " from " + source.name 
        + " to " + dest.name);
    dest.disks.add(source.disks.removeLast());
  }
}