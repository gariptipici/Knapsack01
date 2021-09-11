package com.mobiquity.packer;

import com.mobiquity.util.FileReader;
import com.mobiquity.util.Knapsack;
import com.mobiquity.data.Line;
import com.mobiquity.exception.APIException;
import java.util.List;

public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws APIException {
    List<String> indexes;
    try {
      List<Line> lines = FileReader.read(filePath);
      if (containsUnconstrained(lines)) {
        throw new APIException("File contains unconstrained line(s)");
      }
      indexes = Knapsack.fillKnapsacks(lines);
    } catch (Exception e) {
      throw new APIException(e.getMessage());
    }
    return String.join("\n", indexes);
  }

  private static Boolean containsUnconstrained(List<Line> lines) {
    return lines.stream().anyMatch(
        line -> line.getKnapsackWeight() > 10000 || line.getItems().size() > 15 || line.getItems()
            .stream().anyMatch(item -> item.getWeight() > 10000 || item.getValue() > 100)
    );
  }
}
