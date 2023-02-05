# Enhancing Code Readability with Java Pattern Matching 

## Introduction

Java 14 introduced a new feature called pattern matching, which aims to simplify the process of matching patterns in code and make it more readable. This feature allows developers to match against different types of data, such as objects, primitives, and instance of classes using the instanceof operator in a more concise and readable way.

Additionally, pattern matching also provides a way to extract data from matched patterns and assign them to variables, making it easier to work with matched data. In this article, we will explore the basics of pattern matching and how it can be used to improve the readability and maintainability of your code.

## Definition

In the context of programming languages, pattern matching is a technique used to match a specific pattern within a given input. It can be used to simplify the process of identifying and extracting relevant information from structured data, such as objects, lists, and other complex data types.

In functional languages, pattern matching can be used as an alternative to traditional control flow statements such as if-else or switch-case.

## Benefits

* Simplicity and readability: Pattern matching provides a more concise and readable syntax compared to traditional control flow statements
* Improved type safety: Pattern matching allows developers to match against different types of data, such as objects, primitives, and instances of classes using the instanceof operator. This can lead to better type safety, as it reduces the risk of type errors and makes it easier to handle exceptions and errors.
* Extracting values from matched patterns: With pattern matching, it is possible to extract values from matched patterns and assign them to variables, making it easier to work with matched data.

## Pattern matching in action

Throughout this article, we will utilize the following classes to demonstrate the advantages of pattern matching in Java. 

```java
public interface Shape {
    Double area();
  }

  public final static class Circle implements Shape {
    public final double radius;

    Circle(double radius) {
      this.radius = radius;
    }

    @Override
    public Double area() {
      return Math.PI * radius * radius;
    }
  }

  public final static class Square implements Shape {
    public final double side;

    Square(double side) {
      this.side = side;
    }

    @Override
    public Double area() {
      return side * side;
    }
  }
```

### Type matching

instanceof is a traditional approach to type matching. It checks if an object is an instance of a particular type and returns a boolean value. It is often used in conditional statements to determine the type of an object and take action accordingly.

On the other hand, the switch statement with pattern matching, introduced in Java 17, is a more concise and expressive way to perform type matching. It allows you to match against the type of an object and extract its fields, reducing the risk of type errors. 

```java
 //using instance of
  public static void printLength(Shape shape) {
    if (shape instanceof Circle) {
      Circle circle = (Circle) shape;
      System.out.println("Circle radius=" + circle.radius);
    } else if (shape instanceof Square) {
      Square square = (Square) shape;
      System.out.println("Square side=" + square.side);
    } else {
      System.out.println("Unknown shape");
    }
  }

  // using pattern matching
  public static void printLengthPM(Shape shape) {
    switch (shape) {
      case Circle c -> System.out.println("Circle radius=" + c.radius);
      case Square s -> System.out.println("Square side=" + s.side);
      default -> System.out.println("Unknown shape");
    }
  }
```

As we can see, the use of pattern matching makes the code more concise and readable, and eliminates the need to cast the matched objects to the correct type :love:.

### Null matching

Handling `null` values is a common task in programming, and pattern matching provides an elegant way to handle it. 
In the example bellow `printLengthPM(Shape shape)`, the switch statement includes a case for `null` to handle missing shapes.

In comparison to the traditional method of checking for `null` values through `if` statements, pattern matching simplifies the code and eliminates the need for separate null checks. This makes the code more readable and reduces the potential for maintenance issues as the code grows larger.

```java
public static void printLength(Shape shape) {
    if (shape == null) {
      System.out.println("missing shape");
      return;
    }
    ----------
}
```
----------
```java
public static void printLengthPM(Shape shape) {
    switch (shape) {
      case null -> System.out.println("missing shape");
      case Circle c -> System.out.println("Circle radius=" + c.radius);
      case Square s -> System.out.println("Square side=" + s.side);
      default -> System.out.println("Unknown shape");
    }
  }
```

### Dominance

In Java pattern matching, dominance plays a crucial role in ensuring code correctness and avoiding ambiguity. Dominance refers to the idea that if a certain case matches the input value, all other cases that are more specific or subtypes of the first match should not be executed.

Consider the following example:

```java
public static void processNumber(Object input) {
  switch (input) {
    case Number n -> System.out.println("got a number=" + n);
    case Integer n -> System.out.println("got an integer=" + n);
    default -> System.out.println("Unknown number");
  }
}
```

In this example, the case for Integer n will never be executed because it is dominated by the preceding case for Number n. This leads to a compile-time error, "this case label is dominated by a preceding case label".
To fix this error, the order of cases should be changed so that the more specific cases come first.

### Completeness

Completeness refers to ensuring that all possible cases are handled within a pattern matching expression. In pattern matching, it's important to have a complete switch expression to avoid unexpected behavior or errors in the code.
In this section, we will delve into ensuring completeness in Java pattern matching.
It is important to consider completeness because, as demonstrated in our first example of the `printLengthPM` method, failing to include a `default` case in a `switch` statement will result in a compile error indicating that the `switch` statement does not cover all possible input values.

To avoid the need for a `default` case, the `Shape` interface can be made sealed. This can be done by simply adding the keyword `sealed` to the interface declaration, as follows:

```java
public sealed interface Shape {}
```
Updated version of `printLengthPM` method:
```java
public static void printLengthPM(Shape shape) {
    switch (shape) {
      case null -> System.out.println("missing shape");
      case Circle c -> System.out.println("Circle radius=" + c.radius);
      case Square s -> System.out.println("Square side=" + s.side);
    }
  }
```

If a new implementation of `Shape` named `Rectangle` is added, the above method that uses the sealed interface will not compile until the switch statement is updated to handle the new type `Rectangle`.

It is recommended to not include a default case as it can lead to unexpected results if the switch statement does not cover all possible input values.
Instead, using a sealed interface for the switch expression is a better way to ensure completeness. By declaring the interface sealed, the compiler can verify that all possible subtypes of the switch expression are covered in the cases.
This results in more robust and error-proof code.


### Guarded patterns

Guarded patterns in Java pattern matching provide a way to match against a value only if a certain condition is met.
This allows for more fine-grained control over the matching process and adds an extra level of complexity to the switch statement.

Here's an example:

```java
public static void printLengthPMWithGuard(Shape shape) {
    switch (shape) {
      case Circle c when c.radius > 10 -> System.out.println("Circle with large radius=" + c.radius);
      case Circle c -> System.out.println("Circle with small radius=" + c.radius);
      case Square s when s.side > 10 -> System.out.println("Square with large side=" + s.side);
      case Square s -> System.out.println("Square with small side=" + s.side);
    }
  }
```

In this example, the `printLengthPMWithGuard` method uses **guarded patterns** to match against a `Circle` or `Square` only if a certain condition is met, such as the radius or side being greater than 10.

This allows for more specific handling of values, and makes the code more flexible and maintainable.

## Conclusion

In conclusion, pattern matching is a valuable addition to the Java language that provides a more readable and concise way to perform type matching. Its use of sealed interfaces, pattern matching, and guarded patterns provide a new way to handle null values, ensure completeness, and simplify complex code. With its ease of use and improved readability, pattern matching is a great way to improve your Java code and make it easier to maintain.
Whether you are a beginner or an experienced Java developer, pattern matching is definitely worth exploring and adding to your coding arsenal.