# Numbers to English words converter

This simple application provides a console utility to convert numbers into their English words counterpart. The user
can type into the console any number within the range [Long.MIN_VALUE..Long.MAX_VALUE] and optionally it can use comma(,)
as the thousands separator.

There are also a couple of commands he can type on the console, HELP will provide some information about how to use the software
and QUIT will exit the application.


## Pre-requisites
    * Java 8
    * Maven

## Installing / Getting started

Simply clone the repository located [here](https://github.com/juanfelipem/numbers-to-words "Project repository in github")

```shell
git clone https://github.com/juanfelipem/numbers-to-words.git
cd numbers-to-words
mvn clean install
java -jar target/numbers-to-words-autoexec.jar
```

These previous instructions will download a copy of the repository to your machine, bundle the software using Maven and then execute the application.

## Features

* Convert numeric inputs to English words
* Support of thousands separator (,)
* Handle of numbers within the range [Long.MIN_VALUE..Long.MAX_VALUE] and error handing if the numbers are outside of this range.
* Input validations
* Two utility commands HELP and QUIT


## Design considerations

* Use of plain Java:
    This was decided since the problem didn't really needed anything else to be solved, no extra libraries was used to keep the
    code simple and make the executable lightweight.
