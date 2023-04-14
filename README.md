# Manatee API

The following API is made for educational purposes only and does not provide any meaningful functionalities.

## Getting started

This project requires Java 17 or a newer version to be installed on the machine.
For developers, Amazon Coretta or Eclipse Termium are recommended JDKs.

For development purposes only, the relational H2 database is initialized in the local runtime.
On the shutdown, the database is torn down. There is no other option to set a persistent database.

### For Linux users (bash)

```bash
./gradlew build # Generates OpenAPI models, builds the application and runs tests.
./gradlew bootRun # Starts the application on a local network. 
```

### For Windows users

```bash
gradlew build # Generates OpenAPI models, builds the application and runs tests.
gradlew bootRun # Starts the application on a local network. 
```


# Summary
| Question                                 | Answer |
|------------------------------------------|--------|
| Time  spent (h)                          | 8h     |
| Hardest task, (with reasoning)           | getting aquainted with a new codebase |
| Uncompleted tasks, if any                | bonus task (partially done)  |
| Additional dependencies (with reasoning) | -      | 


In summary, describe your overall experience with the topic, what you learned,
and any technical challenges you encountered. Your answer should be
between 50-100 words.

<br>

### SUMMARY:

I haven't previously worked on a project that used OpenAPI specification for code generation.\
For this reason, debugging easy problems took me a lot longer than I assumed.\
I should have saved more time for the bonus task. I ended up creating just a simple table with static data \
because I spent too much time trying to figure out why I couldn't fetch data from localhost.


Also, I somehow didn't see the part in the introduction where it said to include type and interviewer name.\
Adding the missing requirements took me 15 minutes, so at least there's some progression.
