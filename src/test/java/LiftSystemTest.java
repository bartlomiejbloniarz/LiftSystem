import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class LiftSystemTest {

    @Test
    public void testCreate(){
        LiftSystem liftSystem = new LiftSystem();
        Map<Integer, Lift.LiftStatus> status = liftSystem.getStatus();

        assertTrue(status.isEmpty());
    }

    @Test
    public void testAdd(){
        LiftSystem liftSystem = new LiftSystem();

        assertTrue(liftSystem.addLift(0, 0, 10));
        assertFalse(liftSystem.addLift(0, 1, 5));
        assertTrue(liftSystem.addLift(10, 0, 10));
    }

    @Test
    public void testRemove(){
        LiftSystem liftSystem = new LiftSystem();

        assertTrue(liftSystem.addLift(0, 0, 10));
        assertTrue(liftSystem.addLift(1, 0, 10));
        assertTrue(liftSystem.removeLift(0));
        assertFalse(liftSystem.removeLift(0));

        Map<Integer, Lift.LiftStatus> status = liftSystem.getStatus();

        assertFalse(status.containsKey(0));
        assertTrue(status.containsKey(1));
    }

    @Test
    public void testUpdate(){
        LiftSystem liftSystem = new LiftSystem();

        assertTrue(liftSystem.addLift(0, 0, 10));
        liftSystem.updateLift(0, 1, 5);

        Map<Integer, Lift.LiftStatus> status = liftSystem.getStatus();

        assertEquals(1, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());
        assertFalse(liftSystem.updateLift(10, 0, 10));
    }

    @Test
    public void testInvalidRequests(){
        LiftSystem liftSystem = new LiftSystem();

        assertTrue(liftSystem.addLift(0, 0, 10));

        assertFalse(liftSystem.requestPickup(1, 0));
        assertFalse(liftSystem.requestPickup(0, -1));
        assertFalse(liftSystem.requestPickup(0, 11));

        assertFalse(liftSystem.requestDestination(1, 0));
        assertFalse(liftSystem.requestDestination(0, -1));
        assertFalse(liftSystem.requestDestination(0, 11));
    }

    @Test
    public void testSingleLift(){
        LiftSystem liftSystem = new LiftSystem();

        assertTrue(liftSystem.addLift(0, 5, 10));
        liftSystem.requestPickup(0, 3);
        liftSystem.requestPickup(0, 7);
        liftSystem.requestPickup(0, 5);

        Map<Integer, Lift.LiftStatus> status = liftSystem.getStatus();

        assertEquals(5, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        liftSystem.requestDestination(0, 2);
        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(4, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(3, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        liftSystem.requestDestination(0, 2);
        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(2, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(3, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(4, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(5, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(7, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        liftSystem.requestDestination(0, 6);
        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());
    }

    @Test
    public void testMultipleLifts() {
        LiftSystem liftSystem = new LiftSystem();

        assertTrue(liftSystem.addLift(0, 5, 10));
        assertTrue(liftSystem.addLift(10, 3, 10));
        assertTrue(liftSystem.addLift(20, 7, 10));

        liftSystem.requestPickup(0, 5);
        liftSystem.requestPickup(10, 7);
        liftSystem.requestPickup(20, 5);

        Map<Integer, Lift.LiftStatus> status = liftSystem.getStatus();

        assertEquals(5, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        assertEquals(3, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(10).getCurrentDirection());
        assertFalse(status.get(10).isOpen());

        assertEquals(7, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(20).getCurrentDirection());
        assertFalse(status.get(20).isOpen());

        liftSystem.requestDestination(0, 6);
        status = liftSystem.getStatus();

        assertEquals(5, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        assertEquals(3, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(10).getCurrentDirection());
        assertFalse(status.get(10).isOpen());

        assertEquals(7, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(20).getCurrentDirection());
        assertFalse(status.get(20).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(0).getCurrentDirection());
        assertTrue(status.get(0).isOpen());

        assertEquals(4, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(10).getCurrentDirection());
        assertFalse(status.get(10).isOpen());

        assertEquals(6, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(20).getCurrentDirection());
        assertFalse(status.get(20).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        assertEquals(5, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(10).getCurrentDirection());
        assertFalse(status.get(10).isOpen());

        assertEquals(5, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(20).getCurrentDirection());
        assertTrue(status.get(20).isOpen());

        liftSystem.requestDestination(20, 6);
        liftSystem.requestDestination(20, 4);
        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        assertEquals(6, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(10).getCurrentDirection());
        assertFalse(status.get(10).isOpen());

        assertEquals(4, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(20).getCurrentDirection());
        assertTrue(status.get(20).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        assertEquals(7, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(10).getCurrentDirection());
        assertTrue(status.get(10).isOpen());

        assertEquals(5, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(20).getCurrentDirection());
        assertFalse(status.get(20).isOpen());

        liftSystem.requestDestination(10, 5);
        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        assertEquals(6, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(10).getCurrentDirection());
        assertFalse(status.get(10).isOpen());

        assertEquals(6, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.get(20).getCurrentDirection());
        assertTrue(status.get(20).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        assertEquals(5, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.get(10).getCurrentDirection());
        assertTrue(status.get(10).isOpen());

        assertEquals(6, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(20).getCurrentDirection());
        assertFalse(status.get(20).isOpen());

        liftSystem.step();
        status = liftSystem.getStatus();

        assertEquals(6, status.get(0).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(0).getCurrentDirection());
        assertFalse(status.get(0).isOpen());

        assertEquals(5, status.get(10).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(10).getCurrentDirection());
        assertFalse(status.get(10).isOpen());

        assertEquals(6, status.get(20).getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.get(20).getCurrentDirection());
        assertFalse(status.get(20).isOpen());

    }
}
