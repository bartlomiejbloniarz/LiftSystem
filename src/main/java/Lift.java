public class Lift {
    enum Direction{UP, DOWN, STILL}
    private int currentLevel, highestRequest, lowestRequest, levelsAmount;
    private final int id;
    private boolean[] requests;
    private boolean open;
    private Direction currentDirection;

    Lift(int id, int currentLevel, int levelsAmount){
        this.id = id;
        this.currentLevel = currentLevel;
        this.levelsAmount = levelsAmount;
        requests = new boolean[levelsAmount];
        highestRequest = -1;
        lowestRequest = levelsAmount;
        currentDirection = Direction.STILL;
    }

    boolean requestLevel(int level){
        if (level<0 || level>=levelsAmount)
            return false;
        if (currentLevel == level) {
            openDoors();
            return true;
        }
        if (currentDirection == Direction.STILL){
            if (currentLevel<level)
                currentDirection = Direction.UP;
            else
                currentDirection = Direction.DOWN;
        }
        requests[level] = true;
        if (currentLevel<level)
            highestRequest = Math.max(highestRequest, level);
        else
            lowestRequest = Math.min(lowestRequest, level);
        return true;
    }

    private void openDoors(){
        open = true;
    }

    private void closeDoors(){
        open = false;
    }

    void step(){
        if (open)
            closeDoors();
        if (currentDirection == Direction.UP){
            if (currentLevel < highestRequest)
                currentLevel++;
            else{
                highestRequest = -1;
                if (currentLevel>lowestRequest){
                    currentDirection = Direction.DOWN;
                    currentLevel--;
                }
                else
                    currentDirection = Direction.STILL;
            }
        }
        else if (currentDirection == Direction.DOWN){
            if (currentLevel > lowestRequest)
                currentLevel--;
            else{
                lowestRequest = levelsAmount;
                if (currentLevel<highestRequest){
                    currentDirection = Direction.UP;
                    currentLevel++;
                }
                else
                    currentDirection = Direction.STILL;
            }
        }
        if (requests[currentLevel]) {
            openDoors();
            requests[currentLevel] = false;
        }
    }

    private void resetRequests(){
        requests = new boolean[levelsAmount];
        highestRequest = -1;
        lowestRequest = levelsAmount;
        currentDirection = Direction.STILL;
        open = false;
    }

    void update(int currentLevel, int levelsAmount){
        this.currentLevel = currentLevel;
        this.levelsAmount = levelsAmount;
        resetRequests();
    }

    int getId(){
        return id;
    }

    LiftStatus getStatus(){
        return new LiftStatus(currentLevel, currentDirection, open);
    }

    static class LiftStatus{
        private final int currentLevel;
        private final Direction currentDirection;
        private final boolean open;

        LiftStatus(int currentLevel, Direction currentDirection, boolean open){
            this.currentLevel = currentLevel;
            this.currentDirection = currentDirection;
            this.open = open;
        }

        int getCurrentLevel(){
            return currentLevel;
        }

        Direction getCurrentDirection(){
            return currentDirection;
        }

        boolean isOpen() {
            return open;
        }
    }

}
