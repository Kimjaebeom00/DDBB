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
}