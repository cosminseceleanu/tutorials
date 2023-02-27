public class PatternMatching {

  public static void main(String[] args) {
    printLength(new Circle(1.2));
    printLengthPM(new Square(1.2));
    printLengthPM(null);
    printLength(null);
    printLengthPMWithGuard(new Square(1.2));
    printLengthPMWithGuard(new Square(20.1));
  }

  //using instance of
  public static void printLength(Shape shape) {
    if (shape == null) {
      System.out.println("missing shape");
      return;
    }
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
      case null -> System.out.println("missing shape");
      case Circle c -> System.out.println("Circle radius=" + c.radius);
      case Square s -> System.out.println("Square side=" + s.side);
    }
  }

  public static void printLengthPMWithGuard(Shape shape) {
    switch (shape) {
      case Circle c when c.radius > 10 -> System.out.println("Circle with large radius=" + c.radius);
      case Circle c -> System.out.println("Circle with small radius=" + c.radius);
      case Square s when s.side > 10 -> System.out.println("Square with large side=" + s.side);
      case Square s -> System.out.println("Square with small side=" + s.side);
    }
  }

//  public static void processNumber(Object input) {
//    switch (input) {
//      case Number n -> System.out.println("got a number=" + n);
//      case Integer n -> System.out.println("got an integer=" + n);
//      default -> System.out.println("Unknown number");
//    }
//  }

  public sealed interface Shape {
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
}
