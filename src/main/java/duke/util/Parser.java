package duke.util;

import duke.command.*;
import duke.task.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public Parser() {}

    public static Command parse(String fullInput) throws DukeException {
        String[] inputArr = fullInput.split(" ");
        String command = inputArr[0].toLowerCase();
        String description = fullInput.replace(command + " ", "");

        try {
            if (command.equals("list")) {
                return new ListCommand();

            } else if (command.equals("delete")) {
                if (descriptionExists(inputArr, "Please specify a task number!")) {
                    int taskNum = Integer.parseInt(description);
                    return new DeleteCommand(taskNum);
                }

            } else if (command.equals("mark")) {
                if (descriptionExists(inputArr, "Please specify a task number!")) {
                    int taskNum = Integer.parseInt(description);
                    return new MarkCommand(taskNum);
                }

            } else if (command.equals("unmark")) {
                if (descriptionExists(inputArr, "Please specify a task number!")) {
                    int taskNum = Integer.parseInt(description);
                    return new UnmarkCommand(taskNum);
                }

            } else if (command.equals("todo")) {
                if (descriptionExists(inputArr, "Oops, a todo description cannot be left empty!")) {
                    Task task = new Todo(description);
                    return new AddCommand(task);
                }

            } else if (command.equals("deadline")) {
                if (descriptionExists(inputArr, "Oops, a deadline description cannot be left empty!")) {
                    String[] descrArr = description.split(" /by ");
                    if (keywordExists(descrArr, "/by", "deadline")) {
                        LocalDateTime dateTime = parseDateTime(descrArr[1]);
                        Task task = new Deadline(descrArr[0], dateTime);
                        return new AddCommand(task);
                    }
                }

            } else if (command.equals("event")) {
                if (descriptionExists(inputArr, "Oops, an event description cannot be left empty!")) {
                    String[] descrArr = description.split(" /at ");
                    if (keywordExists(descrArr, "/at", "event")) {
                        LocalDateTime dateTime = parseDateTime(descrArr[1]);
                        Task task = new Event(descrArr[0], dateTime);
                        return new AddCommand(task);
                    }
                }

            } else if (command.equals("bye")) {
                return new ExitCommand();

            } else {
                throw new DukeException("Oh no! I don't understand what that means...");
            }
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date/time format.\n    "
                    + "Please use the following format: yyyy-mm-dd HH:mm");
        }

        return new InvalidCommand();
    }

    public static boolean descriptionExists(String[] inputArr, String errorMessage) throws DukeException {
        if (inputArr.length == 1) {
            throw new DukeException(errorMessage);
        } else {
            return true;
        }
    }

    public static boolean keywordExists(String[] descrArr, String keyword, String taskType) throws DukeException {
        if (descrArr.length == 1) {
            throw new DukeException("Oops, please use " + keyword
                    + " to set a date and time for this " + taskType + "!");
        } else {
            return true;
        }
    }

    public static LocalDateTime parseDateTime(String dt) throws DateTimeParseException {
        return LocalDateTime.parse(dt,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
