package com.mobiquity.data;

import java.util.List;

public class Line {
  private int knapsackWeight;
  private List<Item> items;

  public int getKnapsackWeight() {
    return knapsackWeight;
  }

  public void setKnapsackWeight(int knapsackWeight) {
    this.knapsackWeight = knapsackWeight;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
