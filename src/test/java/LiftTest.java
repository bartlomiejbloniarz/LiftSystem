import org.junit.Test;
import static org.junit.Assert.*;

public class LiftTest {

    @Test
    public void testCreate(){
        Lift lift = new Lift(0, 0, 10);

        Lift.LiftStatus status = lift.getStatus();
        int currentLevel = status.getCurrentLevel();
        Lift.Direction currentDirection = status.getCurrentDirection();

        assertEquals(currentLevel, 0);
        assertEquals(currentDirection, Lift.Direction.STILL);
        assertFalse(status.isOpen());
        assertEquals(0, lift.getId());
    }

    @Test
    public void testInvalidRequest(){
        Lift lift = new Lift(0, 0, 5);

        assertFalse(lift.requestLevel(6));
        assertFalse(lift.requestLevel(-1));
    }

    @Test
    public void testOneRequest(){
        Lift lift = new Lift(0, 0, 10);

        lift.requestLevel(1);
        Lift.LiftStatus status = lift.getStatus();

        assertEquals(0, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(1, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(1, status.getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.getCurrentDirection());
        assertFalse(status.isOpen());
    }

    @Test
    public void testTwoRequestUpAndDown(){
        Lift lift = new Lift(0, 0, 10);

        lift.requestLevel(2);
        Lift.LiftStatus status = lift.getStatus();

        assertEquals(0, status.getCurrentLevel());
        assertEquals(status.getCurrentDirection(), Lift.Direction.UP);
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(1, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(2, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.requestLevel(0);
        lift.step();
        status = lift.getStatus();

        assertEquals(1, status.getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(0, status.getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(0, status.getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.getCurrentDirection());
        assertFalse(status.isOpen());
    }

    @Test
    public void testTwoRequestUp(){
        Lift lift = new Lift(0, 0, 10);

        lift.requestLevel(0);
        lift.requestLevel(2);
        Lift.LiftStatus status = lift.getStatus();

        assertEquals(0, status.getCurrentLevel(), 0);
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(1, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(2, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.requestLevel(4);
        lift.step();
        status = lift.getStatus();

        assertEquals(3, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(4, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(4, status.getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(4, status.getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.getCurrentDirection());
        assertFalse(status.isOpen());
    }

    @Test
    public void testMultipleRequests(){
        Lift lift = new Lift(0, 5, 10);
        lift.requestLevel(7);
        lift.requestLevel(3);
        lift.requestLevel(5);

        Lift.LiftStatus status = lift.getStatus();

        assertEquals(5, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(6, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(7, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.requestLevel(8);
        lift.step();
        status = lift.getStatus();

        assertEquals(8, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(7, status.getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(6, status.getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(5, status.getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.requestLevel(4);
        lift.step();
        status = lift.getStatus();

        assertEquals(4, status.getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(3, status.getCurrentLevel());
        assertEquals(Lift.Direction.DOWN, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(3, status.getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.requestLevel(4);
        lift.step();
        status = lift.getStatus();

        assertEquals(4, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(4, status.getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.getCurrentDirection());
        assertFalse(status.isOpen());
    }

    @Test
    public void testUpdate(){
        Lift lift = new Lift(0, 0, 10);

        lift.requestLevel(1);
        Lift.LiftStatus status = lift.getStatus();

        assertEquals(0, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertFalse(status.isOpen());

        lift.step();
        status = lift.getStatus();

        assertEquals(1, status.getCurrentLevel());
        assertEquals(Lift.Direction.UP, status.getCurrentDirection());
        assertTrue(status.isOpen());

        lift.update(2, 10);
        status = lift.getStatus();

        assertEquals(2, status.getCurrentLevel());
        assertEquals(Lift.Direction.STILL, status.getCurrentDirection());
        assertFalse(status.isOpen());
    }
}
