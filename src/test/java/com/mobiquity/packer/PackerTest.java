package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PackerTest {

  private static final String TEST_FILE_OUTPUT_PATH = "src/test/resources/example_output";
  private static final String TEST_FILE_INPUT_PATH = "src/test/resources/example_input";
  private static final String TEST_FILE_ERROR_INPUT_PATH = "src/test/resources/example_error_input";
  private static final String TEST_FILE_UNCONSTRAINED_INPUT_PATH = "src/test/resources/example_unconstrained_input";

  @Test
  void packTest() throws APIException, IOException {
    String output = Files.lines(Paths.get(TEST_FILE_OUTPUT_PATH))
        .collect(Collectors.joining("\n"));

    File file = new File(TEST_FILE_INPUT_PATH);
    String absolutePath = file.getAbsolutePath();
    String result = Arrays.stream(Packer.pack(absolutePath).split("\n")).map(line ->
        Arrays.stream(line.split(",")).sorted().collect(Collectors.joining(","))
    ).collect(Collectors.joining("\n"));
    Assertions.assertEquals(output, result);
  }

  @Test
  void packTestException() {
    File file = new File(TEST_FILE_ERROR_INPUT_PATH);
    String absolutePath = file.getAbsolutePath();

    Assertions.assertThrows(APIException.class, () -> Packer.pack(absolutePath));
  }

  @Test
  void packTestUnconstrained() {
    File file = new File(TEST_FILE_UNCONSTRAINED_INPUT_PATH);
    String absolutePath = file.getAbsolutePath();

    Assertions.assertThrows(APIException.class, () -> Packer.pack(absolutePath));
  }

}
