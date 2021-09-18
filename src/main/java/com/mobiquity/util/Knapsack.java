package com.mobiquity.util;

import com.mobiquity.data.Item;
import com.mobiquity.data.Line;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// A Dynamic Programming based solution
// for 0-1 Knapsack problem
public class Knapsack {

  public static List<String> fillKnapsacks(List<Line> lines) {
    return lines.stream().map(Knapsack::fillKnapsack).collect(Collectors.toList());
  }

  private static String fillKnapsack(Line line) {
    int W = line.getKnapsackWeight();
    List<Item> items = line.getItems();
    items.sort(Comparator.comparing(Item::getWeight));

    int[] indexes = new int[items.size()];
    int[] wt = new int[items.size()];
    int[] val = new int[items.size()];
    int n = line.getItems().size();
    for (int i = 0; i < n; i++) {
      Item item = items.get(i);
      indexes[i] = item.getIndex();
      wt[i] = (int) Math.round(item.getWeight());
      val[i] = item.getValue();
    }

    StringBuilder result = new StringBuilder();

    int[][] K = new int[n + 1][W + 1];

    buildTable(W, wt, val, n, K);

    // stores the result of Knapsack
    int res = K[n][W];

    int w = W;
    backtrackInTable(indexes, wt, val, n, result, K, res, w);
    return result.toString().isEmpty() ? "-" : result.toString();
  }

  private static void backtrackInTable(int[] indexes, int[] wt, int[] val, int n, StringBuilder result,
      int[][] K, int res, int w) {
    for (int i = n; i > 0 && res > 0; i--) {

      // either the result comes from the top
      // (K[i-1][w]) or from (val[i-1] + K[i-1]
      // [w-wt[i-1]]) as in Knapsack table. If
      // it comes from the latter one/ it means
      // the item is included.
      if (res != K[i - 1][w]) {
        // This item is included.
        result.append(indexes[i - 1]);

        // Since this weight is included its
        // value is deducted
        res = res - val[i - 1];
        w = w - wt[i - 1];

        if (res > 0) {
          result.append(",");
        }
      }
    }
  }

  private static void buildTable(int W, int[] wt, int[] val, int n, int[][] K) {
    // Build table K[][] in bottom up manner
    for (int i = 0; i <= n; i++) {
      for (int w = 0; w <= W; w++) {
        if (i == 0 || w == 0) {
          K[i][w] = 0;
        } else if (wt[i - 1] <= w) {
          int profit1 = val[i - 1] + K[i - 1][w - wt[i - 1]];
          int profit2 = K[i - 1][w];

          K[i][w] = Math.max(profit1, profit2);

        } else {
          K[i][w] = K[i - 1][w];
        }
      }
    }
  }

  public Knapsack(){
  }

}
