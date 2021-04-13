import java.util.HashMap;
import java.util.Map;

public class LiftSystem {

    private final Map<Integer, Lift> lifts;

    LiftSystem(){
        lifts = new HashMap<>();
    }

    boolean addLift(int liftId, int currentLevel, int levelsAmount){
        if (lifts.containsKey(liftId))
            return false;
        Lift tempLift = new Lift(liftId, currentLevel, levelsAmount);
        lifts.put(liftId, tempLift);
        return true;
    }

    boolean removeLift(int liftId){
        if (!lifts.containsKey(liftId))
            return false;
        lifts.remove(liftId);
        return true;
    }

    boolean requestPickup(int liftId, int level) {
        if (!lifts.containsKey(liftId))
            return false;
        return lifts.get(liftId).requestLevel(level);
    }

    boolean requestDestination(int liftId, int level){
        if (!lifts.containsKey(liftId))
            return false;
        return lifts.get(liftId).requestLevel(level);
    }

    boolean updateLift(int liftId, int currentLevel, int levelsAmount){
        if (!lifts.containsKey(liftId))
            return false;
        lifts.get(liftId).update(currentLevel, levelsAmount);
        return true;
    }

    Map<Integer, Lift.LiftStatus> getStatus(){
        HashMap<Integer, Lift.LiftStatus> hashMap = new HashMap<>();
        for (Lift lift: lifts.values()){
            hashMap.put(lift.getId(), lift.getStatus());
        }
        return hashMap;
    }

    void step(){
        for (Lift lift: lifts.values())
            lift.step();
    }
}
