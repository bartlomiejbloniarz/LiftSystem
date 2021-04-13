# LiftSystem
LiftSystem provides user with an interface simulating a lift control service. Repository also contains tests for 
LiftSystem and Lift classes.

### Algorithm
The system is designed to maintain current direction as long as it can. At first the lift has no direction.
Then the first request sets current direction. When lift completes all requests on its way, then it can either change
direction to opposite if there are any requests or stop and wait for new requests. Terminal advantages of this algorithm
over the FCFS approach is that the lift won't circle between levels possibly ignoring requests on its way 
and also there is lower maximal waiting time, because no matter how many requests there are before, the next 
request will be served in at most `2*numberOfLevels` steps.

### LiftSystem class methods
* `boolean addLift(int liftId, int currentLevel, int levelsAmount)` - adds lift with specified parameters. Impossible 
  arguments may cause unspecified results. Returns 
  false if `liftId` is taken, true otherwise.

* `boolean removeLift(int liftId)` - removes lift specified by `liftId`. Returns
  false if `liftId` is not taken, true otherwise.
* `boolean requestDestination(int liftId, int level)` - send pickup request to lift `liftId` for level `level`. Returns
  true on success, false otherwise.
  
* `boolean requestPickup(int liftId, int level)` - currently works the same as requestDestination. 
Can be improved in more advanced systems by adding an argument `requestDirection`. This way the lift can open
  the doors when it is going in the same direction as the request wants, which can prevent the lift from overcrowding.

* `Map<Integer, Lift.LiftStatus> getStatus()` - returns mapping, that for every `liftId` contains LiftStatus object,
which holds 3 methods:
  * `getCurrentLevel()` - returns the current level of the lift.
  * `getCurrentDirection()` - returns direction in which the lift is currently trying to go. Returned type is 
    Lift.Direction enum with possible values `Direction.UP`, `Direction.DOWN` and `Direction.STILL`.
  * `isOpen()` - return true if the doors are currently open, false otherwise.
    
* `void step()` - performs a step of the simulation.

### Interactive interface
Repository contains a Terminal class, which purpose is to simulate interaction between a user and the system. These are
the commands that can be used in this mode (they work in the same way, as their equivalents in the LiftSystem class,
unless specified otherwise):
* `addLift liftId currentLevel levelsAmount`
* `removeLift liftId`
* `requestDestination liftId level`
* `requestPickup liftId level`
* `status` - print number of lifts, and then for each lift print its id, current level, current direction and 
information if the doors are opened.
  
* `status liftId` - print the status of selected lift.
* `step`
* `step n` - perform `n` steps.
* `exit` - exit the simulation.



### Technical details
The system is written in Java with Maven, so the tests can be run with `mvn test` command. In order to run the
interactive interface use `mvn compile` and then `mvn exec:java -q`.
