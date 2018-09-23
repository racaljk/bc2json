# Java bytecode to JSON representation

![](static/logo.png)

**bc2json** could convert `*.class` to `json` representation so that we can use it as **a human readable and also the world's most popular IR(ok, that's json)** for further use.

# Start
Step 1: You can load bytecode from local file
```java
B2Json b2Json = B2Json.fromFilePath("/path/to/Test.class");
```
Optional step 2: and then set some options
```java
b2Json.withOption(Option.PRETTY_PRINTING);
```
Step 3: Got it
```java
String result = b2Json.toJsonString();
System.out.println(result);
// Or persist the result string to disk
b2Json.toJsonFile("test.json");
```

# More
+ Parsing behaviors are based on jvm8 specification. All elements on specification would be parsed into json.
+ You can ignore somewhat meaningless fields according to demands