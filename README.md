# AegonSimpleCalculator

## Development server

Run `./mvnw spring-boot:run` for a dev server.

## Running unit tests

Run `./mvnw clean test` to execute the unit tests.

## Feedback from previous attempt:

- code doesn't compile. Most likely the integrationtest expects a MySQL server to be present, which really shouldn't be the case in a test
- no dao/dto separation.
- API consists of string that is split into pieces instead of a proper request object.
- no default/validating of input. 
- Infinity check instead of proper division by zero checking.
- Everything in service class is in 1 large function.
- Usages of strings instead of proper objects
- tests are very obfuscated due to usage of strings instead of objects.
- Inproper test naming
- run instructions for backend are completely missing 

I feel I've corrected everything, I'm hoping you'll agree with me.
