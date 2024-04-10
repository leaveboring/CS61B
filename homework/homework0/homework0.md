### Self-Check 1.26: Confusing

```
I am method 1.
I am method 1.
I am method 2.
I am method 3.
I am method 1.
I am method 1.
I am method 2.
I am method 1.
I am method 2.
I am method 3.
I am method 1.
```

### Exercise 2.5: starTriangle

```JAVA
public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
```

### Self-Check 2.25: numberTotal

```
24 1
22 2
19 3
15 4
10 5
```

### Exercise 3.23: printIndexed

```
public class Main {
    public static void main(String[] args) {
        String s = "ZELDA";
        String t = printIndexed(s);
        System.out.println(t);
    }
    public static String printIndexed(String s) {
        StringBuffer tmp = new StringBuffer();
        int len = s.length() - 1;
        for (char c : s.toCharArray()) {
            tmp.append(c);
            tmp.append(len);
            len--;
        }
        return tmp.toString();
    }
}
```



### Exercise 4.17: stutter

```JAVA
public class Main {
    public static void main(String[] args) {
        String s = "HELLO";
        String t = stutter(s);
        System.out.println(t);
    }
    public static String stutter(String s) {
        StringBuffer tmp = new StringBuffer();
        for (char c : s.toCharArray()) {
            tmp.append(c);
            tmp.append(c);
        }
        return tmp.toString();
    }
}
```

### ifElseMystery1

```
13 21
5 6
6 5
7 11
```

### Exercise 4.19: quadrant

```JAVA
public class Main {
    public static void main(String[] args) {
        System.out.println(quadrant(12.4, 17.8));
        System.out.println(quadrant(-2.3, 3.5));
        System.out.println(quadrant(-15.2, -3.1));
        System.out.println(quadrant(4.5, -42.0));
        System.out.println(quadrant(0.0, 3.4));
    }
    public static int quadrant(double x, double y) {
        if (x > 0 && y > 0) {
            return 1;
        } else if (x < 0 && y > 0) {
            return 2;
        } else if (x < 0 && y < 0) {
            return 3;
        } else if (x > 0 && y < 0) {
            return 4;
        } else return 0;
    }
}
```

