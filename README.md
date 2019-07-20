# clojure-adventure

adventure: A text-based adventure game written in the functional programming language, Clojure

initial: Baby steps in utilizing the immutable data structures in Clojure

In order to run the clojure projects, first make sure clojure is installed. If you are unsure if you have it or not, type on your command line ```lein repl``` and you should see something like this:

```nREPL server started on port 58594 on host 127.0.0.1 - nrepl://127.0.0.1:58594
REPL-y 0.3.5, nREPL 0.2.6
Clojure 1.6.0
OpenJDK 64-Bit Server VM 1.7.0_71-b14
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

user=> 
```

The numbers may be different.  Hit control-D to exit.

In the src folder of either clojure project you will find a folder which contains the file core.clj file. At the beginning of the core.clj file for the initial project, you will see the lines ```ns initial.core``` which creates a namespace for the code. To run the code from the command line using lein, simply use ```lein repl``` and use clojure rules to call the functions in the core.clj

```
% lein repl
user=> (require 'initial.core)
nil
user=> (use 'initial.core)
nil
user=> (socialist-plus 10 10 10 10)
38
user=> (plus 10 20 30)
60
```

This is just an example, you can experiment with the functions in initial or adventure and experience a novel adventure for yourself.


