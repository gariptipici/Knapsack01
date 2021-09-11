package com.mobiquity.util;

import com.mobiquity.data.Item;
import com.mobiquity.data.Line;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

  private FileReader() {
  }

  public static List<Line> read(String path) throws IOException {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(FileReader::process).collect(Collectors.toList());
    }
  }

  private static Line process(String lineString) {
    String[] weightAndItems = lineString.split(" : ");
    int knapsackWeight = Integer.parseInt(weightAndItems[0]) * 100;
    String itemsString = weightAndItems[1];
    String[] itemsArray = itemsString.substring(1, itemsString.length() - 1).split("\\) \\(");
    List<Item> items = Arrays.stream(itemsArray).map(itemString -> {
      String[] itemArray = itemString.split(",");
      Item item = new Item();
      item.setIndex(Integer.parseInt(itemArray[0]));
      item.setWeight(Double.parseDouble(itemArray[1]) * 100);
      item.setValue(Integer.parseInt(itemArray[2].substring(1)));
      return item;
    }).collect(Collectors.toList());
    Line line = new Line();
    line.setKnapsackWeight(knapsackWeight);
    line.setItems(items);
    return line;
  }

}
