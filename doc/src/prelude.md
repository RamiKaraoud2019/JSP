# Welcome

In this page, we briefly discuss setting up a productive environment for the assignment. The following pages provide an high-level view of the architecture and main components of the provided source code.

Useful links:

 - [Base code](https://github.com/insa-4ir-meta-heuristiques/template-jobshop/)
 - [Documentation (this)](https://insa-4ir-meta-heuristiques.github.io/doc/)
 - [Javadoc](https://insa-4ir-meta-heuristiques.github.io/javadoc/)
 - [Moodle](https://moodle.insa-toulouse.fr/course/view.php?id=1354)
 

## Setting up

Start by accepting the GitHub classroom assignment : 

 - Go to the [invite link](https://classroom.github.com/a/18VUb3aB)
 - Select your name in the list to associate it with you github account (there is a particular link that you can follow if you do not appear in the list). This should create a private repository to which only yourself and the teachers have access. 
 - Clone the repository and get started.

### Working in IntelliJ

For working on this project, we recommend using the IntelliJ-IDEA development environment. It is available in INSA's 
classrooms as well as on `montp.insa-toulouse.fr`.

To import the project in IntelliJ (once IntelliJ is running):

 - Open a new project : `Open project` or `File > Open`
 - Select the `gradle.build` file in the cloned repository. 
 - Select `Open as project`.

To run the program in IntelliJ, you can 

 - Right click on the `src/main/java/jobshop/Main` class in the project view.
 - Select `Run Main.main()`. The program should execute but complain that some arguments are missing.
 - Give it the expected command line arguments : `Run > Edit Configuration`, then fill in the `Program arguments` text box.

### Working on the command line (Gradle)

Compilation instructions are given for Linux. On Windows you can use the `gradlew.bat` script (but you are on your own).

```
❯ ./gradlew build    # Compiles the project
```

The project can be executed directly with `gradle` by specifying the arguments like so :

```
❯ ./gradlew run --args="--solver basic random --instance aaa1 ft"
```

You can also build an executable jar file, and run it with the java command.
This is especially useful if you want to run it on another machine.

```
 # Create a jar file with all dependencies in build/libs/JSP.jar
❯ ./gradlew jar     
# Run the jar file. Only requires a Java Runtime Environment (JRE)
❯ java -jar build/libs/JSP.jar --solver basic --instance ft06
```

