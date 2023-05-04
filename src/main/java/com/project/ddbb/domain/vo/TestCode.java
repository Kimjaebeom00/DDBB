package com.project.ddbb.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCode {
    private String testCode1 = "#include <iostream>\n" +
            "using namespace std;\n" +
            "\n" +
            "int main() {\n" +
            "\tint a = 3;\n" +
            "\tint b = 7;\n" +
            "\tconst int* const pi = &a;\n" +
            "\n" +
            "\t//*pi = 11;\n" +
            "\tcout << a << \" \" << *pi << \"\\n\";\n" +
            "\ta = -6;\n" +
            "\t//pi = &b;\n" +
            "\tcout << b << \" \" << *pi << \"\\n\";\n" +
            "\n" +
            "\treturn 0;\n" +
            "}";
    private String testCode2 = "#include <iostream>\n" +
            "using namespace std;\n" +
            "\n" +
            "class Circle\n" +
            "{\n" +
            "private:\n" +
            "    // mutable 사용시 const로 선언된 멤버함수안에서 필드의 값을 수정할 수 있다\n" +
            "    double radius;  \n" +
            "public:\n" +
            "    //Circle();\n" +
            "    Circle(double r); // 매개변수 생성자를 만들면 기본 생성자도 만들어야 한다.\n" +
            "    double getRadius() const;\n" +
            "    double getArea() const;\n" +
            "    double getPerimeter() const;\n" +
            "    void setRadius(double value);\n" +
            "};\n";

    private String testCode3 = "#include <iostream>\n" +
            "using namespace std;\n" +
            "\n" +
            "class Circle\n" +
            "{\n" +
            "private:\n" +
            "    double radius;\n" +
            "    static int counts;\n" +
            "public:\n" +
            "    Circle(); // default constructor\n" +
            "    Circle(double radius); // parameter constructor\n" +
            "    //Circle(const Circle& circle); // copy constructor\n" +
            "    Circle(const Circle& circle) = delete;\n" +
            "\n" +
            "    ~Circle(); // destructor\n" +
            "    double getRadius() const;\n" +
            "    double getArea() const;\n" +
            "    double getPerimeter() const;\n" +
            "    void setRadius(double value);\n" +
            "    static int getCount();\n" +
            "};\n" +
            "int Circle::counts = 0;\n" +
            "\n" +
            "Circle::Circle() : radius(1.0) {\n" +
            "    counts++;\n" +
            "    cout << this << \" 객체 생성(기본생성자)\\n\";\n" +
            "}\n" +
            "/*\n" +
            "Circle::Circle() {\n" +
            "    radius = 1.0;\n" +
            "    cout << this << \" 객체 생성(기본생성자)\\n\";\n" +
            "}\n" +
            "\n" +
            "Circle::Circle(double radius) {\n" +
            "    radius = radius;  // 멤버변수와 매개변수 구분 불가\n" +
            "    cout << this << \" 객체 생성(매개변수생성자)\\n\";\n" +
            "}\n" +
            "\n" +
            "Circle::Circle(double radius) {\n" +
            "    this->radius = radius;\n" +
            "    cout << this << \" 객체 생성(매개변수생성자)\\n\";\n" +
            "}\n" +
            "*/\n" +
            "Circle::Circle(double radius) : radius(radius){\n" +
            "    counts++;\n" +
            "    cout << this << \" 객체 생성(매개변수생성자)\\n\";\n" +
            "}\n" +
            "/*\n" +
            "Circle::Circle(const Circle& c){\n" +
            "    radius = c.radius;\n" +
            "    cout << this << \" 객체 생성(복사생성자)\\n\";\n" +
            "}\n" +
            "\n" +
            "Circle::Circle(const Circle& circle) \n" +
            ": radius(circle.radius){\n" +
            "    cout << this << \" 객체 생성(복사생성자)\\n\";\n" +
            "}\n" +
            "*/\n" +
            "Circle::~Circle(){\n" +
            "  counts--;\n" +
            "  cout << this << \" 객체 소멸. 내가 너를 기억하마!\\n\";\n" +
            "}\n" +
            "double Circle::getRadius() const\n" +
            "{\n" +
            "    int a = 9;\n" +
            "    //radius = 2.0;\n" +
            "    return radius;\n" +
            "}\n" +
            "double Circle::getArea() const\n" +
            "{\n" +
            "    const double PI = 3.14;\n" +
            "    return (PI * radius * radius);\n" +
            "}\n" +
            "double Circle::getPerimeter() const\n" +
            "{\n" +
            "    const double PI = 3.14;\n" +
            "    return (2 * PI * radius);\n" +
            "}\n" +
            "void Circle::setRadius(double value)\n" +
            "{\n" +
            "    radius = value;\n" +
            "}\n" +
            "int Circle::getCount(){\n" +
            "  return counts;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "void test(){\n" +
            "  cout << \"Circle 4\" << endl;\n" +
            "  Circle circle4(9.0);\n" +
            "  cout << \"반지름: \" << circle4.getRadius() << endl;\n" +
            "\n" +
            "  cout << \"Circle 5\" << endl;\n" +
            "  Circle circle5;\n" +
            "  cout << \"반지름: \" << circle5.getRadius() << endl;\n" +
            "  cout << Circle::getCount() << \"\\n\";\n" +
            "}\n" +
            "\n" +
            "int main()\n" +
            "{\n" +
            "    // long test;\n" +
            "    // cout << sizeof(test) << \"\\n\";\n" +
            "    \n" +
            "    cout << \"Circle 3\" << endl;\n" +
            "    Circle circle3(5.0);\n" +
            "\n" +
            "    cout << \"Circle 1\" << endl;\n" +
            "    Circle circle1;\n" +
            "    circle1.setRadius(10.0);\n" +
            "\n" +
            "    cout << Circle::getCount() << \"\\n\";\n" +
            "    test();\n" +
            "    cout << Circle::getCount() << \"\\n\";\n" +
            "  \n" +
            "    cout << \"반지름: \" << circle1.getRadius() << endl;\n" +
            "    cout << \"넓이: \" << circle1.getArea() << endl;\n" +
            "    cout << \"둘레: \" << circle1.getPerimeter() << endl << endl;\n" +
            "\n" +
            "    cout << \"Circle 2\" << endl;\n" +
            "    Circle circle2;\n" +
            "    circle2.setRadius(20.0);\n" +
            "    cout << \"반지름: \" << circle2.getRadius() << endl;\n" +
            "    cout << \"넓이: \" << circle2.getArea() << endl;\n" +
            "    cout << \"둘레: \" << circle2.getPerimeter() << \"\\n\";\n" +
            "\n" +
            "    cout << \"Circle 6\" << endl;\n" +
            "    Circle circle6(circle2);\n" +
            "    cout << \"반지름: \" << circle6.getRadius() << endl;\n" +
            "\n" +
            "    cout << Circle::getCount() << \"\\n\";\n" +
            "    return 0;\n" +
            "};\n";
}